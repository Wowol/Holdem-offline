package HoldemOffline.Controls;
import javafx.scene.control.Label;

public class LabelWithCurrentChips extends Label {

    public LabelWithCurrentChips() {
        setLayoutX(33.0);
        setLayoutY(139.0);
        getStylesheets().add(getClass().getResource("/styles/label.css").toExternalForm());
        getStyleClass().add("white");
    }
}