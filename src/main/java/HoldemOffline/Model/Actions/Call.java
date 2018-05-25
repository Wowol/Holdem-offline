package HoldemOffline.Model.Actions;

import HoldemOffline.Model.Action;
import HoldemOffline.Model.Actions.Exceptions.ActionException;
import HoldemOffline.Model.Actions.Exceptions.InvalidTableState;
import HoldemOffline.Model.Actions.Exceptions.NotEnoughChips;
import HoldemOffline.Model.Player;
import HoldemOffline.Model.Pot;

public class Call extends Action {

    @Override
    public void make(Player player) throws ActionException {
        if (player.table.currentTurnPots.size() == 0) {
            throw new InvalidTableState();
        }

        if (player.numberOfChipsNeededToCall() >= player.numberOfChips) {
            throw new NotEnoughChips();
        }

        for (Pot p : player.table.currentTurnPots) {
            p.players.putIfAbsent(player, 0);
            player.numberOfChips -= p.maxBet - p.players.get(player);
            p.players.replace(player, p.maxBet);
        }

        player.lastAction = Actions.CALL;
        

    }

    @Override
    public boolean isPossible(Player player) {
        if (!player.isPlaying) {
            return false;
        }

        int chipsNeeded = 0;

        for (Pot p : player.table.currentTurnPots) {
            if (p.players.get(player) < p.maxBet) {
                chipsNeeded += p.maxBet - p.players.get(player);
            }
        }

        if (chipsNeeded >= player.numberOfChips) {
            return false;
        }

        return true;
    }

}