package headfirst._2_observer.observerpattern.subject;

import headfirst._2_observer.observerpattern.observer.Observer;

public interface Subject {
    //옵저버를 등록하는 역할
    public void registerObserver(Observer o);
    //옵저버를 제거하는 역할
    public void removeObserver(Observer o);
    //주제 객체의 상태가 변경되엇을 때 모든 옵저버들한테 알리기 위해 호출되는 메서드
    public void notifyObservers();
}
