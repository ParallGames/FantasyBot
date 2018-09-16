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
	
	public Monster(Monster monster) {
		this.id = monster.getId();
		this.name = monster.getName();
		this.hp = monster.getMaxHealth();
		this.maxHealthPoints = monster.getMaxHealth();
		this.energy = monster.getMaxEnergy();
		this.maxEnergy = monster.getMaxEnergy();
		this.abilitys = monster.getAbilitys();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
