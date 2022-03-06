package com.example.represc.gui;

import com.example.represc.data.EscapeGame;
import com.example.represc.utils.Settings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.Duration;
import java.util.ResourceBundle;

/**
 * @overview Controller of the "NewEscape" view. Serves to get input from the user to create a nex Escape Game.
 * Fires Warning messages if the input is incorrect.
 */
public class NewEscape  {
    private Stage stage;

    @FXML
    private TextField nameField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private TextField difficultyField;

    @FXML
    private Spinner minPlayersSpinner;
    @FXML
    private Spinner maxPlayersSpinner;
    @FXML
    private Spinner durationHoursSpinner;
    @FXML
    private Spinner durationMinutesSpinner;


    /**
     * @overview Checks the input of the user, if the ionput is acceptable, closes the "NEwEscape" Stage after creating
     * the nes Escape Game. If the input is not acceptable, fires a warning message about the invalid inputs.
     */
    @FXML
    protected void saveNewEscape(ActionEvent event) throws IOException {

        // if the inputs are acceptable, creates the new Escape Game and closes the "NewEscape" Stage (window).
        if (inputsOk()) {
            EscapeGame.getEscapeGame().setName(nameField.getText());
            if (!descriptionField.getText().trim().isEmpty()){
                EscapeGame.getEscapeGame().setDescription(descriptionField.getText().trim());
            }
            if (!difficultyField.getText().trim().isEmpty()){
                EscapeGame.getEscapeGame().setDifficulty(difficultyField.getText().trim());
            }
            EscapeGame.getEscapeGame().setPlayersMinimum((int) minPlayersSpinner.getValue());
            EscapeGame.getEscapeGame().setPlayersMaximum((int) maxPlayersSpinner.getValue());
            Duration duration = Duration.ofHours((int)durationHoursSpinner.getValue())
                    .plus(Duration.ofMinutes((int) durationMinutesSpinner.getValue()));
            EscapeGame.getEscapeGame().setEstimatedDuration(duration);

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    /**
     * @effects Opens a warning message if the inputs are not valid
     * @return Returns true if the inputs are valid, false if they aren't.
     */
    boolean inputsOk(){
        StringBuilder invalidInputs = new StringBuilder();
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.strings", Settings.getSettings().getLocale());

        // Checks inputs validity
        if (nameField.getText().trim().isEmpty()) {
            invalidInputs.append(bundle.getString("newEscape.error.emptyName")).append(" \n");
        }
        if ((int)minPlayersSpinner.getValue() > (int)maxPlayersSpinner.getValue()){
            invalidInputs.append(bundle.getString("newEscape.error.MinBiggerThanMax")).append(" \n");
        }

        // Warns the user about any unacceptable input
        if (invalidInputs.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(bundle.getString("warning"));
            alert.setHeaderText("invalidInput");
            alert.setContentText(invalidInputs.toString());

            alert.showAndWait();
            return false;
        }
        return true;
    }

    //TODO Close/Cancel button
}