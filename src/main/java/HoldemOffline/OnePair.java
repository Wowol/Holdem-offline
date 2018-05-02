package HoldemOffline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Function;

public class OnePair implements Function<ArrayList<Card>, Hand> {

    @Override
    public Hand apply(ArrayList<Card> cards) {
        if (cards.size() < 5)
            throw new IllegalArgumentException();

        if (Hand.checkHandToGiven(cards, HandName.ONE_PAIR))
            return null;

        ArrayList<Card> handCards = new ArrayList<>();
        @SuppressWarnings("unchecked")
        ArrayList<Card> tempCards = (ArrayList<Card>) cards.clone();
        Collections.sort(tempCards, Collections.reverseOrder());

        for (int i = tempCards.size() - 1; i > 0; i--) {
            if (tempCards.get(i).getRank() == tempCards.get(i - 1).getRank()) {
                handCards.add(tempCards.get(i));
                handCards.add(tempCards.get(i - 1));
                tempCards.remove(i);
                tempCards.remove(i - 1);
                break;
            }
        }

        if (handCards.size() == 0)
            return null;

        ArrayList<Card> kickers = new ArrayList<>(tempCards.subList(0, 3));

        return new Hand(HandName.ONE_PAIR, handCards, kickers);
    }
}