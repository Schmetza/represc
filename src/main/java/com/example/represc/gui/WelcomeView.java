package com.example.represc.gui;

import com.example.represc.utils.Settings;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @author Schmetz Arnaud
 * @version 1.0
 *
 * @overview Controller for the welcomeView, which is the first one to be shown when the application is launched. Leads
 * to the main view by either loading an escape game or creating a new one.
 *
 * @specfield mainStage: Stage // The Stage of the view.
 * @specfield backGrid: Rectangle // Serves as way to display the moving grid on the view. might not be optimal but not important.
 */
public class WelcomeView implements Initializable {

    private Stage mainStage;
    @FXML
    private Rectangle backGrid;


    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int gridSize = 40;

        // Applies the grid Pattern to the Rectangle backGrid.
        backGrid.setFill(createGridPattern(gridSize));

        // Sets the translation of the moving grid in the background.
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(backGrid);
        translate.setByX(gridSize);
        translate.setByY(gridSize);
        translate.setDuration(Duration.millis(3000));
        translate.setCycleCount(TranslateTransition.INDEFINITE);
        translate.setInterpolator(Interpolator.LINEAR);
        translate.play();
    }

    /**
     * @postcondition gridSize must be positive
     * @deprecated Not the most optimal solution, only use for the welcomeView as it is purely visual and not important.
     * @return Returns a Pattern resembling a grid with a unit size of {@code gridSize}.
     */
    public ImagePattern createGridPattern(int gridSize) {

        Canvas canvas = new Canvas(gridSize, gridSize);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setStroke(Color.web("2B2D42"));
        gc.setFill(Color.web("#FFFFFF"));
        gc.fillRect(0, 0, gridSize, gridSize);
        gc.strokeRect(0, 0, gridSize, gridSize);

        Image image = canvas.snapshot(new SnapshotParameters(), null);

        return new ImagePattern(image, 0, 0, gridSize, gridSize, false);
    }

    /**
     * @effects Loads the "MainView" on the background and the "NewEscape" view on the foreground.
     * @throws IOException
     */
    @FXML protected void switchToMainViewNew(ActionEvent event) throws IOException {
        // Loads the Mainview in the background
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.strings", Settings.getSettings().getLocale());
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/mainView.fxml")), bundle);
        mainStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene mainScene = new Scene(root);
        mainStage.setScene(mainScene);
        mainStage.show();
        mainStage.setMaximized(true);
        mainScene.getStylesheets().add("Main.css");

        // Loads the "new Escape game" window on the foreground
        Stage newEscapeStage = new Stage();
        Parent newEscapeRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/newEscape.fxml")), bundle);
        Scene newEscapeScene = new Scene(newEscapeRoot);
        newEscapeStage.setScene(newEscapeScene);
        newEscapeStage.initModality(Modality.WINDOW_MODAL);
        newEscapeStage.initOwner(mainStage.getScene().getWindow());
        newEscapeStage.initStyle(StageStyle.UNDECORATED);
        newEscapeScene.getStylesheets().add("Windows.css");
        newEscapeStage.show();
    }

    /**
     * @effects Loads the "MainView" and
     * @throws IOException
     */
    @FXML protected void switchToMainViewLoad(ActionEvent event) throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.strings", Settings.getSettings().getLocale());
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/mainView.fxml")), bundle);
        mainStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene mainScene = new Scene(root);
        mainStage.setScene(mainScene);
        mainStage.show();
        mainStage.setMaximized(true);
        mainScene.getStylesheets().add("Main.css");
    }
}

