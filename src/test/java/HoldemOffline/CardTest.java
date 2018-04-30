package HoldemOffline;

import static org.junit.Assert.assertEquals;

import org.junit.*;

public class CardTest {

    @Test(expected = IllegalArgumentException.class)
    public void cardBothNullTest() {
        new Card(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void cardRankNullTest() {
        new Card(null, Suit.DIAMOND);
    }

    @Test(expected = IllegalArgumentException.class)
    public void cardSuitNullTest() {
        new Card(Rank.SIX, null);
    }

    @Test
    public void cardTest() {
        Card card = new Card(Rank.NINE, Suit.SPADE);
        assertEquals(Rank.NINE, card.getRank());
        assertEquals(Suit.SPADE, card.getSuit());
    }
}