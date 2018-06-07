package HoldemOffline.Model.Utilities;

import HoldemOffline.Model.Card;
import HoldemOffline.Model.Player;
import HoldemOffline.Model.Rank;

import java.util.ArrayList;

public class RankPair {
    Rank first;
    Rank second;

    public RankPair(Rank first, Rank second) {
        this.first = first;
        this.second = second;
    }

    public RankPair(Player player) {
        first = player.getHoleCards().get(0).getRank();
        second = player.getHoleCards().get(1).getRank();
    }
}
