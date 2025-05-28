package hu.unideb.inf.controller;

import hu.unideb.inf.model.GameModel;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameController {
    private String player1Name;
    private String player2Name;

    @FXML private GridPane board;

    private final GameModel gameModel = new GameModel();

    public void setPlayers(String player1Name, String player2Name) {
        this.player1Name=player1Name;
        this.player2Name=player2Name;
    }

    @FXML
    private void initialize() {
        for (int row = 0; row < board.getRowCount(); row++) {
            for (int col = 0; col < board.getColumnCount(); col++) {
                StackPane square = createSquare(row, col);
                board.add(square, col, row);
            }
        }
    }

    private StackPane createSquare(int row, int col) {
        StackPane square = new StackPane();
        square.setPrefSize(50, 50);

        Rectangle background = new Rectangle(50, 50);
        background.setStroke(Color.BLACK);
        background.setFill(getColorForCell(row, col));

        square.getChildren().add(background);

        square.setOnMouseClicked(e -> {
            System.out.println("Clicked (" + row + ", " + col + ")");
        });

        return square;
    }

    private Color getColorForCell(int row, int col) {
        int value = gameModel.getBoardCell(row, col);
        return switch (value) {
            case 1 -> Color.RED;
            case 2 -> Color.BLUE;
            default -> Color.WHITE;
        };
    }
}
