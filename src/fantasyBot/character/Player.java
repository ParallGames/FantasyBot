package fantasyBot.character;

public class Player extends Character {

	private String name;
	private int attackDamage;
	private int maxHealthPoints;

	public Player(String name, int attackDamage, int maxHealthPoints) {
		this.name = name;
		this.attackDamage = attackDamage;
		this.maxHealthPoints = maxHealthPoints;
		this.hp = maxHealthPoints;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getAttackDamages() {
		return attackDamage;
	}

	@Override
	public int getMaxHealthPoints() {
		return maxHealthPoints;
	}
}
