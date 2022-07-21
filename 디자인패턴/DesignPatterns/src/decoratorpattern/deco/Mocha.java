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
		return beverage.getDescroption() + " , ��ī";
	}

	//137 ���������� ���� �ڵ�
	public int getSize() { //���Ⱑ �߿��ϴ� �������ִ� ���ڷ����͵��� ���� getSize�� ������ ȣ���Ѵ�.
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
