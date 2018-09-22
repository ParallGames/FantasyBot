package fantasyBot.item.consumable;

import fantasyBot.item.ConsumbleItemStats;
import fantasyBot.item.Item;

public class ConsumableItem extends Item{

	private ConsumableType type;
	private ConsumableRegenerationType regenerationType;
	private int valueOfRegeneration;
	
	public ConsumableItem(ConsumbleItemStats consumableItemStats, int amount) {
		super(consumableItemStats, amount);
		
		this.type = consumableItemStats.getType();
		this.regenerationType = consumableItemStats.getRegenerationType();
		this.valueOfRegeneration = consumableItemStats.getValueOfRegeneration();
	}

	public ConsumableType getType() {
		return type;
	}

	public void setType(ConsumableType type) {
		this.type = type;
	}

	public ConsumableRegenerationType getRegenerationType() {
		return regenerationType;
	}

	public void setRegenerationType(ConsumableRegenerationType regenerationType) {
		this.regenerationType = regenerationType;
	}

	public int getValueOfRegeneration() {
		return valueOfRegeneration;
	}

	public void setValueOfRegeneration(int valueOfRegeneration) {
		this.valueOfRegeneration = valueOfRegeneration;
	}
	

}
