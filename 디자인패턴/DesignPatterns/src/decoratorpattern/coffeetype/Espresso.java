package decoratorpattern.coffeetype;

import decoratorpattern.Beverage;

public class Espresso extends Beverage {
	
	public Espresso() {
		this.descroption = "에스프레소";
	}
	
	@Override
	public double cost() {
		return 1.99;
	}

}
