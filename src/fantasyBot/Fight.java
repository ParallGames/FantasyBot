package fantasyBot;

import fantasyBot.character.Character;
import fantasyBot.character.Player;
import net.dv8tion.jda.core.entities.PrivateChannel;

public class Fight {

	private Player player1;
	private Player player2;
	private PrivateChannel channel;

	public void runFight(Player player1, Player player2, PrivateChannel channel) {

		this.player1 = player1;
		this.player2 = player2;
		this.channel = channel;

		channel.sendMessage("Vous avez commencé un duel !\n" + "----------------------------\n" + player1.getName()
				+ " contre " + player2.getName() + " !").complete();

		choosingPhase();

		// Message message =
		// channel.getMessageById(channel.getLatestMessageId()).complete();

		// message.addReaction("U+0030").complete(); Not Working
	}

	public void fightResponse() {

		// TODO: Does damage with capacity

		player2.recieveDamage(player1.getAttackDamages());

		if (player2.isDead()) {
			endFight();
		} else {
			channel.sendMessage("Vous avez infligé " + player1.getAttackDamages() + " à " + player2.getName() + " ! "
					+ "Il lui reste " + player2.getHP() + " sur " + player2.getMaxHealthPoints() + " !").complete();

			player1.recieveDamage(player2.getAttackDamages());

			if (player1.isDead()) {
				channel.sendMessage(player2.getName() + " vous a infligé " + player2.getAttackDamages()
						+ " ! Vous mourrez sur le coup !\n" + "Vous n'êtes pas très fort pour mourir :/");
			}

			channel.sendMessage(player2.getName() + " vous a infligé " + player2.getAttackDamages() + " ! "
					+ "Il vous reste " + player1.getHP() + " sur " + player1.getMaxHealthPoints() + " !").complete();

			choosingPhase();
		}
	}

	private void choosingPhase() {

		channel.sendMessage("C'est à " + player1.getName() + " de jouer !\n" + "Votre ennemie possède "
				+ player2.getHP() + " points de vie sur " + player2.getMaxHealthPoints() + ".\n"
				+ "Quelle attaque souhaitez-vous effectuer ?\n" + "------------------------------------------\n"
				+ "1. Attaque basique.").submit();
	}

	private void endFight() {
		channel.sendMessage("Vous avez infligé " + player1.getAttackDamages() + " à " + player2.getName()
				+ " ! Il meurt sur ce coup !\n"
				+ "Félicitation ! Vous remportez " + 5 + "d'exp !").complete();
		
		player1.getExperience().addExperience(5, channel);
		
		EventListener.getFightInProgresse().remove(this);

	}

	public Character getPlayer() {
		return player1;
	}

	public Character getIa() {
		return player2;
	}

	public PrivateChannel getChannel() {
		return channel;
	}
}
