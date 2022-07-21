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
		System.out.println(getName() +  "�� ������...");
	}
	
	public void cut() {
		System.out.println(getName() +  "�� �ڸ� ��� �ڸ�����...");
	}
	
	public void box() {
		System.out.println(getName() +  "�� �ڽ��� ������...");
	}
	
	public String getName() { return name; }
}
