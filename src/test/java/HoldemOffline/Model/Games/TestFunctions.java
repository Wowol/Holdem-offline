package HoldemOffline.Model.Games;

import HoldemOffline.Model.Player;
import HoldemOffline.Model.Pot;
import HoldemOffline.Model.Table;

import java.util.Map;

public class TestFunctions {
    public static String printAllPots (Table table) {
        StringBuilder wyn = new StringBuilder();
        for (Pot p : table.allPots) {
            wyn.append("MAXBET: " + p.maxBet + "\n");
            wyn.append("CHIPS: " + p.chips + "\n");
            for (Map.Entry<Player, Integer> pl : p.players.entrySet()) {
                wyn.append("player " + (table.players.indexOf(pl.getKey())+1) + ": " + pl.getValue() + "\n");
            }
            wyn.append("\n");
        }
        return wyn.toString();
    }
}
