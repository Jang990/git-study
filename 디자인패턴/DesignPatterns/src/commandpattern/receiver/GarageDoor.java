package commandpattern.receiver;

public class GarageDoor {
	public void up() {
		System.out.println("차고문 열기!");
	}
	
	public void down() {
		System.out.println("차고문 닫기!");
	}
	
	public void lightOn() {
		System.out.println("차고 전등 켜기!");
	}
	
	public void lightOff() {
		System.out.println("차고 전등 끄기!");
	}
}
