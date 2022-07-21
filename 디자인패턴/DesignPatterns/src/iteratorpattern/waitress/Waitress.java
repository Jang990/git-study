package iteratorpattern.waitress;

import java.util.ArrayList;

import iteratorpattern.Iterator;
import iteratorpattern.MenuItem;
import iteratorpattern.dinermenu.DinerMenu;
import iteratorpattern.pancakehouse.PancakeHouseMenu;

public class Waitress {
	/*
	 * 손님들에게 메뉴를 출력해주는 메소드
	 * 모든 메뉴, 아침 식사 항목만, 점심 식사 항목만, 채식주의자 항목만 을 출력해줌
	 */
	PancakeHouseMenu pancakeHouseMenu;
	DinerMenu dinerMenu;
	
	
	public Waitress() {
		dinerMenu = new DinerMenu();
		pancakeHouseMenu = new PancakeHouseMenu();
	}
	
	//개선된 print문
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
