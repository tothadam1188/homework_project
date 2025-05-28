package hu.unideb.inf.controller;

import hu.unideb.inf.model.core.GameModel;
import hu.unideb.inf.model.core.Move;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.tinylog.Logger;

/**
 * Controller class for the game screen.
 * Manages UI interaction and updates based on game state.
 */
public class GameController {
    @FXML private Text currentPlayer;
    private String player1Name;
    private String player2Name;

    @FXML private GridPane board;

    private StackPane[][] squares;

    private final GameModel gameModel = new GameModel();

    /**
     * Initializes the game board UI and sets up event listeners.
     */
    @FXML
    private void initialize() {
        Logger.debug("Initializing game board");
        int size = GameModel.BOARD_SIZE;
        squares = new StackPane[size][size];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                StackPane square = createSquare(row, col);
                squares[row][col] = square;
                board.add(square, col, row);
            }
        }
        updateCurrentPlayerText();
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
        updateCurrentPlayerText();
    }

    /**
     * Creates a cell on the board with click handling.
     * @param row row of cell on board
     * @param col column of cell on board
     * @return a square of the StackPane class
     */
    private StackPane createSquare(int row, int col) {
        Rectangle background = new Rectangle(70, 70);
        background.setStroke(Color.BLACK);
        background.setFill(getColorForCell(row, col));

        StackPane square = new StackPane(background);
        background.widthProperty().bind(square.widthProperty());
        background.heightProperty().bind(square.heightProperty());

        square.setOnMouseClicked(e -> handleSquareClick(row, col));

        return square;
    }

    /**
     * Handles user clicking a cell to make a move.
     * @param row row of user click on board
     * @param col column of user click on board
     */
    private void handleSquareClick(int row, int col) {
        if (gameModel.isGameOver()) {
            Logger.debug("Click ignored, game is already over.");
            return;
        }
        Logger.debug("Square clicked at ({}, {})", row, col);
        gameModel.makeMove(new Move(row, col));
        updateBoardUI();

        if (gameModel.isGameOver()) {
            String winner = switch (gameModel.getStatus()) {
                case PLAYER_1_WINS -> player1Name;
                case PLAYER_2_WINS -> player2Name;
                default -> "Unknown";
            };
            Logger.info("Game over. Winner: {}", winner);
            currentPlayer.setText("Game Over! " + winner + " wins!");
        } else {
            updateCurrentPlayerText();
        }
    }

    /**
     * Determines the color of a cell based cell value.
     * @param row row of cell on board
     * @param col column of cell on board
     * @return the color of the cell
     */
    private Color getColorForCell(int row, int col) {
        return switch (gameModel.getBoardCell(row, col)) {
            case 1 -> Color.BLUE;
            case 2 -> Color.RED;
            default -> Color.WHITE;
        };
    }

    /**
     * Updates all board cell colors based on cell values.
     */
    private void updateBoardUI() {
        Logger.debug("Updating board UI");
        for (int row = 0; row < GameModel.BOARD_SIZE; row++) {
            for (int col = 0; col < GameModel.BOARD_SIZE; col++) {
                Rectangle rect = (Rectangle) squares[row][col].getChildren().getFirst();
                rect.setFill(getColorForCell(row, col));
            }
        }
    }

    /**
     * Updates the UI text to show the current player's turn.
     */
    private void updateCurrentPlayerText() {
        currentPlayer.setText(switch (gameModel.getNextPlayer()) {
            case PLAYER_1 -> player1Name + "'s turn";
            case PLAYER_2 -> player2Name + "'s turn";
        });
    }
}