package headfirst._2_observer.javaobservable;

import java.util.Observable;

public class WeatherData extends Observable {
    private float temperature;
    private float humidity;
    private float pressure;

    //생성, 삭제 , 알림은 상위 클래스에서 해준다.
   public WeatherData() {}

    //기상 스테이션으로부터 갱신된 측정치를 받으면 옵저버들에게 알림
    public void measurementsChanged(){
       //notifyObservers를 호출하기 전에 setChanged()를 호출해서 상태가 바뀌었다는 것을 알린다.
       setChanged();
       //객체를 보내지 않기에 pull 모델을 사용한 것을 알 수 있다.
       notifyObservers();
    }

    public void setMeasurements(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        measurementsChanged();
    }

    //pull 방식으로 옵저버가 WeatherData 객체의 상태를 알아낼 때 해당 메서드들을 사용한다.
    public float getTemperature() {
        return temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getPressure() {
        return pressure;
    }
}
