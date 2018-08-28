package fantasyBot.model;

public class Character {
	
	private String name;
	private int attackPoints;
	private int healthPoints;
	
	public Character(String name, int attackPoints, int healthPoints) {
		this.name = name;
		this.attackPoints = attackPoints;
		this.healthPoints = healthPoints;
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

	public int getHealthPoints() {
		return healthPoints;
	}

	public void setHealthPoints(int healthPoints) {
		this.healthPoints = healthPoints;
	}
}
