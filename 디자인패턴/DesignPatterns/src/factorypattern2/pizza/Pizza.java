package factorypattern2.pizza;

import java.util.ArrayList;

import factorypattern2.ingredients.Dough;
import factorypattern2.ingredients.Sauce;

public abstract class Pizza {
	String name;
	Dough dough; 
	Sauce sauce;
	ArrayList<String> topping = new ArrayList<>();
	
	public abstract void prepare();
	
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
