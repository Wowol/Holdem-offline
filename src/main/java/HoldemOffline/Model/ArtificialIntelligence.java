package HoldemOffline.Model;

import HoldemOffline.Model.Actions.*;
import HoldemOffline.Model.Actions.Exceptions.ActionException;
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

        System.out.println(HandStrength);
    }

    private double aggression;

    public ArtificialIntelligence(Table table, double aggression) {
        this.table = table;
        this.aggression = aggression;
    }

    public void makeAction() throws ActionException {
        int s = 0;
        RankPair myHand = new RankPair(this);
        HandStrength.putIfAbsent(myHand, 7);

        System.out.println(table.status);
        System.out.println(myHand);
        System.out.println(HandStrength.get(myHand));

        switch (table.status) {
            case PRE_FLOP:
                s += 100 * (7 - HandStrength.get(myHand)) / 7;
                break;
            case FLOP:
                s += 30 * (7 - HandStrength.get(myHand)) / 7;
                s += 50 * currentBestHand.getHandName().ordinal() / 9;
                s += 20 * currentBestHand.getHandCards().get(0).getRank().ordinal() / 13;
                break;
            case TURN:
                s += 15 * (7 - HandStrength.get(myHand)) / 7;
                s += 65 * currentBestHand.getHandName().ordinal() / 9;
                s += 20 * currentBestHand.getHandCards().get(0).getRank().ordinal() / 13;
                break;
            case RIVER:
                s += 80 * currentBestHand.getHandName().ordinal() / 9;
                s += 20 * currentBestHand.getHandCards().get(0).getRank().ordinal() / 13;
        }

        s *= 0.5;
        s += 20 * aggression;
        s += 30 * (1 - (numberOfChipsNeededToCall() / (numberOfChipsNeededToCall() + table.getNumberOfChipsOnTable())));

        System.out.println("s: " + s);
        System.out.println("neededchips: " + numberOfChipsNeededToCall());

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
                System.out.println("CALL");
                makeAction(Actions.CALL);
            } else if (new Check().isPossible(this)) {
                System.out.println("CHECK");
                makeAction(Actions.CHECK);
            } else {
                System.out.println("ALLIN1");
                makeAction(Actions.All_IN);
            }
            return;
        }

        StringBuilder wyn = new StringBuilder();
        for (Pot p : table.allPots) {
            wyn.append("MAXBET: " + p.maxBet + "\n");
            wyn.append("CHIPS: " + p.chips + "\n");
            for (Map.Entry pl : p.players.entrySet()) {
                wyn.append("player " + (table.players.indexOf(pl.getKey())+1) + ": " + pl.getValue() + "\n");
            }
            wyn.append("\n");
        }
        System.out.println(wyn);

        int howMany = new Random().nextInt(s - 50) + Math.max(table.maxBetInCurrentTurn, table.bigBlind) * 2;
        System.out.println("howmzny: " + howMany);

        if (new Raise(howMany).isPossible(this)) {
            System.out.println("RAISE");
            makeAction(Actions.RAISE, howMany);
        } else if (new Bet(howMany).isPossible(this)) {
            System.out.println("BET");
            makeAction(Actions.BET, howMany);
        } else {
            System.out.println("ALLIN2");
            makeAction(Actions.All_IN);
        }
    }
}
