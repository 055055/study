# 생성자에 매개변수가 많다면 빌더를 고려하라

정적 팩터리와 생성자에는 똑같은 제약이 하나 있다. 선택적 매개변수가 많을 때 적절히 대응하기 어렵다는 점이다.  
식품 포장의 영양정보를 표현하는 클래스를 생각해보자. 필수 항목 몇개와 대부분 값이 그냥 0인 선택 항목으로 이루어 진다.  
프로그래머들은 아래와 같은 방식들로 해결하고자 하였다.


## 점층적 생성자 패턴(telescoping constructor pattern)

~~~ java
   public class NutritionFacts {
    private final int servingSize;      // (ml, 1회 제공량)   필수
    private final int servings;         // (회, 총 n회 제공량)  필수
    private final int calories;         // (1회 제공량당)      선택
    private final int fat;              // (g/1회 제공량)     선택
    private final int sodium;           // (mg/1회 제공량)    선택
    private final int carbohydrate;     // (g/1회 제공량)     선택

    public NutritionFacts(int servingSize, int servings) {
        this(servingSize, servings, 0);
    }

    public NutritionFacts(int servingSize, int servings, int calories) {
        this(servingSize, servings, calories, 0);
    }

    public NutritionFacts(int servingSize, int servings, int calories, int fat) {
        this(servingSize, servings, calories, fat, 0);
    }

    public NutritionFacts(int servingSize, int servings, int calories, int fat, int sodium) {
        this(servingSize, servings, calories, fat, sodium, 0);
    }

    public NutritionFacts(int servingSize, int servings, int calories, int fat, int sodium, int carbohydrate) {
        this.servingSize = servingSize;
        this.servings = servings;
        this.calories = calories;
        this.fat = fat;
        this.sodium = sodium;
        this.carbohydrate = carbohydrate;
    }
}


~~~

이 클래스의 인스턴스를 만들려면 원하는 매개변수를 모두 포함한 생성자 중 가장 짧은 것을 골라 호출하면 된다.  

~~~ java
NutritionFacts cocaCola = new NutritionFacts(240, 8, 100, 0, 35, 27);
~~~

점층적 생성자 패턴은 매개변수 개수가 많아지면 클라이언트 코드를 작성하거나 읽기 어렵다는 단점이 있다. 
- 코드를 읽을 때 각 값의 의미가 무엇인지 헷갈릴 수 있다.
- 매개변수가 몇 개인지도 주의해서 세어보아야 한다.
- 타입이 같은 매개변수가 연달아 늘어서 있으면 찾기 어려운 버그로 이어질 수 있다.
- 클라이언트가 실수로 매개변수의 순서를 바꿔 건네줘도 컴파일러는 알아채지 못하고, 결국 런타임시에 엉뚱한 동작을 하게 된다.

## 자바 빈즈패턴(javaBeans pattern)
매개변수가 없는 생성자로 객체를 만든 후, 세터(setter) 메서드들을 호출해 원하는 매개변수의 값을 설정하는 방식  

~~~ java
public class NutritionFacts {
    private int servingSize = -1; //필수; 기본값 없음
    private int servings = -1;    //필수; 기본값 없음
    private int calories = 0;
    private int fat = 0;
    private int sodium = 0;
    private int carbohydrate = 0;

    public NutritionFacts() {
    }

