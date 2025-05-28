package hu.unideb.inf.model;
import game.console.Game;
import java.util.Scanner;

public class ConsoleGame {
    public static void main(String[] args) {
        var gameModel = new GameModel();
        var game = new Game<>(gameModel, ConsoleGame::parseMove);
        game.start();
    }

    public static Move parseMove(String s){
        s=s.trim();
        if (!s.matches("\\d+\\s+\\d+")) {
            throw new IllegalArgumentException();
        }
        var scanner = new Scanner(s);
        return new Move(scanner.nextInt(), scanner.nextInt());
    }
}
