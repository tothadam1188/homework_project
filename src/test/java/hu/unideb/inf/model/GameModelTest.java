package hu.unideb.inf.model;

import game.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameModelTest {

    private GameModel gameModel;

    @BeforeEach
    void setup(){
        gameModel = new GameModel();
    }

    @Test
    void constructorInitializesFieldsProperly(){
        assertEquals(State.Player.PLAYER_1, gameModel.getNextPlayer());
        assertEquals(State.Status.IN_PROGRESS, gameModel.getStatus());
    }

    @Test
    void toStringMethodCorrectlyDisplaysGameBoard(){
        assertEquals("0 1 0 1 0 1 0 1 0 1 0 \n2 0 2 0 2 0 2 0 2 0 2 \n0 1 0 1 0 1 0 1 0 1 0 \n2 0 2 0 2 0 2 0 2 0 2 \n0 1 0 1 0 1 0 1 0 1 0 \n" +
                            "2 0 2 0 2 0 2 0 2 0 2 \n0 1 0 1 0 1 0 1 0 1 0 \n2 0 2 0 2 0 2 0 2 0 2 \n0 1 0 1 0 1 0 1 0 1 0 \n2 0 2 0 2 0 2 0 2 0 2 \n0 1 0 1 0 1 0 1 0 1 0 \n"
                            ,gameModel.toString());
    }

    @Test
    void gameModelClassDetectsPlayer1Win(){
        gameModel.makeMove(new Move(1,1));
        gameModel.makeMove(new Move(3,1));
        gameModel.makeMove(new Move(1,3));
        gameModel.makeMove(new Move(5,1));
        gameModel.makeMove(new Move(1,5));
        gameModel.makeMove(new Move(7,1));
        gameModel.makeMove(new Move(1,7));
        gameModel.makeMove(new Move(9,1));
        gameModel.makeMove(new Move(1,9));

        assertTrue(gameModel.isGameOver());
        assertEquals(State.Status.PLAYER_1_WINS, gameModel.getStatus());
        assertTrue(gameModel.isWinner(State.Player.PLAYER_1));
    }

    @Test
    void gameModelClassDetectsPlayer2Win(){
        gameModel.makeMove(new Move(1,1));
        gameModel.makeMove(new Move(1,1));
        gameModel.makeMove(new Move(3,2));
        gameModel.makeMove(new Move(3,1));
        gameModel.makeMove(new Move(1,3));
        gameModel.makeMove(new Move(5,1));
        gameModel.makeMove(new Move(1,5));
        gameModel.makeMove(new Move(7,1));
        gameModel.makeMove(new Move(1,7));
        gameModel.makeMove(new Move(9,1));

        assertTrue(gameModel.isGameOver());
        assertEquals(State.Status.PLAYER_2_WINS, gameModel.getStatus());
        assertTrue(gameModel.isWinner(State.Player.PLAYER_2));
    }

    @Test
    void gameModelClassDetectsIllegalMove(){
        assertThrows(GameModelException.class, () -> gameModel.makeMove(new Move(1,11)));
    }

}
