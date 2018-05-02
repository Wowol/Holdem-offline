package HoldemOffline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Function;

public class Hand implements Comparable<Hand> {

    private HandName handName;
    private ArrayList<Card> handCards;
    private ArrayList<Card> kickers;

    @SuppressWarnings("unchecked")
    public Hand(HandName handName, ArrayList<Card> handCards, ArrayList<Card> kickers) {
        this.handName = handName;
        this.handCards = (ArrayList<Card>) handCards.clone();
        Collections.sort(this.handCards);
        this.kickers = (ArrayList<Card>) kickers.clone();
        Collections.sort(this.kickers);
    }

    public static boolean checkHandToGiven(ArrayList<Card> cards, HandName given) {
        for (int i = HandName.values().length - 1; i > given.ordinal(); i--) {
            Hand h = HandName.values()[i].checkMe(cards);
            if (h != null)
                return true;
        }

        return false;
    }

    public static Hand checkHand(ArrayList<Card> cards) {
        for (int i = HandName.values().length - 1; i >= 0; i--) {
            Hand h = HandName.values()[i].checkMe(cards);
            if (h != null)
                return h;
        }

        throw new IllegalArgumentException();
    }

    public HandName getHandName() {
        return handName;
    }

    public ArrayList<Card> getHandCards() {
        return handCards;
    }

    public ArrayList<Card> getKickers() {
        return kickers;
    }

    @Override
    public int compareTo(Hand h) {
        if (handName.compareTo(h.handName) != 0)
            return handName.compareTo(h.handName);

        for (int i = handCards.size() - 1; i >= 0; i--) {
            if (handCards.get(i).getRank().compareTo(h.handCards.get(i).getRank()) != 0)
                return handCards.get(i).getRank().compareTo(h.handCards.get(i).getRank());
        }

        for (int i = kickers.size() - 1; i >= 0; i--) {
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
