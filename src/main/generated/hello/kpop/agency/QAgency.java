package hello.kpop.agency;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAgency is a Querydsl query type for Agency
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAgency extends EntityPathBase<Agency> {

    private static final long serialVersionUID = -700198174L;

    public static final QAgency agency = new QAgency("agency");

    public final hello.kpop.socialing.common.entitiy.QBase _super = new hello.kpop.socialing.common.entitiy.QBase(this);

    public final StringPath agencyCd = createString("agencyCd");

    public final NumberPath<Long> agencyId = createNumber("agencyId", Long.class);

    public final StringPath agencyInfo = createString("agencyInfo");

    public final StringPath agencyName = createString("agencyName");

    public final ListPath<hello.kpop.artist.Artist, hello.kpop.artist.QArtist> artists = this.<hello.kpop.artist.Artist, hello.kpop.artist.QArtist>createList("artists", hello.kpop.artist.Artist.class, hello.kpop.artist.QArtist.class, PathInits.DIRECT2);

    public final NumberPath<Integer> countryCd = createNumber("countryCd", Integer.class);

    public final StringPath delYN = createString("delYN");

    public final DatePath<java.time.LocalDate> establishDt = createDate("establishDt", java.time.LocalDate.class);

    public final ListPath<String, StringPath> imageUrls = this.<String, StringPath>createList("imageUrls", String.class, StringPath.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> mod_dt = _super.mod_dt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDt = _super.regDt;

    public final StringPath regId = createString("regId");

    public QAgency(String variable) {
        super(Agency.class, forVariable(variable));
    }

    public QAgency(Path<? extends Agency> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAgency(PathMetadata metadata) {
        super(Agency.class, metadata);
    }

}

