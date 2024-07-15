package hello.kpop.goods;

import hello.kpop.goods.dto.Goods;
import hello.kpop.place.Place;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class GoodsSpecification {

    //굿즈명 검색
    public static Specification<Goods> equalGoodsName(String goodsName) {
        return new Specification<Goods>() {
            @Override
            public Predicate toPredicate(Root<Goods> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get("goodsName"), "%" + goodsName + "%"); }
        };
    }

    //이벤트 카테고리 코드 검색
    public static Specification<Goods> equalGoodsCategoryCode(String goodsCategoryCode) {
        return new Specification<Goods>() {
            @Override
            public Predicate toPredicate(Root<Goods> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("goodsCategoryCode"), goodsCategoryCode);
            }
        };
    }
}
