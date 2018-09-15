package fantasyBot.character.ennemies;

import java.util.ArrayList;

import fantasyBot.character.Character;
import fantasyBot.player.Ability;

public class Monster extends Character {

	public Monster(String name, int health, int energy, ArrayList<Ability> abilityList) {
		this.name = name;
		this.hp = health;
		this.energy = energy;
		this.abilitys = abilityList;
	}

	@Override
	public int getMaxHealthPoints() {
		return 5;
	}

}
