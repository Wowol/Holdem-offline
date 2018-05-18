package HoldemOffline.Model;

import HoldemOffline.Model.Actions.Exceptions.ActionException;

public interface Action {
    public void make(Player player) throws ActionException;

    public boolean isPossible(Player player);
}