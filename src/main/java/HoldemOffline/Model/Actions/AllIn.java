package HoldemOffline.Model.Actions;

import HoldemOffline.Model.Action;
import HoldemOffline.Model.Actions.Exceptions.PlayerIsNotPlaying;
import HoldemOffline.Model.Player;
import HoldemOffline.Model.Actions.Exceptions.ActionException;
import HoldemOffline.Model.Pot;

import java.util.Map;

public class AllIn implements Action {

    @Override
    public void make(Player player) throws ActionException {
        if (!player.isPlaying) {
            throw new PlayerIsNotPlaying();
        }

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

        newPot.maxBet = player.numberOfChips;
        maxPot.maxBet -= player.numberOfChips;

        player.isPlaying = false;

    }

    @Override
    public boolean isPossible(Player player) {
        if (!player.isPlaying) {
            return false;
        }

        return true;
    }

}