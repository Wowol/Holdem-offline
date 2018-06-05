package HoldemOffline.Model.Actions;

import HoldemOffline.Model.Action;
import HoldemOffline.Model.Player;
import HoldemOffline.Model.Pot;
import HoldemOffline.Model.Actions.Exceptions.ActionException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllIn extends Action {

    @Override
    protected void make(Player player) throws ActionException {
        if (player.table.mainPot == null) {
            player.table.mainPot = new Pot();
            player.table.currentTurnPots.add(player.table.mainPot);
            player.table.allPots.add(player.table.mainPot);
        }

        Pot newPot = null;

        List<Pot> temp = new ArrayList<>(player.table.allPots);

        for (Pot p : player.table.allPots) {
            if (p.players.get(player) != null) {
                player.numberOfChips += p.players.get(player);
                p.chips -= p.players.get(player);
                p.players.remove(player);
            }
        }

        while (!temp.isEmpty()) {
            Pot minPot = null;

            for (Pot p : temp) {
                if (minPot == null || minPot.maxBet > p.maxBet) {
                    minPot = p;
                }
            }

            if (minPot == player.table.mainPot) {
                temp.remove(minPot);
                continue;
            }

            if (minPot.maxBet > player.numberOfChips) {
                newPot = new Pot();

                for (Map.Entry<Player, Integer> p : minPot.players.entrySet()) {
                    if (p.getValue() >= player.numberOfChips) {
                        minPot.chips -= player.numberOfChips;
                        p.setValue(p.getValue() - player.numberOfChips);
                        newPot.players.put(p.getKey(), player.numberOfChips);
                        newPot.chips += player.numberOfChips;
                    } else {
                        minPot.chips -= p.getValue();
                        newPot.players.put(p.getKey(), p.getValue());
                        newPot.chips += p.getValue();
                        p.setValue(0);
                    }
                }

                newPot.maxBet = player.numberOfChips;
                newPot.players.put(player, player.numberOfChips);
                newPot.chips += player.numberOfChips;
                minPot.maxBet -= player.numberOfChips;

                player.table.currentTurnPots.add(newPot);
                player.table.allPots.add(newPot);

                break;
            } else {
                minPot.players.put(player, minPot.maxBet);
                minPot.chips += minPot.maxBet;
                player.numberOfChips -= minPot.maxBet;
                temp.remove(minPot);
            }

        }

        if (newPot == null) {
            newPot = new Pot();

            if (player.table.mainPot.maxBet < player.numberOfChips) {
                newPot = player.table.mainPot;
                player.table.mainPot = new Pot();
                player.table.currentTurnPots.add(player.table.mainPot);
                player.table.allPots.add(player.table.mainPot);
                player.table.maxBetInCurrentTurn = player.numberOfChips;
            } else {
                for (Map.Entry<Player, Integer> p : player.table.mainPot.players.entrySet()) {
                    if (p.getValue() >= player.numberOfChips) {
                        player.table.mainPot.chips -= player.numberOfChips;
                        p.setValue(p.getValue() - player.numberOfChips);
                        newPot.players.put(p.getKey(), player.numberOfChips);
                        newPot.chips += player.numberOfChips;
                    } else {
                        player.table.mainPot.chips -= p.getValue();
                        newPot.players.put(p.getKey(), p.getValue());
                        newPot.chips += p.getValue();
                        p.setValue(0);
                    }
                }
                player.table.mainPot.maxBet -= player.numberOfChips;
                player.table.currentTurnPots.add(newPot);
                player.table.allPots.add(newPot);
            }

            newPot.maxBet = player.numberOfChips;
            newPot.players.put(player, player.numberOfChips);
            newPot.chips += player.numberOfChips;
        }

        player.numberOfChips = 0;

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