package com.example.represc.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.example.represc.utils.Settings.getSettings;

/**
 * @author Schmetz Arnaud
 * @version 1.0
 *
 * @overview Starting point of the program, launches the gui, displays the welcome view.
 */
public class ReprEsc extends Application {


    /**
     * @effects starts the gui, displaying the welcome view.
     * **/
    @Override
    public void start(Stage stage) throws IOException {

        // Gets the bundle according to the language settings and creates the "Welcome view" Scene.
        ResourceBundle bundle = ResourceBundle.getBundle("Bundles.strings", getSettings().getLocale());
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader()
                .getResource("welcomeView.fxml")), bundle));

        stage.setTitle("REPR-ESC");
        stage.setMinHeight(500);
        stage.setMinWidth(850);
        stage.setScene(scene);
        scene.getStylesheets().add("Main.css");

        stage.show();
    }

    public static void main(String[] args) {

        getSettings().setLocale(new Locale("fr"));
        Application.launch(ReprEsc.class);
    }
}