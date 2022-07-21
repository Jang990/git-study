package factorypattern.pizza;

public class NYStyleCheesePizza extends Pizza {
	/*
	 PizzaIngredientFactory ingredientFactory;
	 
	 public CheesePizza
	 */
	
	public NYStyleCheesePizza() {
		name = "뉴욕 스타일 소스와 치즈 피자";
		dough = "얇고 바삭한 도우";
		sauce = "마리화나 소스";
		
		topping.add("잘게 썬 레지아노 치즈");
	}
}
