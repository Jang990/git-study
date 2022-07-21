package observerpatterm;

import observerpatterm.display.AnotherCurrentConditionsDisplay;
import observerpatterm.display.CurrentConditionsDisplay;

public class Main {
	/*
	 * é�� 2
	 * 
	 * ���� �ʰ� 3���� ȭ���� ������ �ִ� ���������� �˷��ִ� ���� �����ڰ� ġ��
	 * 1�� ȭ���� �µ�, ����, ����� �˷��ְ�
	 * 2�� ȭ���� �µ�, ����� �˷��ְ�
	 * 3�� ȭ���� ����, ����� �˷��شٰ� �غ���.
	 * ������ �ǽð����� ����Ǹ鼭 �µ�, ����, ��п� ��ȭ�� ����ٸ� �� 3���� ȭ����
	 * ����� ������ �°� ������Ʈ ���־�� �� ���̴�.
	 * �̷� �� ���� ���� ������ ������ �����̴�.
	 * 
	 *  ������ ����(Observer Pattern)
	 * �� ��ü�� ���°� �ٲ�� �� ��ü�� �����ϴ� �ٸ� ��ü������ ������ ���� �ڵ�����
	 * ������ ���ŵǴ� �ϴ�� �������� �����Ѵ�.
	 * (�� ��ü�� ���°� ����Ǹ� �� ��ü�� �����ϴ� ��� ��ü�� ������ ����.)
	 *
	 * 
	 *  ������ ��Ģ(= ��ü���� ��Ģ)
	 * 4. ���� ��ȣ�ۿ��� �ϴ� ��ü ���̿����� �����ϸ� �����ϰ� �����ϴ� �������� ����ؾ� �Ѵ�.
	 * (�����ϰ� �����ϴ� �������� ����ϸ� ���� ������ ���ܵ� ������ ó���� �� �ִ� ������ ��ü���� �ý����� ������ �� �ִ�.)
	 *  
	 *  �������鿡�� ������ �ִ� ������ �߿��ϴٸ� �� ������ �����ϸ� �ȵȴ�.
	 * ������ �ٲ�ٰ� �ؼ� �ٸ� ����� ���´ٸ�, �װ��� '������ ����'�� �ƴϴ�.
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
