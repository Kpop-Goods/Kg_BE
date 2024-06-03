package hello.kpop.artist;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QArtist is a Querydsl query type for Artist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QArtist extends EntityPathBase<Artist> {

    private static final long serialVersionUID = 1549796514L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QArtist artist = new QArtist("artist");

    public final hello.kpop.socialing.common.entitiy.QBase _super = new hello.kpop.socialing.common.entitiy.QBase(this);

    public final hello.kpop.agency.QAgency agency;

    public final StringPath artistCd = createString("artistCd");

    public final NumberPath<Long> artistId = createNumber("artistId", Long.class);

    public final StringPath artistName = createString("artistName");

    public final StringPath comment = createString("comment");

    public final StringPath delYN = createString("delYN");

    public final NumberPath<Integer> followCnt = createNumber("followCnt", Integer.class);

    public final StringPath gender = createString("gender");

    public final ListPath<String, StringPath> imageUrls = this.<String, StringPath>createList("imageUrls", String.class, StringPath.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> mod_dt = _super.mod_dt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDt = _super.regDt;

    public final StringPath regId = createString("regId");

    public final StringPath unitYN = createString("unitYN");

    public QArtist(String variable) {
        this(Artist.class, forVariable(variable), INITS);
    }

    public QArtist(Path<? extends Artist> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QArtist(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QArtist(PathMetadata metadata, PathInits inits) {
        this(Artist.class, metadata, inits);
    }

    public QArtist(Class<? extends Artist> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.agency = inits.isInitialized("agency") ? new hello.kpop.agency.QAgency(forProperty("agency")) : null;
    }

}

