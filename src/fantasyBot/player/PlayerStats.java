package fantasyBot.player;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import fantasyBot.Globals;
import fantasyBot.Main;
import fantasyBot.item.Item;
import fantasyBot.item.armor.ArmorItem;
import fantasyBot.item.armor.ArmorItemStats;
import jdk.nashorn.internal.objects.Global;
import net.dv8tion.jda.core.entities.User;

public class PlayerStats {
	private long playerID;
	private String name;

	private Experience xp;

	private ArrayList<Ability> abilities;
	
	private ArrayList<Item> items;
	private ArrayList<ArmorItem> equipedArmorItem;

	private int maxHP;
	private int maxEnergy;

	public PlayerStats(User player) {
		playerID = player.getIdLong();
		name = player.getName();

		abilities = new ArrayList<Ability>();
		abilities.add(Globals.getAbilities().get(0));
		abilities.add(Globals.getAbilities().get(1)); // Add somes abilities for test features
		abilities.add(Globals.getAbilities().get(2));
		abilities.add(Globals.getAbilities().get(3));

		maxHP = 10;
		maxEnergy = 100;

		xp = new Experience(1, 0);
	}

	public PlayerStats(File save) {
		try {
			DataInputStream input = new DataInputStream(new FileInputStream(save));

			playerID = input.readLong();
			name = Main.getJda().getUserById(playerID).getName();

			xp = new Experience(input.readInt(), input.readInt());

			maxHP = input.readInt();
			maxEnergy = input.readInt();

			abilities = new ArrayList<Ability>();

			int nmbOfAbility = input.readInt();
			for (int i = 0; i < nmbOfAbility; i++) {
				int nextAbilityID = input.readInt();
				for (int j = 0; j < Globals.getAbilities().size(); j++) {
					if (nextAbilityID == Globals.getAbilities().get(j).getId()) {
						abilities.add(Globals.getAbilities().get(j));
					}
				}
			}

			input.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Error loading players");
		}

		maxHP = 10;
	}

	public void save(String folder) {
		File file = new File(folder + "/" + String.valueOf(playerID));

		try {
			DataOutputStream output = new DataOutputStream(new FileOutputStream(file));

			output.writeLong(playerID);
			output.writeInt(xp.getLevel());
			output.writeInt(xp.getLevelActualPoints());

			output.writeInt(maxHP);
			output.writeInt(maxEnergy);

			// Saves the number of ability the player has
			output.writeInt(abilities.size());

			// Saves the id of the ability
			for (int i = 0; i < abilities.size(); i++) {
				output.writeInt(abilities.get(i).getId());
			}

			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getName() {
		return name;
	}

	public int getMaxHP() {
		return maxHP;
	}

	public long getPlayerID() {
		return playerID;
	}

	public Experience getExperience() {
		return xp;
	}

	public int getMaxEnergy() {
		return maxEnergy;
	}

	public void setMaxEnergy(int maxEnergie) {
		this.maxEnergy = maxEnergie;
	}

	public ArrayList<Ability> getAbilities() {
		return abilities;
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}

	public ArrayList<ArmorItem> getEquipedArmorItem() {
		return equipedArmorItem;
	}

	public void setEquipedArmorItem(ArrayList<ArmorItem> equipedArmorItem) {
		this.equipedArmorItem = equipedArmorItem;
	}
}
