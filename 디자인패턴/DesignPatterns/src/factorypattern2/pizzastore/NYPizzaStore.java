package factorypattern2.pizzastore;

import factorypattern2.ingredients.NYPizzaIngredientFactory;
import factorypattern2.ingredients.PizzaIngredientFactory;
import factorypattern2.pizza.CheesePizza;
import factorypattern2.pizza.Pizza;

public class NYPizzaStore extends PizzaStore {

	PizzaIngredientFactory nyPizzaIngredientFactory;
	
	public NYPizzaStore() {
		nyPizzaIngredientFactory = new NYPizzaIngredientFactory(); 
	}
	
	@Override
	protected Pizza createPizza(String type) {
		Pizza pizza;
		
		if(type.equals("cheese")) {
			pizza = new CheesePizza(nyPizzaIngredientFactory);
		}
		else {
			pizza = null;
		}
		
		return pizza;
	}

}
