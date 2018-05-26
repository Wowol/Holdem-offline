package HoldemOffline.Model.Actions;

import HoldemOffline.Model.Action;
import HoldemOffline.Model.Player;
import HoldemOffline.Model.Pot;
import HoldemOffline.Model.Actions.Exceptions.ActionException;

import java.util.Map;

public class AllIn extends Action {

    @Override
    protected void make(Player player) throws ActionException {
        Pot maxPot = new Pot();
        maxPot.chips = 0;

        for (Pot p : player.table.currentTurnPots) {
            if (p.chips > maxPot.chips) {
                maxPot = p;
            }
        }

        Pot newPot = new Pot();

        for (Map.Entry<Player, Integer> p : maxPot.players.entrySet()) {
            if (p.getValue() >= player.numberOfChips) {
                p.setValue(p.getValue() - player.numberOfChips);
                newPot.players.put(p.getKey(), player.numberOfChips);
                newPot.chips += player.numberOfChips;
            } else {
                newPot.players.put(p.getKey(), p.getValue());
                newPot.chips += p.getValue();
                p.setValue(0);
            }
        }

        if (player.numberOfChips > player.table.maxBetInCurrentTurn) {
            player.table.maxBetInCurrentTurn = player.numberOfChips;
        }

        newPot.maxBet = player.numberOfChips;
        maxPot.maxBet -= player.numberOfChips;

        player.numberOfChips = 0;

        player.isPlaying = false;
        player.isAllIn = true;

        player.lastAction = Actions.All_IN;

    }

    @Override
    public boolean isPossible(Player player) {
        if (!player.isPlaying) {
            return false;
        }

        return true;
    }

}