package hello.kpop.socialing.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSocialingView is a Querydsl query type for SocialingView
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSocialingView extends EntityPathBase<SocialingView> {

    private static final long serialVersionUID = 1844679540L;

    public static final QSocialingView socialingView = new QSocialingView("socialingView");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> uId = createNumber("uId", Integer.class);

    public QSocialingView(String variable) {
        super(SocialingView.class, forVariable(variable));
    }

    public QSocialingView(Path<? extends SocialingView> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSocialingView(PathMetadata metadata) {
        super(SocialingView.class, metadata);
    }

}

