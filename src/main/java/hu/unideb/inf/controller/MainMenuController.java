package hu.unideb.inf.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;

/**
 * Controller class for the main menu of the application.
 * Handles input of player names and navigation to the game screen.
 */
public class MainMenuController {
    @FXML private TextField player1Name;
    @FXML private TextField player2Name;

    /**
     * Handles the click event on the "Start" button.
     * @param event the mouse event triggered by clicking the Start button
     * @throws IOException if the FXML file cannot be loaded
     */
    @FXML
    void onStartClick(MouseEvent event) throws IOException {
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
        stage.show();
        Logger.debug("Game scene loaded");
    }
}
