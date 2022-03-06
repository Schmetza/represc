package com.example.represc.gui;

import com.example.represc.utils.commonPhysicalElements;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.example.represc.utils.commonPhysicalElements.getCommonObjectsList;


public class newObject implements Initializable {

    private GridPane statesPane;
    final ToggleGroup initialStateGroup = new ToggleGroup();

    @FXML
    private Label Title;
    @FXML
    private VBox statesVBox;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void adaptToObjectType(String newObjectType, ResourceBundle bundle ) {
        commonPhysicalElements.commonObject objectType = getCommonObjectsList().getCommonObject(newObjectType);
        Title.setText(bundle.getString(objectType.getWindowTitle()));
        ArrayList<commonPhysicalElements.objectState> states = objectType.getStates();

        if (states != null){
            createGridPane();
            for (commonPhysicalElements.objectState state : states){
                addState(state, bundle);
            }
        }
    }

    @FXML
    private void createGridPane() {
        ColumnConstraints nameConstrain = new ColumnConstraints();
        nameConstrain.setPercentWidth(20);
        ColumnConstraints descriptionConstrain = new ColumnConstraints();
        descriptionConstrain.setPercentWidth(50);
        ColumnConstraints initialConstrain = new ColumnConstraints();
        initialConstrain.setHalignment(HPos.CENTER);
        initialConstrain.setPercentWidth(10);
        ColumnConstraints initiallyConstrain = new ColumnConstraints();
        initiallyConstrain.setPercentWidth(10);
        initiallyConstrain.setHalignment(HPos.CENTER);
        ColumnConstraints deleteConstrain = new ColumnConstraints();
        deleteConstrain.setPercentWidth(10);


        statesPane = new GridPane();
        statesPane.add(new Label("Name"), 0, 0);
        statesPane.addColumn(1, new Label("Description"));
        statesPane.addColumn(2, new Label("Initial state"));
        statesPane.addColumn(3, new Label("Initially accessible"));
        statesPane.addColumn(4);
        statesPane.getColumnConstraints().addAll(nameConstrain,descriptionConstrain,initialConstrain,
                initiallyConstrain, deleteConstrain);

        statesVBox.getChildren().add(2, statesPane);
    }

    @FXML
    private void addState(commonPhysicalElements.objectState state, ResourceBundle bundle){
        int row = statesPane.getRowCount()+1;
        String stateName = bundle.getString(state.getStateName());
        String stateDescription = bundle.getString(state.getStateDescription());
        RadioButton initialState = new RadioButton();
        initialState.setToggleGroup(initialStateGroup);
        statesPane.add(new TextField(stateName),0, row);
        statesPane.add(new TextField(stateDescription),1, row);
        statesPane.add(initialState,2, row);
        statesPane.add(new CheckBox(),3, row);
    }

}

