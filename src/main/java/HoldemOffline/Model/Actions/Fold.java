package HoldemOffline.Model.Actions;

import HoldemOffline.Model.Action;
import HoldemOffline.Model.Actions.Exceptions.ActionException;
import HoldemOffline.Model.Player;
import HoldemOffline.Model.Pot;

public class Fold extends Action {

    @Override
    public void make(Player player) throws ActionException {
        for (Pot p : player.table.allPots) {
            p.players.remove(player);
        }

        player.isPlaying = false;

        player.lastAction = Actions.FOLD;
        
    }

    @Override
    public boolean isPossible(Player player) {
        return player.isPlaying;
    }

}