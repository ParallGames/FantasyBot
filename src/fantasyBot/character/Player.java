package fantasyBot.character;

import fantasyBot.Main;
import fantasyBot.player.PlayerStats;
import net.dv8tion.jda.core.entities.PrivateChannel;

public class Player extends Character {

	private PrivateChannel channel;
	private long playerID;

	public Player(PlayerStats stats) {
		this.name = stats.getName();

		this.maxHP = stats.getMaxHP();
		this.hp = stats.getMaxHP();
		this.energy = stats.getMaxEnergy();
		this.maxEnergy = stats.getMaxEnergy();
		
		this.abilities = stats.getAbilities();

		this.channel = Main.getJda().getUserById(stats.getPlayerID()).openPrivateChannel().complete();
		this.playerID = stats.getPlayerID();
	}

	public PrivateChannel getPrivateChannel() {
		return channel;
	}

	public long getPlayerID() {
		return playerID;
	}
}
