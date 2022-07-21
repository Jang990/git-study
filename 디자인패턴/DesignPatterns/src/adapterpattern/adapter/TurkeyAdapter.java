package adapterpattern.adapter;

import adapterpattern.duck.Duck;
import adapterpattern.turkey.Turkey;

public class TurkeyAdapter implements Duck {
	//ĥ������ ������ �ٲ��ִ� ���
	Turkey turkey;
	
	public TurkeyAdapter(Turkey turkey) {
		this.turkey = turkey;
	}
	
	@Override
	public void quack() {
		turkey.gobble();
	}
	
	@Override
	public void fly() {
		//turkey.fly();
		//ĥ������ ���� ���� ���մϴ�. ���� �� �� �ֵ��� ������մϴ�.
		for (int i = 0; i < 5; i++) {
			turkey.fly();
		}
	}
	
}
