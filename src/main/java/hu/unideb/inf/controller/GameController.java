package hu.unideb.inf.controller;

import hu.unideb.inf.model.GameModel;
import hu.unideb.inf.model.Move;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

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
        int size = GameModel.boardSize;
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
        updateCurrentPlayerText();
    }

    /**
     * Creates a cell on the board with click handling.
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
     */
    private void handleSquareClick(int row, int col) {
        gameModel.makeMove(new Move(row, col));
        updateBoardUI();

        if (gameModel.isGameOver()) {
            String winner = switch (gameModel.getStatus()) {
                case PLAYER_1_WINS -> player1Name;
                case PLAYER_2_WINS -> player2Name;
                default -> "Unknown";
            };
            currentPlayer.setText("Game Over! " + winner + " wins!");
        } else {
            updateCurrentPlayerText();
        }
    }

    /**
     * Determines the color of a cell based cell value.
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
        for (int row = 0; row < GameModel.boardSize; row++) {
            for (int col = 0; col < GameModel.boardSize; col++) {
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