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
		.... �̷������� ����
		 */
		
		/*
		 * �̷������� ����ϱ� ������ � ��Ḧ ������ ���� �Ű澲���ʰ� �׳� ���ڸ� ������ �ȴ�.
		 * � ������ ���丮�� ����ϵ� ���� Ŭ������ �״�� ����� �� �ִ�.
		 */
	}
}
