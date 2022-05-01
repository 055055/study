package headfirst._2_observer.javaobservable;

import java.util.Observable;
import java.util.Observer;

public class CurrentConditionsDisplay implements Observer, DisplayElement {
    Observable observable;
    private float temperature;
    private float humidity;

    public CurrentConditionsDisplay(Observable observable) {
        //Observable을 가지고와서 해당 메서드를 호출하여 등록함.
        this.observable = observable;
        observable.addObserver(this);
    }

    @Override
    public void display() {
        System.out.println("Current conditions: " + temperature + "F degrees and" + humidity + "%" + " humidity");
    }

    @Override
    public void update(Observable obs, Object arg) {
        //Observable과 추가 데이터 인자를 모두 받도록 수정
        if (obs instanceof WeatherData) {
            //WeatherData가 맞다면 pull방식으로 데이터를 가지고 오도록함
            WeatherData weatherData = (WeatherData) obs;
            this.temperature = weatherData.getTemperature();
            this.humidity = weatherData.getHumidity();
            display();
        }
    }
}
