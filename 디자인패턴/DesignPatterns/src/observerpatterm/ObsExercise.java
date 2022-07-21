package observerpatterm;

import java.util.Observable;
import java.util.Observer;

import observerpatterm.display.DisplayElement;

public class ObsExercise {
	//107������ ��������
	
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
		
		//�̰� ���� �׳� ���� �޼ҵ�
		private void getNecessaryElements(WeatherData weatherData) {
			currentPressure = weatherData.getPressure();
		}
		
		@Override
		public void display() {
			
		}
	}
}
