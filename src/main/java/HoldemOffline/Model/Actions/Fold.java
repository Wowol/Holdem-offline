package HoldemOffline.Model.Actions;

import HoldemOffline.Model.Action;
import HoldemOffline.Model.Actions.Exceptions.ActionException;
import HoldemOffline.Model.Actions.Exceptions.PlayerIsNotPlaying;
import HoldemOffline.Model.Player;
import HoldemOffline.Model.Pot;

public class Fold implements Action {

    @Override
    public void make(Player player) throws ActionException {
        if (!player.isPlaying) {
            throw new PlayerIsNotPlaying();
        }

        for (Pot p : player.table.allPots) {
            p.players.remove(player);
        }

        player.isPlaying = false;
    }

    @Override
    public boolean isPossible(Player player) {
        return player.isPlaying;
    }

}