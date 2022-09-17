## RACE Condition 
레이스 컨디션(Race Condition) 이란 ?
둘 이상의 스레드가 공유 데이터에 액세스할 수 있고 동시에 변경하려고 할 때 발생하는 문제

둘 이상의 스레드 : 요청
공유 데이터 : 재고 데이터
동시에 변경하려고 할 때 : 업데이트 할때
발생하는 문제 : 값이 정상적으로 바뀌지 않는 문제


해결방법
하나의 스레드만 데이터에 액세스 할 수 있도록 한다.

## java synchronized 문제점
서버가 1대일때는 되는듯싶으나 여러대의 서버를 사용하게되면 사용하지 않았을때와 동일한 문제가 발생된다.

인스턴스단위로 thread-safe 이 보장이 되고, 여러서버가 된다면 여러개의 인스턴스가 있는것과 동일기때문입니다.

- spring @Transactional 에서 사용시 주의 사항
  - spring에서 @Transactional 어노테이션이 있으면 spring aop로 인해 syncronized가 실패한다.
  - 실제 트랜잭션을 종료시키기 전에 다른 메서드가 커밋하기 전 데이터(syncronized 메서드에 접근해서 얻은 값)에 접근해서 데이터를 업데이트 시켜서 데이터가 꼬일 수 있다. 

## database를 활용하여 레이스컨디션 해결

Database 를 활용하여 레이스컨디션 해결해보기
1. Optimistic Lock  
lock 을 걸지않고 문제가 발생할 때 처리합니다.
대표적으로 version column 을 만들어서 해결하는 방법이 있습니다.
    - 장점
      - 별도의 락을 잡지 않아서 pessimistic lock 보다 성능상 이점이 있다.
    - 단점
      - 업데이트가 실패했을 대 재시도 로직을 개발자가 직접해줘야 한다.  
충돌이 빈번하게 일어날 것이라 본다면 pessimistic lock/ 빈번하지 않다면 optimistic lock을 사용하자

2. Pessimistic Lock (exclusive lock)  
다른 트랜잭션이 특정 row 의 lock 을 얻는것을 방지합니다.
A 트랜잭션이 끝날때까지 기다렸다가 B 트랜잭션이 lock 을 획득합니다.
특정 row 를 update 하거나 delete 할 수 있습니다.
일반 select 는 별다른 lock 이 없기때문에 조회는 가능합니다.  
query의 for update 이부분이 락을 걸고 데이터를 가지고 오는 부분이다.

    - 장점
      - 충돌이 번번하게 일어난다면 optimistic lock보다 성능이 좋을 수 있다
      - 락을 통해 업데이트를 제어해서 데이터 정합성보장
    - 단점
      - 별도의 락을 잡아서 성능 감소가 있을 수 있다

3. named Lock 활용하기  
이름과 함께 lock을 획득합니다. 즉 이름을 가진 메타데이터 락이다. 테이블말고 별도 공간에 락을 건다.  
해당 lock 은 다른세션에서 획득 및 해제가 불가능하다.
이름을 가진 락을 획득한 후 해제할 때 까지 다른 세션은 이 락을 획득 못한다.  
>>모든 Lock이 해제를 하지 않는다면 데이터소스가 부족해지는 현상이 나타날 수 있다.  
>>그래서 named lock은 데이터소스를 분리해야 한다. 커넥션을 2개를 사용하기 때문에 lock 획득에 필요한 Connection 1개, transaction(로직)에 필요한 커넥션 1개
>>lock을 사용하는 DB와 일반 DB를 분리하는 것도 하나의 방법이다. 그러나 일반적으로 동일한 DB에서 2개의 커넥션 풀을 사용할 때는  
>>A커넥션 풀은 lock 전용, B커넥션 풀은 로직 전용으로 사용하면 된다. 

**실무에서는 lock사용 DB와 일반 DB를 분리하는 걸 추천한다. 또한 JPA native query를 사용해야 해서 jdbc를 사용하는게 나을 수 있다.(별도 데이터소스 구성)** 

mysql 기준
   - getLock: 락 획득
   - releaseLock: 락 해제

트랜잭션이 종료될 때 락이 자동으로 해제되지 않아서 별도 명령으로 해제 해주거나 선점시간이 끝나야 해제된다.  
- 장점
  - 주로 분산락 구현할 때 사용
  - pessimisticLock은 타임아웃을 구현하기 힘들지만 namedLock은 구현하기 쉽다.
  - 데이터 삽입시 정합성 맞출 때 사용
- 단점
  - 실제 사용시 구현이 복잡할 수 있다 그렇기에 트랜잭션 종료시 락 해제와 세션 관리를 잘해줘야 한다.  

- pessimistic lock / named lock 차이점  
pessimistic lock은 low나 table 단위로 건다. named lock은 meta data에 lock을 건다.


참고문헌
https://dev.mysql.com/doc/refman/8.0/en/glossary.html#glos_exclusive_lock
https://dev.mysql.com/doc/refman/8.0/en/innodb-locking.html
https://dev.mysql.com/doc/refman/8.0/en/locking-functions.html  

## Redis를 이용해보기

### redis를 활용하여 동시성 문제 해결하는 대표적인 라이브러리

- lettuce  
lettuce는 setnx를 사용하여 락을 획득한다. setnx는 spin lock을 사용한다.
    - setnx 명령어를 활용하여 분산락 구현
    - spin lock 방식
      - 락을 획득하려는 쓰레드가 락을 사용할 수 있는지 반복적으로 확인하며 락을 획득하는 방식
- redisson
    - pub-sub 기반으로 lock 구현 제공
      - channel을 만들고 lock을 점유하고 있는 쓰레드가 락을 획득하려고 대기하는 쓰레드에게 해제를 알려주고 안내를 받은 쓰레드가 락을 획득하는 방식
      - lettuce와 다르게 별도의 retry 방식을 작성하지 않아도 된다.

docker image
- docker pull redis
- docker run —name redis-lock -d -p 6379:6379 redis

1. lettuce  
mysql의 namedlock과 비슷하다. 다른 점은 세션관리에 신경을 안써도되고 redis를 사용한다는 점이다.  
구현이 간단하다는 장점이 있으나 spin lock 방식이므로 redis에 부하를 줄 수 있다. 그래서 락 획득 재시도에 텀을 주는 것이 좋다.

2. redisson

