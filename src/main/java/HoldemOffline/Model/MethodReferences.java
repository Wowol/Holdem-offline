package HoldemOffline.Model;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import HoldemOffline.Model.Utilities.Command;

public interface MethodReferences {
    public Consumer<Player> getFunctionToInformPlayerOfHisTurn();

    public BiConsumer<Player, List<Card>> getFunctionToGivePlayersCards();

    public Consumer<Card> getFunctionToAddCardToTable();

    public Consumer<Player> getFunctionToInformPlayersThatPlayerMadeMove();

    public Command getFunctionToNewTurn();

    public Command getFunctionToEndHand();
}