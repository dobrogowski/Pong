package com.company;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class MyScene {

    public MyScene(Scene scene, Stage primaryStage){
        scene.setFill(Color.BLACK); // kolor tła
        primaryStage.setScene(scene); // wyświetla scenę
        primaryStage.show();  // wyświetla okno
    }
    }
