package decoratorpattern.coffeetype;

import decoratorpattern.Beverage;

public class HouseBlend extends Beverage {
	public HouseBlend() {
		descroption = "�Ͽ콺 ����";
	}
	
	@Override
	public double cost() {
		return .89;
	}
}
