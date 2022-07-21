package iteratorpattern.waitress;

import java.util.ArrayList;

import iteratorpattern.Iterator;
import iteratorpattern.MenuItem;
import iteratorpattern.dinermenu.DinerMenu;
import iteratorpattern.pancakehouse.PancakeHouseMenu;

public class Waitress {
	/*
	 * �մԵ鿡�� �޴��� ������ִ� �޼ҵ�
	 * ��� �޴�, ��ħ �Ļ� �׸�, ���� �Ļ� �׸�, ä�������� �׸� �� �������
	 */
	PancakeHouseMenu pancakeHouseMenu;
	DinerMenu dinerMenu;
	
	
	public Waitress() {
		dinerMenu = new DinerMenu();
		pancakeHouseMenu = new PancakeHouseMenu();
	}
	
	//������ print��
	public void printMenu() {
		Iterator pancakeIterator = pancakeHouseMenu.createIterator();
		Iterator dinerIterator = dinerMenu.createIterator();
		printMenu(pancakeIterator);
		printMenu(dinerIterator);
	}
	
	private void printMenu(Iterator iterator) {
		while(iterator.hasNext()) {
			MenuItem menuItem = (MenuItem)iterator.next();
			System.out.println(menuItem.getName() + " ");
			System.out.println(menuItem.getPrice() + "$ ");
			System.out.println(menuItem.getDescription());
		}
	}
}
