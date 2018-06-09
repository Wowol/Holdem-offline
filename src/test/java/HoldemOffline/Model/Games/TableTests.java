package HoldemOffline.Model.Games;

import HoldemOffline.Model.Action;
import HoldemOffline.Model.Actions.Actions;
import HoldemOffline.Model.Actions.Exceptions.ActionException;
import HoldemOffline.Model.Player;
import HoldemOffline.Model.Pot;
import HoldemOffline.Model.Table;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TableTests {
    Table table; // blinds - 10, 20
    Player p1; // chips = 700
    Player p2; // chips = 500
    Player p3; // chips = 300

    // p1 is dealer
    // p2 pays smallBlind
    // p3 pays BigBlind
    // p1 is first player to action

    @Before
    public void initialize() {
        table = new Table();
        p1 = new Player(table);
        p1.numberOfChips = 700;
        p2 = new Player(table);
        p2.numberOfChips = 500;
        p3 = new Player(table);
        p3.numberOfChips = 300;
        table.players.addAll(Arrays.asList(p1, p2, p3));
        table.smallBlind = 10;
        table.bigBlind = 20;
    }

    @Test
    public void BlindsTest1() throws ActionException{
        table.startGame(0);
        assertEquals(table.getSmallBlindPlayer(), p2);
        assertEquals(table.getBigBlindPlayer(), p3);
        assertEquals(700, p1.numberOfChips);
        assertEquals(490, p2.numberOfChips);
        assertEquals(280, p3.numberOfChips);
        for (Pot p : table.currentTurnPots) {
            assertEquals(p, table.mainPot);
            assertEquals(20, p.maxBet);
            assertEquals(30, p.chips);
        }
    }

    @Test
    public void RemovePlayersWithNoChipsTest() throws ActionException {
        table.startGame(0);
        p1.makeAction(Actions.RAISE, 300);
        p2.makeAction(Actions.CALL);
        p3.makeAction(Actions.All_IN);
        p2.makeAction(Actions.CHECK);
        p1.makeAction(Actions.CHECK);
        p2.makeAction(Actions.CHECK);
        p1.makeAction(Actions.CHECK);

        for (Player p : table.players) {
            assertFalse(p.numberOfChips == 0);
        }
    }

    @Test
    public void CardsTest() throws ActionException{
        table.startGame(0);
        assertEquals(0, table.tableCards.size());
        for (Player p : table.players) {
            assertEquals(2, p.getHoleCards().size());
        }
        p1.makeAction(Actions.CALL);
        p2.makeAction(Actions.CALL);
        p3.makeAction(Actions.CHECK);
        assertEquals(3, table.tableCards.size());
        p2.makeAction(Actions.CHECK);
        p3.makeAction(Actions.CHECK);
        p1.makeAction(Actions.CHECK);
        assertEquals(4, table.tableCards.size());
        p2.makeAction(Actions.CHECK);
        p3.makeAction(Actions.CHECK);
        p1.makeAction(Actions.CHECK);
        assertEquals(5, table.tableCards.size());
    }

    @Test
    public void EndGameTest() throws ActionException {
        table.startGame(0);
        assertEquals(30, table.mainPot.chips);
        p1.makeAction(Actions.FOLD);
        p2.makeAction(Actions.FOLD);
        assertEquals(30, table.mainPot.chips);
        assertEquals(680, p1.numberOfChips);
        assertEquals(490, p2.numberOfChips);
        assertEquals(300, p3.numberOfChips);

    }

    @Test
    public void test() {}
}
