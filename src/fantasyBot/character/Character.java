package fantasyBot.character;

import java.util.ArrayList;

import fantasyBot.player.Ability;

public abstract class Character {

	protected String name;
	protected int hp;
	protected int maxHealthPoints;
	protected int energy;
	
	protected ArrayList<Ability> abilitys;
	
	public abstract int getMaxHealthPoints();

	public String getName() {
		return name;
	}
	
	public int getHP() {
		return hp;
	}
	
	public int getMaxHealth() {
		return maxHealthPoints;
	}
	
	public int getEnergy() {
		return energy;
	}

	public ArrayList<Ability> getAbilitys() {
		return abilitys;
	}
	
	public void recieveDamage(int damages) {
		hp -= damages;
	}

	public boolean isDead() {
		return hp < 1;
	}
}
