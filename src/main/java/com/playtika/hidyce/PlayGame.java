package com.playtika.hidyce;

import com.playtika.player.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PlayGame {
    private final Game game;
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private Player bank;
    private Player player;


    public PlayGame() {
        bank = new Player(0, "Bank", 9999);
        player = new Player(1, "Raccoon", 100);

        game = new Game(player, bank, 2);
    }

    public void start() {
        int playerRate = readInt("Enter you rate");
        int playerDiceRoll = readInt("Enter count dice roll");

        game.start(playerRate, playerDiceRoll);
        playerChoice();
    }

    private void playerChoice() {
        System.out.printf("Bank points: %d\n", game.getBankPoints());

        System.out.println("Enter you choice:");
        System.out.println("\t1) Roll");
        System.out.println("\t2) Double Bet");
        System.out.println("\t3) Save");

        while (true) {
            int choice = readInt("");

            switch (choice) {
                case 1: {
                    roll();
                    break;
                }
                case 2: {
                    doubleBet();
                    break;
                }
                case 3: {
                    save();
                    break;
                }
                default: {
                    System.err.println("Incorrect number! Enter again");
                }
            }

            if (game.end() == player.getId()) {
                System.out.println("You win!");
            } else {
                System.out.println("You lose!");
            }

            System.out.printf("Player money: %d\nBank money: %d\n",
                    player.getMoney(), bank.getMoney());
            break;
        }
    }

    private void roll() {
        while (true) {
            boolean status = game.playerRollAndCheckToWin();
            System.out.printf("Tou points: %d | Bank points: %d\n", game.getLastPlayerPoints(), game.getBankPoints());
            System.out.printf("You can do %d dice roll\n", game.getCountDiceRoll());

            if (!status && game.getCountDiceRoll() > 0) {
                int choice = readInt("Dice roll (1 - yes, 0 - not)?");

                if (choice == 1) {
                    continue;
                }
            }

            break;
        }
    }

    private void doubleBet() {
        boolean status = game.doubleBet();
        System.out.printf("Tou points: %d | Bank points: %d\n", game.getLastPlayerPoints(), game.getBankPoints());
    }

    private void save() {
        game.save();
        System.out.printf("Tou points: %d | Bank points: %d\n", game.getLastPlayerPoints(), game.getBankPoints());
        System.out.println("You saved game!");
    }

    private int readInt(String msg) {
        System.out.println(msg);

        int data;
        while (true) {
            System.out.print("~$ ");

            try {
                data = Integer.parseInt(reader.readLine());
            } catch (IOException e) {
                System.err.println("Incorrect number! Repeat entry.");
                continue;
            }

            break;
        }

        return data;
    }
}
