package HoldemOffline.Model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App extends Application {

    private static final String FIRST_SCENE = "/fxml/menu.fxml";

    private static final String TITLE = "Holdem Offline - hands";

    private static final String ICON = "/images/malcin.png";

    private static final Logger log = LoggerFactory.getLogger(App.class);

    public static Stage stage;

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        this.stage = stage;
        log.info("Starting HoldemOffline");
        log.debug("Loading FXML for main view from: {}", FIRST_SCENE);
        FXMLLoader loader = new FXMLLoader();
        Parent rootNode = (Parent) loader.load(getClass().getResourceAsStream(FIRST_SCENE));
        log.debug("Showing JFX scene");
        Scene scene = new Scene(rootNode, 1366, 768);
        stage.setTitle(TITLE);
        stage.getIcons().add(new Image(ICON));
        stage.setScene(scene);
        stage.show();
    }
}