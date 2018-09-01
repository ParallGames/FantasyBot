package fantasyBot.player;

import net.dv8tion.jda.core.entities.User;

public class PlayerStats {
	private Experience xp;

	private String name;

	private int maxHP;
	private int attackDamage;

	private long playerID;

	public PlayerStats(User player) {
		name = player.getName();

		playerID = player.getIdLong();

		maxHP = 10;
		attackDamage = 2;

		xp = new Experience(1, 0);
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
}
