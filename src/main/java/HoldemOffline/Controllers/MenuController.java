package HoldemOffline.Controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import HoldemOffline.Model.ArtificialIntelligence;

import HoldemOffline.Controls.BotPane;
import HoldemOffline.Model.App;
import HoldemOffline.Model.Player;
import HoldemOffline.Model.Table;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

import javafx.scene.layout.HBox;
import javafx.event.ActionEvent;
import javafx.scene.Node;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuController {
    public static Map<String, Double> botAggression = new HashMap<>();

    static {
        botAggression.put("Bad", 1.0);
        botAggression.put("Angry", 0.9);
        botAggression.put("Happy", 0.7);
        botAggression.put("Proud", 0.5);
        botAggression.put("Homo", 0.2);
        botAggression.put("Retarded", -0.5);
    }

    @FXML
    private HBox botsBox;

    @FXML
    private Slider numberOfAiPlayersSlider;

    @FXML
    private Button startGameButton;

    @FXML
    private TextField smallBlindText;

    @FXML
    private TextField bigBlindText;

    @FXML
    private TextField startingChipsText;

    @FXML
    protected void initialize() {
        addFirstBot();
        addSliderListener();
    }

    private void addFirstBot() {
        botsBox.getChildren().add(new BotPane());
    }

    private void addSliderListener() {
        numberOfAiPlayersSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (oldVal.intValue() < newVal.intValue()) {
                for (int x = 0; x < newVal.intValue() - oldVal.intValue(); x++) {
                    botsBox.getChildren().add(new BotPane());
                }
            } else if (oldVal.intValue() > newVal.intValue()) {
                for (int x = 0; x < oldVal.intValue() - newVal.intValue(); x++) {
                    botsBox.getChildren().remove(botsBox.getChildren().size() - x - 1);
                }
            }
        });
    }

    @FXML
    protected void startGameButtonClick(ActionEvent event) {
        Table table = new Table();
        table.setBlinds(Integer.parseInt(smallBlindText.getText()), Integer.parseInt(bigBlindText.getText()));
        int numberOfChips = Integer.parseInt(startingChipsText.getText());

        Player player = new Player(table);
        player.numberOfChips = numberOfChips;
        player.avatar = new Image("/images/malcin.png");
        table.players.add(player);

        for (Node node : botsBox.getChildren()) {
            BotPane botPane = (BotPane) node;
            if (!botPane.botChoosen)
                return;
            Player newAIPlayer = new ArtificialIntelligence(table, botAggression.get(botPane.BotName));
            newAIPlayer.numberOfChips = numberOfChips;
            newAIPlayer.avatar = botPane.avatarImage;

            table.players.add(newAIPlayer);
        }

        loadGame(table);
    }

    private void loadGame(Table table) {
        try {
            FXMLLoader loader = new FXMLLoader();
            Parent rootNode;
            rootNode = (Parent) loader.load(getClass().getResourceAsStream("/fxml/game.fxml"));

            GameController controller = loader.<GameController>getController();

            controller.setTable(table);
            controller.startGame();

            Scene scene = new Scene(rootNode, 1280, 800);
            App.stage.setScene(scene);
            App.stage.setTitle("Game");
            App.stage.setResizable(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}