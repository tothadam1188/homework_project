package hu.unideb.inf.controller;

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

    @FXML
    private void initialize() {
        int size = 11;
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
        background.setFill(Color.WHITE);

        StackPane square = new StackPane(background);
        background.widthProperty().bind(square.widthProperty());
        background.heightProperty().bind(square.heightProperty());

        square.setOnMouseClicked(e -> {
            //TODO: logic
        });

        return square;
    }
}
