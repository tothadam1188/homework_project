package hu.unideb.inf.model;

import game.State;
import org.tinylog.Logger;

public class GameModel implements State<Move>{

    private Player currentPlayer;
    private Status status;
    private int boardSize=11;
    private int[][] board;

    /**
     * Initializes the game model with default settings.
     * This constructor sets the current player to {@link Player#PLAYER_1},
     * sets the game status to {@link Status#IN_PROGRESS}, and initializes
     * an empty game board of size 11x11.
     */
    public GameModel() {
        this.currentPlayer = Player.PLAYER_1;
        this.status = Status.IN_PROGRESS;
        this.board = new int[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (i%2==0 && j%2==1){
                    board[i][j]=1;
                }
                if(i%2==1 && j%2==0){
                    board[i][j]=2;
                }
            }
        }
        Logger.info("GameModel initialized with player={}, status={}", currentPlayer, status);
    }

    /**
     * Returns the next player to make a move.
     * @return the {@link Player} who will play after the current player.
     */
    @Override
    public Player getNextPlayer() {
        if(currentPlayer==Player.PLAYER_1) {
            Logger.debug("Next player: {}", Player.PLAYER_2);
            return Player.PLAYER_2;
        }
        else {
            Logger.debug("Next player: {}", Player.PLAYER_1);
            return Player.PLAYER_1;
        }
    }

    /**
     * Checks whether the game is over by evaluating win conditions for both players.
     * The game ends if:
     * <ul>
     *     <li>{@link Player#PLAYER_1} (value {@code 1}) has a complete vertical column of their markers.</li>
     *     <li>{@link Player#PLAYER_2} (value {@code 2}) has a complete horizontal row of their markers.</li>
     * </ul>
     * If either condition is met, the {@link #status} is updated accordingly and {@code true} is returned.
     * @return {@code true} if a player has won, otherwise {@code false}
     */
    @Override
    public boolean isGameOver() {
        Logger.debug("Checking if the game is over.");
        for (int col = 0; col < boardSize; col++) {
            boolean isContinuous = true;
            for (int row = 0; row < boardSize; row++) {
                if (board[row][col] != 2) {
                    isContinuous = false;
                    break;
                }
            }
            if (isContinuous) {
                status = Status.PLAYER_1_WINS;
                Logger.info("Game over: PLAYER_1 wins");
                return true;
            }
        }
        for (int row = 0; row < boardSize; row++) {
            boolean isContinuous = true;
            for (int col = 0; col < boardSize; col++) {
                if (board[row][col] != 2) {
                    isContinuous = false;
                    break;
                }
            }
            if (isContinuous) {
                if(status==Status.PLAYER_1_WINS) {
                    Logger.info("Game over: DRAW");
                    status = Status.DRAW;
                }
                else {
                    Logger.info("Game over: PLAYER_2 wins");
                    status = Status.PLAYER_2_WINS;
                }
                return true;
            }
        }
        Logger.debug("No winning state reached, game is continuing.");
        return false;
    }

    /**
     * Returns the current status of the game.
     * @return the {@link Status} representing the game's current state.
     */
    @Override
    public Status getStatus() {
        return status;
    }

    /**
     * Checks if the specified player has won the game.
     * @param player the {@link Player} to check for a winning condition
     * @return {@code true} if the given {@code player} has won, {@code false} otherwise
     */
    @Override
    public boolean isWinner(Player player) {
        return State.super.isWinner(player);
    }

    /**
     * Checks whether the specified move is within the bounds of the game board.
     * @param move the {@link Move} to validate
     * @return {@code true} if the {@code move} is inside the board boundaries, {@code false} otherwise
     */
    public boolean isLegalMove(Move move) {
        int row = move.row();
        int col = move.col();
        boolean isMoveLegal=row >= 0 && row < boardSize && col >= 0 && col < boardSize;
        Logger.debug("Move legality checked at ({}, {}): {}", move.row(), move.col(), isMoveLegal);
        return isMoveLegal;
    }

    /**
     * Makes a move on the board for the current player and switches turns.
     * @param move the {@link Move} to execute
     */
    public void makeMove(Move move) {
        Logger.debug("Attempting to make move: {}", move);
        if (!isLegalMove(move)) {
            Logger.error("Illegal move attempted at row={}, col={}", move.row(), move.col());
            throw new GameModelException("Illegal move at row " + move.row() + ", column " + move.col());
        }
        if(currentPlayer==Player.PLAYER_1) {
            Logger.info("PLAYER_1 moved at ({}, {})", move.row(), move.col());
            board[move.col()][move.row()] = 1;
        }
        if(currentPlayer==Player.PLAYER_2) {
            Logger.info("PLAYER_2 moved at ({}, {})", move.row(), move.col());
            board[move.col()][move.row()] = 2;
        }

        if(!isGameOver()) {
            currentPlayer = getNextPlayer();
        }
    }

    /**
     * Returns the current player.
     * @return the {@link game.State.Player} representing the game's current player.
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}
