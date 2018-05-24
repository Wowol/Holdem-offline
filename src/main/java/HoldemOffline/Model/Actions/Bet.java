package HoldemOffline.Model.Actions;

import HoldemOffline.Model.Action;
import HoldemOffline.Model.Actions.Exceptions.ActionException;
import HoldemOffline.Model.Actions.Exceptions.InvalidTableState;
import HoldemOffline.Model.Actions.Exceptions.NotEnoughChips;
import HoldemOffline.Model.Actions.Exceptions.PlayerIsNotPlaying;
import HoldemOffline.Model.Player;
import HoldemOffline.Model.Pot;

public class Bet implements Action {

    private int howMany;

    public Bet(int howMany) {
        this.howMany = howMany;
    }

    @Override
    public void make(Player player) throws ActionException {
        if (!player.isPlaying) {
            throw new PlayerIsNotPlaying();
        }

        if (player.table.maxBetInCurrentTurn != 0) {
            throw new InvalidTableState();
        }

        if (player.numberOfChips <= howMany) {
            throw new NotEnoughChips();
        }

        player.numberOfChips -= howMany;
        player.table.maxBetInCurrentTurn = howMany;
        Pot pot = new Pot();
        pot.chips += howMany;
        pot.players.put(player, howMany);
        pot.maxBet = howMany;
        player.table.currentTurnPots.add(pot);
        player.table.allPots.add(pot);
        player.table.mainPot = pot;

    }

    @Override
    public boolean isPossible(Player player) {
        // TODO
        return false;
    }

}