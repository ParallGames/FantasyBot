package fantasyBot.model;

public class Character {
	
	private String name;
	private int attackPoints;
	private int maxHealthPoints;
	private int actualHealthPoints;
	
	public Character(String name, int attackPoints, int healthPoints) {
		this.name = name;
		this.attackPoints = attackPoints;
		this.maxHealthPoints = healthPoints;
		actualHealthPoints = maxHealthPoints;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAttackPoints() {
		return attackPoints;
	}

	public void setAttackPoints(int attackPoints) {
		this.attackPoints = attackPoints;
	}

	public int getMaxHealthPoints() {
		return maxHealthPoints;
	}

	public void setMaxHealthPoints(int healthPoints) {
		this.maxHealthPoints = healthPoints;
	}

	public int getActualHealthPoints() {
		return actualHealthPoints;
	}

	public void setActualHealthPoints(int actualHealthPoints) {
		this.actualHealthPoints = actualHealthPoints;
	}
}
