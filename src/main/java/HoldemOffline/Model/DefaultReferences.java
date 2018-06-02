package HoldemOffline.Model;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import HoldemOffline.Model.Utilities.Command;

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
    public Consumer<Player> getFunctionToInformPlayersThatPlayerMadeMove() {
        return (Player p) -> {
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
