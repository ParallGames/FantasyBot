package fantasyBot;

import fantasyBot.model.Character;
import net.dv8tion.jda.core.entities.PrivateChannel;

public class Fight {

	private Character player;
	private Character ia;
	private PrivateChannel channel;
	
	public void runFight(Character player, Character ia, PrivateChannel channel) {
		
		
		
	}

	public Character getPlayer() {
		return player;
	}

	public void setPlayer(Character joueur) {
		player = joueur;
	}

	public Character getIa() {
		return ia;
	}

	public void setIa(Character ia) {
		this.ia = ia;
	}

	public PrivateChannel getChannel() {
		return channel;
	}

	public void setChannel(PrivateChannel channel) {
		this.channel = channel;
	}
	
}
