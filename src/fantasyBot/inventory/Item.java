package fantasyBot.inventory;

public class Item {
	
	private int amount;
	private String name;
	private String description;

	public Item(ItemStats itemStats, int amount) {
		this.amount = amount;
		this.name = itemStats.getName();
		this.description = itemStats.getDescription();
	}
	
	public void add(int amont) {
		this.amount += amont;
	}
	
	public void removeAmount(int amont) throws Exception {
		if((this.amount - amont) <= 0) {
			throw new Exception("You can't have a negative amount of a item !");
		} else {
			this.amount -= amont;
		}
	}
	
	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
