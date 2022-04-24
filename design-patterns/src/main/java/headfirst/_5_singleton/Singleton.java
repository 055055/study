package headfirst._5_singleton;

public class Singleton {
    //volatile 키워드를 사용하면 멀티스레딩을 쓰러라도 uniqueInstance 변수가 Singleton인스턴스로 초기화되는 과정이 올바르게 진행되도록 할 수 있다.
    private volatile static Singleton uniqueInstance;

    private Singleton() {

    }

    public static Singleton getInstance() {
        //인스턴스가 있는지 확인해보고 없으면 동기화된 블록으로 들어간다. 이렇게하면 처음에만 동기화가 된다.
        if (uniqueInstance == null) {
            synchronized (Singleton.class) {
                //블록으로 들어온 후에도 다시 한번 변수가 null인지 확인한 후 인스턴스 생성
                if (uniqueInstance == null) {
                    uniqueInstance = new Singleton();
                }
            }
        }
        return uniqueInstance;
    }
}
