package factorypattern.pizzastore;

import factorypattern.pizza.ChicagoStyleCheesePizza;
import factorypattern.pizza.Pizza;

public class ChicagoPizzaStore extends PizzaStore {

	@Override
	protected Pizza createPizza(String type) {
		Pizza pizza;
		if(type.equals("cheese")) {
			pizza = new ChicagoStyleCheesePizza();
		}
		else {
			pizza = null;
		}
		return pizza;
	}

}
