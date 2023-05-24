package board_game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class board_game_appilcation_KG extends Application{
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/first.fxml"));
        stage.setTitle("Main Page");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
