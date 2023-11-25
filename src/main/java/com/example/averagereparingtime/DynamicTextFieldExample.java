package com.example.averagereparingtime;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DynamicTextFieldExample extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Dynamic TextField Example");

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        Label promptLabel = new Label("Wprowadź ilość liczb:");
        TextField countTextField = new TextField();
        Button generateButton = new Button("Generuj pola");

        vbox.getChildren().addAll(promptLabel, countTextField, generateButton);

        generateButton.setOnAction(event -> {
            int count = Integer.parseInt(countTextField.getText());
            generateInputFields(vbox, count);
        });

        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void generateInputFields(VBox vbox, int count) {
        vbox.getChildren().removeIf(node -> node instanceof TextField);

        for (int i = 0; i < count; i++) {
            TextField textField = new TextField();
            textField.setPromptText("Wprowadź liczbę " + (i + 1));
            vbox.getChildren().add(textField);
        }

        Button calculateButton = new Button("Oblicz średnią");
        vbox.getChildren().add(calculateButton);

        calculateButton.setOnAction(event -> calculateAverage(vbox, count));
    }

    private void calculateAverage(VBox vbox, int count) {
        double sum = 0;

        for (int i = 2; i < vbox.getChildren().size(); i++) {
            TextField textField = (TextField) vbox.getChildren().get(i);
            try {
                double value = Double.parseDouble(textField.getText());
                sum += value;
            } catch (NumberFormatException e) {
                // Ignoruj nieprawidłowe wartości
            }
        }

        double average = sum / count;

        Label resultLabel = new Label("Średnia arytmetyczna: " + average);
        vbox.getChildren().add(resultLabel);
    }
}
