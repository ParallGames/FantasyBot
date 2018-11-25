package fantasyBot;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import fantasyBot.character.Character;
import fantasyBot.character.Player;
import fantasyBot.player.PlayerStats;
import fantasyBot.utility.MessageBuilder;
import fantasyBot.utility.RandMath;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class EventListener extends ListenerAdapter {

	public static final char PREFIX = '>';

	@Override
	public void onReady(ReadyEvent event) {

		try {
			Globals.loadAbilities();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}

		System.out.println("Capacités chargées !");

		try {
			Globals.loadItems();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		System.out.println("Items Chargées !");

		try {
			Globals.loadMonsters();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}

		System.out.println("Monstres chargés !");

		Globals.loadPlayers();

		System.out.println("Joueurs Chargés !");

		Main.getJda().getTextChannelsByName("log-bot", true).get(0)
				.sendMessage("Bonjour ! Un total de " + Globals.getAbilities().size() + " capacités ont été chargées."
						+ " " + Globals.getPlayers().size() + " joueurs ont rejoint le jeu !")
				.complete();
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		User author = event.getAuthor();
		if (author.isBot()) {
			return;
		}

		String message = event.getMessage().getContentRaw();

		if (message.length() == 0 || message.charAt(0) != PREFIX) {
			return;
		}

		message = message.substring(1);

		if (message.substring(0, 4).equalsIgnoreCase("play")) {
			try {
				event.getMessage().delete().queue();
			} catch (Exception e) {
				// Do nothing, that's not an issue.
			}

			for (int i = 0; i < Globals.getFightsInProgress().size(); i++) {
				if (Globals.getFightsInProgress().get(i).getPlayer().getName().equals(author.getName())) {
					author.openPrivateChannel().complete()
							.sendMessage(MessageBuilder.createErrorMessage("Vous avez déjà un combat en cours !"))
							.queue();
					return;
				}
			}

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
							.sendMessage(
									MessageBuilder.createErrorMessage("Vous ne pouvez pas vous affronter vous-même !"))
							.queue();
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

			Fight fight = new Fight(player, ennemy);

			if (fight.getEnnemy() instanceof Player) {
				fight.setNeedValidationOfPlayer2(true);
				Message messageToAssigne = ((Player) ennemy).getPrivateChannel()
						.sendMessage(MessageBuilder.createQuestionValidationFights((Player) player)).complete();
				messageToAssigne.addReaction("✅").queue();
				messageToAssigne.addReaction("❌").queue();

				((Player) player).getPrivateChannel()
						.sendMessage(MessageBuilder.createWaitValidationMessage((Player) ennemy)).queue();
			} else {
				fight.setNeedValidationOfPlayer2(false);
				fight.fightAccepted();
			}

			Globals.getFightsInProgress().add(fight);
		}

		if (message.substring(0, 4).equalsIgnoreCase("info")) {
			String command = message.substring(5);

			String[] str = command.split(" ");

			if (str.length < 2) {
				return;
			}

			if (str[0].equals("best")) {
				if (str[1].equals("players")) {
					int bestPlayersSize = 3;

					if (str.length == 3) {
						try {
							bestPlayersSize = Integer.parseInt(str[2]);
						} catch (NumberFormatException e) {
							// Use default size
						}
					}

					if (bestPlayersSize < 1) {
						bestPlayersSize = 1;
					} else if (bestPlayersSize > Globals.getPlayers().size()) {
						bestPlayersSize = Globals.getPlayers().size();
					}

					ArrayList<PlayerStats> bestPlayers = new ArrayList<PlayerStats>();

					ArrayList<PlayerStats> players = new ArrayList<PlayerStats>(Globals.getPlayers());

					for (int i = 0; i < bestPlayersSize; i++) {
						int bestScore = 0;

						for (PlayerStats player : players) {
							if (player.getExperience().getTotalExpPoints() > bestScore) {
								bestScore = player.getExperience().getTotalExpPoints();
							}
						}

						for (PlayerStats player : players) {
							if (player.getExperience().getTotalExpPoints() == bestScore) {
								bestPlayers.add(player);
								players.remove(player);
								break;
							}
						}
					}

					if (event.getTextChannel() == null || !event.getTextChannel().canTalk()) {
						PrivateChannel privateChannel = event.getAuthor().openPrivateChannel().complete();
						privateChannel.sendMessage(MessageBuilder.createTopPlayersMessage(bestPlayers)).queue();
					} else {
						event.getTextChannel().sendMessage(MessageBuilder.createTopPlayersMessage(bestPlayers)).queue();
					}
				}
			}
		}

		if (message.substring(0, 4).equalsIgnoreCase("stop")) {
			User user = event.getAuthor();
			List<Guild> listGuild = user.getMutualGuilds();
			long id = user.getIdLong();

			for (int i = 0; i < listGuild.size(); i++) {
				if (listGuild.get(i).getId().equals(Globals.ID_MAIN_SERVER)) {
					Guild guild = listGuild.get(i);
					Member member = guild.getMemberById(id);
					List<Role> listRole = member.getRoles();

					for (int j = 0; j < listRole.size(); j++) {
						if (listRole.get(j).getName().equals("Admin")
								&& listRole.get(j).getId().equals(Globals.ID_ADMIN_ROLE_MAIN_SERVER)) {
							Globals.savePlayers();
							Main.getJda().getTextChannelsByName("log-bot", true).get(0).sendMessage("Au revoir !")
									.complete();
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
		if (author.isBot()) {
			return;
		}
		String emote = event.getReaction().getReactionEmote().getName();

		for (Fight fight : Globals.getFightsInProgress()) {
			if (fight.getEnnemy().getName().equals(author.getName())) {
				if (fight.isNeedValidationOfPlayer2()) {
					if (emote.equals("✅")) {
						fight.fightAccepted();
					} else if (emote.equals("❌")) {
						fight.fightDeclined();
					}
					return;
				}
			}
		}

		int selection = 0;

		if (emote.equals("1⃣")) {
			selection = 1;
		} else if (emote.equals("2⃣")) {
			selection = 2;
		} else if (emote.equals("3⃣")) {
			selection = 3;
		} else if (emote.equals("4⃣")) {
			selection = 4;
		} else if (emote.equals("👜")) {
			selection = 5;
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
							.queue();
				}
				return;
			} else if (fight.getEnnemy().getName().equals(author.getName())) {
				if (!fight.isTurnOfPlayer1()) {
					fight.player2Turn(selection);
				} else {
					author.openPrivateChannel().complete().sendMessage(MessageBuilder
							.createErrorMessage("Vous ne pouvez pas attaquer ! C'est le tour de votre ennemi !"))
							.queue();
				}
				return;
			}
		}
	}
}
