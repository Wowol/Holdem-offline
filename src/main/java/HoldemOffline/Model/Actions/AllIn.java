package HoldemOffline.Model.Actions;

import HoldemOffline.Model.Action;
import HoldemOffline.Model.Player;
import HoldemOffline.Model.Pot;
import HoldemOffline.Model.Actions.Exceptions.ActionException;

import java.util.Map;

public class AllIn extends Action {

    @Override
    protected void make(Player player) throws ActionException {
        Pot maxPot = new Pot();

        for (Pot p : player.table.currentTurnPots) {
            if (p.maxBet > maxPot.maxBet) {
                maxPot = p;
            }
        }

        Pot newPot = new Pot();

        if (maxPot.maxBet > player.numberOfChips) {
            for (Map.Entry<Player, Integer> p : maxPot.players.entrySet()) {
                if (p.getValue() >= player.numberOfChips) {
                    maxPot.chips -= player.numberOfChips;
                    p.setValue(p.getValue() - player.numberOfChips);
                    newPot.players.put(p.getKey(), player.numberOfChips);
                    newPot.chips += player.numberOfChips;
                } else {
                    maxPot.chips -= p.getValue();
                    newPot.players.put(p.getKey(), p.getValue());
                    newPot.chips += p.getValue();
                    p.setValue(0);
                }
            }

            newPot.maxBet = player.numberOfChips;
            newPot.players.putIfAbsent(player, player.numberOfChips);
            newPot.chips += player.numberOfChips;

            maxPot.maxBet -= player.numberOfChips;

        } else {
            if (player.numberOfChips > player.numberOfChipsNeededToCall()) {
                newPot.maxBet = player.numberOfChips - player.numberOfChipsNeededToCall();
                newPot.chips = newPot.maxBet;

                for (Pot p : player.table.currentTurnPots) {
                    p.players.putIfAbsent(player, 0);
                    p.chips += p.maxBet - p.players.get(player);
                    p.players.put(player, p.maxBet);
                }

                newPot.players.put(player, newPot.maxBet);
            } else {
                newPot.maxBet = player.numberOfChips;

                for (Pot p : player.table.currentTurnPots) {
                    if (p != maxPot) {
                        p.players.putIfAbsent(player, 0);
                        p.chips += p.maxBet - p.players.get(player);
                        player.numberOfChips -= p.maxBet - p.players.get(player);
                        newPot.maxBet -= p.maxBet - p.players.get(player);
                        p.players.put(player, p.maxBet);
                    }
                }

                for (Map.Entry<Player, Integer> p : maxPot.players.entrySet()) {
                    if (p.getValue() >= player.numberOfChips) {
                        maxPot.chips -= player.numberOfChips;
                        p.setValue(p.getValue() - player.numberOfChips);
                        newPot.players.put(p.getKey(), player.numberOfChips);
                        newPot.chips += player.numberOfChips;
                    } else {
                        maxPot.chips -= p.getValue();
                        newPot.players.put(p.getKey(), p.getValue());
                        newPot.chips += p.getValue();
                        p.setValue(0);
                    }
                }

                newPot.maxBet = player.numberOfChips;
                newPot.players.putIfAbsent(player, player.numberOfChips);
                newPot.chips += player.numberOfChips;

                maxPot.maxBet -= player.numberOfChips;
            }
        }

        if (player.numberOfChips > player.table.maxBetInCurrentTurn) {
            player.table.maxBetInCurrentTurn = player.numberOfChips;
            for (Player p : player.table.players) {
                if(p.isPlaying)
                    p.isPlayingThisTurn = true;
            }
        }

        player.numberOfChips = 0;

        player.table.currentTurnPots.add(newPot);
        player.table.allPots.add(newPot);

        player.isPlaying = false;
        player.isAllIn = true;

        player.lastAction = Actions.All_IN;

    }

    @Override
    public boolean isPossible(Player player) {
        if (!player.isPlaying) {
            return false;
        }

        return true;
    }

}