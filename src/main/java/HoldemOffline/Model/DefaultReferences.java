package HoldemOffline.Model;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import HoldemOffline.Model.Actions.Actions;
import HoldemOffline.Model.Utilities.Command;
import HoldemOffline.Model.Utilities.TriConsumer;

public class DefaultReferences implements MethodReferences {

    @Override
    public Consumer<Player> getFunctionToInformPlayerOfHisTurn() {
        return (Player p) -> {
        };
    }

    @Override
    public BiConsumer<Player, List<Card>> getFunctionToGivePlayersCards() {
        return (Player p, List<Card> c) -> {
        };
    }

    @Override
    public Consumer<Card> getFunctionToAddCardToTable() {
        return (Card c) -> {
        };
    }

    @Override
    public TriConsumer<Player, Actions, Integer> getFunctionToInformPlayersThatPlayerMadeMove() {
        return (Player p, Actions a, Integer i) -> {
        };
    }

    @Override
    public Command getFunctionToNewTurn() {
        return () -> {
        };
    }

    @Override
    public Command getFunctionToEndHand() {
        return () -> {
        };
    }
}
