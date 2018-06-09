package HoldemOffline.Model;

import java.util.List;
import HoldemOffline.Model.Actions.Actions;
import HoldemOffline.Model.Actions.Exceptions.ActionException;
import java.util.ArrayList;
import javafx.scene.image.Image;

public class Player {

    // If isPlaying is false, that means player folds or went AllIn in previous turn
    public boolean isPlaying = false;
    public boolean isAllIn = false;
    public boolean isPlayingThisTurn = false;

    public Actions lastAction;
    public Table table;

    public Hand currentBestHand;

    public int numberOfChips;

    public Image avatar;

    private List<Card> holeCards = new ArrayList<>();
	public int lastBetRaiseValue;

    public Player() {}

    public Player(Table table) {
        this.table = table;
    }

    public boolean isFolded() {
        return !isPlaying && !isAllIn;
    }

    public int getNumberOfChips() {
        return numberOfChips;
    }

    public List<Card> getHoleCards() {
        return holeCards;
    }

    public void makeAction(Actions action, int arg) throws ActionException {
        action.make(this, arg);
    }

    public void makeAction(Actions action) throws ActionException {
        action.make(this);
    }

    public int numberOfChipsNeededToCall() {
        int chipsNeeded = 0;

        for (Pot p : table.currentTurnPots) {
            if (p.players.get(this) == null) {
                chipsNeeded += p.maxBet;
                continue;
            }
            if (p.players.get(this) < p.maxBet) {
                chipsNeeded += p.maxBet - p.players.get(this);
            }
        }

        return chipsNeeded;
    }

}