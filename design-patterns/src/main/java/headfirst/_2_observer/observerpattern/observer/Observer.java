package headfirst._2_observer.observerpattern.observer;

public interface Observer {
    //기상 정보가 변경되었을 때 모든 옵저버들한테 전달되는 상태 값들
    public void update(float temp, float humidity, float pressure);
}
