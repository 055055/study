package com.example.stock.service;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockService {
    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

//    @Transactional
//    @Transactional시 실제 트랜잭션 종료시키기 전에 다른 메서드가 커밋하기 전 데이터 (syncronized메서드에 접근해서 얻은 값)에 접근해서 데이터를 업데이트 시켜서 데이터가 꼬일 수 있다.
    public synchronized void decrease(Long id, Long quantity) {
        //get stock
        Stock stock = stockRepository.findById(id).orElseThrow();
        //재고 감소
        stock.decrease(quantity);
        //저장
        stockRepository.saveAndFlush(stock);
    }
}
