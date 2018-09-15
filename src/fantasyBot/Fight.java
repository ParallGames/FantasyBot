package fantasyBot;

import fantasyBot.character.Character;
import fantasyBot.character.Player;
import fantasyBot.character.ennemies.Monster;

public class Fight {

	private Character player1;
	private Character player2;
	private boolean turnOfPlayer1;

	public void runFight(Character player1, Character player2) {
		this.player1 = player1;
		this.player2 = player2;

		if (player1 instanceof Player) {
			((Player) player1).getPrivateChannel().sendMessage("Vous avez commencé un duel !\n"
					+ "----------------------------\n" + player1.getName() + " contre " + player2.getName() + " !")
					.complete();
		}

		if (player2 instanceof Player) {
			((Player) player2).getPrivateChannel().sendMessage("Vous avez commencé un duel !\n"
					+ "----------------------------\n" + player2.getName() + " contre " + player1.getName() + " !")
					.complete();
		}

		turnOfPlayer1 = true;
		player1Choice();
	}

	public void player1Choice() {
		if (player1 instanceof Player) {
			String message = "C'est à " + player1.getName() + " de jouer !\n" + "Votre ennemie possède "
					+ player2.getHP() + " points de vie sur " + player2.getMaxHealthPoints() + ".\n"
					+ "Quelle attaque souhaitez-vous effectuer ?\n"
					+ "------------------------------------------\n";
			
			for(int i = 0; i < ((Player) player1).getAbilitys().size(); i++) {
				message += "\n" + (i + 1) + ". " + ((Player) player1).getAbilitys().get(i).getName() + ".";
			}
			
			((Player) player1).getPrivateChannel().sendMessage(message).submit();
		} else {
			player1Turn(0);
		}
	}

	public void player2Choice() {
		if (player2 instanceof Player) {
			
			String message = "C'est à " + player2.getName() + " de jouer !\n" + "Votre ennemie possède "
					+ player1.getHP() + " points de vie sur " + player1.getMaxHealthPoints() + ".\n"
					+ "Quelle attaque souhaitez-vous effectuer ?\n"
					+ "------------------------------------------\n";
			
			for(int i = 0; i < ((Player) player2).getAbilitys().size(); i++) {
				message += "\n" + (i + 1) + ". " + ((Player) player2).getAbilitys().get(i).getName() + ".";
			}
			
			((Player) player2).getPrivateChannel().sendMessage(message).submit();
		} else {
			player2Turn(0);
		}
	}

	public void player1Turn(int abilityNumber) {
		abilityNumber--;
		if(abilityNumber != -1) {
			player2.recieveDamage(((Player) player1).getAbilitys().get(abilityNumber).getDamage());
		}else {
			player2.recieveDamage(((Monster) player1).getAttackDamages());
		}

		if (player2.isDead()) {
			player1Win(abilityNumber);
			return;
		} else {
			if (player1 instanceof Player) {
				((Player) player1).getPrivateChannel()
						.sendMessage("Vous avez infligé " + ((Player) player1).getAbilitys().get(abilityNumber).getDamage() + " à " + player2.getName()
								+ " ! " + "Il lui reste " + player2.getHP() + " sur " + player2.getMaxHealthPoints()
								+ " !")
						.complete();
			}

			if (player2 instanceof Player && player1 instanceof Monster) {
				((Player) player2).getPrivateChannel()
						.sendMessage(player1.getName() + " vous a infligé " + ((Monster) player1).getAttackDamages()
								+ " ! Il vous reste " + player2.getHP() + " sur " + player2.getMaxHealthPoints()
								+ " ! ")
						.complete();
			}
			
			if(player2 instanceof Player && player1 instanceof Player) {
				((Player) player2).getPrivateChannel()
				.sendMessage(player1.getName() + " vous a infligé " + ((Player) player1).getAbilitys().get(abilityNumber).getDamage()
						+ " ! Il vous reste " + player2.getHP() + " sur " + player2.getMaxHealthPoints()
						+ " ! ")
				.complete();
			}
		}

		turnOfPlayer1 = false;
		player2Choice();
	}

