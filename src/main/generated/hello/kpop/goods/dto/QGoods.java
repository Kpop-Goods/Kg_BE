package hello.kpop.goods.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGoods is a Querydsl query type for Goods
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGoods extends EntityPathBase<Goods> {

    private static final long serialVersionUID = 2133111171L;

    public static final QGoods goods = new QGoods("goods");

    public final hello.kpop.socialing.common.entitiy.QBase _super = new hello.kpop.socialing.common.entitiy.QBase(this);

    public final StringPath artistCd = createString("artistCd");

    public final NumberPath<Integer> count = createNumber("count", Integer.class);

    public final StringPath delYN = createString("delYN");

    public final NumberPath<Integer> goodsCategoryCd = createNumber("goodsCategoryCd", Integer.class);

    public final StringPath goodsContent = createString("goodsContent");

    public final NumberPath<Long> goodsId = createNumber("goodsId", Long.class);

    public final StringPath goodsLink = createString("goodsLink");

    public final StringPath goodsName = createString("goodsName");

    public final ListPath<String, StringPath> imageUrls = this.<String, StringPath>createList("imageUrls", String.class, StringPath.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> mod_dt = _super.mod_dt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDt = _super.regDt;

    public final StringPath regId = createString("regId");

    public QGoods(String variable) {
        super(Goods.class, forVariable(variable));
    }

    public QGoods(Path<? extends Goods> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGoods(PathMetadata metadata) {
        super(Goods.class, metadata);
    }

}

