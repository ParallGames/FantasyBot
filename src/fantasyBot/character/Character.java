package fantasyBot.character;

public abstract class Character {

	protected int hp;

	public abstract String getName();

	public abstract int getAttackDamages();

	public abstract int getMaxHealthPoints();

	public int getHP() {
		return hp;
	}

	public void recieveDamage(int damages) {
		hp -= damages;
	}

	public boolean isDead() {
		return hp < 1;
	}
}
