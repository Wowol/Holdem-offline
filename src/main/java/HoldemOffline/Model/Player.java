package HoldemOffline.Model;

import java.util.List;

import HoldemOffline.Model.Actions.Exceptions.ActionException;

import java.util.ArrayList;

public class Player {

    // If isPlaying is false, that means player folds or went AllIn in previous turn
    public boolean isPlaying = false;
    public Action lastAction;
    public Table table;

    private int numberOfChips;
    private List<Card> holeCards = new ArrayList<>();

    public Player(Table table) {
        this.table = table;
    }

    public int getNumberOfChips() {
        return numberOfChips;
    }

    public List<Card> getHoleCards() {
        return holeCards;
    }

    public void makeAction(Action action) throws ActionException {
        action.make(this);
    }
}