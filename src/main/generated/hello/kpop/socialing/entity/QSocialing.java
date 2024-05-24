package hello.kpop.socialing.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSocialing is a Querydsl query type for Socialing
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSocialing extends EntityPathBase<Socialing> {

    private static final long serialVersionUID = -996240337L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSocialing socialing = new QSocialing("socialing");

    public final hello.kpop.socialing.common.entitiy.QBase _super = new hello.kpop.socialing.common.entitiy.QBase(this);

    public final hello.kpop.artist.QArtist artist;

    public final StringPath chat_url = createString("chat_url");

    public final EnumPath<hello.kpop.socialing.SocialingStatus> del_yn = createEnum("del_yn", hello.kpop.socialing.SocialingStatus.class);

    public final DatePath<java.time.LocalDate> end_date = createDate("end_date", java.time.LocalDate.class);

    public final NumberPath<Integer> follow = createNumber("follow", Integer.class);

    public final NumberPath<Integer> like = createNumber("like", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> mod_dt = _super.mod_dt;

    public final NumberPath<Integer> quota = createNumber("quota", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDt = _super.regDt;

    public final StringPath social_place = createString("social_place");

    public final StringPath socialing_content = createString("socialing_content");

    public final StringPath socialing_name = createString("socialing_name");

    public final NumberPath<Long> socialingId = createNumber("socialingId", Long.class);

    public final DatePath<java.time.LocalDate> start_date = createDate("start_date", java.time.LocalDate.class);

    public final StringPath type = createString("type");

    public final hello.kpop.user.QUser user;

    public final NumberPath<Integer> view = createNumber("view", Integer.class);

    public QSocialing(String variable) {
        this(Socialing.class, forVariable(variable), INITS);
    }

    public QSocialing(Path<? extends Socialing> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSocialing(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSocialing(PathMetadata metadata, PathInits inits) {
        this(Socialing.class, metadata, inits);
    }

    public QSocialing(Class<? extends Socialing> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.artist = inits.isInitialized("artist") ? new hello.kpop.artist.QArtist(forProperty("artist"), inits.get("artist")) : null;
        this.user = inits.isInitialized("user") ? new hello.kpop.user.QUser(forProperty("user")) : null;
    }

}

