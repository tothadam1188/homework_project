package hu.unideb.inf.model;
import game.console.Game;
import java.util.Scanner;

/**
 * Entry point for running the game in a console environment.
 * Initializes the game state and handles user input parsing.
 */
public class ConsoleGame {
    public static void main(String[] args) {
        var gameModel = new GameModel();
        var game = new Game<>(gameModel, ConsoleGame::parseMove);
        game.start();
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
        if (!s.matches("\\d+\\s+\\d+")) {
            throw new ConsoleGameException("Invalid input format. Expected: '<row> <col>'");
        }
        var scanner = new Scanner(s);
        return new Move(scanner.nextInt(), scanner.nextInt());
    }
}
