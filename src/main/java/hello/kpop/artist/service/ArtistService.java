package hello.kpop.artist.service;

import hello.kpop.artist.Artist;
import hello.kpop.artist.dto.ArtistDto;
import hello.kpop.artist.dto.ArtistResponseDto;
import hello.kpop.artist.dto.SuccessResponseDto;
import hello.kpop.artist.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;

    //아티스트 등록
    @Transactional
    public void saveArtist(ArtistDto requestDto) throws Exception {

        if(artistRepository.findByArtistName(requestDto.getArtistName()).isPresent()) {
            throw new Exception("이미 존재하는 아티스트입니다.");
        }

        Artist artist = new Artist(requestDto);
        artistRepository.save(artist);
    }

    //아티스트 전체 조회
    @Transactional
    public List<ArtistResponseDto> selectArtistList() {
        return artistRepository.findAll().stream().map(ArtistResponseDto::new).toList();
    }

    //선택한 아티스트 정보 조회
    @Transactional
    public ArtistResponseDto selectArtistOne(Long id) {
        return artistRepository.findById(id).map(ArtistResponseDto::new).orElseThrow(
                () -> new IllegalArgumentException("아티스트 ID가 존재하지 않습니다."));
    }

    //선택한 아티스트 정보 수정
    @Transactional
    public ArtistResponseDto updateArtist(Long id, ArtistDto requestDto) throws Exception {
        Artist artist = artistRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아티스트 ID가 존재하지 않습니다."));

        artist.update(requestDto);
        return new ArtistResponseDto(artist);
    }

    //선택한 아티스트 삭제
    @Transactional
    public SuccessResponseDto deleteArtist(Long id) throws Exception {
        Artist artist = artistRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아티스트 ID가 존재하지 않습니다."));

        artistRepository.deleteById(id);
        return new SuccessResponseDto(true);
    }

}
