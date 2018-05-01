package HoldemOffline;

import java.util.ArrayList;
import java.util.function.Function;

public class Hand implements Comparable<Hand> {

    private static ArrayList<Function<ArrayList<Card>, Hand>> handList = new ArrayList<>();

    static {
        handList.add(new OnePair());
        handList.add(new HighCard());
    }

    private HandName handName;
    private ArrayList<Card> handCards;
    private ArrayList<Card> kickers;

    @SuppressWarnings("unchecked")
    public Hand(HandName handName, ArrayList<Card> handCards, ArrayList<Card> kickers) {
        this.handName = handName;
        this.handCards = (ArrayList<Card>) handCards.clone();
        this.kickers = (ArrayList<Card>) kickers.clone();
    }

    public static Hand checkHand(ArrayList<Card> cards) {
        for (Function<ArrayList<Card>, Hand> i : handList) {
            Hand h = i.apply(cards);
            if (h != null)
                return h;
        }

        throw new IllegalArgumentException();
    }

    @Override
    public int compareTo(Hand h) {
        if (handName.compareTo(h.handName) != 0)
            return handName.compareTo(h.handName);

        for (int i = 0; i < handCards.size(); i++) {
            if (handCards.get(i).getRank().compareTo(h.handCards.get(i).getRank()) != 0)
                return handCards.get(i).getRank().compareTo(h.handCards.get(i).getRank());
        }

        for (int i = 0; i < kickers.size(); i++) {
            if (kickers.get(i).getRank().compareTo(h.kickers.get(i).getRank()) != 0)
                return kickers.get(i).getRank().compareTo(h.kickers.get(i).getRank());
        }

        return 0;
    }

    @Override
    public String toString() {
        return handName + handCards.toString() + kickers.toString();
    }
}
