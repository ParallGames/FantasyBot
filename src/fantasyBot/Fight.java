package fantasyBot;

import fantasyBot.model.Character;
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

	public void fightResponse() {

		//TODO: Does damage with capacity

		ia.setActualHealthPoints(ia.getActualHealthPoints() - player.getAttackPoints());

		if(ia.getActualHealthPoints() <= 0) {
			endFight();
		}else {
			channel.sendMessage("Vous avez infligé " + player.getAttackPoints() + " à " + ia.getName() + " ! "
					+ "Il lui reste " + ia.getActualHealthPoints() + " sur " + ia.getMaxHealthPoints() + " !").complete();
			
			player.setActualHealthPoints(player.getActualHealthPoints() - ia.getAttackPoints());

			if(player.getActualHealthPoints() <= 0) {
				channel.sendMessage(ia.getName() + " vous a infligé " + ia.getAttackPoints() + " ! Vous mourrez sur le coup !\n"
						+ "Vous n'êtes pas très fort pour mourir :/");
			}
			
			channel.sendMessage(ia.getName() + " vous a infligé " + ia.getAttackPoints() + " ! "
					+ "Il vous reste " + player.getActualHealthPoints() + " sur " + player.getMaxHealthPoints() + " !").complete();
			
			choosingPhase();
		}
	}

	private void choosingPhase() {

		channel.sendMessage("C'est à " + player.getName() + " de jouer !\n"
				+ "Votre ennemie poss�de " + ia.getActualHealthPoints() + " points de vie sur " + ia.getMaxHealthPoints() + ".\n"
				+ "Quelle attaque souhaitez-vous effectuer ?\n" 
				+ "------------------------------------------\n" 
				+ "1. Attaque basique.").submit();
	}
	
	private void endFight() {
		channel.sendMessage("Vous avez infligé " + player.getAttackPoints() + " à " + ia.getName() + " ! Il meurt sur ce coup !\n"
				+ "Félicitation ! Vous remportez : DE L'EXP MAIS PAS TOUS DE SUITE ON TRAVAIL !").complete();
		
		EventListener.getFightInProgresse().remove(this);
		
	}

	public Character getPlayer() {
		return player;
	}

	public Character getIa() {
		return ia;
	}

	public PrivateChannel getChannel() {
		return channel;
	}
}
