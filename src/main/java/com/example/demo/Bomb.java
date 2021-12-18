package com.example.demo;

import javafx.animation.AnimationTimer;

import java.util.ArrayList;

public class Bomb extends Entity {
    private int step;
    private static int radius = 1;
    private boolean walkAble = true;

    public boolean isWalkAble() {
        return walkAble;
    }

    public void setWalkAble(boolean walkAble) {
        this.walkAble = walkAble;
    }

    public Bomb() {
        super();
        loadImage("bomb_0.png");
        step = 0;

    }

    public void stepBomb() {
        loadImage("bomb_" + ((int) step / 200) + ".png");
        step++;
        update();
    }

    AnimationTimer timerBomb = new AnimationTimer() {
        @Override
        public void handle(long l) {
            if (step < 600) {
                stepBomb();
            }
            if (step == 600) {
                step = 0;
                Flame verticalFlameT = new Flame("vertical_top_last", getX(), getY() - 16);
                Flame verticalFlameD = new Flame("vertical_down_last", getX(), getY() + 16);
                Flame horizontalFlameL = new Flame("horizontal_left_last", getX() - 16, getY());
                Flame horizontalFlameR = new Flame("horizontal_right_last", getX() + 16, getY());
                Main.flames.add(verticalFlameT);
                Main.flames.add(verticalFlameD);
                Main.flames.add(horizontalFlameL);
                Main.flames.add(horizontalFlameR);
                verticalFlameT.render();
                verticalFlameD.render();
                horizontalFlameL.render();
                horizontalFlameR.render();
                horizontalFlameL.start();
                horizontalFlameR.start();
                verticalFlameT.start();
                verticalFlameD.start();
                timer.start();
                timerBomb.stop();
            }
        }
    };

    public void stepExplode() {
        loadImage("bomb_exploded" + ((int) step / 100) + ".png");
        step++;
        update();
    }

    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            if (step < 300) {
                stepExplode();
            }
            if (step == 300) {
                timer.stop();
                Main.bombList.remove(this);
                remove();
            }
        }
    };

    public void explode() {
        step = 0;
        timerBomb.start();
    }
}
