package hu.unideb.inf.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameApplication extends Application{

    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/main_menu.fxml"));
        stage.setTitle("JavaFX Two-player Game");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.centerOnScreen();
        stage.show();
    }
}
