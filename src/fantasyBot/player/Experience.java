package fantasyBot.player;

import fantasyBot.utility.MessageBuilder;
import net.dv8tion.jda.core.entities.PrivateChannel;

public class Experience {

	private int level;
	private int levelActualPoints;
	private int levelPointsMax;

	public Experience(int level, int levelActualPoints) {
		this.level = level;
		this.levelActualPoints = levelActualPoints;
		this.levelPointsMax = calculateLevelPointsMax(level);
	}

	public void addExperience(int exp, PrivateChannel privateChannel) {
		levelActualPoints += exp;

		privateChannel.sendMessage(MessageBuilder.createXPGainMessage(exp, levelActualPoints, levelPointsMax, level))
				.queue();

		if (levelActualPoints >= levelPointsMax) {
			levelUp(privateChannel);
		}
	}

	private void levelUp(PrivateChannel privateChannel) {
		levelActualPoints -= levelPointsMax;
		level++;
		levelPointsMax = calculateLevelPointsMax(level);

		privateChannel.sendMessage(MessageBuilder.createLevelUpMessage(level)).queue();
	}
	
	public int getTotalExpPoints() {
		int totalXp = 0;
		for(int i = 0; i < level; i++) {
			totalXp += ((i + 1) * 50) - 50;
		}
		return totalXp + levelActualPoints;
	}

	public int calculateLevelPointsMax(int level) {
		return level * 50;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getLevelActualPoints() {
		return levelActualPoints;
	}

	public void setLevelActualPoints(int levelActualPoints) {
		this.levelActualPoints = levelActualPoints;
	}

	public int getLevelPointsMax() {
		return levelPointsMax;
	}

	public void setLevelPointsMax(int levelPointsMax) {
		this.levelPointsMax = levelPointsMax;
	}

}
