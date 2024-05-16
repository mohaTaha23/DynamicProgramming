package com.example.project1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class KitchenTemp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        TextField textField = new TextField();
        textField.setPrefWidth(250);
        textField.setLayoutY(200);
        textField.setLayoutX(200);

        textField.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            String character = event.getCharacter();

            if (!character.matches("[0-9]") && !character.equals(" ")) {
                event.consume(); // Consume the event to prevent the input
            }
        });
        Button button = new Button("press");
        Light light = new Light();

        light.setLayoutY(100);
        Pane pane = new Pane(textField,button,light);
        button.setOnAction(e->{
            String[] arr = textField.getText().trim().replaceAll("\\s+", " ").split(" ");
            for (String y : arr){
                System.out.println(y);
            }
        });
        Scene scene = new Scene(pane,1000,500);
        stage.setScene(scene);
        stage.show();
    }
}
