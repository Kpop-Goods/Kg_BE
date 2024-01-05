package hello.kpop.goods.repository;

import hello.kpop.goods.dto.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsRepository extends JpaRepository<Goods, Long>  {


}
