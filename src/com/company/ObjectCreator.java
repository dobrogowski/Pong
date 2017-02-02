package com.company;


import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class ObjectCreator extends Main {
    
    public ObjectCreator(Pane pane, Circle ball, Rectangle batDown, Rectangle batUp, Rectangle trap, Line linia, Line linia2, Circle point, Text textScoreUp, Text textScoreDown, Text textTimer, Text controlsUp, Text controlsDown) {
        
        ball.setFill(javafx.scene.paint.Color.WHITE); // kolor kulki
        
        batDown.setTranslateX(szerokosc / 2); // współrzędne bloczka dolnego
        batDown.setTranslateY(wysokosc - 40);
        batDown.setFill(Color.RED);
        
        batUp.setTranslateX(szerokosc / 2); //współrzędne bloczka górnego
        batUp.setTranslateY(wysokosc - 780);
        batUp.setFill(Color.BLUE);
        
        trap.setTranslateX(randomTrap_1_X); //współrzędne przeszkody
        trap.setTranslateY(randomTrap_1_Y);
        trap.setFill(Color.WHITE);
        
        linia.setStroke(Color.WHITE);
        
        linia2.setStroke(Color.WHITE);
        
        point.setFill(Color.WHITE);
        
        textScoreUp.setTranslateX(10);
        textScoreUp.setTranslateY(30);
        textScoreUp.setFont(Font.font(20));
        textScoreUp.setFill(Color.WHITE);
        
        textScoreDown.setTranslateX(10);
        textScoreDown.setTranslateY(780);
        textScoreDown.setFont(Font.font(20));
        textScoreDown.setFill(Color.WHITE);
        
        textTimer.setTranslateX(650);
        textTimer.setTranslateY(400);
        textTimer.setFont(Font.font(50));
        textTimer.setFill(Color.WHITE);

        
        controlsUp.setTranslateX(620);
        controlsUp.setTranslateY(30);
        controlsUp.setFont(Font.font(16));
        controlsUp.setFill(Color.WHITE);
        
        controlsDown.setTranslateX(620);
        controlsDown.setTranslateY(770);
        controlsDown.setFont(Font.font(16));
        controlsDown.setFill(Color.WHITE);
        
        // wyświetlenie elementów
        pane.getChildren().add(ball);
        pane.getChildren().add(batUp);
        pane.getChildren().add(batDown);
        pane.getChildren().add(linia);
        pane.getChildren().add(linia2);
        pane.getChildren().add(point);
        pane.getChildren().add(trap);
        pane.getChildren().add(textScoreUp);
        pane.getChildren().add(textScoreDown);
        pane.getChildren().add(textTimer);
        pane.getChildren().add(controlsUp);
        pane.getChildren().add(controlsDown);

    }
}

