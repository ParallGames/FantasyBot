package fantasyBot.player;

public class Ability {

	private int id;
	private String name;
	private int damage;
	private int energyCost;
	private String description;
	private String attackDescription;

	public Ability(int id, String name, int damage, int energyCost, String description, String attackDescription) {
		this.id = id;
		this.name = name;
		this.damage = damage;
		this.energyCost = energyCost;
		this.description = description;
		this.attackDescription = attackDescription;
	}

	public String getName() {
		return name;
	}

	public int getEnergyCost() {
		return energyCost;
	}

	public String description() {
		return description;
	}

	public String attackDescription() {
		return attackDescription;
	}

	public int getDamage() {
		return damage;
	}

	public int getId() {
		return id;
	}
}
