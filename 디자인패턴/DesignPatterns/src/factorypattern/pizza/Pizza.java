package factorypattern.pizza;

import java.util.ArrayList;

public class Pizza {
	String name;
	String dough;
	String sauce;
	ArrayList<String> topping = new ArrayList<>();
	
	/*
	Main_2 에
	추상 팩토리 패턴을 적용하면
	Dough dough; 
	Sauce sauce; 이런식으로 바뀐다.
	
	public abstarct void prepare();
	 */
	
	public void prepare() {
		System.out.println(getName() + "의 도우를 손으로 돌리는중...");
		System.out.println(getName() + "에 "+ sauce + "를 열심히 바르는중...");
		for (int i = 0; i < topping.size(); i++) {
			System.out.println(getName() +  "에 " + topping.get(i) + "토핑을 이빠이 올리는중...");
		}
	}
	
	public void bake() {
		System.out.println(getName() +  "를 굽는중...");
	}
	
	public void cut() {
		System.out.println(getName() +  "에 자를 대고 자르는중...");
	}
	
	public void box() {
		System.out.println(getName() +  "를 박스에 포장중...");
	}
	
	public String getName() { return name; }
}
