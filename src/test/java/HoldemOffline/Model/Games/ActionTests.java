package HoldemOffline.Model.Games;

import HoldemOffline.Model.Actions.Actions;
import HoldemOffline.Model.Actions.Exceptions.ActionException;
import HoldemOffline.Model.Player;
import HoldemOffline.Model.Pot;
import HoldemOffline.Model.Table;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class ActionTests {
    Table table;
    Player p1;
    Player p2;
    Player p3;
    Player p4;

    @Test
    public void test1() throws ActionException {
        for (int i=0; i<1000; i++) {
            table = new Table();
            p1 = new Player(table);
            p1.numberOfChips = 500;
            p2 = new Player(table);
            p2.numberOfChips = 50;
            p3 = new Player(table);
            p3.numberOfChips = 500;
            table.players.addAll(Arrays.asList(p1, p2, p3));
            table.smallBlind = 10;
            table.bigBlind = 20;

            table.startGame(0);
            p1.makeAction(Actions.RAISE, 300);
            assertEquals(200, p1.numberOfChips);
            p2.makeAction(Actions.All_IN);
            assertEquals(0, p2.numberOfChips);
            p3.makeAction(Actions.CALL);
            assertEquals(200, p3.numberOfChips);
            p3.makeAction(Actions.BET, 100);
            assertEquals(100, p3.numberOfChips);
            p1.makeAction(Actions.CALL);
            assertEquals(100, p1.numberOfChips);
            p3.makeAction(Actions.CHECK);
            p1.makeAction(Actions.CHECK);
            //System.out.println(TestFunctions.printAllPots(table));
            p3.makeAction(Actions.CHECK);
            p1.makeAction(Actions.CHECK);
        /*System.out.println(p1.numberOfChips);
        System.out.println(p2.numberOfChips);
        System.out.println(p3.numberOfChips);*/
            assertTrue(p2.numberOfChips == 0 || p2.numberOfChips == 150);
            assertTrue(p1.numberOfChips == 780 || p1.numberOfChips == 80 || p1.numberOfChips == 930);
            assertTrue(p3.numberOfChips == 790 || p3.numberOfChips == 90 || p3.numberOfChips == 940);
        }
    }

    @Test
    public void test2() throws ActionException {
        table = new Table();
        p1 = new Player(table);
        p1.numberOfChips = 500;
        p2 = new Player(table);
        p2.numberOfChips = 50;
        p3 = new Player(table);
        p3.numberOfChips = 500;
        p4 = new Player(table);
        p4.numberOfChips = 200;
        table.players.addAll(Arrays.asList(p1, p2, p3, p4));
        table.smallBlind = 10;
        table.bigBlind = 20;
        table.startGame(0);

        p4.makeAction(Actions.All_IN);
        p1.makeAction(Actions.RAISE, 400);
        p2.makeAction(Actions.All_IN);
        p3.makeAction(Actions.CALL);
        assertEquals(100, p3.numberOfChips);
        assertEquals("MAXBET: 150\n" +
                "CHIPS: 450\n" +
                "player 4: 150\n" +
                "player 3: 150\n" +
                "player 1: 150\n" +
                "\n" +
                "MAXBET: 200\n" +
                "CHIPS: 400\n" +
                "player 3: 200\n" +
                "player 1: 200\n" +
                "\n" +
                "MAXBET: 50\n" +
                "CHIPS: 200\n" +
                "player 4: 50\n" +
                "player 2: 50\n" +
                "player 3: 50\n" +
                "player 1: 50" + "\n\n", TestFunctions.printAllPots(table));
        System.out.println(TestFunctions.printAllPots(table));
    }
}
