package factorypattern.pizzastore;

import factorypattern.pizza.NYStyleCheesePizza;
import factorypattern.pizza.Pizza;

public class NYPizzaStore extends PizzaStore {

	@Override
	protected Pizza createPizza(String type) {
		Pizza pizza;
		
		if(type.equals("cheese")) {
			pizza = new NYStyleCheesePizza();
		}
		else {
			pizza = null;
		}
		
		return pizza;
	}

}
