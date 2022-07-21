package factorypattern.pizza;

import java.util.ArrayList;

public class Pizza {
	String name;
	String dough;
	String sauce;
	ArrayList<String> topping = new ArrayList<>();
	
	/*
	Main_2 ��
	�߻� ���丮 ������ �����ϸ�
	Dough dough; 
	Sauce sauce; �̷������� �ٲ��.
	
	public abstarct void prepare();
	 */
	
	public void prepare() {
		System.out.println(getName() + "�� ���츦 ������ ��������...");
		System.out.println(getName() + "�� "+ sauce + "�� ������ �ٸ�����...");
		for (int i = 0; i < topping.size(); i++) {
			System.out.println(getName() +  "�� " + topping.get(i) + "������ �̺��� �ø�����...");
		}
	}
	
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
