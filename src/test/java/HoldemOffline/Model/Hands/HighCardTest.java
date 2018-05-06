package HoldemOffline.Model.Hands;

import HoldemOffline.Model.Card;
import HoldemOffline.Model.Hand;
import HoldemOffline.Model.Rank;
import HoldemOffline.Model.Suit;
import org.junit.Test;

import java.util.ArrayList;
import static org.junit.Assert.assertEquals;

public class HighCardTest {
    @Test
    public void basicHigh() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.TWO, Suit.SPADE));
        cards.add(new Card(Rank.THREE, Suit.HEART));
        cards.add(new Card(Rank.FOUR, Suit.HEART));
        cards.add(new Card(Rank.SEVEN, Suit.HEART));
        cards.add(new Card(Rank.NINE, Suit.HEART));
        assertEquals("[NINE HEART]", Hand.checkHand(cards).getHandCards().toString());
    }

    @Test
    public void highTest2() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.TWO, Suit.SPADE));
        cards.add(new Card(Rank.THREE, Suit.HEART));
        cards.add(new Card(Rank.FOUR, Suit.HEART));
        cards.add(new Card(Rank.SEVEN, Suit.HEART));
        cards.add(new Card(Rank.KING, Suit.CLUB));
        cards.add(new Card(Rank.TEN, Suit.DIAMOND));
        cards.add(new Card(Rank.QUEEN, Suit.DIAMOND));

        Hand h = new HighCard().apply(cards);
        assertEquals("[KING CLUB]", h.getHandCards().toString());
        assertEquals("[QUEEN DIAMOND, TEN DIAMOND, SEVEN HEART, FOUR HEART]", h.getKickers().toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void exception() {
        ArrayList<Card> cards = new ArrayList<>();
        Hand.checkHand(cards);
    }
}
