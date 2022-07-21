package strategypattern.weapon;

public class BowAndArrowBehavior implements WeaponBehavior {

	@Override
	public void useWeapon() {
		System.out.println("활을 이용해서 쏜다.");
	}

}