    public void setServingSize(int servingSize) {
        this.servingSize = servingSize;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    public void setCarbohydrate(int carbohydrate) {
        this.carbohydrate = carbohydrate;
    }
}
~~~
점층적 생성자 패턴의 단점들이 자바빈즈 패턴에서는 더 이상 보이지 않는다.  
코드가 길어지기는 했지만 인스턴스를 만들기 쉽고, 그 결과 더 읽기 쉬운 코드가 되었다.

~~~ java
NutritionFacts cocaCola = new NutritionFacts();
cocaCola.setServingSize(240);
cocaCola.setServings(8);
cocaCola.setCalories(100);
cocaCola.setSodium(35);
cocaCola.setCarbohydrate(27);
~~~

하지만 불행히도 자바빈즈는 자신만의 심각한 단점을 지니고 있다.
- 자바빈즈 패턴에서는 객체 하나를 만들려면 메서드를 여러 개 호출해야 하고, 객체가 완전히 생성되기 전까지는 일관성이 무너진 상태에 놓이게 된다.  
  이처럼 일관성이 무너지는 문제 때문에 자바빈즈 패턴에서는 클래스를 불변으로 만들 수 없으며 스레드 안전성을 얻으려면 프로그래머가 추가 작업을 해줘야 한다.


## 빌더 패턴(Builder pattern)
점층적 생성자 패턴의 안전성과 자바빈즈 패턴의 가독성을 겸비했다.  
클라이언트는 필요한 객체를 직접 만드는 대신, 필수 매개변수만으로 생성자(혹은 정적 팩터리)를 호출해 빌더 객체를 얻는다. 그런 다음 빌더 객체가 제공하는 일종의 세터 메서드들로 원하는 선택 매개변수들을 설정한다.  
마지막으로 매개변수가 없는 build 메서드를 호출해 드디어 우리에게 필요한(보통은 불변인) 객체를 얻는다. 빌더는 생성할 클래스 안에 정적 멤버 클래스로 만들어두는게 보통이다.  

~~~ java
public class NutritionFacts {
    private final int servingSize;
    private final int servings;
    private final int calories;
    private final int fat;
    private final int sodium;
    private final int carbohydrate;

    public static class Builder {
        //필수 매개변수
        private final int servingSize;
        private final int servings;

        //선택 매개변수 - 기본값으로 초기화
        private int calories = 0;
        private int fat = 0;
        private int sodium = 0;
        private int carbohydrate = 0;

        public Builder(int servingSize, int servings) {
            this.servingSize = servingSize;
            this.servings = servings;
        }

        public Builder calories(int val) {
            calories = val;
            return this;
        }
        
        public Builder fat (int val){
            fat = val;
            return this;
        }
        
        public Builder sodium( int val){
            sodium = val;
            return this;
        }
        
        public Builder carbohydrate(int val){
            carbohydrate = val;
            return this;
        }
        
        public NutritionFacts build(){
            return new NutritionFacts(this);
        }
    }
    
    private NutritionFacts(Builder builder){
        servingSize = builder.servingSize;
        servings = builder.servings;
        calories = builder.calories;
        fat = builder.fat;
        sodium = builder.sodium;
        carbohydrate = builder.carbohydrate;
    }
}
~~~
NutritionFacts 클래스는 불변이며, 모든 매개변수의 기본값들을 한곳에 모아뒀다.  
빌더의 세터 메서드들은 빌더 자신을 반환하기 때문에 연쇄적으로 호출할 수 있다.(플루언트 api or 메서드 연쇄)  

~~~ java
NutritionFacts cocaCola = new NutritionFacts.Builder(240, 8)
                .calories(100).sodium(35).carbohydrate(27).build();
~~~


## 핵심 정리
1. 점층적 생성타 패턴 - 확장하기 어렵다.
2. 자바빈즈 패턴 - 일관성이 깨지고, 불변으로 만들 수 없다.
3. 빌더 패턴 - 점층적 생성자 패턴과 자바빈즈 패턴의 장점만 취했다.

>생성자나 정적 팩터리가 처리해야 할 매개변수가 많다면 빌더패턴을 선택하는게 더 낫다.  
>매개변수 중 다수가 필수가 아니거나 같은 타입이면 특히 더 그렇다. 빌더는 점층적 생성자보다 클라이언트 코드를 읽고 쓰기가 훨씬 간결하고, 자바빈즈보다 훨씬 안전하다.

## 출처
[이펙티브자바](https://search.shopping.naver.com/book/catalog/32436239326?cat_id=50010920&frm=PBOKMOD&query=%08%EC%9D%B4%ED%8E%99%ED%8B%B0%EB%B8%8C+%EC%9E%90%EB%B0%94&NaPm=ct%3Dl6z138ns%7Cci%3Db0f99d2f2a318dd7ab9dac8495e63c0076fddd86%7Ctr%3Dboknx%7Csn%3D95694%7Chk%3D71936725f1d33db581c3d8c6a56703ade1a7c790)
