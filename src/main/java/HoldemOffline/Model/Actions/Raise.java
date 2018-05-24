package HoldemOffline.Model.Actions;

import HoldemOffline.Model.Action;
import HoldemOffline.Model.Actions.Exceptions.ActionException;
import HoldemOffline.Model.Actions.Exceptions.InvalidTableState;
import HoldemOffline.Model.Actions.Exceptions.NotEnoughChips;
import HoldemOffline.Model.Actions.Exceptions.PlayerIsNotPlaying;
import HoldemOffline.Model.Player;
import HoldemOffline.Model.Pot;

public class Raise implements Action {

    private int howMany;

    public Raise(int howMany) {
        this.howMany = howMany;
    }

    @Override
    public void make(Player player) throws ActionException {
        if (!player.isPlaying) {
            throw new PlayerIsNotPlaying();
        }

        if (player.table.maxBetInCurrentTurn == 0) {
            throw new InvalidTableState();
        }

        int chipsNeeded = 0;

        for (Pot p : player.table.currentTurnPots) {
            if (p.players.get(player) == null) {
                chipsNeeded += p.maxBet;
                continue;
            }
            if (p.players.get(player) < p.maxBet) {
                chipsNeeded += p.maxBet - p.players.get(player);
            }
        }

        if (chipsNeeded + howMany >= player.numberOfChips) {
            throw new NotEnoughChips();
        }

        for (Pot p : player.table.currentTurnPots) {
            p.players.putIfAbsent(player, 0);
            player.numberOfChips -= p.maxBet - p.players.get(player);
            p.players.replace(player, p.maxBet);
        }

        player.table.mainPot.maxBet += howMany;
        player.table.mainPot.players.put(player, player.table.mainPot.maxBet);
        player.numberOfChips -= howMany;

    }

    @Override
    public boolean isPossible(Player player) {
        // TODO
        return false;
    }

}