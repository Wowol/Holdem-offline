package HoldemOffline.Model;

import HoldemOffline.Model.Actions.Exceptions.*;

public abstract class Action {
    public void makeAction(Player player) throws ActionException { // trzeba znalezs lepsze nazwy tym metodom
        if (!isPlayersTurn(player)) {
            throw new NotYourTurnException();
        }

        if (!player.isPlaying) {
            throw new PlayerIsNotPlaying();
        }

        make(player);

        player.isPlayingThisTurn = false;

        player.table.prepareNextMove();
    }

    private boolean isPlayersTurn(Player player) {
        return player.table.getCurrentPlayer() == player;
    }

    protected abstract void make(Player player) throws ActionException;

    public abstract boolean isPossible(Player player);
}