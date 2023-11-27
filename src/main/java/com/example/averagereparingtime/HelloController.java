package com.example.averagereparingtime;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class HelloController {

    @FXML
    private TextField minTxtField;
    @FXML
    private Button obliczBtn;

    @FXML
    private VBox timeForConcreteTrialVBox;

    @FXML
    private void generateForm(ActionEvent event) {
        int numberOfFields = Integer.parseInt(minTxtField.getText());

        // Wyczyszczenie istniejących pól tekstowych
        timeForConcreteTrialVBox.getChildren().clear();

        // Dodanie nowych pól tekstowych
        for (int i = 0; i < numberOfFields; i++) {
            TextField textField = new TextField();
            textField.setPromptText("T " + (i + 1));
            timeForConcreteTrialVBox.getChildren().add(textField);
        }

        timeForConcreteTrialVBox.setMinHeight(numberOfFields * 25.5);
    }

    @FXML
    public void count(){

    }

    public void getData(){

    }

}
