package fantasyBot;

import java.util.List;

import fantasyBot.character.Character;
import fantasyBot.character.Player;
import fantasyBot.player.PlayerStats;
import fantasyBot.utility.MessageBuilder;
import fantasyBot.utility.RandMath;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class EventListener extends ListenerAdapter {

	public static final char PREFIX = '>';

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		String message = event.getMessage().getContentRaw();
		User author = event.getAuthor();

		if (message.length() == 0 || message.charAt(0) != PREFIX) {
			return;
		}

		message = message.substring(1);

		if (message.substring(0, 4).equalsIgnoreCase("play")) {
			try {
				event.getMessage().delete().complete();
			} catch (Exception e) {
				// Do nothing, that's not an issue.
			}

			boolean messageSenderAsAlreadyAFight = false;

			for (int i = 0; i < Globals.getFightsInProgress().size(); i++) {
				if (Globals.getFightsInProgress().get(i).getPlayer().getName().equals(author.getName())) {
					messageSenderAsAlreadyAFight = true;
				}
			}

			if (messageSenderAsAlreadyAFight) {
				author.openPrivateChannel().complete()
						.sendMessage(MessageBuilder.createErrorMessage("Vous avez déjà un combats en cours !"))
						.complete();
			} else {
				Fight fight = new Fight();

				PlayerStats playerStats = null;

				for (PlayerStats stats : Globals.getPlayers()) {
					if (stats.getPlayerID() == author.getIdLong()) {
						playerStats = stats;
						break;
					}
				}

				// If the player is new add him in the list
				if (playerStats == null) {
					playerStats = new PlayerStats(author);
					Globals.getPlayers().add(playerStats);
				}

				Character player = new Player(playerStats);

				User ennemyPlayer = null;

				try {
					if (message.substring(5).equalsIgnoreCase(author.getId())) {
						author.openPrivateChannel().complete()
								.sendMessage(MessageBuilder
										.createErrorMessage("Vous ne pouvez pas vous affronter vous-même !"))
								.complete();
						return;
					} else {
						ennemyPlayer = Main.getJda().getUserById(message.substring(5));
					}
				} catch (Exception e) {
				}

				Character ennemy;

				if (ennemyPlayer == null) {
					int randomIndex = RandMath.randInt(Globals.getNumberOfMonster());
					ennemy = Globals.createMonster(randomIndex);
				} else {
					PlayerStats player2Stats = null;

					for (PlayerStats stats : Globals.getPlayers()) {
						if (stats.getPlayerID() == ennemyPlayer.getIdLong()) {
							player2Stats = stats;
							break;
						}
					}

					// If the player is new add him in the list
					if (player2Stats == null) {
						player2Stats = new PlayerStats(ennemyPlayer);
						Globals.getPlayers().add(player2Stats);
					}

					ennemy = new Player(player2Stats);
				}

				fight.runFight(player, ennemy);

				Globals.getFightsInProgress().add(fight);
			}
		}
		
		if (message.substring(0, 4).equalsIgnoreCase("stop")){
			User user = event.getAuthor();
			List<Guild> listGuild = user.getMutualGuilds();
			long id = user.getIdLong();
			
			for(int i = 0; i < listGuild.size(); i++) {
				if(listGuild.get(i).getId().equals(Globals.ID_MAIN_SERVER)) {
					Guild guild = listGuild.get(i);
					Member member = guild.getMemberById(id);
					List<Role> listRole = member.getRoles();
					
					for(int j = 0; j < listRole.size(); j++) {
						if(listRole.get(j).getName().equals("Admin") && listRole.get(j).getId().equals(Globals.ID_ADMIN_ROLE_MAIN_SERVER)) {
							Globals.savePlayers();
							Main.getJda().getTextChannelsByName("log-bot", true).get(0).sendMessage("Au revoir !").complete();
							Main.getJda().shutdown();
						}
					}
				}
			}
		}
		
	}

	@Override
	public void onPrivateMessageReactionAdd(PrivateMessageReactionAddEvent event) {
		User author = event.getUser();

		String emote = event.getReaction().getReactionEmote().getName();

		int selection = 0;

		if (emote.equals("1⃣")) {
			selection = 1;
		} else if (emote.equals("2⃣")) {
			selection = 2;
		} else if (emote.equals("3⃣")) {
			selection = 3;
		} else if (emote.equals("4⃣")) {
			selection = 4;
		} else {
			return;
		}

		for (Fight fight : Globals.getFightsInProgress()) {
			if (fight.getPlayer().getName().equals(author.getName())) {
				if (fight.isTurnOfPlayer1()) {
					fight.player1Turn(selection);
				} else {
					author.openPrivateChannel().complete().sendMessage(MessageBuilder
							.createErrorMessage("Vous ne pouvez pas attaquer ! C'est le tour de votre ennemi !"))
							.complete();
				}

				return;
			} else if (fight.getEnnemy().getName().equals(author.getName())) {
				if (!fight.isTurnOfPlayer1()) {
					fight.player2Turn(selection);
				} else {
					author.openPrivateChannel().complete().sendMessage(MessageBuilder
							.createErrorMessage("Vous ne pouvez pas attaquer ! C'est le tour de votre ennemi !"))
							.complete();
				}
				return;
			}
		}
	}
}
