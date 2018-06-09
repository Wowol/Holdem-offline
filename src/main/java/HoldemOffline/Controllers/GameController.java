package HoldemOffline.Controllers;

import HoldemOffline.Model.*;
import javafx.beans.value.*;
import javafx.fxml.FXML;
import javafx.scene.layout.*;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiConsumer;
import java.util.function.Consumer;


import javafx.scene.control.*;

import HoldemOffline.Model.Actions.*;
import HoldemOffline.Model.Actions.Exceptions.ActionException;
import HoldemOffline.Model.Utilities.Command;
import HoldemOffline.Model.Utilities.TriConsumer;
import HoldemOffline.Controls.CardImageView;
import HoldemOffline.Controls.PlayerPane;
import javafx.scene.input.*;
import javafx.application.Platform;

public class GameController {

    private static final double TABLE_CARD_HEIGHT = 112.0;

    private static final int AI_WAIT_MILISECONDS = 1000;

    private ReentrantLock lock = new ReentrantLock();

    @FXML
    private AnchorPane tableAnchorPane;

    @FXML
    private AnchorPane buttonsPane;

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
            return (Player p) -> new Thread(new Runnable() {
                @Override
                public void run() {
                    yourTurn(p);
                }
            }).start();
        }

        @Override
        public BiConsumer<Player, List<Card>> getFunctionToGivePlayersCards() {
            return (Player p, List<Card> cards) -> new Thread(new Runnable() {
                @Override
                public void run() {
                    giveCardsToPlayer(p, cards);
                }
            }).start();
        }

        @Override
        public Consumer<Card> getFunctionToAddCardToTable() {
            return (Card c) -> new Thread(new Runnable() {
                @Override
                public void run() {
                    addCardToTable(c);
                }
            }).start();
        }

        @Override
        public TriConsumer<Player, Actions, Integer> getFunctionToInformPlayersThatPlayerMadeMove() {
            return (Player p, Actions a, Integer i) -> new Thread(new Runnable() {
                @Override
                public void run() {
                    playerMadeMove(p, a, i);
                }
            }).start();
        }

        @Override
        public Command getFunctionToNewTurn() {
            return () -> new Thread(new Runnable() {

                @Override
                public void run() {
                    newTurn();
                }
            }).start();
            // Platform.runLater(new Runnable() {
            // @Override
            // public void run() {
            // newTurn();
            // }
            // });
        }

        @Override
        public Command getFunctionToEndHand() {
            return () -> new Thread(new Runnable() {
                @Override
                public void run() {
                    endHand();
                }
            }).start();
        }
    };

    public void setTable(Table table) {
        this.table = table;
    }

    public void startGame() {
        table.setReferences(references);
        addPlayersToView();
        new Thread(table).start();
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
        lock.lock();
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                playerToPane.get(player).cardsBox.setCards(cards);
            }
        });
        lock.unlock();
    }

    private void setSliderTick() {
        betSlider.setMajorTickUnit(table.bigBlind);
        betSlider.setMinorTickCount(table.bigBlind);
        betSlider.valueProperty()
                .addListener((obs, oldval, newVal) -> betSlider.setValue(Math.round(newVal.doubleValue())));
    }

    private void newTurn() {
        lock.lock();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for (Player p : table.players) {
                    playerToPane.get(p).clearActionLabelText();
                }
            }
        });
        lock.unlock();
    }

    private void endHand() {
        lock.lock();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for (Player p : table.players) {
                    playerToPane.get(p).setNumberOfChipsLabelText(p.numberOfChips);
                    playerToPane.get(p).clearActionLabelText();
                }
                tableCardsBox.getChildren().clear();
            }
        });
        lock.unlock();
    }

    private void addCardToTable(Card card) {
        lock.lock();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                tableCardsBox.getChildren().add(new CardImageView(card, TABLE_CARD_HEIGHT));

            }
        });
        lock.unlock();
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

    private void aiTurn(ArtificialIntelligence aiPlayer) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(AI_WAIT_MILISECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ((ArtificialIntelligence) aiPlayer).makeAction();
                        } catch (ActionException e) {
                            e.printStackTrace();
                        }

                    }
                });

            }
        }).start();
    }

    public void yourTurn(Player player) {
        lock.lock();

        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                if (player instanceof ArtificialIntelligence && player.table.status != null) {
                    aiTurn((ArtificialIntelligence) player);
                    return;
                }

                buttonsPane.setVisible(true);

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
        });
        lock.unlock();

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

    private void playerMadeMove(Player player, Actions action, int value) {
        lock.lock();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String text = action.toString();
                if (action == Actions.BET || action == Actions.RAISE || action == Actions.All_IN
                        || action == Actions.SMALL_BLIND || action == Actions.BIG_BLIND) {
                    text += " " + value;
                }
                playerToPane.get(player).setActionLabelText(text);
                playerToPane.get(player).setNumberOfChipsLabelText(player.numberOfChips);
            }
        });
        lock.unlock();
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
            buttonsPane.setVisible(false);
        } catch (ActionException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void playerBetted(InputEvent event) {
        try {
            table.getCurrentPlayer().makeAction(Actions.BET, getRaiseValue());
            buttonsPane.setVisible(false);
        } catch (ActionException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void playerCalled(InputEvent event) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    table.getCurrentPlayer().makeAction(Actions.CALL);
                } catch (ActionException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        buttonsPane.setVisible(false);

    }

    @FXML
    protected void playerChecked(InputEvent event) {
        try {
            table.getCurrentPlayer().makeAction(Actions.CHECK);
            buttonsPane.setVisible(false);
        } catch (ActionException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void playerAllIn(InputEvent event) {
        try {
            table.getCurrentPlayer().makeAction(Actions.All_IN);
            buttonsPane.setVisible(false);
        } catch (ActionException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void playerFolded(InputEvent event) {
        try {
            table.getCurrentPlayer().makeAction(Actions.FOLD);
            buttonsPane.setVisible(false);
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