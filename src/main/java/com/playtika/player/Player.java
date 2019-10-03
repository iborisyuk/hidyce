package com.playtika.player;

import com.playtika.exceptions.NotEnoughMoneyExceptions;
import com.playtika.utils.Dice;

public class Player {
    private final int id;
    private final String name;

    private int money;

    public Player(int id, String name, int money) {
        this.id = id;
        this.name = name;
        this.money = money;
    }

    public void addMoney(int count) {
        money += count;
    }

    public void writeOffMoney(int count) {
        if ((money - count) < 0) {
            throw new NotEnoughMoneyExceptions();
        }

        money -= count;
    }

    public int getMoney() {
        return money;
    }

    public int roll() {
        return Dice.roll();
    }

    public int getSumRolls(int countDice) {
        return Dice.getSumRoll(countDice);
    }

    public int getId() {
        return id;
    }
}
