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
        assertEquals(deck.getSize(), deck.DEFAULT_DECK_SIZE);
        deck.peek();
        assertEquals(deck.getSize(), deck.DEFAULT_DECK_SIZE);
        deck.pop();
        assertEquals(deck.getSize(), deck.DEFAULT_DECK_SIZE - 1);
    }

    @Test
    public void deckCustomSizeTest() {
        int size = 24;
        Deck deck = new Deck(size);
        assertEquals(deck.getSize(), size);
        deck.peek();
        assertEquals(deck.getSize(), size);
        deck.pop();
        assertEquals(deck.getSize(), size - 1);
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