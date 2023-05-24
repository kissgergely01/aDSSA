package board_game.util;

import board_game.model.Square;
import board_game.model.board_game_model_kg;
import board_game.model.Position;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;

/**
 * This class is responsible for handling the squares the players selects
 */
public class board_game_move_selector_KG {
    public enum Phase {
        SELECT_FROM,
        SELECT_TO,
        READY_TO_MOVE,
        CANT_MOVE

    }
    private board_game_model_kg model;
    private ReadOnlyObjectWrapper<Phase> phase = new ReadOnlyObjectWrapper<>(Phase.SELECT_FROM);
    private boolean invalidSelection = false;
    private Position from;
    private Position to;

    /**
     * constructor
     */
    public board_game_move_selector_KG(board_game_model_kg model) {
        this.model = model;
    }

    /**
     * getter for Phase
     */
    public Phase getPhase() {
        return phase.get();
    }

    public ReadOnlyObjectProperty<Phase> phaseProperty() {
        return phase.getReadOnlyProperty();
    }

    /**
     * checks if the player is ready to move
     * @return result
     */
    public boolean isReadyToMove() {
        return phase.get() == Phase.READY_TO_MOVE;
    }
    /**
     * checks if the player can move
     * @return result
     */
    public boolean isUnableToMove() {
        return phase.get() == Phase.CANT_MOVE;
    }

    /**
     * selects where to send the position in its parameter
     * @param position
     */
    public void select(Position position) {
        switch (phase.get()) {
            case SELECT_FROM -> selectFrom(position);
            case SELECT_TO -> selectTo(position);
            case READY_TO_MOVE -> throw new IllegalStateException();
            case CANT_MOVE -> throw new IllegalStateException();
        }
    }

    /**
     * this method is responsible so that the players can select which disc they want to move
     * @param position
     */
    private void selectFrom(Position position) {
        boolean playersDisc = true;
        if (model.currentPlayer ==1){
            if (Square.PLAYER1 != model.getSquare(position)){
                playersDisc = false;
            }
        }
        else{
            if (Square.PLAYER2 != model.getSquare(position)){
                playersDisc = false;
            }
        }
        if (!model.isEmpty(position) && playersDisc) {
            from = position;
            phase.set(Phase.SELECT_TO);
            invalidSelection = false;
        } else {
            invalidSelection = true;
        }
    }
    /**
     * this method is responsible so that the players can select where they want to move their disc
     * @param position
     */
    private void selectTo(Position position) {
        if (model.canMove(from, position)) {
            to = position;
            phase.set(Phase.READY_TO_MOVE);
            invalidSelection = false;
        } else {
            invalidSelection = true;
            phase.set(Phase.CANT_MOVE);
        }

    }

    /**
     * getter for the From
     * @return
     */
    public Position getFrom() {
        if (phase.get() == Phase.SELECT_FROM) {
            throw new IllegalStateException();
        }
        return from;
    }
    /**
     * getter for the To
     * @return
     */
    public Position getTo() {
        if (phase.get() != Phase.READY_TO_MOVE) {
            throw new IllegalStateException();
        }
        return to;
    }

    public boolean isInvalidSelection() {
        return invalidSelection;
    }

    /**
     * This method is responsible to make the move the player selected
     */
    public void makeMove() {
        if (phase.get() != Phase.READY_TO_MOVE) {
            throw new IllegalStateException();
        }
        model.move(from, to);
        if(model.currentPlayer==1){
            model.currentPlayer=2;
        }
        else
            model.currentPlayer=1;
        reset();
    }

    /**
     * resets the positions that was selected
     */
    public void reset() {
        from = null;
        to = null;
        phase.set(Phase.SELECT_FROM);
        invalidSelection = false;
    }
}
