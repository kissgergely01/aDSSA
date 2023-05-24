package board_game.model;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;

import java.util.Random;
import org.tinylog.Logger;

/**
 * A játék modelljét implementáló Class
 */
public class board_game_model_kg {

    private static final int BOARD_HEIGHT = 7;
    private static final int BOARD_WIDTH=5;
    /**
     * The player who can move next
     */
    public int currentPlayer;
    public String winer;
    public boolean GameOver;
    public int counter;


    private ReadOnlyObjectWrapper<Square>[][] board = new ReadOnlyObjectWrapper[BOARD_HEIGHT][BOARD_WIDTH];

    /**
     * Constructor
     * Creates the board with the starting lineup
     */
    public board_game_model_kg() {
        winer = "none";
        counter = 0;
        GameOver = false;
        currentPlayer = 1;
        for (var i = 0; i < BOARD_HEIGHT; i++) {
            Random rnd = new Random();
            int player1 = rnd.nextInt(0, BOARD_WIDTH);
            int player2 = rnd.nextInt(0, BOARD_WIDTH);
            while (player1 == player2){
                player2 = rnd.nextInt(0, BOARD_WIDTH);
            }
            for (var j = 0; j < BOARD_WIDTH; j++) {
                if(j == player1) {
                    board[i][j] = new ReadOnlyObjectWrapper<Square>(Square.PLAYER1);
                } else if (j==player2) {
                    board[i][j] = new ReadOnlyObjectWrapper<Square>(Square.PLAYER2);
                }
                else{
                    board[i][j] = new ReadOnlyObjectWrapper<Square>(Square.NONE);
                }
            }
        }
    }


    public ReadOnlyObjectProperty<Square> squareProperty(int i, int j) {
        return board[i][j].getReadOnlyProperty();
    }

    /**
     * Returns what is in the Square in a possition
     * @param p The possition
     * @return What is in that possition
     */
    public Square getSquare(Position p) {
        return board[p.row()][p.col()].get();
    }

    /**
     * Sets a Square's parameters to the given parameters
     */
    private void setSquare(Position p, Square square) {
        board[p.row()][p.col()].set(square);
    }

    /**
     * Moves a square's datas to an other square
     * @param from
     * @param to
     */
    public void move(Position from, Position to) {
        setSquare(to, getSquare(from));
        setSquare(from, Square.NONE);
        Logger.info("Move made on the board");
        counter++;
        isGameOver();
    }

    /**
     * This method is resposible to check if one of the players wins after each move
     */
    public void isGameOver(){
        boolean CheckMate = true;


        if (board[0][0].get() != Square.NONE && board[0][1].get() != Square.NONE){
            for(int i = 1; i< BOARD_HEIGHT; i++){
                if (!(board[0][0].get() == board[i][0].get() && board[0][1].get() == board[i][1].get()
                        || board[0][0].get()==board[i][BOARD_WIDTH -1].get() && board[0][1].get()==board[i][BOARD_WIDTH -2].get())){
                    CheckMate = false;
                }
            }
            if (CheckMate){
                winer = String.valueOf(board[0][1].getValue());
                GameOver = true;
                Logger.info("GameOver. Winner: {}", winer);
            }
        }
        if (board[0][BOARD_WIDTH -1].get() != Square.NONE && board[0][BOARD_WIDTH -2].get() != Square.NONE){
            for(int i = 1; i< BOARD_HEIGHT; i++){
                if (!(board[0][BOARD_WIDTH -1].get() == board[i][0].get() && board[0][BOARD_WIDTH -2].get() == board[i][1].get()
                        || board[0][BOARD_WIDTH -1].get()==board[i][BOARD_WIDTH -1].get() && board[0][BOARD_WIDTH -2].get()==board[i][BOARD_WIDTH -2].get())){
                    CheckMate = false;
                }
            }
            if (CheckMate){
                winer = String.valueOf(board[0][BOARD_WIDTH -2].getValue());
                GameOver = true;
                Logger.info("GameOver. Winner: {}", winer);
            }
        }

    }

    /**
     * This method is resposible to decide if the player can move from the current square to the selected one
     * @param from current square
     * @param to selected square
     * @return result
     */
    public boolean canMove(Position from, Position to) {
        return isInTheSameRow(from,to) && isOnBoard(from) && isOnBoard(to) && !isEmpty(from) && isEmpty(to) && isPawnMove(from, to);
    }

    /**
     * checks if the square is empty or not
     * @param p the possition where we want to know this
     * @return resault
     */
    public boolean isEmpty(Position p) {
        return getSquare(p) == Square.NONE;
    }

    /**
     * checks if two positions are in the same row or not
     * @param p1 first position
     * @param p2 second position
     * @return resault
     */
    public boolean isInTheSameRow(Position p1, Position p2){
        return p1.row()==p2.row();
    }

    /**
     * checks if the position is on the board or not
     * @param p the position
     * @return resault
     */
    public boolean isOnBoard(Position p) {
        return 0 <= p.row() && p.row() < BOARD_HEIGHT && 0 <= p.col() && p.col() < BOARD_WIDTH;
    }

    /**
     * checks the distance of the two positions
     * @param from first position
     * @param to second position
     * @return if the distance is 1. -> returns true
     */
    public boolean isPawnMove(Position from, Position to) {
        var dy = Math.abs(to.col() - from.col());
        return dy == 1;
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        for (var i = 0; i < BOARD_HEIGHT; i++) {
            for (var j = 0; j < BOARD_WIDTH; j++) {
                sb.append(board[i][j].get().ordinal()).append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        var model = new board_game_model_kg();
        System.out.println(model);
    }

}
