package board_game;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;


public class MainPageController {

    @FXML
    private TextArea leaderboard;
    @FXML
    private TextField Player1Name;
    @FXML
    private TextField Player2Name;

    /**
     * sets the parameters after the scene was loaded
     */
    @FXML
    private void initialize() {
        Player1Name.setText("Player1");
        Player2Name.setText("Player2");
        leaderboard.setText("a");
    }
    /**
     * handles when the player pushed the play button
     */
    @FXML
    private void switchScene(ActionEvent event) throws IOException {
        Logger.info("Player 1 Name entered: {}", Player1Name.getText());
        Logger.info("Player 2 Name entered: {}", Player2Name.getText());
        Logger.info("Scene changed");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/game.fxml"));
        Parent root = loader.load();
        board_game_controller_KG controller = loader.getController();
        controller.setNames(new String[]{Player1Name.getText(),Player2Name.getText()});
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    /**
     * handles when the player pushed the exit button
     */
    @FXML
    private void handleQuitButton(ActionEvent event) throws IOException {
        Logger.info("Exited program");
        Platform.exit();
    }
}