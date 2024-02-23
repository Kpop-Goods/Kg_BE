package hello.kpop.goods.service;


import hello.kpop.goods.dto.Goods;
import hello.kpop.goods.dto.GoodsRequestDto;
import hello.kpop.goods.dto.GoodsResponseDto;
import hello.kpop.goods.repository.GoodsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GoodsService {


    private final GoodsRepository goodsRepository;


    public List<Goods> findGoodslist(){
        return  goodsRepository.findAll();
    }

    public Goods findGoods(Long id){
        return goodsRepository.findById(id).orElseThrow();
    }

    public GoodsResponseDto saveGoods(GoodsRequestDto requestDto){
      return null;
    }

    public void deleteGoods(Long id){
        goodsRepository.deleteById(id);
    }




}
