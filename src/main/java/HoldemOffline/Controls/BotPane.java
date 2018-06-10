package HoldemOffline.Controls;

import javafx.scene.image.ImageView;

import java.util.HashMap;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.beans.value.ObservableValue;

import javafx.scene.layout.*;
import javafx.scene.image.Image;

public class BotPane extends Pane {
    private Label selectAiLabel;
    private ImageView botAvatar;

    public String BotName;
    public ChoiceBox<String> botChooser;
    public boolean botChoosen = false;
    public Image avatarImage;

    private final double DEFAULT_HEIGHT = 200.0;
    private final double DEFAULT_WIDTH = 200.0;

    private final double LABEL_DEFAULT_LAYOUT_X = 74.0;
    private final double LABEL_DEFAULT_LAYOUT_Y = 224.0;
    private final String LABEL_DEFAULT_TEXT = "Select AI";

    private final double CHOICE_BOX_DEFAULT_LAYOUT_X = 25.0;
    private final double CHOICE_BOX_DEFAULT_LAYOUT_Y = 184.0;
    private final double CHOICE_BOX_DEFAULT_WIDTH = 150.0;

    private final double AVATAR_DEFAULT_HEIGHT = 130.0;
    private final double AVATAR_DEFAULT_WIDTH = 150.0;
    private final double AVATAR_DEFAULT_LAYOUT_X = 29.0;
    private final double AVATAR_DEFAULT_LAYOUT_Y = 28.0;

    private final String PLAYERS_FOLDER_URL = "/images/players/";

    final HashMap<String, String> botNameToAvatar = new HashMap<>();
    {
        botNameToAvatar.put("Angry", "angry.png");
        botNameToAvatar.put("Happy", "happy.png");
        botNameToAvatar.put("Proud", "proud.png");
        botNameToAvatar.put("Homo", "homo.png");
    }

    public BotPane() {
        this.setPrefHeight(DEFAULT_HEIGHT);
        this.setPrefWidth(DEFAULT_WIDTH);

        initializeBotNameLabel();
        initializeBotChooser();
        initializeBotAvatar();
    }

    private void setImageAvatar(String name) {
        String url = PLAYERS_FOLDER_URL + botNameToAvatar.get(name);
        avatarImage = new Image(url);
        botAvatar.setImage(avatarImage);
    }

    private void initializeBotNameLabel() {
        selectAiLabel = new Label();
        selectAiLabel.setLayoutX(LABEL_DEFAULT_LAYOUT_X);
        selectAiLabel.setLayoutY(LABEL_DEFAULT_LAYOUT_Y);
        selectAiLabel.setText(LABEL_DEFAULT_TEXT);
        this.getChildren().add(selectAiLabel);
    }

    private void initializeBotChooser() {

        botChooser = new ChoiceBox<String>(FXCollections.observableArrayList(botNameToAvatar.keySet()));
        botChooser.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
                selectAiLabel.setVisible(false);
                setImageAvatar((String) botNameToAvatar.keySet().toArray()[newValue.intValue()]);
                BotName = (String) botNameToAvatar.keySet().toArray()[newValue.intValue()];
                botChoosen = true;
            }
        });

        botChooser.setLayoutX(CHOICE_BOX_DEFAULT_LAYOUT_X);
        botChooser.setLayoutY(CHOICE_BOX_DEFAULT_LAYOUT_Y);
        botChooser.setPrefWidth(CHOICE_BOX_DEFAULT_WIDTH);

        this.getChildren().add(botChooser);
    }

    private void initializeBotAvatar() {
        botAvatar = new ImageView();
        botAvatar.setFitHeight(AVATAR_DEFAULT_HEIGHT);
        botAvatar.setFitWidth(AVATAR_DEFAULT_WIDTH);
        botAvatar.setLayoutX(AVATAR_DEFAULT_LAYOUT_X);
        botAvatar.setLayoutY(AVATAR_DEFAULT_LAYOUT_Y);
        botAvatar.setPickOnBounds(true);
        botAvatar.setPreserveRatio(true);

        this.getChildren().add(botAvatar);
    }
}