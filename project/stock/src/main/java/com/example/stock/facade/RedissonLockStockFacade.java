package com.example.stock.facade;

import com.example.stock.service.StockService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedissonLockStockFacade {
    private RedissonClient redissonClient;

    private StockService stockService;

    public RedissonLockStockFacade(RedissonClient redissonClient, StockService stockService) {
        this.redissonClient = redissonClient;
        this.stockService = stockService;
    }

    public void decrease(Long key, Long quantity){
        RLock lock = redissonClient.getLock(key.toString());

        try {
            //몇 초 동안 점유할 것인지 설정 후 락획득 시도
            boolean available = lock.tryLock(10, 1, TimeUnit.SECONDS);

            if(!available){
                System.out.println("lock 획득 실패");
                return;
            }
            stockService.decrease(key, quantity);
        } catch (Exception e) {
            System.out.println("e = " + e);
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
