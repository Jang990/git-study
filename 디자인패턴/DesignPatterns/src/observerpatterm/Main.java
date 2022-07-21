package observerpatterm;

import observerpatterm.display.AnotherCurrentConditionsDisplay;
import observerpatterm.display.CurrentConditionsDisplay;

public class Main {
	/*
	 * 챕터 2
	 * 
	 * 만약 너가 3개의 화면을 가지고 있는 날씨정보를 알려주는 앱을 만들자고 치자
	 * 1번 화면은 온도, 습도, 기압을 알려주고
	 * 2번 화면은 온도, 기압을 알려주고
	 * 3번 화면은 습도, 기압을 알려준다고 해보자.
	 * 날씨가 실시간으로 변경되면서 온도, 습도, 기압에 변화가 생겼다면 이 3가지 화면을
	 * 변경된 날씨에 맞게 업데이트 해주어야 할 것이다.
	 * 이럴 때 쓰면 좋은 패턴이 옵저버 패턴이다.
	 * 
	 *  옵저버 패턴(Observer Pattern)
	 * 한 객체의 상태가 바뀌면 그 객체에 의존하는 다른 객체들한테 연락이 가고 자동으로
	 * 내용이 갱신되는 일대다 의존성을 정의한다.
	 * (한 객체의 상태가 변경되면 그 객체에 의존하는 모든 객체에 연락이 간다.)
	 *
	 * 
	 *  디자인 원칙(= 객체지향 원칙)
	 * 4. 서로 상호작용을 하는 객체 사이에서는 가능하면 느슨하게 결합하는 디자인을 사용해야 한다.
	 * (느슨하게 결합하는 디자인을 사용하면 변경 사항이 생겨도 무난히 처리할 수 있는 유연한 객체지향 시스템을 구축할 수 있다.)
	 *  
	 *  옵저버들에게 연락을 주는 순서가 중요하다면 이 패턴을 적용하면 안된다.
	 * 순서가 바뀐다고 해서 다른 결과가 나온다면, 그것은 '느스한 결합'이 아니다.
	 */
	
	public static void main(String[] args) {
		WeatherData weatherData = new WeatherData();
		CurrentConditionsDisplay display = new CurrentConditionsDisplay(weatherData);
		AnotherCurrentConditionsDisplay antherDisplay = new AnotherCurrentConditionsDisplay(weatherData);
		weatherData.setMeasurements((float)100.11, (float)100.22, (float)123.123);
		weatherData.setMeasurements((float)2.902, (float)444.2222, (float)3333);
		weatherData.setMeasurements((float)4421.189, (float)1467.2231, (float)11223);
	}
}
