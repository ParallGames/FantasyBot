package fantasyBot;

import fantasyBot.model.Character;
import net.dv8tion.jda.client.events.relationship.FriendRequestReceivedEvent;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class EventListener extends ListenerAdapter{

	public static final char PREFIX = '>';

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		String message = event.getMessage().getContentRaw();
		User author = event.getAuthor();
		
		if(message.charAt(0) != PREFIX) {
			return;
		}
		
		message = message.substring(1);
		
		if(message.equalsIgnoreCase("play")) {
			try {
				event.getMessage().delete().complete();
			} catch(InsufficientPermissionException exception) {
				System.err.println("The message can't be removed.");
			}
			
			PrivateChannel priv = author.openPrivateChannel().complete();
			
			Fight fight = new Fight();
			
			int attack = 3;
			int health = 10;
						
			Character player = new Character(author.getName(), attack, health);
			
			Character ia = new Character("Gros monstre a grou grou", 2, 5);
			
			fight.runFight(player, ia, priv);
		}

		System.out.println(message);
		System.out.println(author.getName());
	}
	
	public void onFriendRequestReceived(FriendRequestReceivedEvent event) {
		event.getFriendRequest().accept().complete();
		System.out.println(event.getFriendRequest().getUser().getName());
	}
}
