package adapterpattern.adapter;

import java.util.Random;

import adapterpattern.duck.Duck;
import adapterpattern.turkey.Turkey;

public class DuckAdapter implements Turkey {
	Duck duck;
	Random rand;
	
	public DuckAdapter(Duck duck) {
		this.duck = duck;
		rand = new Random();
	}
	
	@Override
	public void gobble() {
		duck.quack();
	}
	
	@Override
	public void fly() {
		//오리는 칠면조보다 오래 날기 때문에 5번에 한 번만 날 수 있도록 설정했다.
		if(rand.nextInt(5) == 0)
			duck.fly();
		
	}
}
