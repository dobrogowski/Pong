package com.company;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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

    public enum UserAction {NONE, LEFT, RIGHT, A, D}
    public UserAction action = UserAction.NONE;

    public IntegerProperty scoreUp = new SimpleIntegerProperty();
    public IntegerProperty scoreDown = new SimpleIntegerProperty();
    public long start = System.nanoTime();

    public static void main(String[] args)
    {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        MusicPlayer musicPlayer = new MusicPlayer();

        Scene scene = new Scene(content(), szerokosc + 200, wysokosc); // definiuje scenę o określonym rozmiarze
        scene.setFill(Color.BLACK); // kolor tła
        MyScene myScene = new MyScene(scene,primaryStage);


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
        Circle ball = new Circle(randomBall_X, randomBall_Y, radiusBall); // deklaracja kulki: X, Y, wielkość;

        // bloczek dolny
        Rectangle batDown = new Rectangle(batSzerokosc, batWysokosc);

        //bloczek górny
        Rectangle batUp = new Rectangle(batSzerokosc, batWysokosc);

        //przeskoda 1
        Rectangle trap = new Rectangle(300, 20);

        // wyświetlanie linii poziomej i kropki
        Line linia = new Line(0, 400, 600, 400);
        Line linia2 = new Line(szerokosc, 0, szerokosc, wysokosc);
        Circle point = new Circle(szerokosc / 2, wysokosc / 2, 5);

        //punktacja górna
        Text textScoreUp = new Text();

        //punktacja dolna
        Text textScoreDown = new Text();

        //wyświetlenie zagara
        Text textTimer = new Text();

        //górny opis sterowania
        Text controlsUp = new Text("[A] LEFT  |  RIGHT [D]");

        //dolnt opis sterowania
        Text controlsDown = new Text("[<--] LEFT  |  RIGHT [-->]");

        //animacja i sterowanie
        ObjectCreator objectCreator = new ObjectCreator(pane,ball,batDown,batUp,trap,linia,linia2,point,textScoreUp,textScoreDown,textTimer,controlsUp,controlsDown);
        textScoreUp.textProperty().bind(scoreUp.asString("Score: [%d]"));
        textScoreDown.textProperty().bind(scoreDown.asString("Score: [%d]"));
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

                //licznik czasu gry
                textTimer.setText(String.valueOf((System.nanoTime() - start)/1000000000));


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
