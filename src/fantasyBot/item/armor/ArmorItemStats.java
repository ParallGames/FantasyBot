package fantasyBot.item.armor;

import fantasyBot.item.ItemStats;

public class ArmorItemStats extends ItemStats{

	private ArmorType type;
	private int physicalArmorValue;
	private int magicArmorValue;
	
	public ArmorItemStats(int id, String name, String description, ArmorType type, int physicalArmorValue, int magicArmorValue) {
		super(id, name, description);
		
		this.type = type;
		this.physicalArmorValue = physicalArmorValue;
		this.magicArmorValue = magicArmorValue;
	}

	public ArmorType getType() {
		return type;
	}

	public int getPhysicalArmorValue() {
		return physicalArmorValue;
	}
	
	public int getMagicArmorValue() {
		return magicArmorValue;
	}

}
