package HoldemOffline.Model;

import java.util.ArrayList;
import java.util.List;

import HoldemOffline.Model.Utilities.CircularList;

public class Table {
    public List<Player> players = new CircularList<>();
    public List<Card> tableCards = new ArrayList<>();
    public List<Pot> pots = new ArrayList<>();
    public int dealerIndex;

    public int maxBetInCurrentTurn;

    public int smallBlind;
    public int bigBlind;

    private Deck deck;
    private int currentIndex;

    private boolean isHeadsUp() {
        return players.size() == 2;
    }

    public Player getCurrentPlayer() {
        return players.get(currentIndex);
    }

    public Player getSmallBlindPlayer() {
        return players.get(isHeadsUp() ? dealerIndex : dealerIndex + 1);
    }

    public Player getBigBlindPlayer() {
        return players.get(isHeadsUp() ? dealerIndex + 1 : dealerIndex + 2);
    }

    public void addCards(int number) {
        while (number-- > 0) {
            tableCards.add(deck.pop());
        }
    }

}