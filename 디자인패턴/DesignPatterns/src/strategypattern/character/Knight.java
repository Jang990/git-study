package strategypattern.character;

import strategypattern.weapon.KnifeBehavior;

public class Knight extends Character {
	public Knight() {
		setWeapon(new KnifeBehavior());
	}
}
