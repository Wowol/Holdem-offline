package HoldemOffline.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import HoldemOffline.Model.Actions.Actions;
import HoldemOffline.Model.Actions.Exceptions.ActionException;
import HoldemOffline.Model.Utilities.CircularList;
import HoldemOffline.Model.Utilities.Command;

public class Table {

    private static final int FLOP_NUMBER_CARDS = 3;
    private static final int TURN_NUMBER_CARDS = 1;
    private static final int RIVER_NUMBER_CARDS = 1;

    public List<Player> players = new CircularList<>();
    public List<Card> tableCards = new ArrayList<>();
    public List<Pot> allPots = new ArrayList<>();
    public int dealerIndex;

    public List<Pot> currentTurnPots = new ArrayList<>();
    public Pot mainPot;

    public int maxBetInCurrentTurn;

    public int smallBlind;
    public int bigBlind;

    public TableStatus status;

    private Deck deck = new Deck();
    private int currentIndex;

    private MethodReferences references;

    public Table() {
        references = new MethodReferences() {

            @Override
            public Consumer<Player> getFunctionToInformPlayerOfHisTurn() {
                return (Player p) -> {
                };
            }

            @Override
            public BiConsumer<Player, ArrayList<Card>> getFunctionToGivePlayersCards() {
                return (Player p, ArrayList<Card> c) -> {
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
        };
    }

    public Table(MethodReferences references) {
        this.references = references;
    }

    private boolean isHeadsUp() {
        return players.size() == 2;
    }

    public Player getCurrentPlayer() {
        return players.get(currentIndex);
    }

    public Player getSmallBlindPlayer() {
        return players.get(getSmallBlindIndex());
    }

    public Player getBigBlindPlayer() {
        return players.get(getBigBlindIndex());
    }

    private int getSmallBlindIndex() {
        return isHeadsUp() ? dealerIndex : dealerIndex + 1;
    }

    private int getBigBlindIndex() {
        return isHeadsUp() ? dealerIndex + 1 : dealerIndex + 2;
    }

    public void addCards(int number) {
        while (number-- > 0) {
            Card newCard = deck.pop();
            tableCards.add(newCard);
            references.getFunctionToAddCardToTable().accept(newCard);
        }
    }

    public void startGame() {
        dealerIndex = new Random().nextInt(players.size());
        startGame(dealerIndex);
    }

    public void startGame(int dealerIndex) {
        this.dealerIndex = dealerIndex - 1;
        startNewHand();
    }

    public void startNewHand() {
        deck.addCardsToDeck();
        deck.shuffle();
        giveCardsToPlayers();

        allPots.clear();
        currentTurnPots.clear();
        clearAllPlayersFlags();
        mainPot = null;
        maxBetInCurrentTurn = 0;

        dealerIndex = getNewDealerIndex();
        currentIndex = getSmallBlindIndex();

        payBlinds();
        status = TableStatus.PRE_FLOP;

        references.getFunctionToInformPlayerOfHisTurn().accept(getCurrentPlayer());
    }

    private void removeAllCardsFromPlayers() {
        for (Player player : players) {
            player.getHoleCards().clear();
        }
    }

    private void giveCardsToPlayers() {
        for (Player player : players) {
            player.getHoleCards().add(deck.pop());
            player.getHoleCards().add(deck.pop());
        }
    }

    public void prepareNextMove() {
        references.getFunctionToInformPlayersThatPlayerMadeMove().accept(getCurrentPlayer());
        if (checkIfOnlyOnePlayerNotFolded()) {
            giveAllChipsToPlayer(getFirstPlayerWhoDoesntFold());
            startNewHand();
            return;
        } else if (checkIfAllPlayersExceptOneAreAllIn()) {
            while (endTurn()) {
            }
            endHand();
            return;
        } else if (checkIfAllPlayersMakeActionThatAllowsToNextTurn()) {
            if (!endTurn()) {
                endHand();
                return;
            }
            startTurn();
        } else {
            currentIndex = getFirstPlayingPlayerIndexAfterCurrent();
        }

        references.getFunctionToInformPlayerOfHisTurn().accept(getCurrentPlayer());
    }

    // Return true if we are still at same hand, false when hand is over
    private boolean endTurn() {
        references.getFunctionToNewTurn().execute();

        if (status == null) {
            return false;
        }

        TableStatus nextStatus = getNextTableStatus();
        status = nextStatus;

        if (status == null) {
            return false;
        }


        switch (nextStatus) {
        case FLOP:
            addFlop();
            break;
        case TURN:
            addTurn();
            break;
        case RIVER:
            addRiver();
            break;
        default:
            throw new RuntimeException();
        }
        updatePlayersBestHand();
        clearAllPlayersLastAction();

        currentTurnPots.clear();
        mainPot = null;

        return true;
    }

    private void startTurn() {
        currentIndex = dealerIndex;
        currentIndex = getFirstPlayingPlayerIndexAfterCurrent();
    }

    private void clearAllPlayersFlags() {
        clearAllPlayersLastAction();
        for (Player player : players) {
            player.isPlaying = true;
            player.isPlayingThisTurn = true;
            player.isAllIn = false;
        }
    }

    private void clearAllPlayersLastAction() {
        players.forEach(p -> p.lastAction = null);
    }

    private void updatePlayersBestHand() {
        for (Player player : players) {
            ArrayList<Card> playersAndTableCards = new ArrayList<>();
            playersAndTableCards.addAll(player.getHoleCards());
            playersAndTableCards.addAll(tableCards);
            player.currentBestHand = new Hand(playersAndTableCards);
        }
    }

    private void endHand() {
        giveWinningsToPlayers();
        removeAllCardsFromPlayers();
        removePlayersWithNoChips();

        references.getFunctionToEndHand().execute();

        if (players.size() > 1)
            startNewHand();
    }

    private void giveWinningsToPlayers() {
        for (Pot pot : allPots) {
            TreeMap<Hand, Player> handToPlayer = new TreeMap<>();
            for (Player player : pot.players.keySet()) {
                handToPlayer.put(player.currentBestHand, player);
            }
            Player bestPlayer = handToPlayer.firstEntry().getValue();
            bestPlayer.numberOfChips += pot.chips;
        }
    }

    private TableStatus getNextTableStatus() {
        switch (status) {
        case PRE_FLOP:
            return TableStatus.FLOP;
        case FLOP:
            return TableStatus.TURN;
        case TURN:
            return TableStatus.RIVER;
        case RIVER:
            return null;
        default:
            throw new RuntimeException();
        }
    }

    private int getFirstPlayingPlayerIndexAfterCurrent() {
        for (int x = currentIndex + 1; x < players.size() + currentIndex; x++) {
            if (players.get(x).isPlaying == true) {
                return x;
            }
        }
        throw new RuntimeException();
    }

    private void addFlop() {
        addCards(FLOP_NUMBER_CARDS);
    }

    private void addTurn() {
        addCards(TURN_NUMBER_CARDS);
    }

    private void addRiver() {
        addCards(RIVER_NUMBER_CARDS);
    }

    private int getNewDealerIndex() {
        for (int x = dealerIndex + 1; x < players.size() + dealerIndex; x++) {
            if (players.get(x).numberOfChips > 0) {
                return x;
            }
        }
        // Only one player remaining
        throw new RuntimeException();
    }

    private void payBlinds() {
        try {
            paySmallBlind();
            payBigBlind();
            maxBetInCurrentTurn = bigBlind;
        } catch (ActionException e) {
            // This shouldn't happen - blinds players can always go allin (if chips > 0)
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private void paySmallBlind() throws ActionException {
        Player smallBlindPlayer = getSmallBlindPlayer();
        if (smallBlindPlayer.numberOfChips <= smallBlind) {
            smallBlindPlayer.makeAction(Actions.All_IN);
        } else {
            int temp = bigBlind;
            bigBlind = smallBlind;
            smallBlindPlayer.makeAction(Actions.SMALL_BLIND, smallBlind);
            bigBlind = temp;
        }
    }

    private void payBigBlind() throws ActionException {
        Player bigBlindPlayer = getBigBlindPlayer();
        if (bigBlindPlayer.numberOfChips <= bigBlind) {
            bigBlindPlayer.makeAction(Actions.All_IN);
        } else {
            bigBlindPlayer.makeAction(Actions.BIG_BLIND, bigBlind);
        }
    }

    private void removePlayersWithNoChips() {
        players.removeIf(p -> p.numberOfChips == 0);
    }

    private void giveAllChipsToPlayer(Player player) {
        for (Pot pot : allPots) {
            player.numberOfChips += pot.chips;
        }
    }

    private Player getFirstPlayerWhoDoesntFold() {
        return (Player) players.stream().filter(p -> p.isPlaying == true).limit(1).toArray()[0];
    }

    private boolean checkIfOnlyOnePlayerNotFolded() {
        int numberOfPlayersNotFolded = 0;
        for (Player player : players) {
            if (player.isPlaying || player.isAllIn) {
                numberOfPlayersNotFolded++;
                if (numberOfPlayersNotFolded >= 2) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkIfAllPlayersMakeActionThatAllowsToNextTurn() {
        /*int numberOfPeopleWhoRaisedOrBetted = 0;
        for (Player player : players) {
            if (!player.isPlaying)
                continue;
            if (player.lastAction == null || player.lastAction == Actions.BIG_BLIND) {
                return false;
            }
            if (player.lastAction == Actions.RAISE || player.lastAction == Actions.BET) {
                numberOfPeopleWhoRaisedOrBetted++;
            }
        }*/
        for (Player p : players) {
            if (p.isPlayingThisTurn)
                return false;
        }
        //return numberOfPeopleWhoRaisedOrBetted < 2;
        return true;
    }

    private boolean checkIfAllPlayersExceptOneAreAllIn() {
        int numberOfFoldedPlayers = 0;
        int numberOfAllInPlayers = 0;
        for (Player player : players) {
            if (player.isFolded()) {
                numberOfFoldedPlayers++;
            } else if (player.isAllIn) {
                numberOfAllInPlayers++;
            } else if (player.lastAction == null || player.lastAction == Actions.BIG_BLIND) {
                return false;
            }
        }

        return (players.size() - numberOfFoldedPlayers - numberOfAllInPlayers) == 1;
    }
}