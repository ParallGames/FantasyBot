package fantasyBot.item.weapon;

import fantasyBot.item.Item;
import fantasyBot.player.Ability;

public class WeaponItem extends Item{
	
	private int physicalDamage;
	private int magicDamage;
	private Ability weaponAbility;
	
	public WeaponItem(WeaponItemStats itemStats, int amount) {
		super(itemStats, amount);
		
		this.physicalDamage = itemStats.getPhysicalDamage();
		this.magicDamage = itemStats.getMagicDamage();
		this.weaponAbility = itemStats.getWeaponAbility();
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
