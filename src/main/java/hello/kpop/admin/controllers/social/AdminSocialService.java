package hello.kpop.admin.controllers.social;


import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.kpop.artist.QArtist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminSocialService {

    private final JPAQueryFactory jpaQueryFactory;

    public List<String> indexName(){
        QArtist artist = QArtist.artist;

       List<String > items = jpaQueryFactory
               .select(artist.artistName)
               .from(artist)
               .where()
               .fetch();

       return items;


    }
}
