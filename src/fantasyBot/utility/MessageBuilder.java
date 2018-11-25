package fantasyBot.utility;

import java.awt.Color;
import java.util.ArrayList;

import fantasyBot.character.Character;
import fantasyBot.character.Player;
import fantasyBot.item.consumable.ConsumableItem;
import fantasyBot.player.Ability;
import fantasyBot.player.PlayerStats;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.MessageEmbed.Field;

public class MessageBuilder {
	
	private static final int MAX_ITEMS_ON_PAGE = 10;
	
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

	public static MessageEmbed createQuestionValidationFights(Player player) {
		EmbedBuilder message = new EmbedBuilder();
		message.setTitle(player.getName() + "vous défie en duel !");

		message.setDescription("Acceptez-vous le duel ?");

		return message.build();
	}

	public static MessageEmbed createWaitValidationMessage(Player ennemy) {
		EmbedBuilder message = new EmbedBuilder();
		message.setTitle("Attente d'acceptation du match");

		message.setDescription("Attente de validation du duel avec " + ennemy.getName());

		return message.build();
	}

	public static MessageEmbed createRefuseFight(Player ennemy) {
		EmbedBuilder message = new EmbedBuilder();
		message.setTitle("Match refusé !");

		message.setDescription(ennemy.getName() + " a refusé le combat !");

		message.setColor(ENNEMY_COLOR);

		return message.build();
	}

	public static MessageEmbed createConfirmationMessage(Player ennemy) {
		EmbedBuilder message = new EmbedBuilder();
		message.setTitle("Confirmation");

		message.setDescription("La proposition de duel contre " + ennemy.getName() + " a bien été décliné !");

		message.setColor(Color.GREEN);

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
	
	public static MessageEmbed createItemSelectionMessage(ArrayList<ConsumableItem> usableItem, int pageNmb) {
		EmbedBuilder message = new EmbedBuilder();
		
		message.setTitle("Inventaires");
		
		int maxSize;
		
		if(usableItem.size() > MAX_ITEMS_ON_PAGE) {
			maxSize = MAX_ITEMS_ON_PAGE;
		}else {
			maxSize = usableItem.size();
		}
		
		int nmbMaxPage = (Integer)usableItem.size() / MAX_ITEMS_ON_PAGE;
		
		message.setDescription("Votre inventaires contient " + usableItem.size() + "objets différents utilisables\n"
				+ "Affichage de la page N* " + pageNmb + " sur un total de " + nmbMaxPage);
		
		for(int i = 0; i < maxSize; i++) {
			
			int itemNumber = ((MAX_ITEMS_ON_PAGE * pageNmb) - MAX_ITEMS_ON_PAGE) + 1;
			
			Field itemField = new Field(itemNumber + ". " + usableItem.get(itemNumber - 1).getName(),
							usableItem.get(itemNumber - 1).getDescription() + "\n"
							+ "Nombres : " + usableItem.get(itemNumber - 1).getAmount() + "", true);
			
			message.addField(itemField);
		}
		
		
		return null;
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

	public static MessageEmbed createTopPlayersMessage(ArrayList<PlayerStats> bestPlayers) {
		EmbedBuilder message = new EmbedBuilder();

		message.setColor(Color.YELLOW);

		message.setTitle("Les " + bestPlayers.size() + " meilleurs joueurs du jeu sont :");

		for (int i = 0; i < bestPlayers.size(); i++) {
			PlayerStats player = bestPlayers.get(i);

			message.addField((i + 1) + " : **" + player.getName() + "**",
					"**" + player.getName() + "** est niveau **" + player.getExperience().getLevel()
							+ "** avec un total de **" + player.getExperience().getTotalExpPoints()
							+ "** points d'Exp !",
					false);
		}

		return message.build();
	}
}
