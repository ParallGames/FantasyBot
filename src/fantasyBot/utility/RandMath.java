package fantasyBot.utility;

import java.util.Random;

public class RandMath {
	private static final Random random = new Random();

	public static int randInt(int min, int max) {
		return random.nextInt(max - min) + min;
	}

	public static int randInt(int max) {
		return random.nextInt(max);
	}
}
