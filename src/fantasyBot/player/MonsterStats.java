package fantasyBot.player;

import java.util.ArrayList;

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

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getMaxHP() {
		return maxHp;
	}

	public int getMaxEnergy() {
		return maxEnergy;
	}

	public ArrayList<Ability> getAbilities() {
		return abilities;
	}
}
