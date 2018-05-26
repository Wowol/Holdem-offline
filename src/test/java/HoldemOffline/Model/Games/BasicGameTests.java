package HoldemOffline.Model.Games;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;

import HoldemOffline.Model.Actions.Actions;
import org.junit.Before;
import org.junit.Test;
import HoldemOffline.Model.Actions.Exceptions.ActionException;
import HoldemOffline.Model.Actions.Exceptions.InvalidTableState;
import HoldemOffline.Model.Actions.Exceptions.NotEnoughChips;
import HoldemOffline.Model.Player;
import HoldemOffline.Model.Table;
import HoldemOffline.Model.TableStatus;

public class BasicGameTests {

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
    public void BasicGameTest1() throws ActionException {
        try {
            table.startGame(0);
            p1.makeAction(Actions.CALL);
            p2.makeAction(Actions.CALL);
            p3.makeAction(Actions.CALL);
        } catch (InvalidTableState e) {
            return;
        }
        // nie powinien moc tutaj dac call-a, tylko checka
        fail();
    }

    @Test
    public void BasicGameTest2() throws ActionException {
        table.startGame(0);
        p1.makeAction(Actions.RAISE, 100);
        p2.makeAction(Actions.CALL);
        p3.makeAction(Actions.CALL);
    }

    @Test
    public void TooSmallChips1() throws ActionException {
        try {
            table.startGame(0);
            p1.makeAction(Actions.RAISE, 2000);
        } catch (NotEnoughChips e) {
            return;
        }
    }

    @Test
    public void TooSmallChips2() throws ActionException {
        try {
            table.startGame(0);
            p1.makeAction(Actions.RAISE, 600);
            p2.makeAction(Actions.CALL);
        } catch (NotEnoughChips e) {
            return;
        }
    }

    @Test
    public void BasicGameTest4() throws ActionException {
        table.startGame(0);
        p1.makeAction(Actions.CALL);
        p2.makeAction(Actions.CALL);
        p3.makeAction(Actions.CHECK);
    }

    @Test
    public void BasicGameTest5() throws ActionException {
        table.startGame(0);
        p1.makeAction(Actions.RAISE, 50);
        p2.makeAction(Actions.CALL);
        p3.makeAction(Actions.CALL);
    }

    @Test
    public void BasicGameTest6() throws ActionException {
        table.startGame(0);
        p1.makeAction(Actions.RAISE, 50);
        p2.makeAction(Actions.RAISE, 80);
        p3.makeAction(Actions.CALL);
        p1.makeAction(Actions.CALL);
        assertEquals(table.status, TableStatus.FLOP);
    }
}