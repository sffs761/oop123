package com.example.demo;

import javax.sound.sampled.*;
import java.io.*;

public class Sound {
    private String name;

    /**
     * Constructor.
     */

    public Sound() {
    }

    public Sound(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Play.
     */

    public void Play() throws UnsupportedAudioFileException,
            IOException, LineUnavailableException {
//        String path = "E:\\Repo\\demo\\src\\main\\resources\\com\\example\\Sound\\" + name + ".wav";
        File file = new File(String.valueOf(this.getClass().getResource(name + "wav")));
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
//        String path = "E:\\Repo\\demo\\src\\main\\resources\\com\\example\\Sound\\" + name + ".wav";
        File file = new File(String.valueOf(this.getClass().getResource(name + "wav")));
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

}

