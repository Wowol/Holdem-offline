package HoldemOffline.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import HoldemOffline.Model.App;
import HoldemOffline.Model.Card;
import HoldemOffline.Model.Deck;
import HoldemOffline.Model.Hand;
import HoldemOffline.Controls.CardImageView;

public class SelectCardsController {
    private static final Logger log = LoggerFactory.getLogger(App.class);

    @FXML
    private GridPane gridPane;

    @FXML
    private HBox handCardsBox;

    @FXML
    private HBox kickersBox;

    private ArrayList<Card> selectedCards = new ArrayList<Card>();

    @FXML
    private Label handNameLabel;

    private final String MESSAGE_NOT_ENOUGH_CARDS = "Select more cards!";
    private final double OPACITY_NOT_USED = 0.3;

    @FXML
    protected void initialize() {
        addAllCards();
    }

    private void addAllCards() {
        log.info("Adding cards to scene");
        Deck deck = new Deck();
        int number = 0;
        for (Card c : deck) {
            try {
                ImageView cardView = new CardImageView(c, OPACITY_NOT_USED);
                cardView.setId(c.toString());
                cardView.setOnMousePressed(this::handleCardClick);
                gridPane.add(cardView, number % 12, number / 12);
                number++;
            } catch (IllegalArgumentException e) {
                log.error("Can't load image to card {}", c.toString());
            }
        }
    }

    public void handleCardClick(InputEvent event) {
        if (!(event.getSource() instanceof CardImageView)) {
            log.error("Bad method call");
            throw new RuntimeException();
        }
        CardImageView source = (CardImageView) event.getSource();
        if (!selectedCards.contains(source.card)) {
            selectedCards.add(source.card);
            source.setOpacity(1);
        } else {
            selectedCards.remove(source.card);
            source.setOpacity(OPACITY_NOT_USED);
        }

        clearCards();

        if (selectedCards.size() >= 5) {
            new Thread(new AddCardsThread()).start();
            handNameLabel.setText("Calculating...");
        } else {
            handNameLabel.setText(MESSAGE_NOT_ENOUGH_CARDS);
        }
    }

    private void clearCards() {
        handCardsBox.getChildren().clear();
        kickersBox.getChildren().clear();
    }

    class AddCardsThread implements Runnable {
        @Override
        public void run() {
            Hand h = new Hand(selectedCards);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    addCards(h);
                }
            });
        }

        private void addCards(Hand h) {
            handNameLabel.setText(h.getHandName().toString());
            handCardsBox.getChildren().addAll(
                    h.getHandCards().stream().map(item -> new CardImageView(item)).collect(Collectors.toList()));
            kickersBox.getChildren()
                    .addAll(h.getKickers().stream().map(item -> new CardImageView(item)).collect(Collectors.toList()));
        }
    }

}