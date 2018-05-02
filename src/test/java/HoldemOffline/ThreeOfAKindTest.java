package HoldemOffline;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
        assertEquals(new ThreeOfAKind().apply(cards).getHandName(), HandName.THREE_OF_A_KIND);
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
    }


}
