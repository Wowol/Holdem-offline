package HoldemOffline;

import java.util.ArrayList;
import java.util.function.Function;

public class Hand implements Comparable {

    static ArrayList<Function<ArrayList<Card>, Hand>> handlist = new ArrayList<>();

    static {
        handlist.add(new OnePair());
    }

    HandName handname;
    ArrayList<Card> handcards;
    ArrayList<Card> kickers;

    public Hand(HandName handname, ArrayList<Card> handcards, ArrayList<Card> kickers) {
        this.handname = handname;
        this.handcards = (ArrayList<Card>) handcards.clone();
        this.kickers = (ArrayList<Card>) kickers.clone();
    }

    static public Hand checkHand(ArrayList<Card> cards) {
        for (Function<ArrayList<Card>, Hand> i : handlist) {
            Hand h = i.apply(cards);
            if (h != null)
                return h;
        }
        return null;
    }

    @Override
    public int compareTo(Object o) {
        if (o == null)
            throw new NullPointerException();
        if (!(o instanceof Hand))
            throw new ClassCastException();
        Hand h = (Hand) o;
        if (handname.compareTo(h.handname) != 0)
            return handname.compareTo(h.handname);
        for (int i = 0; i < handcards.size(); i++) {
            if (handcards.get(i).getRank().compareTo(h.handcards.get(i).getRank()) != 0)
                return handcards.get(i).getRank().compareTo(h.handcards.get(i).getRank());
        }
        for (int i = 0; i < kickers.size(); i++) {
            if (kickers.get(i).getRank().compareTo(h.kickers.get(i).getRank()) != 0)
                return kickers.get(i).getRank().compareTo(h.kickers.get(i).getRank());
        }
        return 0;
    }

    @Override
    public String toString() {
        return handname + handcards.toString() + kickers.toString();
    }
}
