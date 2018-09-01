package fantasyBot.character;

import fantasyBot.Main;
import fantasyBot.player.PlayerStats;
import net.dv8tion.jda.core.entities.PrivateChannel;

public class Player extends Character {

	private String name;
	private int attackDamage;
	private int maxHealthPoints;

	private PrivateChannel channel;
	private long playerID;

	public Player(PlayerStats stats) {
		this.name = stats.getName();
		this.attackDamage = stats.getAttackDamage();

		this.maxHealthPoints = stats.getMaxHP();
		this.hp = stats.getMaxHP();

		this.channel = Main.getJda().getUserById(stats.getPlayerID()).openPrivateChannel().complete();
		this.playerID = stats.getPlayerID();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getAttackDamages() {
		return attackDamage;
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
}