	public void player2Turn(int abilityNumber) {
		abilityNumber--;
		if(abilityNumber != -1) {
			player1.recieveDamage(((Player) player2).getAbilitys().get(abilityNumber).getDamage());
		}else {
			player1.recieveDamage(((Monster) player2).getAttackDamages());
		}
		
		if (player1.isDead()) {
			player2Win(abilityNumber);
			return;
		} else {
			if (player2 instanceof Player) {
				((Player) player2).getPrivateChannel()
						.sendMessage("Vous avez infligé " + ((Player) player2).getAbilitys().get(abilityNumber).getDamage() + " à " + player1.getName()
								+ " ! " + "Il lui reste " + player1.getHP() + " sur " + player1.getMaxHealthPoints()
								+ " !")
						.complete();
			}
			
			if (player1 instanceof Player && player2 instanceof Monster) {
				((Player) player1).getPrivateChannel()
						.sendMessage(player2.getName() + " vous a infligé " + ((Monster) player2).getAttackDamages()
								+ " ! Il vous reste " + player1.getHP() + " sur " + player1.getMaxHealthPoints()
								+ " ! ")
						.complete();
			}
			
			if(player2 instanceof Player && player1 instanceof Player) {
				((Player) player2).getPrivateChannel()
				.sendMessage(player2.getName() + " vous a infligé " + ((Player) player2).getAbilitys().get(abilityNumber).getDamage()
						+ " ! Il vous reste " + player1.getHP() + " sur " + player1.getMaxHealthPoints()
						+ " ! ")
				.complete();
			}
		}
		turnOfPlayer1 = true;
		player1Choice();
	}

	public void player1Win(int abilityNumber) {
		if (player1 instanceof Player) {
			((Player) player1).getPrivateChannel()
					.sendMessage("Vous avez infligé " + ((Player) player1).getAbilitys().get(abilityNumber).getDamage() + " à " + player2.getName()
							+ " ! Il meurt sur ce coup !\n" + "Félicitation ! Vous remportez " + 20 + " d'exp !")
					.complete();

			Globals.getPlayerByID(((Player) player1).getPlayerID()).getExperience().addExperience(20,
					((Player) player1).getPrivateChannel());
		}

		if (player2 instanceof Player) {
			((Player) player2)
					.getPrivateChannel().sendMessage(player1.getName()
							+ " vous inflige une attaque létale. Vous perdez.\nVous remportez " + 5 + " d'exp !")
					.complete();

			Globals.getPlayerByID(((Player) player2).getPlayerID()).getExperience().addExperience(5,
					((Player) player2).getPrivateChannel());
		}

		Globals.getFightsInProgress().remove(this);
	}

	public void player2Win(int abilityNumber) {
		if (player2 instanceof Player) {
			((Player) player2).getPrivateChannel()
					.sendMessage("Vous avez infligé " + ((Player)player2).getAbilitys().get(abilityNumber).getDamage() + " à " + player1.getName()
							+ " ! Il meurt sur ce coup !\n" + "Félicitation ! Vous remportez " + 20 + " d'exp !")
					.complete();

			Globals.getPlayerByID(((Player) player2).getPlayerID()).getExperience().addExperience(20,
					((Player) player2).getPrivateChannel());
		}

		if (player1 instanceof Player) {
			((Player) player1)
					.getPrivateChannel().sendMessage(player2.getName()
							+ " vous inflige une attaque létale. Vous perdez.\nVous remportez " + 5 + " d'exp !")
					.complete();

			Globals.getPlayerByID(((Player) player1).getPlayerID()).getExperience().addExperience(5,
					((Player) player1).getPrivateChannel());
		}

		Globals.getFightsInProgress().remove(this);
	}

	public Character getPlayer() {
		return player1;
	}

	public Character getEnnemy() {
		return player2;
	}

	public boolean isTurnOfPlayer1() {
		return turnOfPlayer1;
	}
}
