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

## database를 활용하여 레이스컨디션 해결

Database 를 활용하여 레이스컨디션 해결해보기
1. Optimistic Lock  
lock 을 걸지않고 문제가 발생할 때 처리합니다.
대표적으로 version column 을 만들어서 해결하는 방법이 있습니다.

2. Pessimistic Lock (exclusive lock)  
다른 트랜잭션이 특정 row 의 lock 을 얻는것을 방지합니다.
A 트랜잭션이 끝날때까지 기다렸다가 B 트랜잭션이 lock 을 획득합니다.
특정 row 를 update 하거나 delete 할 수 있습니다.
일반 select 는 별다른 lock 이 없기때문에 조회는 가능합니다.

3. named Lock 활용하기  
이름과 함께 lock 을획득합니다. 해당 lock 은 다른세션에서 획득 및 해제가 불가능합니다.  

- pessimistic lock / named lock 차이점  
pessimistic lock은 low나 table 단위로 건다. named lock은 meta data에 lock을 건다.



참고문헌
https://dev.mysql.com/doc/refman/8.0/en/glossary.html#glos_exclusive_lock
https://dev.mysql.com/doc/refman/8.0/en/innodb-locking.html
https://dev.mysql.com/doc/refman/8.0/en/locking-functions.html


