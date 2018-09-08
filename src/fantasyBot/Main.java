package fantasyBot;

import java.util.Scanner;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

public class Main {

	private static JDA jda;

	public static void main(String[] args) {
		try {
			jda = new JDABuilder(AccountType.BOT).setToken(args[0]).buildBlocking();
		} catch (IndexOutOfBoundsException e) {
			System.err.println("You must provide a token.");
			return;
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		Globals.loadPlayers();

		jda.addEventListener(new EventListener());
		jda.getTextChannelsByName("log-bot", true).get(0).sendMessage("Bonjour !").complete();

		Scanner sc = new Scanner(System.in);

		String text = "";
		while (!text.equalsIgnoreCase("stop")) {
			text = sc.next();
		}
		sc.close();

		Globals.savePlayers();

		jda.getTextChannelsByName("log-bot", true).get(0).sendMessage("Au revoir !").complete();

		jda.shutdown();
	}

	public static JDA getJda() {
		return jda;
	}
}
