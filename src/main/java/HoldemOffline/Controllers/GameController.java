package HoldemOffline.Controllers;

import HoldemOffline.Model.*;
import javafx.beans.value.*;
import javafx.fxml.FXML;
import javafx.scene.layout.*;

import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javafx.scene.image.Image;
import javafx.scene.control.*;

import HoldemOffline.Model.Actions.*;
import HoldemOffline.Model.Actions.Exceptions.ActionException;
import HoldemOffline.Model.Utilities.Command;
import HoldemOffline.Controls.CardImageView;
import HoldemOffline.Controls.PlayerPane;
import javafx.scene.input.*;

public class GameController {
    private static final Logger log = LoggerFactory.getLogger(App.class);

    private static final double TABLE_CARD_HEIGHT = 112.0;

    @FXML
    private AnchorPane tableAnchorPane;

    @FXML
    private Button betRaiseButton;

    @FXML
    private Button callCheckButton;

    @FXML
    private Button foldButton;

    @FXML
    private Pane player0Place;

    @FXML
    private Pane player1Place;

    @FXML
    private Pane player2Place;

    @FXML
    private Pane player3Place;

    @FXML
    private Pane player4Place;

    @FXML
    private Pane player5Place;

    @FXML
    private AnchorPane playersPane;

    @FXML
    private TextField betTextField;

    @FXML
    private Slider betSlider;

    @FXML
    private HBox tableCardsBox;

    private Table table;

    HashMap<Player, PlayerPane> playerToPane = new HashMap<>();

    MethodReferences references = new MethodReferences() {
        @Override
        public Consumer<Player> getFunctionToInformPlayerOfHisTurn() {
            return (Player p) -> yourTurn(p);
        }

        @Override
        public BiConsumer<Player, List<Card>> getFunctionToGivePlayersCards() {
            return (Player p, List<Card> cards) -> giveCardsToPlayer(p, cards);
        }

        @Override
        public Consumer<Card> getFunctionToAddCardToTable() {
            return (Card c) -> addCardToTable(c);
        }

        @Override
        public Consumer<Player> getFunctionToInformPlayersThatPlayerMadeMove() {
            return (Player p) -> playerMadeMove(p);
        }

        @Override
        public Command getFunctionToNewTurn() {
            return () -> newTurn();
        }

        @Override
        public Command getFunctionToEndHand() {
            return () -> endHand();
        }
    };

    public void setTable(Table table) {
        this.table = table;
    }

    public void startGame() {
        table.setReferences(references);
        addPlayersToView();
        table.startGame();
        setSliderProporties();
        tableCardsBox.getChildren().clear();
    }

    private void setSliderProporties() {
        setSliderTick();
        setSliderMinValue();
        setSliderMaxValue();
        setSliderListener();
    }

    private void giveCardsToPlayer(Player player, List<Card> cards) {
        playerToPane.get(player).cardsBox.setCards(cards);
    }

    private void setSliderTick() {
        betSlider.setMajorTickUnit(table.bigBlind);
        betSlider.setMinorTickCount(table.bigBlind);
        betSlider.valueProperty()
                .addListener((obs, oldval, newVal) -> betSlider.setValue(Math.round(newVal.doubleValue())));
    }

    private void newTurn() {
        for (Player p : table.players) {
            playerToPane.get(p).clearActionLabelText();
        }
    }

    private void endHand() {
        for (Player p : table.players) {
            playerToPane.get(p).setNumberOfChipsLabelText(p.numberOfChips);
            playerToPane.get(p).clearActionLabelText();
        }
        tableCardsBox.getChildren().clear();
    }

    private void addCardToTable(Card card) {
        tableCardsBox.getChildren().add(new CardImageView(card, TABLE_CARD_HEIGHT));
    }

    private void setSliderMinValue() {
        betSlider.setMin(table.bigBlind);
    }

    private void setSliderMaxValue() {
        betSlider.setMax(table.getCurrentPlayer().numberOfChips);
    }

    private void setSliderListener() {
        betSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                onSliderDrag();
            }
        });
    }

    private void addPlayersToView() {
        int index = 0;
        for (Player player : table.players) {
            Pane place = (Pane) playersPane.getChildren().get(index++);
            PlayerPane newPlayerPane = new PlayerPane(player.avatar, player);
            place.getChildren().add(newPlayerPane);
            playerToPane.put(player, newPlayerPane);
        }
    }

    public void yourTurn(Player player) {
        if (player instanceof ArtificialIntelligence && player.table.status != null) {
            try {
                ((ArtificialIntelligence) player).makeAction();
                return;
            } catch (ActionException e) {
                e.printStackTrace();
            }
        }

        Raise.MinMax minMax = Raise.getMinMaxRaiseValues(player);
        if (minMax != null) {
            makeRaiseButton(minMax);
        } else {
            makeBetButton(player);
        }

        if (new Call().isPossible(player)) {
            makeCallButton();
        } else if (new Check().isPossible(player)) {
            makeCheckButton();
        } else if (new AllIn().isPossible(player)) {
            makeAllInButton();
        }

        if (new Fold().isPossible(player)) {
            makeFoldButton();
        }
    }

    private void makeRaiseButton(Raise.MinMax minMax) {
        betRaiseButton.setOnMouseClicked(this::playerRaised);
        betRaiseButton.setText("Raise");
        betSlider.setMin(minMax.min);
        betSlider.setMax(minMax.max);
    }

    private void makeBetButton(Player player) {
        betRaiseButton.setOnMouseClicked(this::playerBetted);
        betRaiseButton.setText("Bet");
        betSlider.setMin(table.bigBlind);
        betSlider.setMax(player.numberOfChips);
    }

    private void makeCallButton() {
        callCheckButton.setOnMouseClicked(this::playerCalled);
        callCheckButton.setText("Call");
    }

    private void makeCheckButton() {
        callCheckButton.setOnMouseClicked(this::playerChecked);
        callCheckButton.setText("Check");
    }

    private void playerMadeMove(Player player) {
        playerToPane.get(player).setActionLabelText(player.lastAction.toString());
        playerToPane.get(player).setNumberOfChipsLabelText(player.numberOfChips);
    }

    private void makeFoldButton() {
        foldButton.setOnMouseClicked(this::playerFolded);
        foldButton.setText("Fold");
    }

    private void makeAllInButton() {
        callCheckButton.setOnMouseClicked(this::playerAllIn);
        callCheckButton.setText("All in");
    }

    @FXML
    protected void playerRaised(InputEvent event) {
        try {
            table.getCurrentPlayer().makeAction(Actions.RAISE, getRaiseValue());
        } catch (ActionException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void playerBetted(InputEvent event) {
        try {
            table.getCurrentPlayer().makeAction(Actions.BET, getRaiseValue());
        } catch (ActionException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void playerCalled(InputEvent event) {
        try {
            table.getCurrentPlayer().makeAction(Actions.CALL);
        } catch (ActionException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void playerChecked(InputEvent event) {
        try {
            table.getCurrentPlayer().makeAction(Actions.CHECK);
        } catch (ActionException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void playerAllIn(InputEvent event) {
        try {
            table.getCurrentPlayer().makeAction(Actions.All_IN);
        } catch (ActionException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void playerFolded(InputEvent event) {
        try {
            table.getCurrentPlayer().makeAction(Actions.FOLD);
        } catch (ActionException e) {
            e.printStackTrace();
        }
    }

    private int getRaiseValue() {
        return Integer.parseInt(betTextField.getText());
    }

    private void onSliderDrag() {
        betTextField.setText(Integer.toString((int) betSlider.getValue()));
    }

}