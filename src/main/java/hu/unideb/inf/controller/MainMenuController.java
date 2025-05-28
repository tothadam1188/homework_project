package hu.unideb.inf.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {
    @FXML private TextField player1Name;
    @FXML private TextField player2Name;

    @FXML
    void onStartClick(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/game_view.fxml"));
        Parent parent = loader.load();

        GameController controller = loader.getController();
        controller.setPlayers(player1Name.getText(), player2Name.getText());

        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
