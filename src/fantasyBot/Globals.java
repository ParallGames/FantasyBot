package fantasyBot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import fantasyBot.player.Ability;
import fantasyBot.player.PlayerStats;

public class Globals {
	private static final String SAVE_PATH = System.getProperty("user.home") + "/.fantasyBot";

	private static final String ABILITY_PATH = "ressources/Abilitys.txt";
	
	private static final String SEPARATOR = ":";

	private static final ArrayList<Fight> fightsInProgress = new ArrayList<Fight>();

	private static final ArrayList<PlayerStats> players = new ArrayList<PlayerStats>();

	private static final ArrayList<Ability> abilitys = new ArrayList<Ability>();

	/**
	 * @param id the discord id of the player
	 * @return the player with this id or null if he is not found
	 */
	public static PlayerStats getPlayerByID(long id) {
		for (PlayerStats player : players) {
			if (player.getPlayerID() == id) {
				return player;
			}
		}
		return null;
	}

	public static void loadPlayers() {
		File folder = new File(SAVE_PATH);
		if (folder.exists()) {
			for (File file : folder.listFiles()) {
				players.add(new PlayerStats(file));
			}
		}
	}

	public static void savePlayers() {
		File folder = new File(SAVE_PATH);
		folder.mkdirs();

		for (PlayerStats player : players) {
			player.save(SAVE_PATH);
		}
	}

	public static void loadAbilitys() throws FileNotFoundException {
		File file = new File(ABILITY_PATH);

		Scanner scanner = new Scanner(file);

		try {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();

				if(!line.equals("")) {
					if(!line.substring(0, 2).equals("//")) {
						
						int id = Integer.parseInt(line.split(SEPARATOR)[1]);
						String name = scanner.nextLine().split(SEPARATOR)[1];
						int damage = Integer.parseInt(scanner.nextLine().split(SEPARATOR)[1]);
						int energyCost = Integer.parseInt(scanner.nextLine().split(SEPARATOR)[1]);
						String abilityDescription = scanner.nextLine().split(SEPARATOR)[1];
						String abilityAttackDescription = scanner.nextLine().split(SEPARATOR)[1];

						Ability newAbility = new Ability(id, name, damage, energyCost, abilityDescription, abilityAttackDescription);
						abilitys.add(newAbility);
						
						System.out.println("Capacité " + newAbility.getName() + " chargé");
					}
				}

			}
		}finally {
			scanner.close();
		}
	}
	
	public static ArrayList<Fight> getFightsInProgress() {
		return fightsInProgress;
	}

	public static ArrayList<PlayerStats> getPlayers() {
		return players;
	}

	public static ArrayList<Ability> getAbilitys() {
		return abilitys;
	}
}
