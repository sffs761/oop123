package com.example.demo;

import javafx.animation.AnimationTimer;

public class Flame extends Entity {
    private String direction;
    private int step = 0;

    public Flame(String direction, int x, int y) {
        super();
        setX(x);
        setY(y);
        this.direction = direction;
    }

    public void stepBurn() {
        loadImage("explosion_" + direction + ((int) step / 100) + ".png");
        step++;
        update();
    }

    AnimationTimer burn = new AnimationTimer() {
        @Override
        public void handle(long l) {
            if (step < 300) {
                stepBurn();
            }
            if (step == 300) {
                burn.stop();
                remove();
            }
        }
    };

    public void start() {
        step = 0;
        burn.start();
    }
}
