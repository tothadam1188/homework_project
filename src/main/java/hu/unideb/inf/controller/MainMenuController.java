package hu.unideb.inf.controller;

import game.State;
import hu.unideb.inf.model.persistence.Round;
import hu.unideb.inf.model.persistence.RoundDataManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;
import java.util.List;

/**
 * Controller class for the main menu of the application.
 * Handles input of player names and navigation to the game screen.
 */
public class MainMenuController {
    @FXML private TextField player1Name;
    @FXML private TextField player2Name;

    @FXML private Text player1Stats;
    @FXML private Text player2Stats;

    private final RoundDataManager roundDataManager = new RoundDataManager();

    /**
     * Initializes the listeners for updating player statistics.
     */
    @FXML
    public void initialize() {
        player1Name.textProperty().addListener((obs, oldVal, newVal) -> updatePlayerStats(newVal, player1Stats));
        player2Name.textProperty().addListener((obs, oldVal, newVal) -> updatePlayerStats(newVal, player2Stats));
    }

    @FXML
    private void onStartClick(MouseEvent event) throws IOException {
        Logger.debug("Start button clicked");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/game_view.fxml"));
        Parent parent = loader.load();
        Logger.debug("Game view loaded from FXML");

        GameController controller = loader.getController();

        Logger.info("Player 1: '{}', Player 2: '{}'", player1Name.getText(), player2Name.getText());
        controller.setPlayers(player1Name.getText(), player2Name.getText());

        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(true);
        stage.centerOnScreen();
        stage.show();
        Logger.debug("Game scene loaded");
    }

    private void updatePlayerStats(String playerName, Text statsText) {
        if (playerName == null || playerName.isBlank()) {
            statsText.setText("Wins: 0 / Rounds: 0");
            return;
        }

        try {
            List<Round> rounds = roundDataManager.loadRounds();
            int wins = 0;
            int totalRounds = 0;

            for (Round round : rounds) {
                if (playerName.equalsIgnoreCase(round.getPlayer1Name()) || playerName.equalsIgnoreCase(round.getPlayer2Name())) {
                    totalRounds++;
                    if ((round.getStatus() == State.Status.PLAYER_1_WINS && playerName.equalsIgnoreCase(round.getPlayer1Name())) ||
                            (round.getStatus() == State.Status.PLAYER_2_WINS && playerName.equalsIgnoreCase(round.getPlayer2Name()))) {
                        wins++;
                    }
                }
            }
            statsText.setText("Wins: " + wins + " / Rounds: " + totalRounds);
        } catch (Exception e) {
            statsText.setText("Wins: 0 / Rounds: 0");
            Logger.warn(e, "Failed to load rounds for player stats");
        }
    }
}
