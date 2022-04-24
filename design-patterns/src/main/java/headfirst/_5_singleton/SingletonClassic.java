package headfirst._5_singleton;

public class SingletonClassic {
    //Singleton 클래스의 유일한 인스턴스를 저장하기 위한 정적 변수
    private static SingletonClassic uniqueInstance;

    //생성자를 private로 선언했기 때문에 Singleton에서만 클래스의 인스턴스를 만들 수 있다.
    private SingletonClassic() {

    }

    public static SingletonClassic getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new SingletonClassic();
        }
        //필요할 때 인스턴스를 생성할 수 있다. 이런 방법은 게으른 인스턴스 생성(Lazy Instantiation)이라고 부른다.

        return uniqueInstance;
    }
}
