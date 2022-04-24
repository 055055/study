# 싱글턴 패턴

## 정의 

싱글턴 패턴은 해당 클래스의 인스턴스가 하나만 만들어지고, 어디서든지 그 인스턴스에 접근할 수 있도록 하기 위한 패턴이다. 


## 사용 용도 예

- 객체 중에 하나만 있으면 되는 것들 
 예) 스레드 풀, 캐시, 대화상자, 사용자 설정이라든가 레지스트리 설정을 처리하는 객체, 로그 기록용 객체, 프린터나 그래픽 카드 같은 
디바이스를 위한 디바이스 드라이버 등

- 이런 형식의 객체를 쓸 때는 인스턴스를 두 개 이상 만들게 되면 프로그램이 이상하게 돌아가던지 자원을 불필요하게 잡아먹는다든 가 결과에 일관성이 없어지는 것 같은 심각한 문제가 생길 수 있다. 

## 멀티스레딩 문제 해결

~~~
public class Singleton {
    private static Singleton uniqueInstance;

    private Singleton() {

    }

    public static synchronized Singleton getInstance() {
        if (uniqueInstance == null) {
                    uniqueInstance = new Singleton();
                }
        }
        return uniqueInstance;
    }
}
~~~

getInstance()를 동기화 시킴으로 인해 두 스레드가 이 메소드를 동시에 실행시키는 것으 방지

** 동기화를 해서 문제가 해결되지만 속도 문제가 생김. 불필요한 오버헤드 증가 발생  
일단 uniqueInstance 변수에 Singleton 인스턴스를 대입하고 나면 굳이 이 메소드를 동기화된 상태로 유지시킬 필요가 없기 때문이다.

### 더 효율적인 방법은?

1. getInstance()의 속도가 그리 중요하지 않다면 syncronized 상태로 둔다.  
메소드를 동기화하면 성능이 100정도 저하되지만 큰 부담을 주지 않는다면 그대로 둔다. 그러나 만약 getInstance()가 애플리케이션에서 병목으로 작용한다면 다른 
 방법을 고려해야 한다.
   

2. 인스턴스를 필요할 때 생성하지 말고, 청므부터 만들어 버린다.  
애플리케이션에서 반드시 Singleton의 인스턴스를 생성하고, 그 인스턴스를 항상 사용한다면, 또는 인스턴스를 실행중에 수시로 만들고 관리하기가 성가시다면 아래와 같이 처음부터 Singleton 인스턴스를 만들어버리는 것도 좋은 방법이다.

~~~
public class Singleton {
    private static Singleton uniqueInstance = new Singleton();

    private Singleton() {

    }

    public static Singleton getInstance() {
        return uniqueInstance;
    }
}
~~~

이런 접근법을 사용하면 클래스가 로딩될 때 JVM에서 Singleton의 유일한 인스턴스를 생성해 준다.
JVM에서 유일한 인스턴스를 생성하기 전에는 그 어떤 스레드도 uniqueInstance 정적 변수에 접근할 수 없다.

3. DCL(Double-Checking Locking)을 써서 getInstance()에서 동기화되는 부분을 줄인다.  
DCL을 사용하면 일단 인스턴스가 생성되어 있는지 확인한 다음, 생성되어 있지 않았을 때만 동기화를 할 수 있다. 이렇게 하면 처음에만 동기화를 하고 나중에는 동기화를 하지 않아도 된다.

~~~
public class Singleton {
    private volatile static Singleton uniqueInstance;

    private Singleton() {

    }

    public static Singleton getInstance() {
        if (uniqueInstance == null) {
            synchronized (Singleton.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new Singleton();
                }
            }
        }
        return uniqueInstance;
    }
}
~~~

getInstance() 메소드를 사용할 때 속도가 문제가 될 수 있다면 이런식으로 Singleton을 구현함으로 오버헤드를 극적으로 줄일 수 있다. 

## Question
Q) 전역변수가 싱글턴보다 나쁜이유를 모르겠는데 전역번수를 쓰면 되는거 아닌지?  

A) 자바의 전역 변수는 기본적으로 객체에 대한 정적 레퍼런스이다. 전역 변수를 이런 식으로 사용하는 데는 몇 가지 단점이 있다.
1. 게으른 인스턴스를 생성할 수 없고, 처음부터 끝까지 인스턴스를 가지고 있어야 한다.
2. 전역변수를 사용하다 보면 간단한 객체에 대한 전역 레퍼런스를 자꾸 만들게 되면서 네임스페이스를 지저분하게 만드는 경향이 생기게 된다. 




## 핵심 정리

- 어떤 클래스에 싱글턴 패턴을 적용하면 애플리케이션에 그 클래스의 인스턴스가 최대 한 개 까지만 있도록 할 수 있다.
- 싱글턴 패턴을 이용하면 유일한 인스턴스를 어디서든지 접근할 수 있다.
- 자바에서 싱글턴 패턴을 구현할 때는 private 생성자와 정적 메소드, 정적 변수를 사용 한다.
- 다중 스레드를 사용하는 애플리케이션에서는 속도와 자원 문제를 파악해보고 적절한 구현법을 사용해야 한다.
  (사실 모든 애플리케이션에서 멀티스레딩을 쓸 수 있다고 생각해야 한다.)
- DCL을 사용하는 방법은 자바 2 버전 5(자바 1.5)보다 전에 나온 버전에서는 쓸 수 없다.
- 클래스 로더가 여러 개 있으며 싱글턴이 제대로 작동하지 않고, 여러 개의 인스턴스가 생길 수 있다.
- 1.2 버전 보다 전에 나온 JVM을 사용하는 경우에는 가비지 컬렉터 관련 버그 때문에 싱글턴 레지스트리를 사용해야 할 수도 있다.
