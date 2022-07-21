package decoratorpattern.coffeetype;

import decoratorpattern.Beverage;

public class HouseBlend extends Beverage {
	public HouseBlend() {
		descroption = "하우스 블렌드";
	}
	
	@Override
	public double cost() {
		return .89;
	}
}
