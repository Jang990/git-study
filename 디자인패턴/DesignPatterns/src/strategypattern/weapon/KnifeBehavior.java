package strategypattern.weapon;

public class KnifeBehavior implements WeaponBehavior {

	@Override
	public void useWeapon() {
		System.out.println("나이프로 찌른다!");
	}

}
