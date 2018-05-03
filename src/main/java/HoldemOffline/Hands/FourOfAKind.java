package HoldemOffline.Hands;

import HoldemOffline.Card;
import HoldemOffline.Hand;
import HoldemOffline.HandName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Function;

public class FourOfAKind implements Function<ArrayList<Card>, Hand> {
    @Override
    public Hand apply(ArrayList<Card> cards) {
        if (cards == null || cards.size() < 5)
            throw new IllegalArgumentException();

        if (Hand.checkHandToGiven(cards, HandName.FOUR_OF_A_KIND))
            return null;

        @SuppressWarnings("unchecked")
        ArrayList<Card> sortedCards = (ArrayList<Card>) cards.clone();
        ArrayList<Card> handCards = new ArrayList<>();

        Collections.sort(sortedCards, Collections.reverseOrder());

        int counter = 1;
        for (int i = 0; i < sortedCards.size() - 1; i++) {
            if (sortedCards.get(i).getRank() == sortedCards.get(i + 1).getRank())
                counter++;
            else
                counter = 1;

            if (counter == 4) {
                handCards.addAll(sortedCards.subList(i - 2, i + 2));
                sortedCards.removeAll(sortedCards.subList(i - 2, i + 2));
                break;
            }
        }

        if (counter != 4)
            return null;

        ArrayList<Card> kickers = new ArrayList<>();
        kickers.add(sortedCards.get(0));

        return new Hand(HandName.FOUR_OF_A_KIND, handCards, kickers);
    }
}
