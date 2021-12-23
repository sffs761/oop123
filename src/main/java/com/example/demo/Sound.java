//package com.example.demo.Sound;
//
//import javax.sound.sampled.*;
//import java.io.*;
//import java.util.*;
//
//public class Sound {
//    /**
//     * Constructor.
//     */
//
//    Sound() {}
//
//    /**
//     * Play.
//     */
//
//    public void Play(String name) throws UnsupportedAudioFileException,
//            IOException, LineUnavailableException {
//        String path =  "path"; // đường dẫn file wav
//        File file = new File(path);
//        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
//        Clip clip = AudioSystem.getClip();
//        clip.open(audioStream);
//        clip.start();
//    }
//
//    /**
//     * Loop.
//     */
//
//    public void Loop(String name) throws UnsupportedAudioFileException,
//            IOException, LineUnavailableException {
//        String path = "path"; // đường dẫn file wav
//        File file = new File(path);
//        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
//        Clip clip = AudioSystem.getClip();
//        clip.open(audioStream);
//        clip.loop(Clip.LOOP_CONTINUOUSLY);
//    }
//
//    /**
//     * demoSound
//     */
//    public static void main (String[] args) throws UnsupportedAudioFileException,
//            IOException, LineUnavailableException {
//
//        Scanner scanner = new Scanner(System.in);
//
//        File file = new File("01_Title Screen.wav");
//        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
//        Clip clip = AudioSystem.getClip();
//        clip.open(audioStream);
//
//        String response = "";
//        while(!response.equals("Q")) {
//            System.out.println("P = play, S = Stop, R = Reset, Q = Quit");
//            System.out.print("Enter your choice: ");
//
//            response = scanner.next();
//            response = response.toUpperCase();
//
//            switch(response) {
//                case ("P"):
//                    clip.start();
//                    break;
//                case ("S"): clip.stop();
//                    break;
//                case ("R"): clip.setMicrosecondPosition(0);
//                    break;
//                case ("Q"): clip.close();
//                    break;
//                default: System.out.println("Not a valid response");
//            }
//        }
//        System.out.println("Bye!");
//    }
//}

