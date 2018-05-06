package HoldemOffline.Model.Hands;

import HoldemOffline.Model.*;
import org.junit.Test;

import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class OnePairTest {
    @Test
    public void basicPair1() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.TWO, Suit.SPADE));
        cards.add(new Card(Rank.THREE, Suit.HEART));
        cards.add(new Card(Rank.FOUR, Suit.HEART));
        cards.add(new Card(Rank.SEVEN, Suit.HEART));
        cards.add(new Card(Rank.NINE, Suit.HEART));
        assertNull(new OnePair().apply(cards));
    }

    @Test
    public void basicPair2() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.TWO, Suit.SPADE));
        cards.add(new Card(Rank.TWO, Suit.HEART));
        cards.add(new Card(Rank.FOUR, Suit.HEART));
        cards.add(new Card(Rank.SEVEN, Suit.HEART));
        cards.add(new Card(Rank.NINE, Suit.HEART));
        assertNull(new HighCard().apply(cards));
        assertEquals(HandName.ONE_PAIR, Hand.checkHand(cards).getHandName());
        assertEquals("[TWO SPADE, TWO HEART]", Hand.checkHand(cards).getHandCards().toString());
    }

    @Test
    public void pairTest3() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.TWO, Suit.SPADE));
        cards.add(new Card(Rank.SEVEN, Suit.HEART));
        cards.add(new Card(Rank.FOUR, Suit.HEART));
        cards.add(new Card(Rank.TEN, Suit.HEART));
        cards.add(new Card(Rank.FOUR, Suit.CLUB));
        cards.add(new Card(Rank.EIGHT, Suit.CLUB));
        cards.add(new Card(Rank.KING, Suit.CLUB));
        Hand h = new OnePair().apply(cards);
        assertNotNull(h);
        assertEquals("[FOUR HEART, FOUR CLUB]", h.getHandCards().toString());
        assertEquals("[KING CLUB, TEN HEART, EIGHT CLUB]", h.getKickers().toString());
    }
}
