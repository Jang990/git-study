package decoratorpattern.coffeetype;

import decoratorpattern.Beverage;

public class Espresso extends Beverage {
	
	public Espresso() {
		this.descroption = "����������";
	}
	
	@Override
	public double cost() {
		return 1.99;
	}

}
