package com.example.represc.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainView implements Initializable {

    @FXML private Tab representTab;
    @FXML private Tab verifyTab;
    @FXML private Tab simulateTab;
    @FXML private ScrollPane mapContainer;

    private GameMap gameMap;



    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle){

        VBox representHeader = new VBox();
        VBox verifyHeader = new VBox();
        VBox simulateHeader = new VBox();

        Label representLabel = new Label(resourceBundle.getString("main.text.represent"));
        Label verifyLabel = new Label(resourceBundle.getString("main.text.verify"));
        Label simulateLabel = new Label(resourceBundle.getString("main.text.simulate"));

        ImageView representIcon = new ImageView(new Image(getClass().getResource("/Icons/Icon.png").toExternalForm()));
        ImageView verifyIcon = new ImageView(new Image(getClass().getResource("/Icons/Icon.png").toExternalForm()));
        ImageView simulateIcon = new ImageView(new Image(getClass().getResource("/Icons/Icon.png").toExternalForm()));
        representIcon.setFitWidth(30); representIcon.setFitHeight(30);
        verifyIcon.setFitWidth(30); verifyIcon.setFitHeight(30);
        simulateIcon.setFitWidth(30); simulateIcon.setFitHeight(30);

        representHeader.setAlignment(Pos.CENTER);
        verifyHeader.setAlignment(Pos.CENTER);
        simulateHeader.setAlignment(Pos.CENTER);

        representHeader.getChildren().addAll(representIcon, representLabel);
        verifyHeader.getChildren().addAll(verifyIcon, verifyLabel);
        simulateHeader.getChildren().addAll(simulateIcon, simulateLabel);

        representTab.setGraphic(representHeader);
        verifyTab.setGraphic(verifyHeader);
        simulateTab.setGraphic(simulateHeader);

        gameMap = new GameMap();
        representTab.setContent(gameMap);
    }


    @FXML
    private void newRoom() throws IOException {
        Locale locale = new Locale("fr", "BE");

        gameMap.drawByLine();
        Button confirmer = new Button("Confirmer");

        Button annuler = new Button("Annuler");
        annuler.setOnAction(gameMap.stopDrawing);
        new VBox(confirmer, annuler);

        //ResourceBundle bundle = ResourceBundle.getBundle("bundles.strings", locale);
        //loadNewObjectWindow(bundle,"room");
    }

    @FXML
    private void newDoor() throws IOException {
        Locale locale = new Locale("fr", "BE");
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.strings", locale);
        loadNewObjectWindow(bundle,"door");
    }

    @FXML
    private void newLock() throws IOException {
        Locale locale = new Locale("fr", "BE");
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.strings", locale);
        loadNewObjectWindow(bundle,"lock");
    }

    @FXML
    private void newKey() throws IOException {
        Locale locale = new Locale("fr", "BE");
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.strings", locale);
        loadNewObjectWindow(bundle,"key");
    }

    @FXML
    private void newPlayer() throws IOException {
        Locale locale = new Locale("fr", "BE");
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.strings", locale);
        loadNewObjectWindow(bundle,"player");
    }

    @FXML
    private void newStaff() throws IOException {
        Locale locale = new Locale("fr", "BE");
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.strings", locale);
        loadNewObjectWindow(bundle,"staff");
    }

    @FXML
    private void newHint() throws IOException {
        Locale locale = new Locale("fr", "BE");
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.strings", locale);


        Stage newCountdownStage = new Stage();
        Parent newCountdownRoot = FXMLLoader.load(getClass().getResource("/Hint.fxml"), bundle);
        Scene newCountdownScene = new Scene(newCountdownRoot);
        newCountdownStage.setScene(newCountdownScene);
        newCountdownStage.initModality(Modality.WINDOW_MODAL);
        //newCountdownStage.initOwner(newCountdownStage.getScene().getWindow());

        newCountdownScene.getStylesheets().add("Windows.css");
        //newCountdownStage.initStyle(StageStyle.UNDECORATED);
        newCountdownStage.show();

    }

    @FXML
    private void newCountdown() throws IOException {
        Locale locale = new Locale("fr", "BE");
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.strings", locale);


        Stage newCountdownStage = new Stage();
        Parent newCountdownRoot = FXMLLoader.load(getClass().getResource("/newCountdown.fxml"), bundle);
        Scene newCountdownScene = new Scene(newCountdownRoot);
        newCountdownStage.setScene(newCountdownScene);
        newCountdownStage.initModality(Modality.WINDOW_MODAL);
        //newCountdownStage.initOwner(newCountdownStage.getScene().getWindow());

        newCountdownScene.getStylesheets().add("Windows.css");
        //newCountdownStage.initStyle(StageStyle.UNDECORATED);
        newCountdownStage.show();
    }

    //TODO Implem Task
    @FXML
    private void newTask() throws IOException {
        Locale locale = new Locale("fr", "BE");
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.strings", locale);
        loadNewObjectWindow(bundle,"door");
    }

    //TODO Implem Event
    @FXML
    private void newEvent() throws IOException {
        Locale locale = new Locale("fr", "BE");
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.strings", locale);
        loadNewObjectWindow(bundle,"door");
    }

    @FXML
    public void loadNewObjectWindow(ResourceBundle bundle, String newObjectType) throws IOException {
        Stage newObjectStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/newObject.fxml"));
        Parent newObjectRoot = loader.load();
        newObject controller = loader.getController();
        controller.adaptToObjectType(newObjectType, bundle);

        Scene newObjectScene = new Scene(newObjectRoot);
        newObjectStage.setScene(newObjectScene);
        newObjectStage.setAlwaysOnTop(true);

        newObjectScene.getStylesheets().add("Windows.css");
        //newObjectStage.initStyle(StageStyle.UNDECORATED);
        newObjectStage.show();
    }
}