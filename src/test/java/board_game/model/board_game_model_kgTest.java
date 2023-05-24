package board_game.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class board_game_model_kgTest {
    private static final int BOARD_HEIGHT = 7;
    private static final int BOARD_WIDTH=5;
    private board_game_model_kg testmodel;

    @BeforeEach
    void setUp() {
        testmodel = new board_game_model_kg();
    }

    @Test
    void getSquare() {
        testmodel.getSquare(new Position(0,0));
    }

    @Test
    void getSquareInvalid() {
        try {
            testmodel.getSquare(new Position(0, 10));
            fail("the position is out of the boards range this should trigger an exception");
        }catch (Exception e){System.out.println("Property caught exception: "+e.getMessage());}
    }
    @Test
    void move() {
        testmodel.move(new Position(0,0),new Position(0,1));
    }
    @Test
    void moveInvalid() {
        try{
        testmodel.move(new Position(0,-1),new Position(0,10));
        fail("one of the posittion is out of the boards range this should trigger an exception");
        }catch (Exception e){System.out.println("Property caught exception: "+e.getMessage());}
    }
    @Test
    void isGameOver() {
        testmodel.isGameOver();
    }

    @Test
    void canMove() {
        testmodel.canMove(new Position(0,1),new Position(0,2));
    }
    @Test
    void canMoveInvaid() {
        boolean ertek =testmodel.canMove(new Position(0, 1), new Position(1, 3));
        assertEquals(false,ertek);

    }

    @Test
    void isEmpty() {
        boolean erek = testmodel.isEmpty(new Position(0,0));
        assertNotNull(erek);
    }

    @Test
    void isInTheSameRow() {

        assertEquals(true,testmodel.isInTheSameRow(new Position(0,1),new Position(0,4)));
    }
    @Test
    void isInTheSameRowInvalid() {

        assertEquals(false,testmodel.isInTheSameRow(new Position(1,1),new Position(0,4)));
    }
    @Test
    void isOnBoard() {
        assertEquals(true,testmodel.isOnBoard(new Position(0,0)));
    }
    @Test
    void isOnBoardInvalid() {
        assertEquals(false,testmodel.isOnBoard(new Position(-1,0)));
    }

    @Test
    void isPawnMove() {
        assertEquals(true,testmodel.isPawnMove(new Position(0,0),new Position(0,1)));
    }

    @Test
    void isPawnMoveInvalid() {
        assertEquals(false,testmodel.isPawnMove(new Position(0,3),new Position(0,0)));
    }

    @Test
    void testToString() {
        String palya = testmodel.toString();
        assertNotNull(palya);
    }
}