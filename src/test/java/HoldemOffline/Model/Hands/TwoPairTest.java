package HoldemOffline.Model.Hands;

import HoldemOffline.Model.Card;
import HoldemOffline.Model.Hand;
import HoldemOffline.Model.Rank;
import HoldemOffline.Model.Suit;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class TwoPairTest {
        @Test
        public void twoPairTest1() {
                ArrayList<Card> cards = new ArrayList<Card>(
                                Arrays.asList(new Card(Rank.NINE, Suit.DIAMOND), new Card(Rank.TEN, Suit.CLUB),
                                                new Card(Rank.THREE, Suit.DIAMOND), new Card(Rank.JACK, Suit.CLUB),
                                                new Card(Rank.TWO, Suit.HEART), new Card(Rank.THREE, Suit.HEART),
                                                new Card(Rank.QUEEN, Suit.SPADE), new Card(Rank.ACE, Suit.SPADE)));
                TwoPair twoPair = new TwoPair();
                assertNull(twoPair.apply(cards));
        }

        @Test
        public void twoPairTest2() {
                ArrayList<Card> cards = new ArrayList<Card>(Arrays.asList(new Card(Rank.NINE, Suit.DIAMOND),
                                new Card(Rank.TEN, Suit.CLUB), new Card(Rank.NINE, Suit.SPADE),
                                new Card(Rank.JACK, Suit.CLUB), new Card(Rank.ACE, Suit.SPADE)));
                TwoPair twoPair = new TwoPair();
                assertNull(twoPair.apply(cards));
        }

        @Test
        public void twoPairTest3() {
                ArrayList<Card> cards = new ArrayList<Card>(Arrays.asList(new Card(Rank.NINE, Suit.DIAMOND),
                                new Card(Rank.TEN, Suit.CLUB), new Card(Rank.NINE, Suit.SPADE),
                                new Card(Rank.TEN, Suit.SPADE), new Card(Rank.ACE, Suit.SPADE)));
                TwoPair twoPair = new TwoPair();
                Hand h = twoPair.apply(cards);
                assertNotNull(h);
                assertArrayEquals(Arrays.asList(new Card(Rank.ACE, Suit.SPADE)).toArray(), h.getKickers().toArray());
                assertArrayEquals(Arrays
                                .asList(new Card(Rank.TEN, Suit.SPADE), new Card(Rank.TEN, Suit.CLUB),
                                                new Card(Rank.NINE, Suit.SPADE), new Card(Rank.NINE, Suit.DIAMOND))
                                .toArray(), h.getHandCards().toArray());
        }

        @Test
        public void twoPairTest4() {
                ArrayList<Card> cards = new ArrayList<Card>(Arrays.asList(new Card(Rank.NINE, Suit.DIAMOND),
                                new Card(Rank.TEN, Suit.CLUB), new Card(Rank.NINE, Suit.SPADE),
                                new Card(Rank.KING, Suit.CLUB), new Card(Rank.KING, Suit.HEART),
                                new Card(Rank.TEN, Suit.SPADE), new Card(Rank.ACE, Suit.SPADE)));
                TwoPair twoPair = new TwoPair();
                Hand h = twoPair.apply(cards);
                assertNotNull(h);
                assertArrayEquals(Arrays.asList(new Card(Rank.ACE, Suit.SPADE)).toArray(), h.getKickers().toArray());
                assertArrayEquals(Arrays
                                .asList(new Card(Rank.KING, Suit.HEART), new Card(Rank.KING, Suit.CLUB),
                                                new Card(Rank.TEN, Suit.SPADE), new Card(Rank.TEN, Suit.CLUB))
                                .toArray(), h.getHandCards().toArray());
        }

        @Test
        public void twoPairTest5() {
                ArrayList<Card> cards = new ArrayList<Card>(Arrays.asList(new Card(Rank.QUEEN, Suit.DIAMOND),
                                new Card(Rank.TEN, Suit.CLUB), new Card(Rank.NINE, Suit.SPADE),
                                new Card(Rank.KING, Suit.CLUB), new Card(Rank.KING, Suit.HEART),
                                new Card(Rank.TWO, Suit.SPADE), new Card(Rank.ACE, Suit.SPADE)));
                TwoPair twoPair = new TwoPair();
                Hand h = twoPair.apply(cards);
                assertNull(h);
        }
}
