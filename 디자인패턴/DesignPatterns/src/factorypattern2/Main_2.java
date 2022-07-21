package factorypattern2;

import factorypattern.pizza.Pizza;
import factorypattern.pizzastore.NYPizzaStore;
import factorypattern.pizzastore.PizzaStore;
import factorypattern2.pizza.CheesePizza;

public class Main_2 {
	/*
	 *  챕터 4-2
	 * 
	 *  추상 팩토리 패턴(Abstract Factory Pattern)
	 * 추상 팩토리 패턴에서는 인터페이스를 이용하여 서로 연관된, 또는 의존하는
	 * 객체를 구상 클래스를 지정하지 않고도 생성할 수 있습니다. 
	 * 
	 * 클라이언트와 팩토리에서 생산되는 제품을 분리시킬 수 있다.
	 * 피자의 원자재가 바뀌건 말건 클라이언트는 그냥 하던대로 피자를 시키면된다.
	 * 
	 * 팩토리 메소드 패턴은 pizza = new NYStyleCheesePizza();
	 * 추상 팩토리 패턴은  pizza = new CheesePizza(nyPizzaIngredientFactory);
	 * 팩토리 메소드 패턴에서 시카고 스타일 피자를 만들려면
	 * pizza = new ChicagoStyle(피자명); ex) new ChicagoStyleCheesePizza();
	 * 추상 팩토리 패턴에서 시카고 스타일 피자를 만드려면
	 * pizza = new (피자명)(시카고 원재료); ex) new CheesePizza(chicagoIngredientFactory);
	 * 
	 * 코드부분을 한번 쭉 살펴보고 어떤부분이 달라졌는지 대략적으로 이해하자.
	 * 여기부분은 대충 만들어놨다... 미안
	 */
	public static void main(String[] args) {
		//메인부분(클라이언트)에서는 바뀌는 것이 없다 
		PizzaStore nyStore = new NYPizzaStore();
		Pizza pizza = nyStore.orderPizza("cheese");
		System.out.println(pizza.getName());
	}
}
