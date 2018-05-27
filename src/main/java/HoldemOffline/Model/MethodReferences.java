package HoldemOffline.Model;

import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import HoldemOffline.Model.Utilities.Command;

public interface MethodReferences {
    public Consumer<Player> getFunctionToInformPlayerOfHisTurn();

    public BiConsumer<Player, ArrayList<Card>> getFunctionToGivePlayersCards();

    public Consumer<Card> getFunctionToAddCardToTable();

    public Consumer<Player> getFunctionToInformPlayersThatPlayerMadeMove();

    public Command getFunctionToNewTurn();

    public Command getFunctionToEndHand();
}