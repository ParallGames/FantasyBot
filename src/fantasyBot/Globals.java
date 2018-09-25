package fantasyBot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import fantasyBot.character.Monster;
import fantasyBot.item.ItemStats;
import fantasyBot.player.Ability;
import fantasyBot.player.MonsterStats;
import fantasyBot.player.PlayerStats;

public class Globals {
	
	public static final String ID_MAIN_SERVER = "485136006866272276";
	
	public static final String ID_ADMIN_ROLE_MAIN_SERVER = "485136350891212811";
	
	private static final String SAVE_PATH = System.getProperty("user.home") + "/.fantasyBot";

	private static final String ABILITY_PATH = "resources/Abilities.txt";

	private static final String MONSTERS_PATH = "resources/Monsters.txt";
	
	private static final String ARMOR_ITEMS_PATH = "resources/Items/ArmorItems.txt";
	
	private static final String WEAPON_ITEMS_PATH = "resources/Items/WeaponItems.txt";
	
	private static final String NON_USABLE_ITEM_PATH = "resources/Items/NonUsableItems.txt";
	
	private static final String CONSUMABLE_ITEM_PATH = "resources/Items/ConsumableItems.txt";

	private static final String SEPARATOR = ":";

	private static final int NUMBER_MAX_OF_ABILITY = 4;

	private static final ArrayList<Fight> fightsInProgress = new ArrayList<Fight>();

	private static final ArrayList<PlayerStats> players = new ArrayList<PlayerStats>();

	private static final ArrayList<MonsterStats> monsters = new ArrayList<MonsterStats>();

	private static final ArrayList<Ability> abilities = new ArrayList<Ability>();
	
	private static final ArrayList<ItemStats> items = new ArrayList<>();

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

	/**
	 * @param id the ability id from the file of {@link #ABILITY_PATH}
	 * @return the ability with the same id or null if is not found
	 */
	public static Ability getAbilityByID(int id) {
		for (Ability ability : abilities) {
			if (ability.getId() == id) {
				return ability;
			}
		}
		return null;
	}

	public static Monster createMonster(int index) {
		return new Monster(monsters.get(index));
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

	public static void loadAbilities() throws FileNotFoundException {
		File file = new File(ABILITY_PATH);

		Scanner scanner = new Scanner(file);

		try {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();

				if (!line.equals("")) {
					if (!line.substring(0, 2).equals("//")) {

						int id = Integer.parseInt(line.split(SEPARATOR)[1]);
						String name = scanner.nextLine().split(SEPARATOR)[1];
						int damage = Integer.parseInt(scanner.nextLine().split(SEPARATOR)[1]);
						int energyCost = Integer.parseInt(scanner.nextLine().split(SEPARATOR)[1]);
						String abilityDescription = scanner.nextLine().split(SEPARATOR)[1];
						String abilityAttackDescription = scanner.nextLine().split(SEPARATOR)[1];

						Ability newAbility = new Ability(id, name, damage, energyCost, abilityDescription,
								abilityAttackDescription);
						abilities.add(newAbility);

						System.out.println("Capacité " + newAbility.getName() + " chargée");
					}
				}

			}
		} finally {
			scanner.close();
		}
	}
	
	public static void loadItems() throws FileNotFoundException {
		File file = new File(ARMOR_ITEMS_PATH);
		
		Scanner scanner = new Scanner(file);
	}

	public static void loadMonsters() throws FileNotFoundException {
		File file = new File(MONSTERS_PATH);

		Scanner scanner = new Scanner(file);

		try {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();

				if (!line.equals("")) {
					if (!line.substring(0, 2).equals("//")) {

						int id = Integer.parseInt(line.split(SEPARATOR)[1]);
						String name = scanner.nextLine().split(SEPARATOR)[1];
						int health = Integer.parseInt(scanner.nextLine().split(SEPARATOR)[1]);
						int energy = Integer.parseInt(scanner.nextLine().split(SEPARATOR)[1]);

						ArrayList<Ability> abilitysOfMonster = new ArrayList<Ability>();
						for (int i = 0; i < NUMBER_MAX_OF_ABILITY; i++) {
							Ability ability = getAbilityByID(Integer.parseInt(scanner.nextLine().split(SEPARATOR)[1]));
							if (!(ability == null)) {
								abilitysOfMonster.add(ability);
							}
						}

						MonsterStats monster = new MonsterStats(id, name, health, energy, abilitysOfMonster);
						monsters.add(monster);
					}
				}
			}
		} finally {
			scanner.close();
		}
	}

	public static ArrayList<Fight> getFightsInProgress() {
		return fightsInProgress;
	}

	public static ArrayList<PlayerStats> getPlayers() {
		return players;
	}

	public static ArrayList<Ability> getAbilities() {
		return abilities;
	}

	public static int getNumberOfMonster() {
		return monsters.size();
	}
}
