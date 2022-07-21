package iteratorpattern.waitress;

import java.util.ArrayList;
import java.util.Iterator;

import iteratorpattern.MenuItem;
import iteratorpattern.dinermenu.DinerMenu;
import iteratorpattern.pancakehouse.PancakeHouseMenu;

public class ExWaitress {
	/*
	 * �մԵ鿡�� �޴��� ������ִ� �޼ҵ�
	 * ��� �޴�, ��ħ �Ļ� �׸�, ���� �Ļ� �׸�, ä�������� �׸� �� �������
	 */
	PancakeHouseMenu pancakeHouseMenu;
	ArrayList<MenuItem> breakfastItems;
	
	DinerMenu dinerMenu;
	MenuItem[] lunchItems;
	
	public ExWaitress() {
		dinerMenu = new DinerMenu();
		lunchItems = dinerMenu.getMenuItems();
		
		pancakeHouseMenu = new PancakeHouseMenu();
		breakfastItems = pancakeHouseMenu.getMenuItems();
	}
	
	public void printMenu() { //��� �޴� ���
		for (int i = 0; i < breakfastItems.size(); i++) {
			MenuItem menuItem = breakfastItems.get(i);
			System.out.println(menuItem.getName() + " ");
			System.out.println(menuItem.getPrice() + "$ ");
			System.out.println(menuItem.getDescription());
		}
		for (int i = 0; i < lunchItems.length; i++) {
			MenuItem menuItem = lunchItems[i];
			System.out.println(menuItem.getName() + " ");
			System.out.println(menuItem.getPrice() + "$ ");
			System.out.println(menuItem.getDescription());
		}
		//��ȯ���� �� ���� ��������...
	}
}
