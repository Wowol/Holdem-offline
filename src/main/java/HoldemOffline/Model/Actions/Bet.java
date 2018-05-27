package HoldemOffline.Model.Actions;

import HoldemOffline.Model.Action;
import HoldemOffline.Model.Actions.Exceptions.ActionException;
import HoldemOffline.Model.Actions.Exceptions.InvalidTableState;
import HoldemOffline.Model.Actions.Exceptions.NotEnoughChips;
import HoldemOffline.Model.Player;
import HoldemOffline.Model.Pot;

public class Bet extends Action {

    private int howMany;

    public Bet(int howMany) {
        this.howMany = howMany;
    }

    @Override
    protected void make(Player player) throws ActionException {
        if (player.table.mainPot != null) {
            throw new InvalidTableState();
        }

        if (player.numberOfChips <= howMany) {
            throw new NotEnoughChips();
        }

        if (player.table.bigBlind > howMany) {
            throw new InvalidTableState();
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

        for (Player p : player.table.players) {
            if(p.isPlaying)
                p.isPlayingThisTurn = true;
        }

        player.lastAction = Actions.BET;
        
    }

    @Override
    public boolean isPossible(Player player) {
        if (player.table.mainPot != null) {
            return false;
        }

        if (player.numberOfChips <= howMany) {
            return false;
        }

        if (player.table.bigBlind > howMany) {
            return false;
        }

        return false;
    }

}