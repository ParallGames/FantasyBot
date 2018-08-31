package fantasyBot.character.ennemies;

import fantasyBot.character.Character;

public class Spider extends Character {

	public Spider() {
		hp = getMaxHealthPoints();
	}

	@Override
	public String getName() {
		return "Spider";
	}

	@Override
	public int getAttackDamages() {
		return 1;
	}

	@Override
	public int getMaxHealthPoints() {
		return 5;
	}

}
