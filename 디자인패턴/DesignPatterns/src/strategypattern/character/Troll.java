package strategypattern.character;

import strategypattern.weapon.AxeBehavior;

public class Troll extends Character {
	public Troll() {
		setWeapon(new AxeBehavior());
	}
}
