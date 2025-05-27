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
        assertEquals(State.Player.PLAYER_1, gameModel.getCurrentPlayer());
        assertEquals(State.Status.IN_PROGRESS, gameModel.getStatus());
    }

    @Test
    void getNextPlayerSwitchesPlayer(){
        assertEquals(State.Player.PLAYER_2, gameModel.getNextPlayer());
    }

}
