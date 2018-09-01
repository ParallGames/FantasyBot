package fantasyBot;

import fantasyBot.character.Character;
import fantasyBot.character.Player;
import fantasyBot.character.ennemies.Spider;
import fantasyBot.player.PlayerStats;
import net.dv8tion.jda.client.events.relationship.FriendRequestReceivedEvent;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class EventListener extends ListenerAdapter {

	public static final char PREFIX = '>';

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		String message = event.getMessage().getContentRaw();
		User author = event.getAuthor();

		for (Fight fight : Globals.getFightsInProgress()) {
			if (fight.getPlayer().getName().equals(author.getName()) && event.getPrivateChannel() != null) {

				boolean attackIsCorrect = false;

				try {
					for (int j = 1; j < 5; j++) {
						if (Integer.parseInt(message) == j) {
							attackIsCorrect = true;
						}
					}
				} catch (NumberFormatException e) {
					fight.getChannel().sendMessage("L'attaque est invalide !");
				}

				if (attackIsCorrect) {
					fight.fightResponse();
				}

				return;

			}
		}

		if (message.charAt(0) != PREFIX) {
			return;
		}

		message = message.substring(1);

		if (message.equalsIgnoreCase("play")) {
			try {
				event.getMessage().delete().complete();
			} catch (InsufficientPermissionException exception) {
				System.err.println("The message can't be removed.");
			}

			boolean messageSenderAsAlreadyAFight = false;
			int fightId = 0;

			for (int i = 0; i < Globals.getFightsInProgress().size(); i++) {
				if (Globals.getFightsInProgress().get(i).getPlayer().getName().equals(author.getName())) {
					messageSenderAsAlreadyAFight = true;
					fightId = i;
				}
			}

			if (messageSenderAsAlreadyAFight) {
				Fight fight = Globals.getFightsInProgress().get(fightId);

				fight.getChannel().sendMessage("Vous avez déjà un combats en cours !");
			} else {
				PrivateChannel priv = author.openPrivateChannel().complete();

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

				Character ennemy = new Spider();

				fight.runFight(player, ennemy, priv);

				Globals.getFightsInProgress().add(fight);
			}
		}

		System.out.println(message);
		System.out.println(author.getName());
	}

	public void onFriendRequestReceived(FriendRequestReceivedEvent event) {
		event.getFriendRequest().accept().complete();
		System.out.println(event.getFriendRequest().getUser().getName());
	}
}
