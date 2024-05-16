package com.example.project1;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class DemonstrationPagePane extends ScrollPane {     // create scrollPane that shows lights and sources number
    String[] allLightsArray ;       // the lights array
    String[] powerSources ;         // the sources array
    String[] validLights;           // the turned on lights
    Pane pane = new Pane();         // pane to set on scrollPane



    public DemonstrationPagePane(String[] allLightsArray, String[] powerSources, String[] validLights) {
        this.allLightsArray = allLightsArray;
        this.powerSources = powerSources;
        this.validLights = validLights;
        this.setContent(pane);
        demonstrate();
    }

    // set all elements on scrollPane and cook them

    private void demonstrate(){
        int numberOfLights = powerSources.length;   // the sources number which is == lights number
        int tracer = -1;    // for all the array

        while (++tracer<numberOfLights){
            Label src = new Label(powerSources[tracer]+"");
            src.setLayoutX(364);  // 3rd quarter of the pane - 20
            src.setLayoutY(tracer*40+20);   // +20 off set // *20 space between
            src.setPrefHeight(36);          // like the light

            pane.getChildren().add(src);
        }
        // add lights
        // add light bulbs on the scrollPane w their numbers
        tracer=0;
        Light [] bulbsArray = new Light[allLightsArray.length];
        while (tracer<numberOfLights){
            Light light = new Light(190,tracer*40+40);                      // 265 + 5 space + 18 radius
            pane.getChildren().add(light);
            System.out.println(tracer);
            bulbsArray[tracer] = light;
            Label number = new Label(allLightsArray[tracer]+"");
            number.setLayoutX(128);
            number.setLayoutY(tracer*40+20);
            number.setPrefHeight(36);          // like the light
            pane.getChildren().add(number);
            tracer++;
        }
        // some of lights bulbs might be on --> we turn them using answer array

        tracer = validLights.length-1;  // on the answer array
        int secondTracer =0;            // on the bulbs  array

        while (tracer>=0&&secondTracer<allLightsArray.length){  // second tracer might seem useless ... but just in case
            if (validLights[tracer]==null) {
                tracer--;
                continue;
            }
//            System.out.println(secondTracer +" this is second tracer");
//            System.out.println(tracer+" this is tracer");

            if(validLights[tracer].equals(allLightsArray[secondTracer])){
                bulbsArray[secondTracer].turnOn();
                bulbsArray[secondTracer].pos = Integer.parseInt(allLightsArray[secondTracer]);
                tracer--;
                secondTracer++;
            }
            else {
                secondTracer++;
            }
        }
        int value;
        for (int i =0; i<bulbsArray.length;i++){
           if (bulbsArray[i].pos!=-1){
               Line line = new Line();
               line.setStroke(Color.GREEN);
               line.setStrokeWidth(2);
               line.setFill(Color.LIGHTGREEN);
               line.setStartX(bulbsArray[i].getLayoutX()+18);
               line.setStartY(bulbsArray[i].getLayoutY());
               line.setEndX(362);       // should be 64 but it would overLap
               line.setEndY(bulbsArray[i].pos*40);
               pane.getChildren().add(line);
           }
        }

    }



}
