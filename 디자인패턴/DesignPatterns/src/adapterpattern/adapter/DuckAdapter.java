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
		//������ ĥ�������� ���� ���� ������ 5���� �� ���� �� �� �ֵ��� �����ߴ�.
		if(rand.nextInt(5) == 0)
			duck.fly();
		
	}
}
