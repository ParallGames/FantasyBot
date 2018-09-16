package fantasyBot.character;

import java.util.ArrayList;

import fantasyBot.player.Ability;

public class Monster extends Character {

	public Monster(int id, String name, int maxHealth, int maxEnergy, ArrayList<Ability> abilityList) {
		this.name = name;
		this.hp = maxHealth;
		this.maxHealthPoints = maxHealth;
		this.energy = maxEnergy;
		this.maxEnergy = maxEnergy;
		this.abilities = abilityList;
	}
	
	public Monster(Monster monster) {
		this.name = monster.getName();
		this.hp = monster.getMaxHealth();
		this.maxHealthPoints = monster.getMaxHealth();
		this.energy = monster.getMaxEnergy();
		this.maxEnergy = monster.getMaxEnergy();
		this.abilities = monster.getAbilities();
	}
}
