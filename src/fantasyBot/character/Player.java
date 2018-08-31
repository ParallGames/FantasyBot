package fantasyBot.character;

public class Player extends Character {

	private String name;
	private int attackDamage;
	private int maxHealthPoints;
	private Experience experience;

	public Player(String name, int attackDamage, int maxHealthPoints) {
		this.name = name;
		this.attackDamage = attackDamage;
		this.maxHealthPoints = maxHealthPoints;
		this.hp = maxHealthPoints;
		experience = new Experience(1, 0);
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

	public Experience getExperience() {
		return experience;
	}

	public void setExperience(Experience experience) {
		this.experience = experience;
	}
}
