package HoldemOffline.Model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Pot {
    public Map<Player, Integer> players;
    public int chips;
    public int maxBet;

    public Pot() {
        players = new HashMap<>();
        chips = 0;
        maxBet = 0;
    }
}