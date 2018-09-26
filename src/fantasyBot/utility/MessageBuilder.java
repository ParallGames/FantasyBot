package fantasyBot.utility;

import java.awt.Color;

import fantasyBot.character.Character;
import fantasyBot.character.Player;
import fantasyBot.player.Ability;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

public class MessageBuilder {
	private static final Color BATTLE_COLOR = Color.WHITE;
	private static final Color ENNEMY_COLOR = Color.RED;
	private static final Color PLAYER_COLOR = Color.BLUE;

	private static final Color ERROR_COLOR = Color.MAGENTA;
	private static final Color XP_COLOR = Color.GREEN;

	public static MessageEmbed createErrorMessage(String text) {
		EmbedBuilder message = new EmbedBuilder();
		message.setTitle("Erreur");
		message.setDescription(text);
		message.setColor(ERROR_COLOR);

		return message.build();
	}

	public static MessageEmbed createCombatIntroductionMessage(Player player, Character ennemy, Character starter) {
		EmbedBuilder message = new EmbedBuilder();
		message.setTitle("Le combat commence");

		String text = player.getName() + " contre " + ennemy.getName() + ".\n";

		if (player.getName().equals(starter.getName())) {
			text += "Vous commencez.";
		} else {
			text += "Votre adversaire commence.";
		}

		message.setDescription(text);

		message.setColor(BATTLE_COLOR);

		return message.build();
	}

	public static MessageEmbed createAttackChoiceMessage(Player player, Character ennemy) {
		EmbedBuilder message = new EmbedBuilder();

		message.setTitle("C'est à vous d'attaquer");

		String text = "Votre ennemi possède **" + ennemy.getHP() + "** points de vie sur **" + ennemy.getMaxHP()
				+ "**.\n";
		text += "Vous possèdez **" + player.getEnergy() + "** points d'énergie sur **" + player.getMaxEnergy()
				+ "**.\n";
		text += "Quelle attaque souhaitez-vous effectuer ?\n";

		message.setDescription(text);

		for (int i = 0; i < player.getAbilities().size(); i++) {
			Ability ability = player.getAbilities().get(i);

			String abilityText = "Coût : **" + ability.getEnergyCost() + "**\n";
			abilityText += "Dégats : **" + ability.getDamage() + "**\n";
			abilityText += ability.description();

			message.addField((i + 1) + ". " + ability.getName(), abilityText, false);
		}

		message.setColor(BATTLE_COLOR);

		return message.build();
	}

	public static MessageEmbed createDamageReceivedMessage(Player player, Character ennemy, Ability attack) {
		EmbedBuilder message = new EmbedBuilder();

		message.setTitle(ennemy.getName() + " vous a infligé **" + attack.getDamage() + "** dégats !");

		String text = "Il vous reste **" + player.getHP() + "** points de vie sur **" + player.getMaxHP() + "** !";
		message.setDescription(text);

		message.setColor(ENNEMY_COLOR);

		return message.build();
	}

	public static MessageEmbed createDamageDealtMessage(Player player, Character ennemy, Ability attack) {
		EmbedBuilder message = new EmbedBuilder();

		message.setTitle("Vous infligez **" + attack.getDamage() + "** dégats à " + ennemy.getName() + " !");

		String text = "Il lui reste **" + ennemy.getHP() + "** points de vie sur **" + ennemy.getMaxHP() + "** !";
		message.setDescription(text);

		message.setColor(PLAYER_COLOR);

		return message.build();
	}

	public static MessageEmbed createWinMessage(Player player, Character ennemy, Ability attack) {
		EmbedBuilder message = new EmbedBuilder();

		message.setTitle("Victoire");

		message.setDescription("Vous avez infligé **" + attack.getDamage() + "** dégats à " + ennemy.getName()
				+ ". Il en meurt sur le coup.");

		message.setColor(PLAYER_COLOR);

		return message.build();
	}

	public static MessageEmbed createDefeatMessage(Player player, Character ennemy, Ability attack) {
		EmbedBuilder message = new EmbedBuilder();

		message.setTitle("Défaite");

		message.setDescription(ennemy.getName() + " vous inflige une attaque létale. vous en mourez sur le coup.");

		message.setColor(ENNEMY_COLOR);

		return message.build();
	}

	public static MessageEmbed createXPGainMessage(int xpGain, int currentXP, int maxXP, int level) {
		EmbedBuilder message = new EmbedBuilder();

		message.setTitle("Expérience gagnée");

		message.setDescription("Vous remportez **" + xpGain + "** points d'expérience.\n" + "Vous avez **" + currentXP
				+ "** sur **" + maxXP + "** requis pour passer au niveau **" + (level + 1) + "**.");

		message.setColor(XP_COLOR);

		return message.build();
	}

	public static MessageEmbed createLevelUpMessage(int newLevel) {
		EmbedBuilder message = new EmbedBuilder();

		message.setTitle("Niveau gagné");

		message.setDescription("Félicitation vous passez au niveau **" + newLevel + "** !");

		message.setColor(XP_COLOR);

		return message.build();
	}
}
