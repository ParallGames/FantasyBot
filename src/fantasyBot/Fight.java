package fantasyBot;

import java.util.ArrayList;

import fantasyBot.character.Character;
import fantasyBot.character.Monster;
import fantasyBot.character.Player;
import fantasyBot.player.Ability;
import fantasyBot.utility.MessageBuilder;
import fantasyBot.utility.RandMath;
import net.dv8tion.jda.core.entities.Message;

public class Fight {

	private Character player1;
	private Character player2;
	private boolean turnOfPlayer1;
	private boolean needValidationOfPlayer2;

	
	public void fightAccepted() {
		if (player1 instanceof Player) {
			((Player) player1).getPrivateChannel()
			.sendMessage(MessageBuilder.createCombatIntroductionMessage((Player) player1, player2, player1))
			.complete();
		}

		if (player2 instanceof Player) {
			((Player) player2).getPrivateChannel()
			.sendMessage(MessageBuilder.createCombatIntroductionMessage((Player) player2, player1, player2))
			.complete();
		}

		turnOfPlayer1 = true;
		player1Choice();
	}
	
	public void fightDeclined() {
		((Player) player1).getPrivateChannel().sendMessage(MessageBuilder.createRefuseFight((Player) player2)).queue();
		((Player) player2).getPrivateChannel().sendMessage(MessageBuilder.createConfirmationMessage((Player) player2)).queue();
		Globals.getFightsInProgress().remove(this);
	}
	
	public void constructFight(Character player1, Character player2) {
		this.player1 = player1;
		this.player2 = player2;
	}

	public void player1Choice() {
		if (player1 instanceof Player) {
			Message mess = ((Player) player1).getPrivateChannel()
					.sendMessage(MessageBuilder.createAttackChoiceMessage((Player) player1, player2)).complete();

			assigneReaction(mess, ((Player) (player1)));
		} else {
			int indexOfAbility = RandMath.randInt(((Monster) player1).getAbilities().size()) + 1;
			player1Turn(indexOfAbility);
		}
	}

	public void player2Choice() {
		if (player2 instanceof Player) {
			Message mess = ((Player) player2).getPrivateChannel()
					.sendMessage(MessageBuilder.createAttackChoiceMessage((Player) player2, player1)).complete();

			assigneReaction(mess, ((Player) (player2)));
		} else {
			int indexOfAbility = RandMath.randInt(((Monster) player2).getAbilities().size()) + 1;
			player2Turn(indexOfAbility);
		}
	}

	public void assigneReaction(Message mess, Player player) {
		if (player.getAbilities().size() >= 1) {
			mess.addReaction("1⃣").queue();
		}

		if (player.getAbilities().size() >= 2) {
			mess.addReaction("2⃣").queue();
		}

		if (player.getAbilities().size() >= 3) {
			mess.addReaction("3⃣").queue();
		}

		if (player.getAbilities().size() >= 4) {
			mess.addReaction("4⃣").queue();
		}
	}

	public void player1Turn(int abilityNumber) {
		abilityNumber--;

		if (player1 instanceof Player) {
			if (player1.getAbilities().get(abilityNumber).getEnergyCost() > player1.getEnergy()) {
				((Player) player1).getPrivateChannel()
				.sendMessage(MessageBuilder.createErrorMessage("Vous ne possèdez pas assez d'énergie !"))
				.queue();
				return;
			}
		} else {
			ArrayList<Ability> usableAbility = new ArrayList<>();

			for(int i = 0; i < player1.getAbilities().size(); i++) {
				if(player1.getAbilities().get(i).getEnergyCost() <= player1.getEnergy()) {
					usableAbility.add(player1.getAbilities().get(i));
				}
			}

			abilityNumber = RandMath.randInt(usableAbility.size());
			
			for(int i = 0; i < player1.getAbilities().size(); i++) {
				if(player1.getAbilities().get(i).getId() == usableAbility.get(abilityNumber).getId()) {
					abilityNumber = i - 1;
					break;
				}
			}
		}

		player2.recieveDamage(player1.getAbilities().get(abilityNumber).getDamage());
		player1.setEnergy(player1.getEnergy() - player1.getAbilities().get(abilityNumber).getEnergyCost());

		if (player2.isDead()) {
			player1Win(abilityNumber);
			return;
		} else {
			if (player1 instanceof Player) {
				((Player) player1).getPrivateChannel().sendMessage(MessageBuilder
						.createDamageDealtMessage((Player) player1, player2, player1.getAbilities().get(abilityNumber)))
				.queue();
			}

			if (player2 instanceof Player) {
				((Player) player2).getPrivateChannel()
				.sendMessage(MessageBuilder.createDamageReceivedMessage((Player) player2, player1,
						player1.getAbilities().get(abilityNumber)))
				.queue();
			}
		}

		turnOfPlayer1 = false;
		player2Choice();
	}

