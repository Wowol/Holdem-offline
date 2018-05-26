package HoldemOffline.Model.Hands;

import HoldemOffline.Model.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HandTest {
    @Test
    public void test1() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.NINE, Suit.HEART));
        cards.add(new Card(Rank.NINE, Suit.SPADE));
        cards.add(new Card(Rank.TEN, Suit.SPADE));
        cards.add(new Card(Rank.KING, Suit.SPADE));
        cards.add(new Card(Rank.QUEEN, Suit.DIAMOND));
        Hand a = new Hand(cards);
        assertEquals(a.getHandName(), HandName.ONE_PAIR);

        ArrayList<Card> cards2 = new ArrayList<>();
        cards2.add(new Card(Rank.NINE, Suit.HEART));
        cards2.add(new Card(Rank.NINE, Suit.SPADE));
        cards2.add(new Card(Rank.TWO, Suit.SPADE));
        cards2.add(new Card(Rank.ACE, Suit.SPADE));
        cards2.add(new Card(Rank.QUEEN, Suit.DIAMOND));
        Hand b = new Hand(cards2);
        assertEquals(-1, a.compareTo(b));
    }

    @Test
    public void test2(){
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
        Hand h = new Hand(cards);
        assertEquals(HandName.STRAIGHT_FLUSH, h.getHandName());
    }

    @Test
    public void test3(){
        ArrayList<Card> cards = new ArrayList<>(Arrays.asList(
                new Card(Rank.ACE, Suit.SPADE),
                new Card(Rank.KING, Suit.HEART),
                new Card(Rank.KING, Suit.DIAMOND),
                new Card(Rank.QUEEN, Suit.SPADE),
                new Card(Rank.JACK, Suit.CLUB),
                new Card(Rank.TEN, Suit.HEART),
                new Card(Rank.NINE, Suit.SPADE),
                new Card(Rank.ACE, Suit.CLUB)
        ));
        Hand h = new Hand(cards);
        assertEquals(HandName.STRAIGHT, h.getHandName());
    }

    @Test
    public void test4(){
        ArrayList<Card> cards = new ArrayList<>(Arrays.asList(
                new Card(Rank.TWO, Suit.SPADE),
                new Card(Rank.FOUR, Suit.SPADE),
                new Card(Rank.JACK, Suit.SPADE),
                new Card(Rank.TEN, Suit.SPADE),
                new Card(Rank.NINE, Suit.HEART)
        ));
        Hand h = new Hand(cards);
        assertEquals(HandName.HIGH_CARD, h.getHandName());
    }

    @Test
    public void test5(){
        ArrayList<Card> cards = new ArrayList<>(Arrays.asList(
                new Card(Rank.TWO, Suit.SPADE),
                new Card(Rank.FOUR, Suit.SPADE),
                new Card(Rank.JACK, Suit.SPADE),
                new Card(Rank.TEN, Suit.SPADE),
                new Card(Rank.NINE, Suit.HEART),
                new Card(Rank.NINE, Suit.CLUB)
        ));
        Hand h = new Hand(cards);
        assertEquals(HandName.ONE_PAIR, h.getHandName());
    }

    @Test
    public void test6(){
        ArrayList<Card> cards = new ArrayList<>(Arrays.asList(
                new Card(Rank.TWO, Suit.SPADE),
                new Card(Rank.FOUR, Suit.SPADE),
                new Card(Rank.JACK, Suit.SPADE),
                new Card(Rank.TEN, Suit.SPADE),
                new Card(Rank.NINE, Suit.HEART),
                new Card(Rank.NINE, Suit.DIAMOND),
                new Card(Rank.NINE, Suit.CLUB)
        ));
        Hand h = new Hand(cards);
        assertEquals(HandName.THREE_OF_A_KIND, h.getHandName());
    }

    @Test
    public void test7(){
        ArrayList<Card> cards = new ArrayList<>(Arrays.asList(
                new Card(Rank.TWO, Suit.SPADE),
                new Card(Rank.TWO, Suit.DIAMOND),
                new Card(Rank.TWO, Suit.HEART),
                new Card(Rank.TWO, Suit.CLUB),
                new Card(Rank.FOUR, Suit.SPADE),
                new Card(Rank.JACK, Suit.SPADE),
                new Card(Rank.TEN, Suit.SPADE),
                new Card(Rank.NINE, Suit.HEART)
        ));
        Hand h = new Hand(cards);
        assertEquals(HandName.FOUR_OF_A_KIND, h.getHandName());
    }

    @Test
    public void test8(){
        ArrayList<Card> cards = new ArrayList<>(Arrays.asList(
                new Card(Rank.TWO, Suit.SPADE),
                new Card(Rank.TWO, Suit.DIAMOND),
                new Card(Rank.FOUR, Suit.SPADE),
                new Card(Rank.JACK, Suit.SPADE),
                new Card(Rank.TEN, Suit.SPADE),
                new Card(Rank.NINE, Suit.HEART),
                new Card(Rank.NINE, Suit.DIAMOND)
        ));
        Hand h = new Hand(cards);
        assertEquals(HandName.TWO_PAIR, h.getHandName());
    }

    @Test
    public void test9(){
        ArrayList<Card> cards = new ArrayList<>(Arrays.asList(
                new Card(Rank.TWO, Suit.SPADE),
                new Card(Rank.TWO, Suit.DIAMOND),
                new Card(Rank.FOUR, Suit.SPADE),
                new Card(Rank.JACK, Suit.SPADE),
                new Card(Rank.TEN, Suit.SPADE),
                new Card(Rank.NINE, Suit.HEART),
                new Card(Rank.NINE, Suit.DIAMOND),
                new Card(Rank.NINE, Suit.CLUB)
        ));
        Hand h = new Hand(cards);
        assertEquals(HandName.FULL_HOUSE, h.getHandName());
    }

    @Test
    public void test10(){
        ArrayList<Card> cards = new ArrayList<>(Arrays.asList(
                new Card(Rank.TWO, Suit.SPADE),
                new Card(Rank.TWO, Suit.DIAMOND),
                new Card(Rank.FOUR, Suit.SPADE),
                new Card(Rank.JACK, Suit.SPADE),
                new Card(Rank.TEN, Suit.SPADE),
                new Card(Rank.NINE, Suit.SPADE),
                new Card(Rank.NINE, Suit.DIAMOND)
        ));
        Hand h = new Hand(cards);
        assertEquals(HandName.FLUSH, h.getHandName());
    }

    @Test
    public void test11(){
        ArrayList<Card> cards = new ArrayList<>(Arrays.asList(
                new Card(Rank.TWO, Suit.SPADE),
                new Card(Rank.TWO, Suit.DIAMOND),
                new Card(Rank.FOUR, Suit.SPADE),
                new Card(Rank.JACK, Suit.SPADE),
                new Card(Rank.TEN, Suit.SPADE),
                new Card(Rank.NINE, Suit.SPADE),
                new Card(Rank.NINE, Suit.DIAMOND),
                new Card(Rank.ACE, Suit.DIAMOND),
                new Card(Rank.QUEEN, Suit.DIAMOND),
                new Card(Rank.THREE, Suit.DIAMOND)
        ));
        Hand h = new Hand(cards);
        assertEquals(HandName.FLUSH, h.getHandName());
        assertEquals(Suit.DIAMOND, h.getHandCards().get(0).getSuit());
    }

  //  @Test
    public void testAll() {
        // If test is too slow, change to lower number (or just disable whole test :))
        int numberOfTests = 10000000;
        
        int delta = 15;

        Double expectedValues[] = new Double[] {50.118, 42.257, 4.754, 2.113, .392, .197, .144, .024, .001539};
        for(int x=0; x<expectedValues.length; x++)
            expectedValues[x] *= numberOfTests/100;


        Random random = new Random();
        ArrayList<Card> cards = new ArrayList<Card>();
        int[] numberOfOccurrences = new int[HandName.values().length];
        Hand hand;
        while (numberOfTests-- != 0) {
            cards.clear();
            while(cards.size() < 5) {
                Card newRandomCard = new Card(Rank.values()[random.nextInt(Rank.values().length)], 
                    Suit.values()[random.nextInt(Suit.values().length)]);
                if (!cards.contains(newRandomCard)) {
                    cards.add(newRandomCard);
                }
            }
            hand = new Hand(cards);
            numberOfOccurrences[hand.getHandName().ordinal()]++;
        }

        for (int x=0; x<HandName.values().length; x++) {
            double d = 100*(Math.abs((numberOfOccurrences[x]-expectedValues[x]))/expectedValues[x]);
            assertTrue("Hand: "+ HandName.values()[x] + ", Delta: "+delta + ", Current: " + d + ", \n NumerOfOccurences: "+
                numberOfOccurrences[x] + ", expected: "+expectedValues[x], delta > d);
        }
    }
}
