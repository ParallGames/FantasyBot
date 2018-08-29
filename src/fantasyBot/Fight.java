package fantasyBot;

import fantasyBot.model.Character;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.PrivateChannel;

public class Fight {

	private Character player;
	private Character ia;
	private PrivateChannel channel;
	
	public void runFight(Character player, Character ia, PrivateChannel channel) {
		
		this.player = player;
		this.ia = ia;
		this.channel = channel;

		channel.sendMessage("Vous avez commencé un duel !\n"
				+ "----------------------------\n"+
				player.getName() + " contre " + ia.getName() + " !").complete();
		
		choosingPhase();
		
		//Message message = channel.getMessageById(channel.getLatestMessageId()).complete();
		
		//message.addReaction("U+0030").complete(); Not Working
	}
	
	public void fightResponse(String attackChoice) {
		 
		int capacityNbr = Integer.parseInt(attackChoice);
		
		//TODO: Does damage with capacity
		
		ia.setActualHealthPoints(ia.getActualHealthPoints() - player.getAttackPoints());
		
		channel.sendMessage("Vous avez infligé " + player.getAttackPoints() + " à " + ia.getName() + " ! "
				+ "Il lui reste " + ia.getActualHealthPoints() + " sur " + ia.getMaxHealthPoints() + " !").complete();
		
		
		player.setActualHealthPoints(player.getActualHealthPoints() - ia.getAttackPoints());
		
		channel.sendMessage(ia.getName() + " vous a infligé " + ia.getAttackPoints() + " ! "
				+ "Il vous reste " + player.getActualHealthPoints() + " sur " + player.getMaxHealthPoints() + " !").complete();
		
		choosingPhase();
	}
	
	private void choosingPhase() {

		channel.sendMessage("C'est à " + player.getName() + " de jouer !\n"
				+ "Votre ennemie possède " + ia.getActualHealthPoints() + " points de vie sur " + ia.getMaxHealthPoints() + ".\n"
				+ "Quelle attaque souhaitez-vous effectuer ?\n" 
				+ "------------------------------------------\n" 
				+ "1. Attaque basique.").submit();
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
