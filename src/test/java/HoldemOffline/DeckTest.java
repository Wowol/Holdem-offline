package HoldemOffline;

import static org.junit.Assert.assertEquals;
import org.junit.*;

public class DeckTest {

    @Test(expected = IllegalArgumentException.class)
    public void sizeNotDivisibleBy4Test() {
        new Deck(50);
    }

    @Test(expected = IllegalArgumentException.class)
    public void sizeNegativeTest() {
        new Deck(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void sizeTooManyTest() {
        new Deck(53);
    }

    @Test
    public void deckDefaultSizeTest() {
        Deck deck = new Deck();
        assertEquals(Deck.DEFAULT_DECK_SIZE, deck.getSize());
        deck.peek();
        assertEquals(Deck.DEFAULT_DECK_SIZE, deck.getSize());
        deck.pop();
        assertEquals(Deck.DEFAULT_DECK_SIZE - 1, deck.getSize());
    }

    @Test
    public void deckCustomSizeTest() {
        int size = 24;
        Deck deck = new Deck(size);
        assertEquals(size, deck.getSize());
        deck.peek();
        assertEquals(size, deck.getSize());
        deck.pop();
        assertEquals(size -1, deck.getSize());
    }

    @Test(expected = IllegalStateException.class)
    public void deckEmptyTest() {
        int size = 4;
        Deck deck = new Deck(size);
        for (int x = 0; x < 5; x++) {
            deck.pop();
        }
    }
}