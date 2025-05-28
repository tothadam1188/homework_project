package hu.unideb.inf.model;

import hu.unideb.inf.model.console.ConsoleGame;
import hu.unideb.inf.model.exception.ConsoleGameException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ConsoleGameTest {
    @Test
    void parseMoveValidInputReturnsMove(){
        var move = ConsoleGame.parseMove("3 4");
        assertEquals(3, move.row());
        assertEquals(4, move.col());
    }

    @Test
    void parseMoveInvalidInputsThrowException() {
        assertThrows(ConsoleGameException.class, () -> ConsoleGame.parseMove("a"));
        assertThrows(ConsoleGameException.class, () -> ConsoleGame.parseMove(""));
        assertThrows(ConsoleGameException.class, () -> ConsoleGame.parseMove("1"));
        assertThrows(ConsoleGameException.class, () -> ConsoleGame.parseMove("1 1 1"));
    }
}
