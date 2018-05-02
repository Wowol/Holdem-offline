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
        cards.add(new Card(Rank.FOUR, Suit.HEART));
        cards.add(new Card(Rank.SEVEN, Suit.HEART));
        cards.add(new Card(Rank.NINE, Suit.HEART));
        //System.out.println(new HighCard().apply(cards));
        assertEquals(Hand.checkHand(cards).getHandCards().toString(), "[NINE HEART]");
    }

    @Test
    public void basicPair1(){
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.TWO, Suit.SPADE));
        cards.add(new Card(Rank.THREE, Suit.HEART));
        cards.add(new Card(Rank.FOUR, Suit.HEART));
        cards.add(new Card(Rank.SEVEN, Suit.HEART));
        cards.add(new Card(Rank.NINE, Suit.HEART));
        //System.out.println(new HighCard().apply(cards));
        assertEquals(new OnePair().apply(cards), null);
    }

    @Test
    public void basicPair2(){
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.TWO, Suit.SPADE));
        cards.add(new Card(Rank.TWO, Suit.HEART));
        cards.add(new Card(Rank.FOUR, Suit.HEART));
        cards.add(new Card(Rank.SEVEN, Suit.HEART));
        cards.add(new Card(Rank.NINE, Suit.HEART));
        assertEquals(new HighCard().apply(cards), null);
        assertEquals(Hand.checkHand(cards).getHandName(), HandName.ONE_PAIR);
        assertEquals(Hand.checkHand(cards).getHandCards().toString(), "[TWO HEART, TWO SPADE]");
    }


    @Test(expected = IllegalArgumentException.class)
    public void exception(){
        ArrayList<Card> cards = new ArrayList<>();
        Hand.checkHand(cards);
    }
}
