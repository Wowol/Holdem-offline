package HoldemOffline.Model;

import HoldemOffline.Model.Actions.Actions;
import HoldemOffline.Model.Actions.Call;
import HoldemOffline.Model.Actions.Check;
import HoldemOffline.Model.Actions.Exceptions.ActionException;
import HoldemOffline.Model.Actions.Raise;
import HoldemOffline.Model.Utilities.RankPair;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ArtificialIntelligence extends Player {
    private static Map<RankPair, Integer> HandStrength = new HashMap<>();

    static {
        HandStrength.put(new RankPair(Rank.ACE, Rank.ACE), 0);
        HandStrength.put(new RankPair(Rank.KING, Rank.KING), 0);
        HandStrength.put(new RankPair(Rank.QUEEN, Rank.QUEEN), 0);
        HandStrength.put(new RankPair(Rank.JACK, Rank.JACK), 0);
        HandStrength.put(new RankPair(Rank.ACE, Rank.KING), 0);

        HandStrength.put(new RankPair(Rank.TEN, Rank.TEN), 1);
        HandStrength.put(new RankPair(Rank.ACE, Rank.QUEEN), 1);
        HandStrength.put(new RankPair(Rank.ACE, Rank.JACK), 1);
        HandStrength.put(new RankPair(Rank.KING, Rank.QUEEN), 1);

        HandStrength.put(new RankPair(Rank.NINE, Rank.NINE), 2);
        HandStrength.put(new RankPair(Rank.ACE, Rank.TEN), 2);
        HandStrength.put(new RankPair(Rank.KING, Rank.JACK), 2);
        HandStrength.put(new RankPair(Rank.QUEEN, Rank.JACK), 2);
        HandStrength.put(new RankPair(Rank.JACK, Rank.TEN), 2);

        HandStrength.put(new RankPair(Rank.EIGHT, Rank.EIGHT), 3);
        HandStrength.put(new RankPair(Rank.KING, Rank.TEN), 3);
        HandStrength.put(new RankPair(Rank.QUEEN, Rank.TEN), 3);
        HandStrength.put(new RankPair(Rank.JACK, Rank.NINE), 3);
        HandStrength.put(new RankPair(Rank.TEN, Rank.NINE), 3);
        HandStrength.put(new RankPair(Rank.NINE, Rank.EIGHT), 3);

        HandStrength.put(new RankPair(Rank.ACE, Rank.NINE), 4);
        HandStrength.put(new RankPair(Rank.ACE, Rank.EIGHT), 4);
        HandStrength.put(new RankPair(Rank.ACE, Rank.SEVEN), 4);
        HandStrength.put(new RankPair(Rank.ACE, Rank.SIX), 4);
        HandStrength.put(new RankPair(Rank.ACE, Rank.FIVE), 4);
        HandStrength.put(new RankPair(Rank.ACE, Rank.FOUR), 4);
        HandStrength.put(new RankPair(Rank.ACE, Rank.THREE), 4);
        HandStrength.put(new RankPair(Rank.ACE, Rank.TWO), 4);
        HandStrength.put(new RankPair(Rank.QUEEN, Rank.NINE), 4);
        HandStrength.put(new RankPair(Rank.TEN, Rank.EIGHT), 4);
        HandStrength.put(new RankPair(Rank.EIGHT, Rank.SEVEN), 4);
        HandStrength.put(new RankPair(Rank.SEVEN, Rank.SEVEN), 4);
        HandStrength.put(new RankPair(Rank.SEVEN, Rank.SIX), 4);

        HandStrength.put(new RankPair(Rank.SIX, Rank.SIX), 5);
        HandStrength.put(new RankPair(Rank.FIVE, Rank.FIVE), 5);
        HandStrength.put(new RankPair(Rank.KING, Rank.NINE), 5);
        HandStrength.put(new RankPair(Rank.JACK, Rank.EIGHT), 5);
        HandStrength.put(new RankPair(Rank.EIGHT, Rank.SIX), 5);
        HandStrength.put(new RankPair(Rank.SEVEN, Rank.FIVE), 5);
        HandStrength.put(new RankPair(Rank.FIVE, Rank.FOUR), 5);

        HandStrength.put(new RankPair(Rank.FOUR, Rank.FOUR), 6);
        HandStrength.put(new RankPair(Rank.THREE, Rank.THREE), 6);
        HandStrength.put(new RankPair(Rank.TWO, Rank.TWO), 6);
        HandStrength.put(new RankPair(Rank.KING, Rank.EIGHT), 6);
        HandStrength.put(new RankPair(Rank.KING, Rank.SEVEN), 6);
        HandStrength.put(new RankPair(Rank.KING, Rank.SIX), 6);
        HandStrength.put(new RankPair(Rank.KING, Rank.FIVE), 6);
        HandStrength.put(new RankPair(Rank.KING, Rank.FOUR), 6);
        HandStrength.put(new RankPair(Rank.KING, Rank.THREE), 6);
        HandStrength.put(new RankPair(Rank.KING, Rank.TWO), 6);
        HandStrength.put(new RankPair(Rank.QUEEN, Rank.EIGHT), 6);
        HandStrength.put(new RankPair(Rank.TEN, Rank.SEVEN), 6);
        HandStrength.put(new RankPair(Rank.SIX, Rank.FOUR), 6);
        HandStrength.put(new RankPair(Rank.FIVE, Rank.THREE), 6);
        HandStrength.put(new RankPair(Rank.FOUR, Rank.THREE), 6);
    }

    private double aggression;

    public ArtificialIntelligence(Table table, double aggression) {
        this.table = table;
        this.aggression = aggression;
    }

    void makeAction() throws ActionException{
        int s = 0;

        HandStrength.putIfAbsent(new RankPair(this), 7);

        switch (table.status) {
            case PRE_FLOP:
                s += 100 * (7 - HandStrength.get(new RankPair(this))) / 7;
            case FLOP:
                s += 30 * (7 - HandStrength.get(new RankPair(this))) / 7;
                s += 50 * (10 - currentBestHand.getHandName().ordinal());
                s += 20 * (13 - currentBestHand.getHandCards().get(0).getRank().ordinal());
            case TURN:
                s += 15 * (7 - HandStrength.get(new RankPair(this))) / 7;
                s += 65 * (10 - currentBestHand.getHandName().ordinal());
                s += 20 * (13 - currentBestHand.getHandCards().get(0).getRank().ordinal());
            case RIVER:
                s += 80 * (10 - currentBestHand.getHandName().ordinal());
                s += 20 * (13 - currentBestHand.getHandCards().get(0).getRank().ordinal());
        }

        s *= 0.5;
        s += 20 * aggression;
        s += 30 * (1 - numberOfChipsNeededToCall() / (numberOfChipsNeededToCall() + chipsOnTable()));

        if (s <= 35) {
            if (new Check().isPossible(this)) {
                makeAction(Actions.CHECK);
            } else {
                makeAction(Actions.FOLD);
            }
            return;
        }

        if (s <= 60) {
            if (new Call().isPossible(this)) {
                makeAction(Actions.CALL);
            } else {
                makeAction(Actions.All_IN);
            }
            return;
        }

        int howMany = new Random(s/10 - 6).nextInt();

        if (Raise.getMinMaxRaiseValues(this) != null) {
            howMany += Raise.getMinMaxRaiseValues(this).min;
        } else {
            howMany += table.bigBlind * 2;
        }

        if (new Raise(howMany).isPossible(this)) {
            makeAction(Actions.RAISE, howMany);
        } else {
            makeAction(Actions.All_IN);
        }
    }

    private int chipsOnTable() {
        int wyn = 0;

        for (Pot p : table.allPots) {
            wyn += p.chips;
        }

        return wyn;
    }
}
