package HoldemOffline.Hands;

import org.junit.Test;

import java.util.ArrayList;

import HoldemOffline.*;
import static org.junit.Assert.assertEquals;

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
}
