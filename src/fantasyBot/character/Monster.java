package fantasyBot.character;

import java.util.ArrayList;

import fantasyBot.player.Ability;

public class Monster extends Character {
	
	private int id;

	public Monster(int id, String name, int maxHealth, int maxEnergy, ArrayList<Ability> abilityList) {
		this.id = id;
		this.name = name;
		this.hp = maxHealth;
		this.maxHealthPoints = maxHealth;
		this.energy = maxEnergy;
		this.maxEnergy = maxEnergy;
		this.abilitys = abilityList;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
