package hu.unideb.inf.model;
import game.console.Game;
import org.tinylog.Logger;

import java.util.Scanner;

/**
 * Entry point for running the game in a console environment.
 * Initializes the game state and handles user input parsing.
 */
public class ConsoleGame {
    public static void main(String[] args) {
        Logger.info("Starting ConsoleGame.");
        var gameModel = new GameModel();
        var game = new Game<>(gameModel, ConsoleGame::parseMove);
        game.start();
        Logger.info("ConsoleGame has ended.");
    }

    /**
     * Parses a user input string into a {@link Move} object.
     * The expected input format is two space-separated integers (row and column).
     * @param s the input string from the console
     * @return a {@link Move} object representing the user's move
     * @throws IllegalArgumentException if the input format is invalid
     */
    public static Move parseMove(String s){
        s=s.trim();
        Logger.debug("Parsing move from input: '{}'", s);
        if (!s.matches("\\d+\\s+\\d+")) {
            Logger.error("Invalid move input format: '{}'", s);
            throw new ConsoleGameException("Invalid input format. Expected: '<row> <col>'");
        }
        var scanner = new Scanner(s);
        var move = new Move(scanner.nextInt(), scanner.nextInt());
        Logger.debug("Parsed move: {}", move);
        return move;
    }
}
