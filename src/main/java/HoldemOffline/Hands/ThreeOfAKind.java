package HoldemOffline.Hands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Function;
import HoldemOffline.*;


public class ThreeOfAKind implements Function<ArrayList<Card>, Hand> {
    @Override
    public Hand apply(ArrayList<Card> cards) {
        if (cards == null || cards.size() < 5)
            throw new IllegalArgumentException();

        if (Hand.checkHandToGiven(cards, HandName.THREE_OF_A_KIND))
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

            if (counter == 3) {
                handCards.add(sortedCards.get(i - 1));
                handCards.add(sortedCards.get(i));
                handCards.add(sortedCards.get(i + 1));
                sortedCards.remove(i + 1);
                sortedCards.remove(i);
                sortedCards.remove(i - 1);
                break;
            }
        }

        if(counter != 3)
            return null;

        ArrayList<Card> kickers = new ArrayList<>(sortedCards.subList(0, 2));

        return new Hand(HandName.THREE_OF_A_KIND, handCards, kickers);
    }
}
