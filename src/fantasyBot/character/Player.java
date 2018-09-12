package fantasyBot.character;

import java.util.ArrayList;

import fantasyBot.Main;
import fantasyBot.player.Ability;
import fantasyBot.player.PlayerStats;
import net.dv8tion.jda.core.entities.PrivateChannel;

public class Player extends Character {

	private String name;
	private int maxHealthPoints;
	private ArrayList<Ability> abilitys;

	private PrivateChannel channel;
	private long playerID;

	public Player(PlayerStats stats) {
		this.name = stats.getName();

		this.maxHealthPoints = stats.getMaxHP();
		this.hp = stats.getMaxHP();
		
		this.abilitys = stats.getAbilitys();

		this.channel = Main.getJda().getUserById(stats.getPlayerID()).openPrivateChannel().complete();
		this.playerID = stats.getPlayerID();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getMaxHealthPoints() {
		return maxHealthPoints;
	}

	public PrivateChannel getPrivateChannel() {
		return channel;
	}

	public long getPlayerID() {
		return playerID;
	}

	public ArrayList<Ability> getAbilitys() {
		return abilitys;
	}
}
