package HoldemOffline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Function;

public class HighCard implements Function<ArrayList<Card>, Hand> {
    @Override
    public Hand apply(ArrayList<Card> cards) {
        if (cards == null || cards.size() < 5)
            throw new IllegalArgumentException();

        if (Hand.checkHandToGiven(cards, HandName.HIGH_CARD))
            return null;

        @SuppressWarnings("unchecked")
        ArrayList<Card> tempCards = (ArrayList<Card>) cards.clone();

        Collections.sort(tempCards, Collections.reverseOrder());
        ArrayList<Card> handCards = new ArrayList<>();
        ArrayList<Card> kickers = new ArrayList<>();
        handCards.add(tempCards.get(0));

        for (int i = 1; i < 5; i++)
            kickers.add(tempCards.get(i));

        return new Hand(HandName.HIGH_CARD, handCards, kickers);
    }
}
