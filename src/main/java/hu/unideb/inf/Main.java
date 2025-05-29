package hu.unideb.inf;

import hu.unideb.inf.view.GameApplication;
import javafx.application.Application;

/**
 * The main entry point for the application.
 * Launches the JavaFX game app.
 */
public class Main{

    public static void main(String[] args) {
        Application.launch(GameApplication.class, args);
    }
}