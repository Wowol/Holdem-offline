package HoldemOffline;

public class Card {
    public enum Rank{
        TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN,
        JACK, QUEEN, KING, ACE
    }
    public enum Suit{
        HEART, DIAMOND, CLUB, SPADE
    }
    Rank rank;
    Suit suit;
    public Card(Rank rank, Suit suit){
        this.rank=rank;
        this.suit=suit;
    }
}
