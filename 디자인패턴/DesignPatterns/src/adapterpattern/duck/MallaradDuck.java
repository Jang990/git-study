package adapterpattern.duck;

public class MallaradDuck implements Duck {
	@Override
	public void quack() {
		System.out.println("²Ğ²Ğ");
	}
	@Override
	public void fly() {
		System.out.println("Àú´Â ³¯ ¼ö ÀÖ¾î¿ä");
	}
}
