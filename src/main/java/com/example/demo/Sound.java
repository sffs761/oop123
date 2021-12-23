package com.example.demo;

import javax.sound.sampled.*;
import java.io.*;

public class Sound {
    /**
     * Constructor.
     */

    Sound() {}

    /**
     * Play.
     */

    public void Play(String name) throws UnsupportedAudioFileException,
            IOException, LineUnavailableException {
        String path =  "E:\\Repo\\demo\\src\\main\\resources\\com\\example\\Sound\\" + name + ".wav";
        File file = new File(path);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();
    }

    /**
     * Loop.
     */

    public void Loop(String name) throws UnsupportedAudioFileException,
            IOException, LineUnavailableException {
        String path = "E:\\Repo\\demo\\src\\main\\resources\\com\\example\\Sound\\" + name + ".wav";
        File file = new File(path);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

}

