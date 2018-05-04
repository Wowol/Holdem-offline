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

public class FourOfAKindTest {
    @Test
    public void test1() {
        ArrayList<Card> cards = new ArrayList<>(Arrays.asList(
                new Card(Rank.ACE, Suit.SPADE),
                new Card(Rank.TWO, Suit.CLUB),
                new Card(Rank.TWO, Suit.DIAMOND),
                new Card(Rank.TWO, Suit.HEART),
                new Card(Rank.TWO, Suit.SPADE),
                new Card(Rank.FOUR, Suit.CLUB),
                new Card(Rank.FIVE, Suit.DIAMOND),
                new Card(Rank.EIGHT, Suit.CLUB)
        ));
        Hand h = new FourOfAKind().apply(cards);
        assertNotNull(h);
    }

    @Test
    public void test2() {
        ArrayList<Card> cards = new ArrayList<>(Arrays.asList(
                new Card(Rank.ACE, Suit.SPADE),
                new Card(Rank.TWO, Suit.CLUB),
                new Card(Rank.TWO, Suit.DIAMOND),
                new Card(Rank.TWO, Suit.HEART),
                new Card(Rank.THREE, Suit.SPADE),
                new Card(Rank.FOUR, Suit.CLUB),
                new Card(Rank.FIVE, Suit.DIAMOND),
                new Card(Rank.EIGHT, Suit.CLUB)
        ));
        Hand h = new FourOfAKind().apply(cards);
        assertNull(h);
    }

    @Test
    public void test3() {
        ArrayList<Card> cards = new ArrayList<>(Arrays.asList(
                new Card(Rank.ACE, Suit.SPADE),
                new Card(Rank.TWO, Suit.CLUB),
                new Card(Rank.TWO, Suit.DIAMOND),
                new Card(Rank.ACE, Suit.HEART),
                new Card(Rank.TWO, Suit.SPADE),
                new Card(Rank.ACE, Suit.CLUB),
                new Card(Rank.ACE, Suit.DIAMOND),
                new Card(Rank.EIGHT, Suit.CLUB)
        ));
        Hand h = new FourOfAKind().apply(cards);
        assertNotNull(h);
        assertEquals(Arrays.asList(
                new Card(Rank.ACE, Suit.SPADE),
                new Card(Rank.ACE, Suit.HEART),
                new Card(Rank.ACE, Suit.DIAMOND),
                new Card(Rank.ACE, Suit.CLUB)
        ), h.getHandCards());
        assertEquals(Arrays.asList(
                new Card(Rank.EIGHT, Suit.CLUB)
        ), h.getKickers());
    }

    @Test
    public void test4() {
        ArrayList<Card> cards = new ArrayList<>(Arrays.asList(
                new Card(Rank.ACE, Suit.SPADE),
                new Card(Rank.TWO, Suit.CLUB),
                new Card(Rank.ACE, Suit.HEART),
                new Card(Rank.ACE, Suit.CLUB),
                new Card(Rank.ACE, Suit.DIAMOND)
        ));
        Hand h = new FourOfAKind().apply(cards);
        assertNotNull(h);
        assertEquals(Arrays.asList(
                new Card(Rank.ACE, Suit.SPADE),
                new Card(Rank.ACE, Suit.HEART),
                new Card(Rank.ACE, Suit.DIAMOND),
                new Card(Rank.ACE, Suit.CLUB)
        ), h.getHandCards());
        assertEquals(Arrays.asList(
                new Card(Rank.TWO, Suit.CLUB)
        ), h.getKickers());
    }

    @Test
    public void test5() {
        ArrayList<Card> cards = new ArrayList<>(Arrays.asList(
                new Card(Rank.TWO, Suit.SPADE),
                new Card(Rank.ACE, Suit.CLUB),
                new Card(Rank.TWO, Suit.HEART),
                new Card(Rank.TWO, Suit.CLUB),
                new Card(Rank.TWO, Suit.DIAMOND)
        ));
        Hand h = new Hand(cards);

        assertNotNull(h);
        assertEquals(Arrays.asList(
                new Card(Rank.TWO, Suit.SPADE),
                new Card(Rank.TWO, Suit.HEART),
                new Card(Rank.TWO, Suit.DIAMOND),
                new Card(Rank.TWO, Suit.CLUB)
        ), h.getHandCards());
        assertEquals(Arrays.asList(
                new Card(Rank.ACE, Suit.CLUB)
        ), h.getKickers());
    }
}
