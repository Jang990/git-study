package strategypattern.character;

import strategypattern.weapon.WeaponBehavior;

public class Character {
	private WeaponBehavior weapon;
	
	public void setWeapon(WeaponBehavior weapon) {
		this.weapon = weapon;
	}
	
	public void fight() {
		weapon.useWeapon();
	}
}
