package HoldemOffline.Model.Hands;

import HoldemOffline.Model.Card;
import HoldemOffline.Model.Hand;
import HoldemOffline.Model.Rank;
import HoldemOffline.Model.Suit;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;

public class StraightTest {
    @Test
    public void straightTest1() {
        ArrayList<Card> cards = new ArrayList<>(Arrays.asList(
                new Card(Rank.ACE, Suit.SPADE),
                new Card(Rank.QUEEN, Suit.CLUB),
                new Card(Rank.JACK, Suit.DIAMOND),
                new Card(Rank.JACK, Suit.CLUB),
                new Card(Rank.TEN, Suit.CLUB),
                new Card(Rank.NINE, Suit.DIAMOND),
                new Card(Rank.EIGHT, Suit.CLUB)
        ));
        assertEquals(Arrays.asList(
                new Card(Rank.QUEEN, Suit.CLUB),
                new Card(Rank.JACK, Suit.DIAMOND),
                new Card(Rank.TEN, Suit.CLUB),
                new Card(Rank.NINE, Suit.DIAMOND),
                new Card(Rank.EIGHT, Suit.CLUB)
        ), new Straight().apply(cards).getHandCards());
    }

    @Test
    public void straightTest2() {
        ArrayList<Card> cards = new ArrayList<>(Arrays.asList(
                new Card(Rank.ACE, Suit.SPADE),
                new Card(Rank.QUEEN, Suit.CLUB),
                new Card(Rank.JACK, Suit.DIAMOND),
                new Card(Rank.TEN, Suit.CLUB),
                new Card(Rank.SEVEN, Suit.DIAMOND),
                new Card(Rank.EIGHT, Suit.CLUB)
        ));
        assertNull(new Straight().apply(cards));
    }

    @Test
    public void straightTest3() {
        ArrayList<Card> cards = new ArrayList<>(Arrays.asList(
                new Card(Rank.ACE, Suit.SPADE),
                new Card(Rank.QUEEN, Suit.CLUB),
                new Card(Rank.JACK, Suit.DIAMOND),
                new Card(Rank.TEN, Suit.CLUB),
                new Card(Rank.SEVEN, Suit.DIAMOND),
                new Card(Rank.EIGHT, Suit.CLUB)
        ));
        assertNull(new Straight().apply(cards));
    }

    @Test
    public void straightTest4() {
        ArrayList<Card> cards = new ArrayList<>(Arrays.asList(
                new Card(Rank.ACE, Suit.SPADE),
                new Card(Rank.TWO, Suit.CLUB),
                new Card(Rank.TWO, Suit.DIAMOND),
                new Card(Rank.THREE, Suit.DIAMOND),
                new Card(Rank.THREE, Suit.SPADE),
                new Card(Rank.FOUR, Suit.CLUB),
                new Card(Rank.FIVE, Suit.DIAMOND),
                new Card(Rank.EIGHT, Suit.CLUB)
        ));
        Hand h = new Straight().apply(cards);
        assertNotNull(h);
    }

    @Test
    public void straightTest5() {
        ArrayList<Card> cards = new ArrayList<>(Arrays.asList(
                new Card(Rank.ACE, Suit.SPADE),
                new Card(Rank.TWO, Suit.CLUB),
                new Card(Rank.TWO, Suit.DIAMOND),
                new Card(Rank.THREE, Suit.DIAMOND),
                new Card(Rank.THREE, Suit.SPADE),
                new Card(Rank.FOUR, Suit.CLUB),
                new Card(Rank.FIVE, Suit.DIAMOND),
                new Card(Rank.SIX, Suit.DIAMOND),
                new Card(Rank.EIGHT, Suit.CLUB)
        ));
        Hand h = new Straight().apply(cards);
        assertEquals(Rank.SIX, h.getHandCards().get(0).getRank());
    }
}
