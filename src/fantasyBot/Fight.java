package fantasyBot;

import fantasyBot.character.Character;
import fantasyBot.character.Monster;
import fantasyBot.character.Player;
import fantasyBot.utility.RandMath;
import net.dv8tion.jda.core.entities.Message;

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
					+ player2.getHP() + " points de vie sur " + player2.getMaxHealth() + ".\n"
					+ "Quelle attaque souhaitez-vous effectuer ?\n"
					+ "------------------------------------------\n";

			for(int i = 0; i < ((Player) player1).getAbilities().size(); i++) {
				message += "\n" + (i + 1) + ". " + ((Player) player1).getAbilities().get(i).getName() + ".";
			}

			Message mess = ((Player) player1).getPrivateChannel().sendMessage(message).complete();
			
			assigneReaction(mess, ((Player) (player1)));

		} else {
			int indexOfAbility = RandMath.randInt(((Monster) player1).getAbilities().size()) + 1;
			player1Turn(indexOfAbility);
		}
	}

	public void player2Choice() {
		if (player2 instanceof Player) {

			String message = "C'est à " + player2.getName() + " de jouer !\n" + "Votre ennemie possède "
					+ player1.getHP() + " points de vie sur " + player1.getMaxHealth() + ".\n"
					+ "Quelle attaque souhaitez-vous effectuer ?\n"
					+ "------------------------------------------\n";

			for(int i = 0; i < ((Player) player2).getAbilities().size(); i++) {
				message += "\n" + (i + 1) + ". " + ((Player) player2).getAbilities().get(i).getName() + ".";
			}

			Message mess = ((Player) player2).getPrivateChannel().sendMessage(message).complete();

			assigneReaction(mess, ((Player) (player2)));
		} else {
			int indexOfAbility = RandMath.randInt(((Monster) player2).getAbilities().size()) + 1;
			player2Turn(indexOfAbility);
		}
	}
	
	public void assigneReaction(Message mess, Player player) {
		if(player.getAbilities().size() >= 1) {
			mess.addReaction("1⃣").complete();
		}
		
		if(player.getAbilities().size() >= 2) {
			mess.addReaction("2⃣").complete();
		}
		
		if(player.getAbilities().size() >= 3) {
			mess.addReaction("3⃣").complete();
		}
		
		if(player.getAbilities().size() >= 4) {
			mess.addReaction("4⃣").complete();
		}
	}

	public void player1Turn(int abilityNumber) {
		abilityNumber--;
		
		boolean attackIsOK = false;
		while(!attackIsOK) {
			if(((Character) player1).getAbilities().get(abilityNumber).getEnergyCost() > ((Character) player1).getEnergy()) {
				if(player1 instanceof Player) {
					((Player) player1).getPrivateChannel().sendMessage("Vous ne possèdez pas assez d'énergie !").complete();
					return;
				}else {
					abilityNumber =RandMath.randInt(player1.getAbilities().size());
				}
			}else {
				attackIsOK = true;
			}
		}

		player2.recieveDamage(((Character) player1).getAbilities().get(abilityNumber).getDamage());
		player1.setEnergy(player1.getEnergy() - ((Character) player1).getAbilities().get(abilityNumber).getEnergyCost());

		if (player2.isDead()) {
			player1Win(abilityNumber);
			return;
		} else {
			if (player1 instanceof Player) {
				((Player) player1).getPrivateChannel()
				.sendMessage("Vous avez infligé " + ((Character) player1).getAbilities().get(abilityNumber).getDamage() + " à " + player2.getName()
				+ " ! Il lui reste " + player2.getHP() + " sur " + player2.getMaxHealth()
				+ " !")
				.complete();
			}

			if(player2 instanceof Player) {
				((Player) player2).getPrivateChannel()
				.sendMessage(player1.getName() + " vous a infligé " + ((Character) player1).getAbilities().get(abilityNumber).getDamage()
						+ " ! Il vous reste " + player2.getHP() + " PV sur " + player2.getMaxHealth()
						+ " !")
				.complete();
			}
		}

		turnOfPlayer1 = false;
		player2Choice();
	}

	public void player2Turn(int abilityNumber) {
		abilityNumber--;
		
		boolean attackIsOK = false;
		while(!attackIsOK) {
			if((((Character) player2).getAbilities().get(abilityNumber).getEnergyCost() > ((Character) player2).getEnergy())) {
				if(player2 instanceof Player) {
					((Player) player2).getPrivateChannel().sendMessage("Vous ne possèdez pas assez d'énergie !").complete();
					return;
				}else {
					abilityNumber = RandMath.randInt(player2.getAbilities().size());
				}
			}else {
				attackIsOK = true;
			}
		}
		
		player1.recieveDamage(((Character) player2).getAbilities().get(abilityNumber).getDamage());
		player2.setEnergy(player2.getEnergy() - ((Character) player2).getAbilities().get(abilityNumber).getEnergyCost());
		
		if (player1.isDead()) {
			player2Win(abilityNumber);
			return;
		} else {
			if (player2 instanceof Player) {
				((Player) player2).getPrivateChannel()
				.sendMessage("Vous avez infligé " + ((Character) player2).getAbilities().get(abilityNumber).getDamage() + " à " + player1.getName()
				+ " ! " + "Il lui reste " + player1.getHP() + " PV sur " + player1.getMaxHealth()
				+ " !")
				.complete();
			}

			if(player1 instanceof Player) {
				((Player) player1).getPrivateChannel()
				.sendMessage(player2.getName() + " vous a infligé " + ((Character) player2).getAbilities().get(abilityNumber).getDamage()
						+ " ! Il vous reste " + player1.getHP() + " PV sur " + player1.getMaxHealth()
						+ " !")
				.complete();
			}
		}
		turnOfPlayer1 = true;
		player1Choice();
	}

	public void player1Win(int abilityNumber) {
		if (player1 instanceof Player) {
			((Player) player1).getPrivateChannel()
			.sendMessage("Vous avez infligé " + ((Player) player1).getAbilities().get(abilityNumber).getDamage() + " à " + player2.getName()
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
			.sendMessage("Vous avez infligé " + ((Player)player2).getAbilities().get(abilityNumber).getDamage() + " à " + player1.getName()
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