	public void player2Turn(int abilityNumber) {
		abilityNumber--;

		if (player2 instanceof Player) {
			if (player2.getAbilities().get(abilityNumber).getEnergyCost() > player2.getEnergy()) {
				((Player) player2).getPrivateChannel()
				.sendMessage(MessageBuilder.createErrorMessage("Vous ne possèdez pas assez d'énergie !"))
				.queue();
				return;
			}
		} else {
			ArrayList<Ability> usableAbility = new ArrayList<>();

			for(int i = 0; i < player2.getAbilities().size(); i++) {
				if(player2.getAbilities().get(i).getEnergyCost() <= player2.getEnergy()) {
					usableAbility.add(player2.getAbilities().get(i));
				}
			}

			abilityNumber = RandMath.randInt(usableAbility.size());
			
			for(int i = 0; i < player2.getAbilities().size(); i++) {
				if(player2.getAbilities().get(i).getId() == usableAbility.get(abilityNumber).getId()) {
					abilityNumber = i;
					break;
				}
			}
		}

		player1.recieveDamage(player2.getAbilities().get(abilityNumber).getDamage());
		player2.setEnergy(player2.getEnergy() - player2.getAbilities().get(abilityNumber).getEnergyCost());

		if (player1.isDead()) {
			player2Win(abilityNumber);
			return;
		} else {
			if (player2 instanceof Player) {
				((Player) player2).getPrivateChannel().sendMessage(MessageBuilder
						.createDamageDealtMessage((Player) player2, player1, player2.getAbilities().get(abilityNumber)))
				.queue();
			}

			if (player1 instanceof Player) {
				((Player) player1).getPrivateChannel()
				.sendMessage(MessageBuilder.createDamageReceivedMessage((Player) player1, player2,
						player2.getAbilities().get(abilityNumber)))
				.queue();
			}
		}
		turnOfPlayer1 = true;
		player1Choice();
	}

	public void player1Win(int abilityNumber) {
		if (player1 instanceof Player) {
			((Player) player1).getPrivateChannel().sendMessage(MessageBuilder.createWinMessage((Player) player1,
					player2, player1.getAbilities().get(abilityNumber))).queue();

			Globals.getPlayerByID(((Player) player1).getPlayerID()).getExperience().addExperience(20,
					((Player) player1).getPrivateChannel());
		}

		if (player2 instanceof Player) {
			((Player) player2).getPrivateChannel().sendMessage(MessageBuilder.createDefeatMessage((Player) player2,
					player1, player1.getAbilities().get(abilityNumber))).queue();

			Globals.getPlayerByID(((Player) player2).getPlayerID()).getExperience().addExperience(5,
					((Player) player2).getPrivateChannel());
		}

		Globals.getFightsInProgress().remove(this);
	}

	public void player2Win(int abilityNumber) {
		if (player2 instanceof Player) {
			((Player) player2).getPrivateChannel().sendMessage(MessageBuilder.createWinMessage((Player) player2,
					player1, player2.getAbilities().get(abilityNumber))).queue();

			Globals.getPlayerByID(((Player) player2).getPlayerID()).getExperience().addExperience(20,
					((Player) player2).getPrivateChannel());
		}

		if (player1 instanceof Player) {
			((Player) player1).getPrivateChannel().sendMessage(MessageBuilder.createDefeatMessage((Player) player1,
					player2, player2.getAbilities().get(abilityNumber))).queue();

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

	public boolean isNeedValidationOfPlayer2() {
		return needValidationOfPlayer2;
	}

	public void setNeedValidationOfPlayer2(boolean needValidationOfPlayer2) {
		this.needValidationOfPlayer2 = needValidationOfPlayer2;
	}
}
