package observerpatterm;

import java.util.Observable;
import java.util.Observer;

import observerpatterm.display.DisplayElement;

public class ObsExercise {
	//107페이지 연습문제
	
	class ForecastDisplay implements Observer, DisplayElement{
		private float currentPressure = 29.92f;
		private float lastPressure;
		
		@Override
		public void update(Observable obs, Object arg) {
				if(obs instanceof WeatherData) {
					WeatherData weatherData = (WeatherData) obs;
					lastPressure = currentPressure;
					getNecessaryElements(weatherData);
					display();
				}
		}
		
		//이건 내가 그냥 만들어본 메소드
		private void getNecessaryElements(WeatherData weatherData) {
			currentPressure = weatherData.getPressure();
		}
		
		@Override
		public void display() {
			
		}
	}
}
