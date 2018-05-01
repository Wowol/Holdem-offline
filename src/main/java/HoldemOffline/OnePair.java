package HoldemOffline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Function;

public class OnePair implements Function<ArrayList<Card>, Hand> {
    @Override
    public Hand apply(ArrayList<Card> cards) {
        ArrayList<Card> handcards = new ArrayList<>();
        ArrayList<Card> kickers = new ArrayList<>();
        ArrayList<Card> tempcards = (ArrayList<Card>) cards.clone();
        Collections.sort(tempcards);
        for (int i = tempcards.size() - 1; i > 0; i--) {
            if (tempcards.get(i).getRank() == tempcards.get(i - 1).getRank()) {
                handcards.add(tempcards.get(i));
                handcards.add(tempcards.get(i - 1));
                tempcards.remove(i);
                tempcards.remove(i - 1);
                break;
            }
        }
        for (Card i : tempcards)
            kickers.add(i);
        if (handcards.size() == 0)
            return null;
        return new Hand(HandName.ONE_PAIR, handcards, kickers);
    }
}
