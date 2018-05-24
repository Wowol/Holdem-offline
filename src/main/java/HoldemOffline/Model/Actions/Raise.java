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
        if (player.table.currentTurnPots.size() == 0) {
            throw new InvalidTableState();
        }

        if (player.numberOfChipsNeededToCall() + howMany >= player.numberOfChips) {
            throw new NotEnoughChips();
        }

        for (Pot p : player.table.currentTurnPots) {
            p.players.putIfAbsent(player, 0);
            player.numberOfChips -= p.maxBet - p.players.get(player);
            p.players.replace(player, p.maxBet);
        }

        if (player.table.mainPot == null) {
            player.table.mainPot = new Pot();
        }

        player.table.mainPot.maxBet += howMany;
        player.table.mainPot.players.put(player, player.table.mainPot.maxBet);
        player.numberOfChips -= howMany;

    }

    @Override
    public boolean isPossible(Player player) {
        if (player.table.currentTurnPots.size() == 0) {
            return false;
        }

        if (player.numberOfChipsNeededToCall() + howMany >= player.numberOfChips) {
            return false;
        }

        return false;
    }

}