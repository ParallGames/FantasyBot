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
import net.dv8tion.jda.core.entities.User;

public class PlayerStats {
	private long playerID;
	private String name;

	private Experience xp;
	
	private ArrayList<Ability> abilitys;

	private int maxHP;
	private int maxEnergy;

	public PlayerStats(User player) {
		playerID = player.getIdLong();
		name = player.getName();
		
		abilitys = new ArrayList<Ability>();
		abilitys.add(Globals.getAbilitys().get(0));
		abilitys.add(Globals.getAbilitys().get(1)); //Add somes abilitys for test features
		abilitys.add(Globals.getAbilitys().get(2));

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
			
			abilitys = new ArrayList<Ability>();
			
			int nmbOfAbility = input.readInt();
			for(int i = 0; i < nmbOfAbility; i++) {
				int nextAbilityID = input.readInt();
				for(int j = 0; j < Globals.getAbilitys().size(); j++) {
					if(nextAbilityID == Globals.getAbilitys().get(j).getId()) {
						abilitys.add(Globals.getAbilitys().get(j));
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
			
			//Saves the number of ability the player has
			output.writeInt(abilitys.size());
			
			//Saves the id of the ability
			for(int i = 0; i < abilitys.size(); i++) {
				output.writeInt(abilitys.get(i).getId());
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

	public ArrayList<Ability> getAbilitys() {
		return abilitys;
	}
}
