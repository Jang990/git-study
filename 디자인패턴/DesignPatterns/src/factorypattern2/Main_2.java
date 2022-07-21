package factorypattern2;

import factorypattern.pizza.Pizza;
import factorypattern.pizzastore.NYPizzaStore;
import factorypattern.pizzastore.PizzaStore;
import factorypattern2.pizza.CheesePizza;

public class Main_2 {
	/*
	 *  é�� 4-2
	 * 
	 *  �߻� ���丮 ����(Abstract Factory Pattern)
	 * �߻� ���丮 ���Ͽ����� �������̽��� �̿��Ͽ� ���� ������, �Ǵ� �����ϴ�
	 * ��ü�� ���� Ŭ������ �������� �ʰ� ������ �� �ֽ��ϴ�. 
	 * 
	 * Ŭ���̾�Ʈ�� ���丮���� ����Ǵ� ��ǰ�� �и���ų �� �ִ�.
	 * ������ �����簡 �ٲ�� ���� Ŭ���̾�Ʈ�� �׳� �ϴ���� ���ڸ� ��Ű��ȴ�.
	 * 
	 * ���丮 �޼ҵ� ������ pizza = new NYStyleCheesePizza();
	 * �߻� ���丮 ������  pizza = new CheesePizza(nyPizzaIngredientFactory);
	 * ���丮 �޼ҵ� ���Ͽ��� ��ī�� ��Ÿ�� ���ڸ� �������
	 * pizza = new ChicagoStyle(���ڸ�); ex) new ChicagoStyleCheesePizza();
	 * �߻� ���丮 ���Ͽ��� ��ī�� ��Ÿ�� ���ڸ� �������
	 * pizza = new (���ڸ�)(��ī�� �����); ex) new CheesePizza(chicagoIngredientFactory);
	 * 
	 * �ڵ�κ��� �ѹ� �� ���캸�� ��κ��� �޶������� �뷫������ ��������.
	 * ����κ��� ���� ��������... �̾�
	 */
	public static void main(String[] args) {
		//���κκ�(Ŭ���̾�Ʈ)������ �ٲ�� ���� ���� 
		PizzaStore nyStore = new NYPizzaStore();
		Pizza pizza = nyStore.orderPizza("cheese");
		System.out.println(pizza.getName());
	}
}
