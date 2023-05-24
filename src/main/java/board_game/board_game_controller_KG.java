package board_game;

import board_game.model.board_game_model_kg;
import board_game.model.Position;
import board_game.model.Square;
import board_game.util.board_game_move_selector_KG;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.tinylog.Logger;


import java.io.IOException;

import static board_game.util.board_game_move_selector_KG.Phase;

public class board_game_controller_KG {



    @FXML
    private GridPane board;

    private board_game_model_kg model = new board_game_model_kg();

    private board_game_move_selector_KG selector = new board_game_move_selector_KG(model);

    private String[] PlayerNames;

    /**
     * getter for the Name
     * @return
     */
    public String[] getNames() {
        return PlayerNames;
    }

    /**
     * setter for the name
     * @param name
     */
    public void setNames(String[] name) {
        this.PlayerNames = name;
        Logger.info("Player names are set to {}", name);
    }


    /**
     * sets the parameters after the scene was loaded
     */
    @FXML
    private void initialize() {

        for (var i = 0; i < board.getRowCount(); i++) {
            for (var j = 0; j < board.getColumnCount(); j++) {
                var square = createSquare(i, j);
                board.add(square, j, i);
            }
        }
        selector.phaseProperty().addListener(this::showSelectionPhaseChange);
        Logger.info("Board created");
    }

    /**
     * creates a square in i,j position
     * sets its parameters to defaults
    */
    private StackPane createSquare(int i, int j) {
        var square = new StackPane();
        square.getStyleClass().add("square");
        var piece = new Circle(50);
        piece.fillProperty().bind(createSquareBinding(model.squareProperty(i, j)));
        square.getChildren().add(piece);
        square.setOnMouseClicked(this::handleMouseClick);
        return square;
    }

    /**
     * handles when the player clicked to a square
     */
    @FXML
    private void handleMouseClick(MouseEvent event) {
        var square = (StackPane) event.getSource();
        var row = GridPane.getRowIndex(square);
        var col = GridPane.getColumnIndex(square);
        Logger.info("Click on square ({},{})", row, col);
        selector.select(new Position(row, col));
        if (selector.isReadyToMove()) {
            selector.makeMove();
            IsOver();

        } else if (selector.isUnableToMove()) {
            selector.reset();
        }

    }

    /**
     * handles the end of the game
     */
    private void IsOver() {
        if (model.GameOver){
            Logger.info("Game Over phase started");
            String Wname;
            String other;
            int count = model.counter;
            try {
                if (model.winer == "PLAYER1"){
                    Wname = PlayerNames[0];
                    other = PlayerNames[1];

                }
                else{
                    Wname = PlayerNames[1];
                    other = PlayerNames[0];
                }
                SwitchToENDScene(Wname);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * this method is responsible to switch to the end scene
    */
    private void SwitchToENDScene(String name) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/END.fxml"));
        Parent root = loader.load();
        ENDController controller = loader.getController();
        controller.setName(name);
        Stage stage = (Stage) board.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private ObjectBinding<Paint> createSquareBinding(ReadOnlyObjectProperty<Square> squareProperty) {
        return new ObjectBinding<Paint>() {
            {
                super.bind(squareProperty);
            }
            @Override
            protected Paint computeValue() {
                return switch (squareProperty.get()) {
                    case NONE -> Color.TRANSPARENT;
                    case PLAYER1 -> Color.LIGHTGRAY;
                    case PLAYER2 -> Color.BLACK;
                };
            }
        };
    }

    /**
     * This method is responsible to handle that the players can see the selected square
    */
    private void showSelectionPhaseChange(ObservableValue<? extends Phase> value, Phase oldPhase, Phase newPhase) {
        switch (newPhase) {
            case SELECT_FROM -> {}
            case SELECT_TO -> showSelection(selector.getFrom());
            case READY_TO_MOVE -> hideSelection(selector.getFrom());
            case CANT_MOVE -> hideSelection(selector.getFrom());
        }
    }

    /**
     * This method is responsible to show the selected square in GUI by creating a bold boarder around the square
     */
    private void showSelection(Position position) {
        var square = getSquare(position);
        square.getStyleClass().add("selected");
    }

    /**
     * This method is responsible to hide the selected square in GUI by hiding the bold boarder
     */

    private void hideSelection(Position position) {
        var square = getSquare(position);
        square.getStyleClass().remove("selected");
    }

    /**
     * getter for the square
     * @param position
     * @return
     */
    private StackPane getSquare(Position position) {
        for (var child : board.getChildren()) {
            if (GridPane.getRowIndex(child) == position.row() && GridPane.getColumnIndex(child) == position.col()) {
                return (StackPane) child;
            }
        }
        throw new AssertionError();
    }

}
