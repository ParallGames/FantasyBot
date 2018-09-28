package fantasyBot.character;

import java.util.ArrayList;

import fantasyBot.item.Item;
import fantasyBot.item.armor.ArmorItem;
import fantasyBot.player.Ability;

public abstract class Character {

	protected String name;
	protected int hp;
	protected int maxHP;
	protected int energy;
	protected int maxEnergy;

	protected ArrayList<Ability> abilities;
	
	protected ArrayList<Item> listeOfItems;
	protected ArrayList<ArmorItem> equipedArmor;

	public String getName() {
		return name;
	}

	public int getHP() {
		return hp;
	}

	public int getMaxHP() {
		return maxHP;
	}

	public int getEnergy() {
		return energy;
	}

	public ArrayList<Ability> getAbilities() {
		return abilities;
	}

	public void recieveDamage(int damages) {
		hp -= damages;
	}

	public boolean isDead() {
		return hp < 1;
	}

	public int getMaxEnergy() {
		return maxEnergy;
	}

	public void setMaxEnergy(int maxEnergy) {
		this.maxEnergy = maxEnergy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public ArrayList<ArmorItem> getEquipedArmor() {
		return equipedArmor;
	}

	public void setEquipedArmor(ArrayList<ArmorItem> equipedArmor) {
		this.equipedArmor = equipedArmor;
	}

	public ArrayList<Item> getListeOfItems() {
		return listeOfItems;
	}

	public void setListeOfItems(ArrayList<Item> listeOfItems) {
		this.listeOfItems = listeOfItems;
	}
}
