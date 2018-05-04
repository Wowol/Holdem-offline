package HoldemOffline.Hands;

import HoldemOffline.Card;
import HoldemOffline.Hand;
import HoldemOffline.Rank;
import HoldemOffline.Suit;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;

public class StraightFlushTest {
    @Test
    public void test1() {
        ArrayList<Card> cards = new ArrayList<>(Arrays.asList(
                new Card(Rank.ACE, Suit.SPADE),
                new Card(Rank.KING, Suit.HEART),
                new Card(Rank.KING, Suit.SPADE),
                new Card(Rank.QUEEN, Suit.SPADE),
                new Card(Rank.JACK, Suit.SPADE),
                new Card(Rank.TEN, Suit.SPADE),
                new Card(Rank.NINE, Suit.SPADE),
                new Card(Rank.ACE, Suit.CLUB)
        ));
        Hand h = new StraightFlush().apply(cards);
        assertNotNull(h);
        assertEquals(Arrays.asList(
                new Card(Rank.ACE, Suit.SPADE),
                new Card(Rank.KING, Suit.SPADE),
                new Card(Rank.QUEEN, Suit.SPADE),
                new Card(Rank.JACK, Suit.SPADE),
                new Card(Rank.TEN, Suit.SPADE)
        ), h.getHandCards());
    }

    @Test
    public void test2() {
        ArrayList<Card> cards = new ArrayList<>(Arrays.asList(
                new Card(Rank.ACE, Suit.SPADE),
                new Card(Rank.KING, Suit.HEART),
                new Card(Rank.KING, Suit.SPADE),
                new Card(Rank.QUEEN, Suit.SPADE),
                new Card(Rank.JACK, Suit.SPADE),
                new Card(Rank.TEN, Suit.SPADE),
                new Card(Rank.NINE, Suit.SPADE),
                new Card(Rank.QUEEN, Suit.HEART),
                new Card(Rank.JACK, Suit.HEART),
                new Card(Rank.TEN, Suit.HEART),
                new Card(Rank.ACE, Suit.CLUB)
        ));
        Hand h = new StraightFlush().apply(cards);
        assertEquals(Suit.SPADE, h.getHandCards().get(0).getSuit());
    }

    @Test
    public void test3() {
        ArrayList<Card> cards = new ArrayList<>(Arrays.asList(
                new Card(Rank.ACE, Suit.SPADE),
                new Card(Rank.TWO, Suit.SPADE),
                new Card(Rank.THREE, Suit.SPADE),
                new Card(Rank.FIVE, Suit.SPADE),
                new Card(Rank.FOUR, Suit.SPADE),
                new Card(Rank.KING, Suit.SPADE),
                new Card(Rank.QUEEN, Suit.SPADE)
        ));
        Hand h = new StraightFlush().apply(cards);
        assertEquals(Arrays.asList(
                new Card(Rank.FIVE, Suit.SPADE),
                new Card(Rank.FOUR, Suit.SPADE),
                new Card(Rank.THREE, Suit.SPADE),
                new Card(Rank.TWO, Suit.SPADE),
                new Card(Rank.ACE, Suit.SPADE)
        ), h.getHandCards());
    }

    @Test
    public void test4() {
        ArrayList<Card> cards = new ArrayList<>(Arrays.asList(
                new Card(Rank.ACE, Suit.SPADE),
                new Card(Rank.TWO, Suit.SPADE),
                new Card(Rank.THREE, Suit.SPADE),
                new Card(Rank.FIVE, Suit.SPADE),
                new Card(Rank.FOUR, Suit.SPADE),
                new Card(Rank.KING, Suit.SPADE),
                new Card(Rank.QUEEN, Suit.SPADE),
                new Card(Rank.JACK, Suit.SPADE),
                new Card(Rank.TEN, Suit.SPADE)
        ));
        Hand h = new StraightFlush().apply(cards);
        assertEquals(Arrays.asList(
                new Card(Rank.ACE, Suit.SPADE),
                new Card(Rank.KING, Suit.SPADE),
                new Card(Rank.QUEEN, Suit.SPADE),
                new Card(Rank.JACK, Suit.SPADE),
                new Card(Rank.TEN, Suit.SPADE)
        ), h.getHandCards());
    }

    @Test
    public void test5() {
        ArrayList<Card> cards = new ArrayList<>(Arrays.asList(
                new Card(Rank.ACE, Suit.HEART),
                new Card(Rank.TWO, Suit.SPADE),
                new Card(Rank.THREE, Suit.SPADE),
                new Card(Rank.FIVE, Suit.SPADE),
                new Card(Rank.FOUR, Suit.SPADE),
                new Card(Rank.KING, Suit.SPADE),
                new Card(Rank.QUEEN, Suit.SPADE),
                new Card(Rank.JACK, Suit.SPADE),
                new Card(Rank.TEN, Suit.SPADE)
        ));
        Hand h = new StraightFlush().apply(cards);
        assertNull(h);
    }
}
