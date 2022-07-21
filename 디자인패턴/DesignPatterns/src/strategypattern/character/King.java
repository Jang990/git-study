package strategypattern.character;

import strategypattern.weapon.SwordBehavior;

public class King extends Character {
	public King() {
		setWeapon(new SwordBehavior());
	}
}
