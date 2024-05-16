package com.example.project1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainPage extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    // in order to make a usable program
    // it should take no more than 800MB memory __ it`s still high tho
    // so in order to achieve this the input max value is 10000
    @Override
    public void start(Stage stage) {                // main page that just has a button to-
        Pane firstPagePane = new Pane();            // to run the program
        Button startButton = new Button("start");
        startButton.setPrefSize(64,32);
        startButton.setLayoutX(480);
        startButton.setLayoutY(240);
        firstPagePane.getChildren().add(startButton);

        Scene scene = new Scene(firstPagePane,1024,512);
        startButton.setOnAction(e->{
            firstPagePane.getChildren().clear();
            SecondPagePane secondPagePane = new SecondPagePane();
            firstPagePane.getChildren().add(secondPagePane);
        });                                                 // LightStaleGrey
//        firstPagePane.setStyle("-fx-background-color: #fde5dc;");        // css styling
//        startButton.setStyle("-fx-text-fill: white;");
//        startButton.setStyle("-fx-font-size: 15px;");
//        startButton.setStyle("-fx-background-color: #32292f;");

        scene.getStylesheets().add(this.getClass().getResource("LightMode.css").toExternalForm());
        stage.setTitle("LCS project");
        stage.setScene(scene);
        stage.show();
    }
}
