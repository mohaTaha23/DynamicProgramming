package com.example.project1;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SecondPagePane extends Pane {
    Button calculateButton = new Button("calculate");
    TextField answerTextField = new TextField();
    TextField lightsOrderAnswer = new TextField();
    TextArea tableBox = new TextArea();
    TextField numberOfLightsInput = new TextField();                       // where the user put the first input
    TextField lightsOrderInput = new TextField();                           // where the user enter the lights order
    Button showDemonstration = new Button("show demonstration");

    SecondPagePane(){
        this.setPrefSize(1024,512);
        render();
    }
    private void render(){      // this function fill the pane with elements (labels and inputs stuff) and it only runs once

        Label numberOfLightsLabel = new Label("Enter Number of Lights: ");  // label
        numberOfLightsInput.setPrefWidth(256);
        HBox firstInputBlock = new HBox(numberOfLightsLabel,numberOfLightsInput);   // add the priors to a block
        firstInputBlock.setLayoutX(256);
        firstInputBlock.setLayoutY(64);                                             // set the sizes

        Label lights = new Label("Enter the Lights order: ");               // label
        lightsOrderInput.setPrefWidth(256);                                     // sizes
        HBox secondInputBlock = new HBox(lights,lightsOrderInput);              // add them to a block (second input)
        secondInputBlock.setLayoutX(256);
        secondInputBlock.setLayoutY(100);
        secondInputBlock.setSpacing(10);

        lightsOrderInput.addEventFilter(KeyEvent.KEY_TYPED, event -> {          // only allows numbers and whiteSpace
            String character = event.getCharacter();

            if (!character.matches("[0-9]") && !character.equals(" ")) {
                event.consume(); // Consume the event to prevent the input
            }
        });

        numberOfLightsInput.addEventFilter(KeyEvent.KEY_TYPED, event -> {       // only allows numbers
            String character = event.getCharacter();

            if (!character.matches("[0-9]")) {
                event.consume(); // Consume the event to prevent the input
            }
        });




        // =============  ===== start with the output section =========== here nothing should be taking inputs =============

         Label label1 = new Label("The answer:");
         Label label2 = new Label("The optimal lights:");
         Label label3 = new Label("The DP Table:");

        label1.setLayoutX(32);
        label1.setLayoutY(256);
        answerTextField.setLayoutX(128);
        answerTextField.setLayoutY(256);
        answerTextField.setPrefWidth(64);
        answerTextField.setEditable(false);

        label2.setLayoutX(32);
        label2.setLayoutY(332);
        lightsOrderAnswer.setLayoutX(150);
        lightsOrderAnswer.setLayoutY(332);
        lightsOrderAnswer.setPrefWidth(256);
        lightsOrderAnswer.setEditable(false);

        label3.setLayoutX(748);
        label3.setLayoutY(226);
        tableBox.setPrefSize(286,256);
        tableBox.setLayoutX(640);
        tableBox.setLayoutY(246);
        tableBox.setEditable(false);



        // ============================================ end of output section ==============================

        Button autoGenerateButton = new Button("auto-fill");
        autoGenerateButton.setLayoutX(750);
        autoGenerateButton.setLayoutY(100);
        autoGenerateButton.setOnAction(e->{
            String numberOfLightsString = numberOfLightsInput.getText();      //puts and do operation on it
            int numberOfLights =-1;
            try {
                numberOfLights = Integer.parseInt(numberOfLightsString);
                if (numberOfLights==0){
                    throw new Exception();
                }
                else {
                    lightsOrderInput.clear();
                    for (int i=0;i<numberOfLights;i++){
                        int temp = numberOfLights;
                        lightsOrderInput.appendText(((int) Math.floor(Math.random()*temp)+1)+" ");
                    }
                }
            }
            catch (Exception exception){
                System.out.println(exception.getMessage());
                Alert alert = new Alert(Alert.AlertType.ERROR,"Enter a valid lights first");
                alert.show();
            }

        });

        calculateButton.setOnAction(e->{                                      // when the user click calculate it takes the in-
            String numberOfLightsString = numberOfLightsInput.getText();      //puts and do operation on it
            int numberOfLights =-1;
            try {
                numberOfLights = Integer.parseInt(numberOfLightsString);
                if (numberOfLights==0){
                    throw new Exception();
                }
                else validateInput(numberOfLights);
            }
            catch (Exception exception){
                System.out.println(exception.getMessage());
                Alert alert = new Alert(Alert.AlertType.ERROR,"Enter a valid lights number");
                alert.show();
            }
        });
        calculateButton.setLayoutX(850);
        calculateButton.setLayoutY(100);
        this.getChildren().addAll(firstInputBlock,secondInputBlock, calculateButton,label1,label2,answerTextField, lightsOrderAnswer,label3,tableBox,autoGenerateButton);

        showDemonstration.setPrefSize(160,40);
        showDemonstration.setLayoutX(408);
        showDemonstration.setLayoutY(440);
    }

    private void validateInput(int numberOflights){
        String[] arr = lightsOrderInput.getText().trim().replaceAll("\\s+", " ").split(" ");
        if (arr[0] ==""){       // if input is empty : [0] =="" is passed => so we handle
            Alert alert = new Alert(Alert.AlertType.ERROR,"you need to add lights to run the program");
            alert.show();
            return;
        }
        if (arr.length == numberOflights){
            boolean valid =true;
            boolean needEdit = false;
            for (int i =0; i<= numberOflights-1 ; i++){
                try {
//                    System.out.println(arr[i]);
                    if (Integer.parseInt(arr[i])<=0){
//                        System.out.println("here");
                        throw new NumberFormatException();
                    }
                    else if (Integer.parseInt(arr[i]) > numberOflights){
                        Alert alert = new Alert(Alert.AlertType.WARNING,Integer.parseInt(arr[i])+" is out of Range ... you can edit");
                        alert.show();
                    }
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR,"input must be positive integers only... check again");
                    alert.show();
                    valid=false;
                    break;
                }
            }
            if (valid)
                calculate(arr);
            return;
        }
        else if (arr.length < numberOflights){
            Alert alert = new Alert(Alert.AlertType.ERROR,(numberOflights-arr.length)+ " lights are missing .. add more");
            alert.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR,arr.length-numberOflights+" more lights than expected, check please");
            alert.show();
        }
    }

    private void calculate (String[] lightsArray){             // does the calculations and print the on answer, light order answer , table box
        String[] orderedArray = new String[lightsArray.length];
        for (int i =0 ; i< orderedArray.length;i++ ){
            orderedArray[i] = Integer.toString(i+1);
        }
        answerTextField.clear();;
        tableBox.clear();
        lightsOrderAnswer.clear();
        char [][] array = LCS(lightsArray,orderedArray);
        printAnswer(array , lightsArray, orderedArray);
    }
    public  char [][] LCS (String [] first, String [] second){
        short x = (short) first.length;
        short z = (short) second.length;

        short [][] c = new short [z+1][x+1];
        char [][] answer = new char[z+1][x+1];
        for (int i=0 ; i<=x ; i++){
            c[0][i] =0;
            c[i][0] =0;
        }
        for (int i =0 ; i<x;i++){
            answer[0][i+1] = '0';
            answer[i+1][0] = '0';
        }

        for (int j=1; j<=z ; j++){
            for (int i=1 ; i<=x ; i++){
                if (first[i-1].equals(second[j-1])){
                    c[j][i] = (short) (c[j-1][i-1]+1);
                    answer[j][i] ='D';
                }
                else {
                    if (c[j-1][i] < c[j][i-1]){
                        c[j][i] = c[j][i-1];
                        answer[j][i]='S';
                    }
                    else {
                        c[j][i] = c[j-1][i];
                        answer[j][i] = 'U';
                    }
                }
            }
        }
        for (int i =1 ; i<=x ; i++){
            c[i][0] = (short) i;
        }
        for (int j =1 ;j <=x ; j++){
            c[0][j] = Short.parseShort(first[j-1]);
        }

        for (int i=0 ; i< c.length ; i++){
            for (int j=0 ; j<c[0].length; j++){
                tableBox.appendText(c[i][j] +"\t");
            }
            tableBox.appendText("\n");
        }

        answerTextField.setText(c[x][z]+"");
        return answer;

    }                        // take the out  // the entered one
    private void printAnswer(char[][] array, String [] lightsArray ,String[] orderedOne ){
        String[] ans = new String[lightsArray.length];
        int x = array.length-1;
        int y = array[x].length-1;
        int c =-1;
        while (x>=0 && y>=0){
            if (array[x][y] == 'D'){
                System.out.println(lightsArray[y - 1]);
                ans[++c] = lightsArray[y - 1];
                y--;
                x--;
            }
            else if (array[x][y] == 'S'){
                y--;
            }
            else {
                x--;
            }
        }
        while (c>-1){
            lightsOrderAnswer.appendText(ans[c]+" ");
            c--;
        }
        if (! this.getChildren().contains(showDemonstration)){
            this.getChildren().add(showDemonstration);
        }
        showDemonstration.setOnAction(event -> {
            startDemonstration(lightsArray,orderedOne,ans);
        });
    }

    private void startDemonstration(String[] lightsArray,String[] orderedOne ,String[] ans){
        DemonstrationPagePane pane = new DemonstrationPagePane(lightsArray,orderedOne,ans);
        Scene scene = new Scene(pane,512,612);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Demonstration");
        stage.show();
    }

}
