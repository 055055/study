package com.example.stock.service;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockService {
    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    //    @Transactional
//    @Transactional시 실제 트랜잭션 종료시키기 전에 다른 메서드가 커밋하기 전 데이터 (syncronized메서드에 접근해서 얻은 값)에 접근해서 데이터를 업데이트 시켜서 데이터가 꼬일 수 있다.
    public synchronized void decrease_syncronized(Long id, Long quantity) {
        //get stock
        Stock stock = stockRepository.findById(id).orElseThrow();
        //재고 감소
        stock.decrease(quantity);
        //저장
        stockRepository.saveAndFlush(stock);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void decrease(Long id, Long quantity) {
        //get stock
        Stock stock = stockRepository.findById(id).orElseThrow();
        //재고 감소
        stock.decrease(quantity);
        //저장
        stockRepository.saveAndFlush(stock);
    }
//    jpa의 네이티브 쿼리를 사용하고 동일한 데이터 소스 사용할것
//    실제로 사용할 때는 데이터 소스 분리해서 사용할 것
//    같은 데이터 소스를 사용하면 커넥트풀이 부족해질 수 있다.
//    그렇기 때문에 실무에서는 데이터 소스를 분리하는 걸 추천한다.
//    실제로는 네이티브 쿼리 jpa에서 쓰는 것보다 jdbc사용 권장(별도 데이터소스로)
//    그리고 stockService는 부모의 트랜잭션과 별도로 실행해야되서 propagation을 변경
}
