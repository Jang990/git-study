package adapterpattern.duck;

public class MallaradDuck implements Duck {
	@Override
	public void quack() {
		System.out.println("�в�");
	}
	@Override
	public void fly() {
		System.out.println("���� �� �� �־��");
	}
}
