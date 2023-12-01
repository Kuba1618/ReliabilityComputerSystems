package com.example.averagereparingtime;

import counting.Data;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

public class HelloController {
    public int numberOfFields;
    public Data data;
    public List<Float> concreteTimesList;
    @FXML
    private TextField minTxtField,maxAverageReparingTimeTxtField,requiredReparingTimeTxtField,alphaTxtField,betaTxtField;
    @FXML
    private Button obliczBtn,saveToFileBtn;
    @FXML
    private Label isConditionMetLabel,Tnd, Tng;
    @FXML
    private VBox timeForConcreteTrialVBox;

    @FXML
    private void generateForm(ActionEvent event) {
        numberOfFields = Integer.parseInt(minTxtField.getText());

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

        try{

            float maksAvgReparingTime = Float.parseFloat(maxAverageReparingTimeTxtField.getText());
            float requiredRepairTime = Float.parseFloat(requiredReparingTimeTxtField.getText());
            float a = Float.parseFloat(alphaTxtField.getText());
            float b = Float.parseFloat(betaTxtField.getText());
            getDataFromVBox();
            data = new Data(numberOfFields,concreteTimesList,maksAvgReparingTime,requiredRepairTime,a,b);
            data.countData();

            if(data.isConditionMet()){
                isConditionMetLabel.setTextFill(Color.GREEN);
                isConditionMetLabel.setText("Wymaganie podstawowe spełnione !");
            } else if(!data.isConditionMet()){
                isConditionMetLabel.setTextFill(Color.RED);
                isConditionMetLabel.setText("wymaganie podstawowe NIE spełnione !");
            }

            Tnd.setText("Tnd = "  + data.getTnd());
            Tng.setText("Tng = " + data.getTng());

        }catch(Exception exception){
            PauseTransition hitAnimation = new PauseTransition(Duration.seconds(2));
            hitAnimation.playFromStart();
            isConditionMetLabel.setVisible(true);
            isConditionMetLabel.setTextFill(Color.RED);
            isConditionMetLabel.setText("Błąd programu !!!");
            hitAnimation.setOnFinished(e -> isConditionMetLabel.setText(""));
        }
    }

    public void getDataFromVBox(){

        concreteTimesList = new LinkedList<>();

        for(int i = 0; i < timeForConcreteTrialVBox.getChildren().size();i++){
            TextField concreteTimeValue = (TextField)timeForConcreteTrialVBox.getChildren().get(i);
            try{
                float concreteTime = Float.parseFloat(concreteTimeValue.getText());
                concreteTimesList.add(concreteTime);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void saveToFile() {

        String desktopPath = System.getProperty("user.home") + File.separator + "Desktop";

        String filePath = desktopPath + File.separator + "Data_of_average_reparing_time.txt";

        try {
                FileWriter fileWriter = new FileWriter(filePath, true);
                PrintWriter printWriter = new PrintWriter(fileWriter);

                printWriter.println(data.toString() + "\n\n-------------------------------------");

                printWriter.close();
                fileWriter.close();

                System.out.println("Zapisano do pliku: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
