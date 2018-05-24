package HoldemOffline.Model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Pot {
    public Map<Player, Integer> players = new HashMap<>();
    public int chips = 0;
    public int maxBet = 0;
}