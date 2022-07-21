package adapterpattern.turkey;

public class WildTurkey implements Turkey {
	@Override
	public void gobble() {
		System.out.println("°ñ°ñ");
	}
	
	@Override
	public void fly() {
		System.out.println("Àú´Â Àá±ñ ³¯ ¼ö ÀÖ¾î¿ä");
	}
}
