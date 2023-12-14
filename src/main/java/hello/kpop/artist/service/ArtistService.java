package hello.kpop.artist.service;

import hello.kpop.artist.Artist;
import hello.kpop.artist.dto.ArtistDto;
import hello.kpop.artist.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;

    @Transactional
    public void signUp(ArtistDto artistDto) throws Exception {

        Artist artist1 = Artist.builder()
                .artistImg(artistDto.getArtistImg())
                .artistContent(artistDto.getArtistContent())
                .artistName(artistDto.getArtistName())
                .artistCount(artistDto.getArtistCount())
                .build();

        artistRepository.save(artist1);
    }

}
