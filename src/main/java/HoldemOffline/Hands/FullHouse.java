package HoldemOffline.Hands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Function;
import HoldemOffline.*;

public class FullHouse implements Function<ArrayList<Card>, Hand> {
    public Hand apply(ArrayList<Card> cards) {
        if (cards == null || cards.size() < 5)
            throw new IllegalArgumentException();

        if (Hand.checkHandToGiven(cards, HandName.FULL_HOUSE))
            return null;

        @SuppressWarnings("unchecked")
        ArrayList<Card> sortedCards = (ArrayList<Card>) cards.clone();

        Collections.sort(sortedCards, Collections.reverseOrder());
        ArrayList<Card> maxPair = null;
        ArrayList<Card> maxThreeCards = null;

        for (int i = 0; i < sortedCards.size() - 1; i++) {
            if (maxThreeCards == null && i < sortedCards.size() - 2
                    && sortedCards.get(i).getRank() == sortedCards.get(i + 1).getRank()
                    && sortedCards.get(i).getRank() == sortedCards.get(i + 2).getRank()) {
                maxThreeCards = new ArrayList<Card>(sortedCards.subList(i, i + 3));
                i++;
                if (maxPair != null)
                    break;
                continue;
            }

            if (maxPair == null && sortedCards.get(i).getRank() == sortedCards.get(i + 1).getRank()) {
                maxPair = new ArrayList<Card>(sortedCards.subList(i, i + 2));
                if (maxThreeCards != null)
                    break;
            }
        }
        if (maxPair == null || maxThreeCards == null) {
            return null;
        }

        maxThreeCards.addAll(maxPair);

        return new Hand(HandName.FULL_HOUSE, maxThreeCards, new ArrayList<Card>());
    }
}
