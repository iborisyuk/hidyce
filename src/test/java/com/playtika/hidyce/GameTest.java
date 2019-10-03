package com.playtika.hidyce;

import com.playtika.exceptions.NotEnoughMoneyExceptions;
import com.playtika.exceptions.NotEnoughRollExceptions;
import com.playtika.player.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {

    private Game game;
    private Player bank;
    private Player player;
    private int bankId = 0;
    private int playerId = 1;

    @Before
    public void init() {
        bank = new Player(bankId, "Bank", 100);
        player = new Player(playerId, "Raccoon", 100);
        game = new Game(player, bank, 2);
    }

    @Test
    public void mustStartGame() {
        game.start(10, 0);

        assertTrue(game.getBankPoints() != 0);
    }

    @Test(expected = NotEnoughMoneyExceptions.class)
    public void mustFailedStartLargeNumberRoll() {
        game.start(10, 20);
    }

    @Test(expected = NotEnoughMoneyExceptions.class)
    public void mustFailedStartLargeNumberRate() {
        game.start(200, 0);
    }

    @Test
    public void mustCorrectCheckWinPlayers() {
        game.start(10, 0);

        assertEquals(game.playerRollAndCheckToWin(), game.getLastPlayerPoints() > game.getBankPoints());
    }

    @Test
    public void mustCorrectCheckAddMoneyToWinPlayer() {
        game.start(10, 0);
        game.playerRollAndCheckToWin();

        if (game.end() == playerId) {
            assertEquals(player.getMoney(), 110);
            assertEquals(bank.getMoney(), 90);
        } else {
            assertEquals(bank.getMoney(), 110);
            assertEquals(player.getMoney(), 90);
        }
    }

    @Test
    public void mustCorrectCheckDiceRoll() {
        int countDiceRoll = 2;
        game.start(10, countDiceRoll);

        assertEquals(game.getCountDiceRoll(), countDiceRoll);

        if (game.playerRollAndCheckToWin()) {
            assertEquals(game.getCountDiceRoll(), Game.END_ROLL);
        } else {
            assertEquals(game.getCountDiceRoll(), countDiceRoll - 1);
        }
    }

    @Test(expected = NotEnoughRollExceptions.class)
    public void mustFailedDiceRoll() {
        game.start(10, 0);
        game.playerRollAndCheckToWin();
        game.playerRollAndCheckToWin();
    }

    @Test
    public void mustDoubleBetRate() {
        game.start(20, 0);
        game.doubleBet();

        if (game.end() == bankId) {
            assertEquals(bank.getMoney(), 140);
            assertEquals(player.getMoney(), 60);
        } else {
            assertEquals(player.getMoney(), 140);
            assertEquals(bank.getMoney(), 60);
        }
    }

    @Test
    public void mustCorrectSave() {
        game.start(20, 0);
        game.save();;

        assertEquals(game.end(), bankId);
        assertEquals(bank.getMoney(), 110);
        assertEquals(player.getMoney(), 90);
    }
}