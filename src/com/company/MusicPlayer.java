package com.company;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;


public class MusicPlayer
{

    public MusicPlayer()
    {
        // muzyka
        String musicFile = "Pong.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }


}
