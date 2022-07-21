package factorypattern2.ingredients;

public class NYPizzaIngredientFactory implements PizzaIngredientFactory {

	@Override
	public Dough createDough() {
		return new ThinCrustDough();
	}

	@Override
	public Sauce createSauce() {
		return null;
		//return new MarinaraSauce(); 
	}
	
	//... 이런식으로 만든다

}
