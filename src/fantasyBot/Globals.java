package fantasyBot;

import java.util.ArrayList;

import fantasyBot.player.PlayerStats;

public class Globals {
	private static final ArrayList<Fight> fightsInProgress = new ArrayList<Fight>();

	private static final ArrayList<PlayerStats> players = new ArrayList<PlayerStats>();

	public static ArrayList<Fight> getFightsInProgress() {
		return fightsInProgress;
	}

	public static ArrayList<PlayerStats> getPlayers() {
		return players;
	}

	/**
	 * 
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
}
