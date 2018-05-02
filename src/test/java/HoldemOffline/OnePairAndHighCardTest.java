package HoldemOffline;

import org.junit.Test;

import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class OnePairAndHighCardTest {
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

    @Test(expected = IllegalArgumentException.class)
    public void exception() {
        ArrayList<Card> cards = new ArrayList<>();
        Hand.checkHand(cards);
    }
}
