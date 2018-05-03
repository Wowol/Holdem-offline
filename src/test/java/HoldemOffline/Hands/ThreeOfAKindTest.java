package HoldemOffline.Hands;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import HoldemOffline.*;

public class ThreeOfAKindTest {
    @Test
    public void threeTest1() {
        ArrayList<Card> cards = new ArrayList<>(Arrays.asList(
                new Card(Rank.FIVE, Suit.DIAMOND),
                new Card(Rank.KING, Suit.SPADE),
                new Card(Rank.QUEEN, Suit.CLUB),
                new Card(Rank.FIVE, Suit.SPADE),
                new Card(Rank.FIVE, Suit.HEART),
                new Card(Rank.ACE, Suit.DIAMOND),
                new Card(Rank.TWO, Suit.DIAMOND)
        ));
        Hand h = new ThreeOfAKind().apply(cards);
        assertEquals(h.getHandName(), HandName.THREE_OF_A_KIND);
        assertEquals(Arrays.asList(
                new Card(Rank.FIVE, Suit.SPADE),
                new Card(Rank.FIVE, Suit.HEART),
                new Card(Rank.FIVE, Suit.DIAMOND)
        ), h.getHandCards());
        assertEquals(Arrays.asList(
                new Card(Rank.ACE, Suit.DIAMOND),
                new Card(Rank.KING, Suit.SPADE)
        ), h.getKickers());
    }

    @Test
    public void threeTest2() {
        ArrayList<Card> cards = new ArrayList<>(Arrays.asList(
                new Card(Rank.FOUR, Suit.DIAMOND),
                new Card(Rank.KING, Suit.SPADE),
                new Card(Rank.QUEEN, Suit.CLUB),
                new Card(Rank.FIVE, Suit.SPADE),
                new Card(Rank.FIVE, Suit.HEART),
                new Card(Rank.ACE, Suit.DIAMOND),
                new Card(Rank.TWO, Suit.DIAMOND)
        ));
        assertEquals(new ThreeOfAKind().apply(cards), null);
    }

    @Test
    public void threeTest3() {
        ArrayList<Card> cards = new ArrayList<>(Arrays.asList(
                new Card(Rank.FIVE, Suit.DIAMOND),
                new Card(Rank.KING, Suit.SPADE),
                new Card(Rank.QUEEN, Suit.CLUB),
                new Card(Rank.FIVE, Suit.SPADE),
                new Card(Rank.FIVE, Suit.HEART),
                new Card(Rank.ACE, Suit.DIAMOND),
                new Card(Rank.TWO, Suit.DIAMOND)
        ));
        assertNull(new TwoPair().apply(cards));
    }

    @Test
    public void threeTest4() {
        ArrayList<Card> cards = new ArrayList<>(Arrays.asList(
                new Card(Rank.FOUR, Suit.DIAMOND),
                new Card(Rank.KING, Suit.SPADE),
                new Card(Rank.QUEEN, Suit.CLUB),
                new Card(Rank.ACE, Suit.SPADE),
                new Card(Rank.ACE, Suit.HEART),
                new Card(Rank.ACE, Suit.DIAMOND),
                new Card(Rank.TWO, Suit.DIAMOND)
        ));
        Hand h = new ThreeOfAKind().apply(cards);
        assertNotNull(h);
        assertEquals(5, h.getHandCards().size() + h.getKickers().size());
        assertEquals(Arrays.asList(
                new Card(Rank.ACE, Suit.SPADE),
                new Card(Rank.ACE, Suit.HEART),
                new Card(Rank.ACE, Suit.DIAMOND)
        ), h.getHandCards());
        assertEquals(Arrays.asList(
                new Card(Rank.KING, Suit.SPADE),
                new Card(Rank.QUEEN, Suit.CLUB)
        ), h.getKickers());
    }

}
