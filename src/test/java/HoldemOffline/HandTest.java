package HoldemOffline;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class HandTest {
    @Test
    public void test1() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.NINE, Suit.HEART));
        cards.add(new Card(Rank.NINE, Suit.SPADE));
        ArrayList<Card> kickers = new ArrayList<>();
        kickers.add(new Card(Rank.TEN, Suit.SPADE));
        kickers.add(new Card(Rank.KING, Suit.SPADE));
        kickers.add(new Card(Rank.QUEEN, Suit.DIAMOND));
        Hand a = new Hand(HandName.ONE_PAIR, cards, kickers);
        ArrayList<Card> cards2 = new ArrayList<>();
        cards2.add(new Card(Rank.NINE, Suit.HEART));
        cards2.add(new Card(Rank.NINE, Suit.SPADE));
        ArrayList<Card> kickers2 = new ArrayList<>();
        kickers2.add(new Card(Rank.TWO, Suit.SPADE));
        kickers2.add(new Card(Rank.ACE, Suit.SPADE));
        kickers2.add(new Card(Rank.QUEEN, Suit.DIAMOND));
        Hand b = new Hand(HandName.ONE_PAIR, cards2, kickers2);
        assertEquals(-1, a.compareTo(b));
    }
}
