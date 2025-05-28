package hu.unideb.inf.controller;

import hu.unideb.inf.model.GameModel;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class GameController {
    @FXML private Text currentPlayer;
    @FXML private GridPane board;

    private StackPane[][] squares;

    private final GameModel gameModel = new GameModel();

    @FXML
    private void initialize() {
        int size = GameModel.boardSize;
        squares = new StackPane[size][size];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                StackPane square = createSquare(row, col);
                squares[row][col] = square;
                board.add(square, col, row);
            }
        }
    }

    private StackPane createSquare(int row, int col) {
        Rectangle background = new Rectangle(70, 70);
        background.setStroke(Color.BLACK);
        background.setFill(getColorForCell(row, col));

        StackPane square = new StackPane(background);
        background.widthProperty().bind(square.widthProperty());
        background.heightProperty().bind(square.heightProperty());

        square.setOnMouseClicked(e -> {
            //TODO: logic
        });

        return square;
    }

    private Color getColorForCell(int row, int col) {
        return switch (gameModel.getBoardCell(row, col)) {
            case 1 -> Color.BLUE;
            case 2 -> Color.RED;
            default -> Color.WHITE;
        };
    }
}