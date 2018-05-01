package HoldemOffline;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;


public class OnePairAndHighCardTest {
    @Test
    public void basicHigh(){
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.TWO, Suit.SPADE));
        cards.add(new Card(Rank.THREE, Suit.HEART));
        assertEquals(Hand.checkHand(cards).toString(), "HIGH_CARD[THREE HEART][TWO SPADE]");
    }

    @Test
    public void basicPair(){
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.TWO, Suit.SPADE));
        cards.add(new Card(Rank.TWO, Suit.HEART));
        assertEquals(Hand.checkHand(cards).toString(), "ONE_PAIR[TWO SPADE, TWO HEART][]");
    }

    @Test
    public void basicPair2(){
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.TWO, Suit.SPADE));
        cards.add(new Card(Rank.THREE, Suit.HEART));
        cards.add(new Card(Rank.TWO, Suit.HEART));
        assertEquals(Hand.checkHand(cards).toString(), "ONE_PAIR[TWO SPADE, TWO HEART][THREE HEART]");
    }

    @Test(expected = IllegalArgumentException.class)
    public void exception(){
        ArrayList<Card> cards = new ArrayList<>();
        Hand.checkHand(cards);
    }
}
