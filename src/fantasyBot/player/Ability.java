package fantasyBot.player;

public class Ability {

	private String name;
	private int damage;
	private int energyCost;
	private String abilityDescription;
	private String abilityAttackDescription;
	
	public Ability(String name, int damage, int energyCost, String abilityDescription, String abilityAttackDescription) {
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

}
