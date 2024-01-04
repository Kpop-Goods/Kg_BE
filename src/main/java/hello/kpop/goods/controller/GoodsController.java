package hello.kpop.goods.controller;



import hello.kpop.goods.dto.Goods;
import hello.kpop.goods.dto.GoodsRequestDto;
import hello.kpop.goods.dto.GoodsResponseDto;
import hello.kpop.goods.service.GoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/goods")
public class GoodsController {


    private final GoodsService goodsService;


    @GetMapping
    public List<Goods> getAllGoods() {
        return goodsService.findGoodslist();
    }


    @PostMapping
    public ResponseEntity<?> createGoods(@RequestBody GoodsRequestDto requestDto) {
        GoodsResponseDto saveGoods = goodsService.saveGoods(requestDto);
        return new ResponseEntity<>(saveGoods, HttpStatus.CREATED);
    }



    @PutMapping("/{id}")
    public ResponseEntity<?> updateGoods(@PathVariable("id") Long id, @RequestBody GoodsRequestDto requestDto){

        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGoods(@PathVariable("id") Long id){
        goodsService.deleteGoods(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}




