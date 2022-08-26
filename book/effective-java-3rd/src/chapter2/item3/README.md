# private 생성자나 열거 타입으로 싱글턴임을 보증하라
~~~text
싱글턴(singleton)이란 인스턴스를 오직 하나만 생성할 수 있는 클래스를 말한다.  
싱글턴의 전형적인 예로는 함수와 같은 무상태(stateless) 객체나 설계상 유일해야 하는 시스템 컴포넌트를 들 수 있다.  
한번의 객체 생성으로 재사용할 수 있기 떄문에 메모리 낭비를 방지할 수 있다.  

클래스를 싱글턴으로 만들면 이를 사용하는 클라이언트를 테스트하기가 어려워질 수 있다.  
싱글턴 인스턴스를 가짜(mock) 구현으로 대체할 수 없기 때문이다.  
또한 멀티 쓰레드 환경에서 동기화 문제가 발생할 수 도 있다.
~~~

## 싱글턴을 만드는 방식

### 필드 방식의 싱글턴

~~~ java
public class Elvis {
    public static final Elvis INSTANCE = new Elvis();
    private Elvis() {...}
    
    public void leaveTheBuilding() {...}
}
~~~

~~~ java
public class Elvis {
    public static final Elvis INSTATNCE = new Elvis();

    private Elvis() {

    }

    public void leaveTheBuilding() {
        System.out.println("leaveTheBuilding");
    }

    public static void main(String[] args) {
        Elvis instatnce = Elvis.INSTATNCE;
        instatnce.leaveTheBuilding();
    }
}
~~~

private 생성자는 public static final 필드인 Elvis.INSTANCE를 초기화할 때 딱 한번 호출된다.  
public이나 protected 생성자가 없으므로 Elvis 클래스가 초기화될 때 만들어진 인스턴스가 전체 시스템에서 하나 뿐임을 보장한다.  


장점
- 해당 클래스가 싱글턴임이 API에 명백히 드러난다. public static 필드가 final이니 절대로 다른 객체를 참조할 수 없다.
- 간결함

단점
- 리플랙션 API의 AccessibleObject.setAccessible을 사용해 private 생성자를 호출 할 수 있다. 
>> 이러한 공격을 방어하려면 생성자를 수정하여 두 번째 객체가 생성되려 할 때 예외를 던지게 하면 된다.

### 정적 팩터리 방식의 싱글턴

~~~ java
public class Elvis {
    private static final Elvis INSTANCE = new Elvis();
    private Elvis() {...}
    public static Elvis getInstance() { return INSTATNCE;}
    
    public void leaveTheBuilding() {...}
}
~~~

~~~ java
public class Elvis {
    private static final Elvis INSTATNCE = new Elvis();
    private Elvis(){

    }

    public static Elvis getInstance(){
        return INSTATNCE;
    }

    public void leaveTheBuilding() {
        System.out.println("leaveTheBuilding");
    }

    public static void main(String[] args) {
        Elvis instance = Elvis.getInstance();
        instance.leaveTheBuilding();
    }
~~~
Elvis.getInstance는 항상 같은 객체의 참조를 반환하므로 제 2의 Elvis 인스턴스란 결코 만들어지지 않는다.(리플렉션 예외는 동일 적용)  

장점
- 언제든지 API를 바꾸지 않고도 싱글턴이 아니게 변경할 수 있다.
- 정적 팩터리를 제네릭 싱글턴 팩터리로 만들 수 있다.
- 정적 팩터리의 메서드 참조를 공급자(supplier)로 사용할 수 있다.  
  - ex) Elvis::getInstance를 Supplier<Elvis>로 사용하는 식이다.

단점
- 리플랙션 API의 AccessibleObject.setAccessible을 사용해 private 생성자를 호출 할 수 있다.
>> 방어 하려면 두 번째 객체가 생성되려 할 때 예외를 던지게 하면 된다.
- 위 장점이 필요없다면 1번이 더 나을 수 있다.

### 열거 타입 방식의 싱글턴 - 바람직한 방법
~~~ java
public enum Elvis {
    INSTANCE;
    
    public void leaveThebuilding() {...}
}
~~~

~~~ java
public enum Elvis {
    INSTANCE;

    public void leaveTheBuilding() {
        System.out.println("leaveTheBuilding");
    }

    public static void main(String[] args) {
        Elvis instance = Elvis.INSTANCE;
        instance.leaveTheBuilding();
    }
}
~~~
public 필드 방식과 비슷하지만, 더 간결하고, 추가 노력 없이 직렬화 할 수 있고, 심지어 아주 복잡한 직렬화 상황이나 리플렉션 공격에서도 제2의 인스턴스가 생기는 일을 완벽히 막아준다.  
**조금 부자연스러워 보일 수는 있으나 대부분 상황에서는 원소가 하나뿐인 열거 타입이 싱글턴을 만드는 가장 좋은 방법이다.**  
단, 만들려는 싱글턴이 Enum 외의 클래스를 상속해야 한다면 이 방법은 사용할 수 없다.

이러한 싱글턴 클래스를 직렬화하려면 단순히 Serializable을 구현한다고 선언하는 것만으로는 부족하다.  
모든 인스턴스 필드를 일시적(transient)이라고 선언하고 readResolve 메서드를 제공해야 한다.  
이렇게 하지 않으면 직렬화된 인스턴스를 역직렬화할 때마다 새로운 인스턴스가 만들어진다.  
**가짜 Elvis 탄생을 예방하고 싶다면 Elvis 클래스에 다음의 readResolve 메서드를 추가하자.**

~~~ java
private Object readResolve() {
   //'진짜' Elvis를 반환하고, 가짜 Elvis는 가비지 컬렉터에 맡긴다. 
}
~~~


