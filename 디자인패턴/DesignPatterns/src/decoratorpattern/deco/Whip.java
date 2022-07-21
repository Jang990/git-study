package decoratorpattern.deco;

import decoratorpattern.Beverage;
import decoratorpattern.CondimentDecorator;

public class Whip extends CondimentDecorator{
	Beverage beverage;
	
	public Whip(Beverage beverage) {
		this.beverage = beverage;
	}
	
	@Override
	public String getDescroption() {
		return beverage.getDescroption() + ", »÷«Œ≈©∏≤";
	}

	@Override
	public double cost() {
		return .10 + beverage.cost();
	}
	
}
