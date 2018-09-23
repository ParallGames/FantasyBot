package fantasyBot.item.weapon;

import fantasyBot.item.ItemStats;
import fantasyBot.player.Ability;

public class WeaponItemStats extends ItemStats{

	private int physicalDamage;
	private int magicDamage;
	private Ability weaponAbility;
	
	public WeaponItemStats(int id, String name, String description, int physicalDamage, int magicDamage, Ability weaponAbility) {
		super(id, name, description);
		
		this.physicalDamage = physicalDamage;
		this.magicDamage = magicDamage;
		this.weaponAbility = weaponAbility;
	}

	public int getPhysicalDamage() {
		return physicalDamage;
	}

	public void setPhysicalDamage(int physicalDamage) {
		this.physicalDamage = physicalDamage;
	}

	public int getMagicDamage() {
		return magicDamage;
	}

	public void setMagicDamage(int magicDamage) {
		this.magicDamage = magicDamage;
	}

	public Ability getWeaponAbility() {
		return weaponAbility;
	}

	public void setWeaponAbility(Ability weaponAbility) {
		this.weaponAbility = weaponAbility;
	}
}