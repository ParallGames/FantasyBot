package fantasyBot;

import fantasyBot.character.Character;
import fantasyBot.character.Player;
import fantasyBot.character.ennemies.Spider;
import fantasyBot.player.PlayerStats;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class EventListener extends ListenerAdapter {

	public static final char PREFIX = '>';

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		String message = event.getMessage().getContentRaw();
		User author = event.getAuthor();

		for (Fight fight : Globals.getFightsInProgress()) {
			if (event.getPrivateChannel() != null) {
				if (fight.getPlayer().getName().equals(author.getName())) {
					boolean attackIsCorrect = false;

					try {
						for (int j = 1; j < 5; j++) {
							if (Integer.parseInt(message) == j) {
								attackIsCorrect = true;
							}
						}
					} catch (NumberFormatException e) {
						author.openPrivateChannel().complete().sendMessage("L'attaque est invalide !").complete();
					}

					if (attackIsCorrect) {
						fight.player1Turn();
					}
					return;
				} else if (fight.getEnnemy().getName().equals(author.getName())) {
					boolean attackIsCorrect = false;

					try {
						for (int j = 1; j < 5; j++) {
							if (Integer.parseInt(message) == j) {
								attackIsCorrect = true;
							}
						}
					} catch (NumberFormatException e) {
						author.openPrivateChannel().complete().sendMessage("L'attaque est invalide !").complete();
					}

					if (attackIsCorrect) {
						fight.player2Turn();
					}
					return;
				}
			}
		}

		if (message.charAt(0) != PREFIX) {
			return;
		}

		message = message.substring(1);

		if (message.substring(0, 4).equalsIgnoreCase("play")) {
			try {
				event.getMessage().delete().complete();
			} catch (Exception e) {
				System.err.println("The message can't be removed.");
			}

			boolean messageSenderAsAlreadyAFight = false;

			for (int i = 0; i < Globals.getFightsInProgress().size(); i++) {
				if (Globals.getFightsInProgress().get(i).getPlayer().getName().equals(author.getName())) {
					messageSenderAsAlreadyAFight = true;
				}
			}

			if (messageSenderAsAlreadyAFight) {
				author.openPrivateChannel().complete().sendMessage("Vous avez déjà un combats en cours !").complete();
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
					if(message.substring(5).equalsIgnoreCase(author.getId())) {
						author.openPrivateChannel().complete().sendMessage("Vous ne pouvez pas vous affronter vous-même !").complete();
						return;
					} else {
						ennemyPlayer = Main.getJda().getUserById(message.substring(5));
					}
				} catch (Exception e) {
				}

				Character ennemy;

				if (ennemyPlayer == null) {
					ennemy = new Spider();
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

		System.out.println(message);
		System.out.println(author.getName());
	}
}
