package hello.kpop.calendar;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCalendar is a Querydsl query type for Calendar
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCalendar extends EntityPathBase<Calendar> {

    private static final long serialVersionUID = 233079490L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCalendar calendar = new QCalendar("calendar");

    public final hello.kpop.artist.QArtist artist;

    public final DateTimePath<java.util.Date> end = createDateTime("end", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath link = createString("link");

    public final StringPath meta = createString("meta");

    public final StringPath name = createString("name");

    public final DateTimePath<java.util.Date> start = createDateTime("start", java.util.Date.class);

    public QCalendar(String variable) {
        this(Calendar.class, forVariable(variable), INITS);
    }

    public QCalendar(Path<? extends Calendar> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCalendar(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCalendar(PathMetadata metadata, PathInits inits) {
        this(Calendar.class, metadata, inits);
    }

    public QCalendar(Class<? extends Calendar> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.artist = inits.isInitialized("artist") ? new hello.kpop.artist.QArtist(forProperty("artist"), inits.get("artist")) : null;
    }

}

