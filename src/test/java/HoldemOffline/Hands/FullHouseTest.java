package HoldemOffline.Hands;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import HoldemOffline.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class FullHouseTest {
        @Test
        public void fullHouseTestNull() {
                ArrayList<Card> cards = new ArrayList<Card>(
                                Arrays.asList(new Card(Rank.NINE, Suit.DIAMOND), new Card(Rank.TEN, Suit.CLUB),
                                                new Card(Rank.THREE, Suit.DIAMOND), new Card(Rank.JACK, Suit.CLUB),
                                                new Card(Rank.TWO, Suit.HEART), new Card(Rank.THREE, Suit.HEART),
                                                new Card(Rank.QUEEN, Suit.SPADE), new Card(Rank.ACE, Suit.SPADE)));
                FullHouse fullHouse = new FullHouse();
                assertNull(fullHouse.apply(cards));
        }

        @Test
        public void fullHouseTestNull2() {
                ArrayList<Card> cards = new ArrayList<Card>(Arrays.asList(new Card(Rank.NINE, Suit.DIAMOND),
                                new Card(Rank.TEN, Suit.CLUB), new Card(Rank.NINE, Suit.SPADE),
                                new Card(Rank.JACK, Suit.CLUB), new Card(Rank.ACE, Suit.SPADE)));
                FullHouse fullHouse = new FullHouse();
                assertNull(fullHouse.apply(cards));
        }

        @Test
        public void fullHouseTest3() {
                ArrayList<Card> cards = new ArrayList<Card>(Arrays.asList(new Card(Rank.NINE, Suit.DIAMOND),
                                new Card(Rank.TEN, Suit.CLUB), new Card(Rank.NINE, Suit.SPADE),
                                new Card(Rank.TWO, Suit.CLUB), new Card(Rank.NINE, Suit.CLUB),
                                new Card(Rank.TEN, Suit.SPADE), new Card(Rank.ACE, Suit.SPADE)));
                FullHouse fullHouse = new FullHouse();
                Hand h = fullHouse.apply(cards);
                assertNotNull(h);
                assertEquals(0, h.getKickers().size());
                assertArrayEquals(Arrays.asList(new Card(Rank.NINE, Suit.SPADE), new Card(Rank.NINE, Suit.DIAMOND),
                                new Card(Rank.NINE, Suit.CLUB), new Card(Rank.TEN, Suit.SPADE),
                                new Card(Rank.TEN, Suit.CLUB)).toArray(), h.getHandCards().toArray());
        }

        @Test
        public void fullHouseTest4() {
                ArrayList<Card> cards = new ArrayList<Card>(Arrays.asList(new Card(Rank.NINE, Suit.DIAMOND),
                                new Card(Rank.TEN, Suit.CLUB), new Card(Rank.NINE, Suit.SPADE),
                                new Card(Rank.NINE, Suit.CLUB), new Card(Rank.KING, Suit.HEART),
                                new Card(Rank.TEN, Suit.SPADE), new Card(Rank.ACE, Suit.SPADE)));
                FullHouse fullHouse = new FullHouse();
                Hand h = fullHouse.apply(cards);
                assertNotNull(h);
                assertEquals(0, h.getKickers().size());
                assertArrayEquals(Arrays.asList(new Card(Rank.NINE, Suit.SPADE), new Card(Rank.NINE, Suit.DIAMOND),
                                new Card(Rank.NINE, Suit.CLUB), new Card(Rank.TEN, Suit.SPADE),
                                new Card(Rank.TEN, Suit.CLUB)).toArray(), h.getHandCards().toArray());
        }

        @Test
        public void fullHouseTest5() {
                ArrayList<Card> cards = new ArrayList<Card>(Arrays.asList(new Card(Rank.ACE, Suit.DIAMOND),
                                new Card(Rank.TEN, Suit.CLUB), new Card(Rank.NINE, Suit.SPADE),
                                new Card(Rank.KING, Suit.CLUB), new Card(Rank.KING, Suit.HEART),
                                new Card(Rank.ACE, Suit.CLUB), new Card(Rank.ACE, Suit.SPADE)));
                FullHouse fullHouse = new FullHouse();
                Hand h = fullHouse.apply(cards);
                assertNotNull(h);
                assertEquals(0, h.getKickers().size());
                assertArrayEquals(Arrays.asList(new Card(Rank.ACE, Suit.SPADE), new Card(Rank.ACE, Suit.DIAMOND),
                                new Card(Rank.ACE, Suit.CLUB), new Card(Rank.KING, Suit.HEART),
                                new Card(Rank.KING, Suit.CLUB)).toArray(), h.getHandCards().toArray());
        }
}
