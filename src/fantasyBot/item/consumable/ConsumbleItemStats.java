package fantasyBot.item.consumable;

import fantasyBot.item.ItemStats;

public class ConsumbleItemStats extends ItemStats{
	
	private ConsumableType type;
	private ConsumableRegenerationType regenerationType;
	private int valueOfRegeneration;

	public ConsumbleItemStats(int id, String name, String description, ConsumableType type, ConsumableRegenerationType regenerationType, int valueOfRegeneration) {
		super(id, name, description);
		
		this.type = type;
		this.regenerationType = regenerationType;
		this.valueOfRegeneration = valueOfRegeneration;
	}

	public ConsumableType getType() {
		return type;
	}

	public ConsumableRegenerationType getRegenerationType() {
		return regenerationType;
	}

	public int getValueOfRegeneration() {
		return valueOfRegeneration;
	}

}
