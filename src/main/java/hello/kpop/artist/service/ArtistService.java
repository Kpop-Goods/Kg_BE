package hello.kpop.artist.service;

import hello.kpop.agency.Agency;
import hello.kpop.agency.repository.AgencyRepository;
import hello.kpop.agency.status.DelStatus;
import hello.kpop.artist.Artist;
import hello.kpop.artist.ArtistSpecification;
import hello.kpop.artist.dto.ArtistDto;
import hello.kpop.artist.dto.ArtistResponseDto;
import hello.kpop.artist.dto.SuccessResponseDto;
import hello.kpop.artist.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final AgencyRepository agencyRepository;

    //아티스트 등록
    @Transactional
    public ArtistResponseDto saveArtist(ArtistDto requestDto, Long id) throws Exception {

        //아티스트 중복 체크
        if(artistRepository.findByArtistName(requestDto.getArtistName()).isPresent()) {
            throw new Exception("이미 존재하는 아티스트 입니다.");
        }

        Agency agency = agencyRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("소속사 ID가 존재하지 않습니다."));

        Artist artist = new Artist(requestDto, agency);

        //등록 시 삭제여부는 N으로 설정
        artist.updateDelYN(DelStatus.DEFAULT);

        artistRepository.save(artist);

        return new ArtistResponseDto(artist);
    }

    //페이징 + 아티스트 전체 조회
    @Transactional(readOnly = true)
    public Page<Artist> pageArtistList(int page, int size) {
        //삭제여부 "Y"인 데이터 가져옴
        List<Artist> artists = artistRepository.findByDelYN("Y");

        //전체 데이터 조회
        Pageable pageable = PageRequest.of(page, size, Sort.by("artistId").descending());
        Page<Artist> artistPage = artistRepository.findAll(pageable);

        //"Y"인 데이터를 제외한 후 반환
        List<Artist> filteredArtists = artistPage.getContent().stream()
                .filter(artist -> !artists.contains(artist))
                .collect(Collectors.toList());

        return new PageImpl<>(filteredArtists, pageable, artistPage.getTotalElements());
    }

    //아티스트 전체 조회
    @Transactional
    public List<ArtistResponseDto> selectArtistList() {

        //삭제여부 "Y"인 데이터 가져옴
        List<Artist> artists = artistRepository.findByDelYN("Y");

        if(artists.isEmpty()) { //"Y"인 데이터가 없을 경우 전체 데이터 조회
            return artistRepository.findAll().stream().map(ArtistResponseDto::new).toList();
        } else {
            //"Y"인 데이터를 제외한 후 반환
            List<ArtistResponseDto> responseDtoList = artistRepository.findAll()
                    .stream()
                    .filter(artist -> !artists.contains(artist))
                    .map(ArtistResponseDto::new)
                    .collect(Collectors.toList());
            return responseDtoList;
        }
    }

    //선택한 아티스트 정보 상세 조회
    @Transactional
    public ArtistResponseDto selectArtistOne(Long id) {
        Artist artist = artistRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아티스트 ID가 존재하지 않습니다."));

        //만약 해당 장소가 삭제되었다면("Y"로 표시되어 있다면) 예외를 던짐
        if(artist.getArtistId().equals(id) && artist.getDelYN().equals("Y")) {
            throw new IllegalArgumentException("삭제된 아티스트입니다.");
        }

        return new ArtistResponseDto(artist);
    }

    //선택한 아티스트 정보 수정
    @Transactional
    public ArtistResponseDto updateArtist(Long id, ArtistDto requestDto) throws Exception {
        Artist artist = artistRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아티스트 ID가 존재하지 않습니다."));

        //만약 해당 장소가 삭제되었다면("Y"로 표시되어 있다면) 예외를 던짐
        if(artist.getArtistId().equals(id) && artist.getDelYN().equals("Y")) {
            throw new IllegalArgumentException("삭제된 아티스트입니다.");
        }

        artist.update(requestDto);
        return new ArtistResponseDto(artist);
    }

    //선택한 아티스트 삭제
    @Transactional
    public SuccessResponseDto deleteArtist(Long id) throws Exception {
        Artist artist = artistRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아티스트 ID가 존재하지 않습니다."));

        artist.updateDelYN(DelStatus.DELETE);

        return new SuccessResponseDto(true);
    }

    //아티스트 검색했을 시 조회
    @Transactional
    public List<ArtistResponseDto> searchArtist(String artistName) {
        Specification<Artist> spec = (root, query, criteriaBuilder) -> null;

        if(artistName != null) {
            spec = spec.and(ArtistSpecification.equalArtistName(artistName));
        }

        // 'delYN'이 'Y'인 데이터를 제외하기 위한 스펙 추가
        spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("delYN"), "Y"));

        return artistRepository.findAll(spec).stream().map(ArtistResponseDto::new).toList();
    }
}
