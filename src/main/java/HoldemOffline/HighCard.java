package HoldemOffline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Function;

public class HighCard implements Function<ArrayList<Card>, Hand> {
    @Override
    public Hand apply(ArrayList<Card> cards) {
        if(cards.size() == 0)
            throw new IllegalArgumentException();

        Collections.sort(cards);
        ArrayList<Card> handCards = new ArrayList<>();
        ArrayList<Card> kickers = new ArrayList<>();
        handCards.add(cards.get(cards.size() - 1));

        for (int i = 0; i < cards.size() - 1; i++)
            kickers.add(cards.get(i));

        return new Hand(HandName.HIGH_CARD, handCards, kickers);
    }
}
