package iteratorpattern.pancakehouse;

import java.util.ArrayList;

import iteratorpattern.Iterator;
import iteratorpattern.MenuItem;
import iteratorpattern.dinermenu.DinerMenuIterator;

public class PancakeHouseMenu {
	ArrayList<MenuItem> menuItems;
	
	public PancakeHouseMenu() {
		menuItems = new ArrayList<>();
		
		addItem("K&b ������ũ ��Ʈ",
				"��ũ����� ���׿� �佺Ʈ�� ��鿩�� ������ũ",
				true,
				2.99);
		
		addItem("���ַ� ������ũ ��Ʈ",
				"�ް� �Ķ��̿� �ҽ����� ��鿩�� ������ũ",
				false,
				2.99);
		
		addItem("��纣�� ������ũ",
				"�ż��� ��纣���� ��纣�� �÷����� ���� ������ũ",
				true,
				3.49);
		
		addItem("����",
				"ȭ��, ���⿡ ���� ��纣���� ���⸦ ���� �� �ֽ��ϴ�.��",
				true,
				3.59);
	}
	
	public void addItem(String name, String description, boolean vegetarian, double price) {
		MenuItem menuItem = new MenuItem(name, description, vegetarian, price);
		menuItems.add(menuItem);
	}
	
	public ArrayList<MenuItem> getMenuItems() {
		return menuItems;
	}
	
	//�ߺ��� ���ֱ� ���� �ϰ��� �������̽��� ������ִ� �޼ҵ�
	public Iterator createIterator() {
		return new PancakeHouseMenuIterator(menuItems);
	}
}
