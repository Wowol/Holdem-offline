package HoldemOffline.Model.Actions;

import HoldemOffline.Model.Action;
import HoldemOffline.Model.Actions.Exceptions.ActionException;
import HoldemOffline.Model.Actions.Exceptions.InvalidTableState;
import HoldemOffline.Model.Player;

public class Check extends Action {

    @Override
    public void make(Player player) throws ActionException {
        if (player.numberOfChipsNeededToCall() != 0) {
            throw new InvalidTableState();
        }

        player.lastAction = Actions.CHECK;
        
    }

    @Override
    public boolean isPossible(Player player) {
        if (!player.isPlaying) {
            return false;
        }

        if (player.numberOfChipsNeededToCall() != 0) {
            return false;
        }

        return false;
    }

}