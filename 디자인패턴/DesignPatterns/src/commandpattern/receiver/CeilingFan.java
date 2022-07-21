package commandpattern.receiver;

public class CeilingFan {
	public static final int HIGH = 3; //강풍
	public static final int MEDIUM = 2; //약풍
	public static final int LOW = 1; //미풍
	public static final int OFF = 0; //종료
	String location;
	int speed; //현재 선풍기의 속도를 저장하는 변수
	
	public CeilingFan(String location) {
		this.location = location;
		speed = OFF;
	}
	
	public void high() {
		speed = HIGH;
	}
	
	public void medium() {
		speed = MEDIUM;
	}
	
	public void low() {
		speed = LOW;
	}
	
	public void off() {
		speed = OFF;
	}
	
	public int getSpeed() {
		return speed;
	}
}
