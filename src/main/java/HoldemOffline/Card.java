package HoldemOffline;

public class Card implements Comparable{

    private final Rank rank;
    private final Suit suit;

    public Card(Rank rank, Suit suit) {
        if (rank == null || suit == null) {
            throw new IllegalArgumentException();
        }
        this.rank = rank;
        this.suit = suit;
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    @Override
    public int compareTo(Object o) {
        if(o == null)
            throw new NullPointerException();
        if(!(o instanceof Card))
            throw new ClassCastException();
        Card c = (Card) o;
        if(rank.compareTo(c.rank) != 0)
            return rank.compareTo(c.rank);
        return suit.compareTo(c.suit);
    }

    @Override
    public String toString(){
        return rank.toString() + " " + suit.toString();
    }
}
