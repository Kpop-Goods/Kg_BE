package hello.kpop.place;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class PlaceSpecification {

    //이벤트명 검색
    public static Specification<Place> equalEventName(String eventName) {
        return new Specification<Place>() {
            @Override
            public Predicate toPredicate(Root<Place> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get("eventName"), "%" + eventName + "%");            }
        };
    }

    //이벤트 카테고리 코드 검색
    public static Specification<Place> equalEventCategoryCode(Integer eventCategoryCd) {
        return new Specification<Place>() {
            @Override
            public Predicate toPredicate(Root<Place> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("eventCategoryCd"), eventCategoryCd);
            }
        };
    }

    //아티스트 검색
    public static Specification<Place> equalArtist(Long artistId) {
        return new Specification<Place>() {
            @Override
            public Predicate toPredicate(Root<Place> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("artist").get("artistId"), artistId);
            }
        };
    }

    //소속사 검색
    public static Specification<Place> equalAgency(Long agencyId) {
        return new Specification<Place>() {
            @Override
            public Predicate toPredicate(Root<Place> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("artist").get("agency").get("agencyId"), agencyId);
            }
        };
    }

    //시작날짜만 검색
    public static Specification<Place> onlyStartDate(LocalDate startDate) {
        return new Specification<Place>() {
            @Override
            public Predicate toPredicate(Root<Place> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), startDate);
            }
        };
    }

    //마감날짜만 검색
    public static Specification<Place> onlyEndDate(LocalDate endDate) {
        return new Specification<Place>() {
            @Override
            public Predicate toPredicate(Root<Place> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("endDate"), endDate);
            }
        };
    }

    //시작날짜, 마감날짜 검색
    public static Specification<Place> betweenStartDate(LocalDate startDate, LocalDate endDate) {
        return new Specification<Place>() {
            @Override
            public Predicate toPredicate(Root<Place> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.between(root.get("startDate"), startDate, endDate);
            }
        };
    }
}
