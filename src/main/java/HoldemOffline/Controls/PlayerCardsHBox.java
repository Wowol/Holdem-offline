package HoldemOffline.Controls;

import java.util.List;

import HoldemOffline.Model.Card;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class PlayerCardsHBox extends HBox {

    private static final int SPACING = 5;

    public PlayerCardsHBox(List<Card> cards) {
        setSpacing(SPACING);
        setLayoutX(86.0);
        setLayoutY(139.0);
        setPrefHeight(44.0);
        setPrefWidth(89.0);

        setCards(cards);
    }

    public void setCards(List<Card> cards) {
        getChildren().clear();
        for (Card card : cards) {
            ImageView cardView = new CardImageView(card, card == null ? 80 : 100);
            getChildren().add(cardView);
        }
    }
}