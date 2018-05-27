package HoldemOffline.Controls;

import javafx.scene.control.Label;

public class LabelWithLastAction extends Label {
    public LabelWithLastAction() {
        setLayoutX(9.0);
        setLayoutY(1.0);
        getStylesheets().add(getClass().getResource("/styles/label.css").toExternalForm());
        getStyleClass().add("white");
    }
}