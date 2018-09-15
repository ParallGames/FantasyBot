package fantasyBot;

import java.io.FileNotFoundException;
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

		try {
			Globals.loadAbilitys();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		
		System.out.println("Capacités chargées !");
		
		try {
			Globals.loadMonsters();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		
		System.out.println("Monstres chargées !");
		
		Globals.loadPlayers();
		
		System.out.println("Joueurs Chargées");

		jda.addEventListener(new EventListener());
		jda.getTextChannelsByName("log-bot", true).get(0).sendMessage("Bonjour ! Un total de " + Globals.getAbilitys().size() + " capacités a été chargés."
				+ " " + Globals.getPlayers().size() + " joueurs ont rejoint le jeu !").complete();

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
