package board_game;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;

public class ENDController {

    @FXML
    private Text text;

    private String Winnername;

    /**
     * getter for the Name
     * @return
     */
    public String getName() {
        return Winnername;
    }

    /**
     * setter for the name
     * @param name
     */
    public void setName(String name) {
        this.Winnername = name;
        Logger.info("Winner's name is set to {}", Winnername);
    }

    /**
     * sets the parameters after the scene was loaded
     */
    @FXML
    private void initialize() {
        Logger.info("GameOver scene loaded");
        Platform.runLater(() -> {
            text.setText("The Winner's name: " + Winnername + "!");
        });
    }
    /**
     * handles when the player pushed the button
    */

    @FXML
    private void handleExitButton(ActionEvent event) throws IOException {
        Logger.info("Returned to Main Scene");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/first.fxml"));
        Parent root = loader.load();
        MainPageController controller = loader.getController();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}