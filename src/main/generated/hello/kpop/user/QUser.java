package hello.kpop.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 1487056610L;

    public static final QUser user = new QUser("user");

    public final NumberPath<Integer> followCnt = createNumber("followCnt", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> joinDate = createDateTime("joinDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastModDate = createDateTime("lastModDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> lockCnt = createNumber("lockCnt", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> lockLastDate = createDateTime("lockLastDate", java.time.LocalDateTime.class);

    public final ComparablePath<Character> lockYn = createComparable("lockYn", Character.class);

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final StringPath profileImageUrl = createString("profileImageUrl");

    public final StringPath refreshToken = createString("refreshToken");

    public final StringPath socialId = createString("socialId");

    public final EnumPath<SocialType> socialType = createEnum("socialType", SocialType.class);

    public final StringPath userEmail = createString("userEmail");

    public final StringPath userName = createString("userName");

    public final StringPath userStatCode = createString("userStatCode");

    public final EnumPath<Role> userType = createEnum("userType", Role.class);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

