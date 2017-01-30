package com.company;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application
{

    // wspolrzedne kulki
    double polozenieX;
    double polozenieY;
    double zmianaX = 6;
    double zmianaY = 6;

    // rozmiar okna
    int wysokosc = 800;
    int szerokosc = 600;

    //rozmiary bloczków
    int batWysokosc = 20;
    int batSzerokosc = 150;

    //współrzędne losowe kulki
    double randomBall_X = (double)(Math.random()*400);
    double randomBall_Y = (double)(Math.random()*400);

    //współrzędne losowe przeszkody
    double randomTrap_1_X = (double)(Math.random()*300);
    double randomTrap_1_Y = (double)(Math.random()*600);

    // promień kulki
    double radiusBall = 10;

    private enum UserAction {NONE, LEFT, RIGHT, A, D}
    private UserAction action = UserAction.NONE;

    private IntegerProperty scoreUp = new SimpleIntegerProperty();
    private IntegerProperty scoreDown = new SimpleIntegerProperty();
    private IntegerProperty timer = new SimpleIntegerProperty();




    public static void main(String[] args)
    {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        MusicPlayer musicPlayer = new MusicPlayer();

        primaryStage.setTitle("Pong"); // wyświetla tytuł na górnej belce
        Scene scene = new Scene(content(), szerokosc + 200, wysokosc); // definiuje scenę o określonym rozmiarze
        scene.setFill(Color.BLACK); // kolor tła
        primaryStage.setScene(scene); // wyświetla scenę
        primaryStage.show();  // wyświetla okno


        scene.setOnKeyPressed(event ->
        {

            switch (event.getCode())
            {
                case LEFT:
                    action = UserAction.LEFT;
                    break;
                case RIGHT:
                    action = UserAction.RIGHT;
                    break;
                case A:
                    action = UserAction.A;
                    break;
                case D:
                    action = UserAction.D;
                    break;
            }

        });

        scene.setOnKeyReleased(event ->
        {
            switch (event.getCode())
            {
                case LEFT:
                    action = UserAction.NONE;
                    break;
                case RIGHT:
                    action = UserAction.NONE;
                    break;
                case A:
                    action = UserAction.NONE;
                    break;
                case D:
                    action = UserAction.NONE;
                    break;
            }
        });
    }


    private Parent content()
    {

        Pane pane = new Pane();


        // kulka
        Circle ball = new Circle(randomBall_X, randomBall_Y, radiusBall); // deklaracja kulki: X, Y, wielkość
        ball.setFill(Color.WHITE); // kolor kulki

        // bloczek dolny
        Rectangle batDown = new Rectangle(batSzerokosc, batWysokosc);
        batDown.setTranslateX(szerokosc/2); // współrzędne bloczka dolnego
        batDown.setTranslateY(wysokosc-40);
        batDown.setFill(Color.RED);

        //bloczek górny
        Rectangle batUp = new Rectangle(batSzerokosc, batWysokosc);
        batUp.setTranslateX(szerokosc/2); //współrzędne bloczka górnego
        batUp.setTranslateY(wysokosc-780);
        batUp.setFill(Color.BLUE);

        //przeskoda 1
        Rectangle trap = new Rectangle(300, 20);
        trap.setTranslateX(randomTrap_1_X); //współrzędne przeszkody
        trap.setTranslateY(randomTrap_1_Y);
        trap.setFill(Color.WHITE);

        // wyświetlanie linii poziomej i kropki
        Line linia = new Line(0, 400,600, 400);
        linia.setStroke(Color.WHITE);
        Line linia2 = new Line(szerokosc, 0, szerokosc, wysokosc);
        linia2.setStroke(Color.WHITE);
        Circle point = new Circle(szerokosc/2, wysokosc/2, 5);
        point.setFill(Color.WHITE);

        //punktacja górna
        Text textScoreUp = new Text();
        textScoreUp.setTranslateX(10);
        textScoreUp.setTranslateY(30);
        textScoreUp.setFont(Font.font(20));
        textScoreUp.setFill(Color.WHITE);
        textScoreUp.textProperty().bind(scoreUp.asString("Score: [%d]"));

        //punktacja dolna
        Text textScoreDown = new Text();
        textScoreDown.setTranslateX(10);
        textScoreDown.setTranslateY(780);
        textScoreDown.setFont(Font.font(20));
        textScoreDown.setFill(Color.WHITE);
        textScoreDown.textProperty().bind(scoreDown.asString("Score: [%d]"));

        //wyświetlenie zagara
        Text textTimer = new Text();
        textTimer.setTranslateX(650);
        textTimer.setTranslateY(400);
        textTimer.setFont(Font.font(50));
        textTimer.setFill(Color.WHITE);
        textTimer.textProperty().bind(timer.asString("[%d]"));

        //górny opis sterowania
        Text controlsUp = new Text("[A] LEFT  |  RIGHT [D]");
        controlsUp.setTranslateX(620);
        controlsUp.setTranslateY(30);
        controlsUp.setFont(Font.font(16));
        controlsUp.setFill(Color.WHITE);

        //dolnt opis sterowania
        Text controlsDown = new Text("[<--] LEFT  |  RIGHT [-->]");
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


        //animacja i sterowanie
        AnimationTimer animationBall = new AnimationTimer()
        {
            @Override
            public void handle(long now)
            {
                // animacja kulki

                //odbijanie od ścian
                if (ball.getTranslateX() >= (szerokosc - randomBall_X) - radiusBall || ball.getTranslateX() <= -randomBall_X + radiusBall )
                {
                    zmianaX = -zmianaX;
                }

                //odbijanie od paletek
                //górna
                if ((ball.getTranslateY() <= (batUp.getTranslateY() + 30) - randomBall_Y ) && (ball.getTranslateY() >= (batUp.getTranslateY() + 10 - randomBall_Y)))
                {
                    if((ball.getTranslateX() <= batUp.getTranslateX() - randomBall_X + batUp.getWidth()) && (ball.getTranslateX() >= batUp.getTranslateX() - randomBall_X)) {

                        zmianaY = -zmianaY;
                    }
                }
                //dolna
                if ((ball.getTranslateY() >= (batDown.getTranslateY() - randomBall_Y) - radiusBall) && (ball.getTranslateY() <= (batDown.getTranslateY() + 10  - randomBall_Y) ))
                {
                    if((ball.getTranslateX() <= batDown.getTranslateX() - randomBall_X + batDown.getWidth()) && (ball.getTranslateX() >= batDown.getTranslateX() - randomBall_X)) {

                        zmianaY = -zmianaY;
                    }
                }
                /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                //odbijanie od przeszkody 1
                if ((ball.getTranslateY() <= (trap.getTranslateY() + 30) - randomBall_Y ) && (ball.getTranslateY() >= (trap.getTranslateY() + 10 - randomBall_Y)))
                {
                    if((ball.getTranslateX() <= trap.getTranslateX() - randomBall_X + trap.getWidth()) && (ball.getTranslateX() >= trap.getTranslateX() - randomBall_X)) {

                        zmianaY = -zmianaY;
                    }
                }
                if ((ball.getTranslateY() >= (trap.getTranslateY() - randomBall_Y) - radiusBall) && (ball.getTranslateY() <= (trap.getTranslateY() +10  - randomBall_Y) ))
                {
                    if((ball.getTranslateX() <= trap.getTranslateX() - randomBall_X + trap.getWidth()) && (ball.getTranslateX() >= trap.getTranslateX() - randomBall_X)) {

                        zmianaY = -zmianaY;
                    }
                }
                ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


                //punktacja
                if (ball.getTranslateY() >= (wysokosc - randomBall_Y) - radiusBall && ball.getTranslateY() <= (wysokosc - randomBall_Y) - radiusBall + 5)
                {
                    scoreUp.set(scoreUp.get() + 1);

                    polozenieX = 100;
                    ball.setTranslateX(polozenieX);
                    polozenieY = 100;
                    ball.setTranslateY(polozenieY);

                }
                if (ball.getTranslateY() <= -randomBall_Y + radiusBall && ball.getTranslateY() >= (-randomBall_Y + radiusBall) - 5 )
                {
                    scoreDown.set(scoreDown.get() + 1);

                    polozenieX = 100;
                    ball.setTranslateX(polozenieX);
                    polozenieY = 100;
                    ball.setTranslateY(polozenieY);
                }
                /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                //zmiana położenia X kulki
                polozenieX = polozenieX + zmianaX;
                ball.setTranslateX(polozenieX);
                //zmiana położenia Y kulki
                polozenieY = polozenieY + zmianaY;
                ball.setTranslateY(polozenieY);


                timer.set(timer.get() - 1);


            }

        };

        //animacja i sterowanie bloczkami
        AnimationTimer animationBatt = new AnimationTimer()
        {
            @Override
            public void handle(long now)
            {

                switch (action)
                {
                    case LEFT:
                        batDown.setTranslateX(batDown.getTranslateX() - 10);
                        if (batDown.getTranslateX() <= 0)
                        {
                            action = UserAction.NONE;
                        }
                        break;
                    case RIGHT:
                        batDown.setTranslateX(batDown.getTranslateX() + 10);
                        if (batDown.getTranslateX() >= szerokosc - batDown.getWidth())
                        {
                            action = UserAction.NONE;
                        }
                        break;
                    case A:
                        batUp.setTranslateX(batUp.getTranslateX() - 10);
                        if (batUp.getTranslateX() <= 0)
                        {
                            action = UserAction.NONE;
                        }
                        break;
                    case D:
                        batUp.setTranslateX(batUp.getTranslateX() + 10);
                        if (batUp.getTranslateX() >= szerokosc - batUp.getWidth())
                        {
                            action = UserAction.NONE;
                        }
                        break;

                }
            }
        };

        animationBall.start();
        animationBatt.start();

        return pane;
    }
}
