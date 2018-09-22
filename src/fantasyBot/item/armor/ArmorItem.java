package fantasyBot.item.armor;

import fantasyBot.item.ArmorItemStats;
import fantasyBot.item.Item;

public class ArmorItem extends Item{

	private ArmorType type;
	private int physicalArmorValue;
	private int magicArmorValue;
	
	public ArmorItem(ArmorItemStats armorItemStats, int amount) {
		super(armorItemStats, amount);
		
		this.type = armorItemStats.getType();
		this.physicalArmorValue = armorItemStats.getPhysicalArmorValue();
		this.magicArmorValue = armorItemStats.getMagicArmorValue();
	}

	public ArmorType getType() {
		return type;
	}

	public void setType(ArmorType type) {
		this.type = type;
	}

	public int getPhysicalArmorValue() {
		return physicalArmorValue;
	}

	public void setPhysicalArmorValue(int physicalArmorValue) {
		this.physicalArmorValue = physicalArmorValue;
	}

	public int getMagicArmorValue() {
		return magicArmorValue;
	}

	public void setMagicArmorValue(int magicArmorValue) {
		this.magicArmorValue = magicArmorValue;
	}
}
