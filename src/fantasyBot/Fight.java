package fantasyBot;

import fantasyBot.model.Character;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.PrivateChannel;

public class Fight {

	private Character player;
	private Character ia;
	private PrivateChannel channel;
	private

	public void runFight(Character player, Character ia, PrivateChannel channel) {
		
		this.player = player;
		this.ia = ia;
		this.channel = channel;

		channel.sendMessage("Vous avez commencé un duel !\n"
				+ "----------------------------\n"+
				player.getName() + " contre " + ia.getName() + " !").complete();

		channel.sendMessage("C'est à " + player.getName() + " de commencer !\n"
				+ "Quelle attaque souhaitez-vous effectuer ?\n" 
				+ "------------------------------------------\n" 
				+ "1. Attaque basique.").submit();
		
		Message message = channel.getMessageById(channel.getLatestMessageId()).complete();
		
		//message.addReaction("U+0030").complete(); Not Working

	}
	
	public void fightResponse() {
		 
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
