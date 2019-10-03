package com.playtika.hidyce;

import com.playtika.exceptions.NotEnoughMoneyExceptions;
import com.playtika.exceptions.NotEnoughRollExceptions;
import com.playtika.player.Player;

public class Game {
    public static final int END_ROLL = -1;

    private final Player bank;
    private final Player player;

    private final int countDice;

    private int rate = 0;
    private int startRate = 0;
    private int countDiceRoll = 0;

    private int bankPoints = 0;
    private int lastPlayerPoints = 0;

    public Game(Player player, Player bank, int countDice) {
        this.player = player;
        this.bank = bank;
        this.countDice = countDice;
    }

    public void start(int rate, int countDiceRoll) {
        this.startRate = rate;
        this.countDiceRoll = countDiceRoll;

        if (player.getMoney() < startRate || player.getMoney() < (startRate * countDiceRoll)) {
            throw new NotEnoughMoneyExceptions();
        }

        bankPoints = bank.getSumRolls(countDice);
    }

    public boolean playerRollAndCheckToWin() {
        if (canUserRollDice()) {
            throw new NotEnoughRollExceptions();
        }

        lastPlayerPoints = player.getSumRolls(countDice);

        countDiceRoll--;
        rate += startRate;

        if (lastPlayerPoints >= bankPoints) {
            countDiceRoll = END_ROLL;
            return true;
        }

        return false;
    }

    public boolean canUserRollDice() {
        return countDiceRoll == END_ROLL;
    }

    public boolean doubleBet() {
        if (player.getMoney() < (startRate + startRate)) {
            throw new NotEnoughMoneyExceptions();
        }

        startRate += startRate;
        boolean status = playerRollAndCheckToWin();
        countDiceRoll = END_ROLL;
        return status;
    }

    public void save() {
        if (rate == 0) {
            rate = startRate;
        }

        rate /= 2;
        countDiceRoll = END_ROLL;
    }

    public int end() {
        if (lastPlayerPoints > bankPoints) {
            bank.writeOffMoney(rate);
            player.addMoney(rate);

            return player.getId();
        }

        player.writeOffMoney(rate);
        bank.addMoney(rate);

        return bank.getId();
    }

    public int getBankPoints() {
        return bankPoints;
    }

    public int getCountDiceRoll() {
        return countDiceRoll;
    }

    public int getLastPlayerPoints() {
        return lastPlayerPoints;
    }
}
