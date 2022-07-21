package adapterpattern.adapter;

import adapterpattern.duck.Duck;
import adapterpattern.turkey.Turkey;

public class TurkeyAdapter implements Duck {
	//칠면조를 오리로 바꿔주는 어뎁터
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
		//칠면조는 오래 날지 못합니다. 오래 날 수 있도록 해줘야합니다.
		for (int i = 0; i < 5; i++) {
			turkey.fly();
		}
	}
	
}
