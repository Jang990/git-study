package decoratorpattern.deco;

import decoratorpattern.Beverage;
import decoratorpattern.CondimentDecorator;

public class Mocha extends CondimentDecorator {
	Beverage beverage;
	
	public Mocha(Beverage beverage) {
		this.beverage = beverage;
	}
	
	@Override
	public String getDescroption() {
		return beverage.getDescroption() + " , 모카";
	}

	//137 연습문제를 위한 코드
	public int getSize() { //여기가 중요하다 감싸져있는 데코레이터들을 따라 getSize를 줄줄이 호출한다.
		return beverage.getSize();
	}
	
	@Override
	public double cost() {
		double cost = beverage.cost();
		if(getSize() == Beverage.GRANDE) {
			cost += .25;
		}
		else if(getSize() == Beverage.VENTY) {
			cost += .30;
		}
		else if(getSize() == Beverage.TALL){
			cost += .20;
		}
		return cost;
	}

}
