package fantasyBot.player;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import fantasyBot.Main;
import net.dv8tion.jda.core.entities.User;

public class PlayerStats {
	private long playerID;
	private String name;

	private Experience xp;

	private int maxHP;
	private int attackDamage;

	public PlayerStats(User player) {
		playerID = player.getIdLong();
		name = player.getName();

		maxHP = 10;
		attackDamage = 2;

		xp = new Experience(1, 0);
	}

	public PlayerStats(File save) {
		try {
			DataInputStream input = new DataInputStream(new FileInputStream(save));

			playerID = input.readLong();
			name = Main.getJda().getUserById(playerID).getName();

			xp = new Experience(input.readInt(), input.readInt());

			input.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Error loading players");
		}

		maxHP = 10;
		attackDamage = 2;
	}

	public String getName() {
		return name;
	}

	public int getMaxHP() {
		return maxHP;
	}

	public int getAttackDamage() {
		return attackDamage;
	}

	public long getPlayerID() {
		return playerID;
	}

	public Experience getExperience() {
		return xp;
	}

	public void save(String folder) {
		File file = new File(folder + "/" + String.valueOf(playerID));

		try {
			DataOutputStream output = new DataOutputStream(new FileOutputStream(file));

			output.writeLong(playerID);
			output.writeInt(xp.getLevel());
			output.writeInt(xp.getLevelActualPoints());

			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
