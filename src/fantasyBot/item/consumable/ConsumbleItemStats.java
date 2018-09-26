package fantasyBot.item.consumable;

import fantasyBot.item.ItemStats;

public class ConsumbleItemStats extends ItemStats{
	
	private ConsumableType type;
	private ConsumableRegenerationType regenerationType;
	private int valueOfRegeneration;
	private int turnOfRegeneration;

	public ConsumbleItemStats(int id, String name, String description,
			ConsumableType type, ConsumableRegenerationType regenerationType, int valueOfRegeneration, int numberOfTurnRegeneration) {
		super(id, name, description);
		
		this.type = type;
		this.regenerationType = regenerationType;
		this.valueOfRegeneration = valueOfRegeneration;
		this.turnOfRegeneration = numberOfTurnRegeneration;
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

	public int getTurnOfRegeneration() {
		return turnOfRegeneration;
	}

}
