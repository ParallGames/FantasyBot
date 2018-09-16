package fantasyBot.player;

import java.util.ArrayList;

import fantasyBot.character.Monster;

public class MonsterStats {
	
	private int id;
	private String name;
	private int maxHp;
	private int maxEnergy;
	private ArrayList<Ability> abilities = new ArrayList<Ability>();
	
	public MonsterStats(int id, String name, int maxHp, int maxEnergy, ArrayList<Ability> abilities) {
		this.id = id;
		this.name = name;
		this.maxHp = maxHp;
		this.maxEnergy = maxEnergy;
		this.abilities = abilities;
	}
	
	public Monster createMonster() {
		return new Monster(id, name, maxHp, maxEnergy, abilities);
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getMaxHp() {
		return maxHp;
	}

	public int getMaxEnergy() {
		return maxEnergy;
	}

	public ArrayList<Ability> getAbilities() {
		return abilities;
	}
}
