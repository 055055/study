# 생성자 대신 정적 팩터리 메서드를 고려하라

클라이언트가 클래스의 인스턴스를 얻는 전통적인 수단은 public 생성자다.  
하지만 모든 프로그래머가 꼭 알아둬야 할 기법이 하나 더 있다.  
클래스는 생성자와 별도로 정적 팩터리 메서드(static factory method)를 제공할 수 있다.  
그 클래스의 인스턴스를 반환하는 단순한 정적 메서드 말이다.
~~~ java
public static Boolean valueOf (boolean b) {
        return b?Boolean.TRUE:Boolean.FALSE;
}
~~~

위 코드는 boolean 기본 타입의 박싱 클래스(boxed class)인 Boolean에서 발췌한 간단한예다.  
이 메서드는 기본 타입인 boolean 값을 받아 Boolean 객체 참조로 변환해준다.  

## 정적 팩터리 메서드가 생성자보다 좋은 다섯가지 장점

1. 이름을 가질 수 있다.  
   생성자에 넘기는 매개변수와 생성자 자체만으로는 반환될 객체의 특성을 제대로 설명하지 못한다.  
   반면 정적 팩터리는 이름만 잘 지으면 반환될 객체의 특성을 쉽게 묘사할 수 있다.
    

    ex) BigInteger(int, int, Random)와 정적 팩터리 메서드인 BigInteger.probablePrime 중 어느 쪽이 값이 소수인 BigInteger를 반환한다는 의미를 더 잘 설명하는지 생각해보자.

    

2. 호출될 때마다 인스턴스를 새로 생성하지 않아도 된다.  
   - 이 덕분에 불변 클래스(immutable class)는 인스턴스를 미리 만들어 놓거나 새로 생성한 인스턴스를 캐싱하여 재활용하는 식으로 불필요한 객체 생성을 피할 수 있다.  
   　    
   ex) Boolean.valueOf(boolean) 메서드는 객체를 아예 생성하지 않는다.  
       따라서 (특히 생성 비용이 큰) 같은 객체가 자주 요청되는 상황이라면 성능을 상당히 끌어올려 준다.  

   - 반복되는 요청에 같은 객체를 반환하는 식으로 정적 팩터리 방식의 클래스는 언제 어느 인스턴스를 살아있게 할지를 철저히 통제할 수 있다. 이런 클래스를 **인스턴스 통제(instance-controlled)** 클래스라 한다.  
   　        
     인스턴스를 통제하면 클래스를 싱글턴으로 만들 수도, 인스턴스화 불가로 만들수도 있다. 또한 불변 값 클래스에서 동치인 인스턴스가 단 하나뿐임을 보장할 수 있다.(a == b 일때만 a.equals(b)가 성립).  
     인스턴스 통제는 플라이웨이트 패턴의 근간이 되며, 열거 타입은 인스턴스가 하나만 만들어짐을 보장한다.
      
    
3. 반환 타입의 하위 타입 객체를 반환할 수 있는 능력이 있다.  
   이를 통해 반환할 객체의 클래스를 자유롭게 선택할 수 있게 하는 '엄청난 유연성을' 선물한다.  
   API를 만들 때 이 유연성을 응용하면 구현 클래스를 공개하지 않고도 그 객체를 반환할 수 있어 API를 작게 유지할 수 있다.
      
   
4. 입력 매개변수에 따라 매번 다른 클래스의 객체를 반환할 수 있다.  
    반환 타입의 하위 타입이기만 하면 어떤 클래스의 객체를 반환하든 상관 없다.     
   

5. 정적 팩터리 메서드를 작성하는 시점에는 반환할 객체의 클래스가 존재하지 않아도 된다.
    클래스를 미리 만들지 않아도 컴파일이 가능하도록 유연함 제공

## 정적 팩터리의 단점

1. 상속을 하려면 public이나 protected 생성자가 필요하니 정적 팩터리 메서드만 제공하면 하위 클래스를 만들 수 없다.  
   즉 private 생성자만 제공해서는 상속을 할 수 없다. 그러나 이 제약은 상속보다 컴포지션을 사용하도록 유도하고 불변 타입으로 만들려면 이 제약을 지켜야 한다는 점에서 오히려 장점이 될 수도 있다.  
   

2. 정적 팩터리 메서드는 프로그래머가 찾기 어렵다. 

## 정적 팩터리 메서드에 흔히 사용하는 명명 방식

- from : 매개변수를 하나 받아서 해당 타입의 인스턴스를 반환하는 형변환 메서드  
~~~
ex) Date d = Date.from(instant);
~~~

- of: 여러 매개변수를 받아 적합한 타입의 인스턴스를 반환하는 집계 메서드  
~~~
ex) Set<Rank> faceCards = EnumSet.of(JACK, QUEEN, KING);
~~~
- valueOf: from과 of의 더 자세한 버전  
~~~
ex) BigInteger prime = BigInteger.valueOf(Integer.MAX_VALUE);
~~~
- instance 혹은 getInstance: (매개변수를 받는다면) 매개변수로 명시한 인스턴스를 반환하지만, 같은 인스턴스임을 보장하지는 않는다.  
~~~
ex) StackWalker luke = StackWalker.getInstance(options);
~~~
- create 혹은 newInstance: instance 혹은 getInstance와 같지만, 매번 새로운 인스턴스를 생성해 반환함을 보장한다.  
~~~
ex) Object newArray = Array.newInstance(classObject, arrayLen);
~~~
- getType: getInstance와 같으나, 생성할 클래스가 아닌 다른 클래스에 팩터리 메서드를 정의할 때 쓴다.  "Type"은 팩터리 메서드가 반환할 객체의 타입이다.  
~~~
ex) FileStore fs = Files.getFileStore(path);
~~~
- newType: newInstance와 같으나, 생성할 클래스가 아닌 다른 클래스에 팩터리 메서드를 정의할 때 쓴다. "Type"은 팩터리 메서드가 반환할 객체의 타입이다.
~~~
ex) BufferedReader br = Files.newBufferedReader(path);
~~~ 
- type: getType과 newType의 간결한 버전
~~~
ex) List<Complaint> litany = Collections.list(legacyLitany);
~~~

## 핵심 정리
>정적 팩터리 메서드와 public 생성자는 각자의 쓰임새가 있으니 상대적인 장단점을 이해하고 사용하는 것이 좋다.  
>그렇다고 하더라도 정적 팩터리를 사용하는 게 유리한 경우가 더 많으므로 무작정 public 생성자를 제공하던 습관이 있다면 고치자

## 출처
[이펙티브자바](https://search.shopping.naver.com/book/catalog/32436239326?cat_id=50010920&frm=PBOKMOD&query=%08%EC%9D%B4%ED%8E%99%ED%8B%B0%EB%B8%8C+%EC%9E%90%EB%B0%94&NaPm=ct%3Dl6z138ns%7Cci%3Db0f99d2f2a318dd7ab9dac8495e63c0076fddd86%7Ctr%3Dboknx%7Csn%3D95694%7Chk%3D71936725f1d33db581c3d8c6a56703ade1a7c790)