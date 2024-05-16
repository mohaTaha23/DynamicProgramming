package com.example.project1;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Light extends Circle {
     int pos = -1;
        public Light(){
            this.setFill(Color.WHITE);
            this.setRadius(18);
            this.setStroke(Color.BLACK);
            this.setStrokeWidth(2);
        }
    public Light(int x , int y){
        this.setFill(Color.WHITE);
        this.setRadius(18);
        this.setStroke(Color.BLACK);
        this.setStrokeWidth(2);
        this.setLayoutX(x);
        this.setLayoutY(y);
    }
    void turnOn(){
        this.setFill(Color.YELLOW);
    }

}