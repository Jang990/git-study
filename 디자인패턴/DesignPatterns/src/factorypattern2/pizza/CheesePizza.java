package factorypattern2.pizza;

import factorypattern2.pizza.Pizza; 
import factorypattern2.ingredients.PizzaIngredientFactory;

public class CheesePizza extends Pizza {
	PizzaIngredientFactory ingredientFactory;
	
	public CheesePizza(PizzaIngredientFactory ingredientFactory) {
		this.ingredientFactory = ingredientFactory;
	}

	@Override
	public void prepare() {
		dough = ingredientFactory.createDough();
		/*
		sauce = ingredientFactory.createSauce();
		.... 이런식으로 증가
		 */
		
		/*
		 * 이런식으로 사용하기 때문에 어떤 재료를 쓰는지 전혀 신경쓰지않고 그냥 피자를 만들어내면 된다.
		 * 어떤 지역의 팩토리를 사용하든 피자 클래스를 그대로 사용할 수 있다.
		 */
	}
}
