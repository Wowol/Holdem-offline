package HoldemOffline.Controllers;

import javafx.beans.value.*;
import javafx.fxml.FXML;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javafx.scene.image.Image;
import javafx.scene.control.*;

import HoldemOffline.Model.App;
import HoldemOffline.Model.Card;
import HoldemOffline.Model.MethodReferences;
import HoldemOffline.Model.Player;
import HoldemOffline.Model.Table;
import HoldemOffline.Model.Actions.*;
import HoldemOffline.Model.Actions.Exceptions.ActionException;
import HoldemOffline.Model.Utilities.Command;
import HoldemOffline.Controls.CardImageView;
import HoldemOffline.Controls.PlayerPane;
import javafx.scene.input.*;

public class GameController {
    private static final Logger log = LoggerFactory.getLogger(App.class);

    private static final int SMALL_BLIND = 10;
    private static final int BIG_BLIND = 20;
    private static final double TABLE_CARD_HEIGHT = 112.0;

    @FXML
    private AnchorPane tableAnchorPane;

    @FXML
    private Pane playerOnePane;

    @FXML
    private Pane playerTwoPane;

    @FXML
    private Pane playerThreePane;

    @FXML
    private Button betRaiseButton;

    @FXML
    private Button callCheckButton;

    @FXML
    private Button foldButton;

    @FXML
    private TextField betTextField;

    @FXML
    private Slider betSlider;

    @FXML
    private HBox tableCardsBox;

    private Table table;

    private ArrayList<Player> players = new ArrayList<>();

    HashMap<Player, PlayerPane> playerToPane = new HashMap<>();

    @FXML
    protected void initialize() {
        table = new Table(new MethodReferences() {

            @Override
            public Consumer<Player> getFunctionToInformPlayerOfHisTurn() {
                return (Player p) -> yourTurn(p);
            }

            @Override
            public BiConsumer<Player, ArrayList<Card>> getFunctionToGivePlayersCards() {
                return null;
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
        });
        addPlayersToTable();
        setBlinds(SMALL_BLIND, BIG_BLIND);
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

    private void setSliderTick() {
        betSlider.setMajorTickUnit(table.bigBlind);
        betSlider.setMinorTickCount(table.bigBlind);
        betSlider.valueProperty()
                .addListener((obs, oldval, newVal) -> betSlider.setValue(Math.round(newVal.doubleValue())));
    }

    private void newTurn() {
        for (Player p : players) {
            playerToPane.get(p).clearActionLabelText();
        }
    }

    private void endHand() {
        for (Player p : players) {
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
                System.out.println(new_val.doubleValue());
                onSliderDrag();
            }
        });
    }

    private void setBlinds(int smallBlind, int bigBlind) {
        table.smallBlind = smallBlind;
        table.bigBlind = bigBlind;
    }

    private void addPlayersToTable() {
        Player p1 = new Player(table);
        p1.numberOfChips = 700;

        Player p2 = new Player(table);
        p2.numberOfChips = 500;

        Player p3 = new Player(table);
        p3.numberOfChips = 300;
        players.addAll(Arrays.asList(p1, p2, p3));

        table.players.addAll(Arrays.asList(p1, p2, p3));
    }

    private void addPlayersToView() {
        PlayerPane player1Pane = new PlayerPane(new Image("/images/players/proud.png"), players.get(0));
        PlayerPane player2Pane = new PlayerPane(new Image("/images/players/happy.png"), players.get(1));
        PlayerPane player3Pane = new PlayerPane(new Image("/images/players/angry.png"), players.get(2));

        playerOnePane.getChildren().add(player1Pane);
        playerTwoPane.getChildren().add(player2Pane);
        playerThreePane.getChildren().add(player3Pane);

        playerToPane.put(players.get(0), player1Pane);
        playerToPane.put(players.get(1), player2Pane);
        playerToPane.put(players.get(2), player3Pane);
    }

    public void yourTurn(Player player) {
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    protected void playerBetted(InputEvent event) {
        try {
            table.getCurrentPlayer().makeAction(Actions.BET, getRaiseValue());
        } catch (ActionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    protected void playerCalled(InputEvent event) {
        try {
            table.getCurrentPlayer().makeAction(Actions.CALL);
        } catch (ActionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    protected void playerChecked(InputEvent event) {
        try {
            table.getCurrentPlayer().makeAction(Actions.CHECK);
        } catch (ActionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    protected void playerAllIn(InputEvent event) {
        try {
            table.getCurrentPlayer().makeAction(Actions.All_IN);
        } catch (ActionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    protected void playerFolded(InputEvent event) {
        try {
            table.getCurrentPlayer().makeAction(Actions.FOLD);
        } catch (ActionException e) {
            // TODO Auto-generated catch block
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