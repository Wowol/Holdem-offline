package HoldemOffline.Model;

import HoldemOffline.Model.Actions.Exceptions.ActionException;
import HoldemOffline.Model.Actions.Exceptions.PlayerIsNotPlaying;

public interface Action {
    default void makeAction(Player player) throws ActionException { //trzeba znalezs lepsze nazwy tym metodom
        if (!player.isPlaying) {
            throw new PlayerIsNotPlaying();
        }

        make(player);
    }

    public void make(Player player) throws ActionException;

    public boolean isPossible(Player player);
}