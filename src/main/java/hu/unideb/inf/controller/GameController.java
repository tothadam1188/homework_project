package hu.unideb.inf.controller;

import hu.unideb.inf.model.core.GameModel;
import hu.unideb.inf.model.core.Move;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;

/**
 * Controller class for the game screen.
 * Manages UI interaction and updates based on game state.
 */
public class GameController {
    private static final double CELL_SIZE = 70.0;

    @FXML private GridPane board;
    @FXML private Text currentPlayer;
    @FXML private Button restartButton;

    private String player1Name;
    private String player2Name;

    private StackPane[][] boardCells;
    private final GameModel gameModel = new GameModel();

    @FXML
    private void initialize() {
        Logger.debug("Initializing game board");
        int size = GameModel.BOARD_SIZE;
        boardCells = new StackPane[size][size];

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                StackPane cell = createCell(row, col);
                boardCells[row][col] = cell;
                board.add(cell, col, row);
            }
        }

        restartButton.setVisible(false);
        updateCurrentPlayerDisplay();
    }

    /**
     * Sets the player names and updates UI text.
     * @param player1Name name of player 1
     * @param player2Name name of player 2
     */
    public void setPlayers(String player1Name, String player2Name) {
        this.player1Name=player1Name;
        this.player2Name=player2Name;
        gameModel.setPlayers(player1Name, player2Name);
        updateCurrentPlayerDisplay();
    }

    private StackPane createCell(int row, int col) {
        Rectangle background = new Rectangle(CELL_SIZE, CELL_SIZE);
        background.setStroke(Color.BLACK);
        background.setFill(determineCellColor(row, col));

        StackPane cell = new StackPane(background);
        cell.setPrefSize(CELL_SIZE, CELL_SIZE);
        background.widthProperty().bind(cell.widthProperty());
        background.heightProperty().bind(cell.heightProperty());

        cell.setOnMouseClicked(e -> handleCellClick(row, col));

        return cell;
    }

    private void handleCellClick(int row, int col) {
        if (gameModel.isGameOver()) {
            Logger.debug("Click ignored, game is already over.");
            return;
        }

        Logger.debug("Cell clicked at ({}, {})", row, col);
        gameModel.makeMove(new Move(row, col));
        refreshBoardDisplay();

        if (gameModel.isGameOver()) {
            handleGameOver();
        } else {
            updateCurrentPlayerDisplay();
        }
    }

    private void handleGameOver() {
        String winner = switch (gameModel.getStatus()) {
            case PLAYER_1_WINS -> player1Name;
            case PLAYER_2_WINS -> player2Name;
            default -> "Unknown";
        };

        Logger.info("Game over. Winner: {}", winner);
        currentPlayer.setText("Game Over! " + winner + " wins!");
        restartButton.setVisible(true);
    }

    private Color determineCellColor(int row, int col) {
        return switch (gameModel.getBoardCell(row, col)) {
            case 1 -> Color.BLUE;
            case 2 -> Color.RED;
            default -> Color.WHITE;
        };
    }

    /**
     * Updates all board cell colors based on cell values.
     */
    private void refreshBoardDisplay() {
        Logger.debug("Refreshing board display");
        for (int row = 0; row < GameModel.BOARD_SIZE; row++) {
            for (int col = 0; col < GameModel.BOARD_SIZE; col++) {
                Rectangle cellRect = (Rectangle) boardCells[row][col].getChildren().getFirst();
                cellRect.setFill(determineCellColor(row, col));
            }
        }
    }

    /**
     * Updates the UI text to show the current player's turn.
     */
    private void updateCurrentPlayerDisplay() {
        currentPlayer.setText(switch (gameModel.getNextPlayer()) {
            case PLAYER_1 -> player1Name + "'s turn";
            case PLAYER_2 -> player2Name + "'s turn";
        });
    }

    /**
     * Handles clicking the restart button to return to the main menu.
     * Loads {@code main_menu.fxml} and replaces the current scene.
     */
    @FXML
    private void onRestartClick() {
        try {
            Logger.debug("Restart button clicked, loading main menu");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main_menu.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) board.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            Logger.error("Failed to load main menu on restart", e);
        }
    }
}