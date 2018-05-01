package HoldemOffline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Function;

public class OnePair implements Function<ArrayList<Card>, Hand> {
    @Override
    @SuppressWarnings("unchecked")
    public Hand apply(ArrayList<Card> cards) {
        ArrayList<Card> handCards = new ArrayList<>();
        ArrayList<Card> tempCards = (ArrayList<Card>) cards.clone();
        Collections.sort(tempCards);

        for (int i = tempCards.size() - 1; i > 0; i--) {
            if (tempCards.get(i).getRank() == tempCards.get(i - 1).getRank()) {
                handCards.add(tempCards.get(i));
                handCards.add(tempCards.get(i - 1));
                tempCards.remove(i);
                tempCards.remove(i - 1);
                break;
            }
        }

        ArrayList<Card> kickers = new ArrayList<>(tempCards);

        if (handCards.size() == 0)
            return null;

        return new Hand(HandName.ONE_PAIR, handCards, kickers);
    }
}
