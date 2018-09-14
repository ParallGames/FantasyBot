package fantasyBot.player;

public class Ability {

	private int id;
	private String name;
	private int damage;
	private int energyCost;
	private String abilityDescription;
	private String abilityAttackDescription;
	
	public Ability(int id, String name, int damage, int energyCost, String abilityDescription, String abilityAttackDescription) {
		this.id = id;
		this.name = name;
		this.damage = damage;
		this.energyCost = energyCost;
		this.abilityDescription = abilityDescription;
		this.abilityAttackDescription = abilityAttackDescription;
	}

	public String getName() {
		return name;
	}

	public int getEnergyCost() {
		return energyCost;
	}
	
	public String getAbilityDescription() {
		return abilityDescription;
	}

	public String getAbilityAttackDescription() {
		return abilityAttackDescription;
	}

	public int getDamage() {
		return damage;
	}

	public int getId() {
		return id;
	}
}
