package HoldemOffline;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;

public class FlushTest {
    @Test
    public void flushTest1() {
        ArrayList<Card> cards = new ArrayList<>(Arrays.asList(
                new Card(Rank.FIVE, Suit.DIAMOND),
                new Card(Rank.KING, Suit.SPADE),
                new Card(Rank.QUEEN, Suit.SPADE),
                new Card(Rank.THREE, Suit.SPADE),
                new Card(Rank.JACK, Suit.SPADE),
                new Card(Rank.ACE, Suit.SPADE),
                new Card(Rank.TWO, Suit.DIAMOND),
                new Card(Rank.TWO, Suit.SPADE)
        ));
        Hand h = new Flush().apply(cards);
        assertNotNull(h);
        assertEquals(Arrays.asList(
                new Card(Rank.ACE, Suit.SPADE),
                new Card(Rank.KING, Suit.SPADE),
                new Card(Rank.QUEEN, Suit.SPADE),
                new Card(Rank.JACK, Suit.SPADE),
                new Card(Rank.THREE, Suit.SPADE)
        ), h.getHandCards());
        assertEquals(new ArrayList<Card>(), h.getKickers());
    }

    @Test
    public void flushTest2() {
        ArrayList<Card> cards = new ArrayList<>(Arrays.asList(
                new Card(Rank.FIVE, Suit.DIAMOND),
                new Card(Rank.KING, Suit.SPADE),
                new Card(Rank.QUEEN, Suit.SPADE),
                new Card(Rank.THREE, Suit.SPADE),
                new Card(Rank.JACK, Suit.SPADE),
                new Card(Rank.ACE, Suit.SPADE),
                new Card(Rank.TWO, Suit.DIAMOND),
                new Card(Rank.THREE, Suit.DIAMOND),
                new Card(Rank.JACK, Suit.DIAMOND),
                new Card(Rank.ACE, Suit.DIAMOND)
        ));
        Hand h = new Flush().apply(cards);
        assertEquals(Suit.SPADE, h.getHandCards().get(0).getSuit());
    }

    @Test
    public void flushTest3() {
        ArrayList<Card> cards = new ArrayList<>(Arrays.asList(
                new Card(Rank.FIVE, Suit.DIAMOND),
                new Card(Rank.KING, Suit.SPADE),
                new Card(Rank.QUEEN, Suit.CLUB),
                new Card(Rank.THREE, Suit.SPADE),
                new Card(Rank.JACK, Suit.SPADE),
                new Card(Rank.ACE, Suit.SPADE),
                new Card(Rank.TWO, Suit.DIAMOND)
        ));
        Hand h = new Flush().apply(cards);
        assertNull(h);
    }

    @Test
    public void flushTest4() {
        ArrayList<Card> cards = new ArrayList<>(Arrays.asList(
                new Card(Rank.KING, Suit.CLUB),
                new Card(Rank.QUEEN, Suit.CLUB),
                new Card(Rank.THREE, Suit.CLUB),
                new Card(Rank.JACK, Suit.CLUB),
                new Card(Rank.ACE, Suit.CLUB),
                new Card(Rank.FIVE, Suit.DIAMOND),
                new Card(Rank.TWO, Suit.DIAMOND),
                new Card(Rank.THREE, Suit.DIAMOND),
                new Card(Rank.JACK, Suit.DIAMOND),
                new Card(Rank.ACE, Suit.DIAMOND)
        ));
        Hand h = new Flush().apply(cards);
        assertEquals(Suit.CLUB, h.getHandCards().get(0).getSuit());
    }
}
