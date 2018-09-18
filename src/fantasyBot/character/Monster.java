package fantasyBot.character;

import fantasyBot.player.MonsterStats;

public class Monster extends Character {

	public Monster(MonsterStats monster) {
		this.name = monster.getName();
		this.hp = monster.getMaxHP();
		this.maxHP = monster.getMaxHP();
		this.energy = monster.getMaxEnergy();
		this.maxEnergy = monster.getMaxEnergy();
		this.abilities = monster.getAbilities();
	}
}
