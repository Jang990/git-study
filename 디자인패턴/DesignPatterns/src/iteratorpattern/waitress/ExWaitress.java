package iteratorpattern.waitress;

import java.util.ArrayList;
import java.util.Iterator;

import iteratorpattern.MenuItem;
import iteratorpattern.dinermenu.DinerMenu;
import iteratorpattern.pancakehouse.PancakeHouseMenu;

public class ExWaitress {
	/*
	 * 손님들에게 메뉴를 출력해주는 메소드
	 * 모든 메뉴, 아침 식사 항목만, 점심 식사 항목만, 채식주의자 항목만 을 출력해줌
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
	
	public void printMenu() { //모든 메뉴 출력
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
		//순환문이 두 개로 나뉘었다...
	}
}
