package com.example.demo;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Balloom extends Enemy {
    protected int direction = 0;

    public Balloom() {
        super();
        score = 100;
        loadImage("balloom.png");
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public Balloom(int x, int y) {
        super(x, y);
        score = 100;
        loadImage("balloom.png");
        speed = 0.025;
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void move() {
        switch (direction) {
            case 0:
                if (frame.getY() > 0) {
                    frame.setY(frame.getY() - speed * Main.SCALE);
                }
                break;
            case 1:
                if (frame.getY() + frame.getHeight() < Main.SCREEN_HEIGHT) {
                    frame.setY(frame.getY() + speed * Main.SCALE);
                }
                break;
            case 2:
                if (frame.getX() > 0) {
                    frame.setX(frame.getX() - speed * Main.SCALE);
                }
                break;
            case 3:
                if (frame.getX() + frame.getWidth() < Main.SCREEN_WIDTH) {
                    frame.setX(frame.getX() + speed * Main.SCALE);
                }
                break;
        }
    }

    private boolean checkCollision() {
        for (Wall wall : Main.walls) {
            if (Collision.isCollision(this, wall)) {
                return true;
            }
        }
        for (Brick brick : Main.bricks) {
            if (Collision.isCollision(this, brick)) {
                return true;
            }
        }
        for (Character otherEnemy : Main.enemies) {
            if (otherEnemy != this && Collision.isCollision(this, otherEnemy)) {
                return true;
            }
        }
        for (Bomb bomb : Main.bombList) {
            if (Collision.isCollision(this, bomb)) {
                return true;
            }
        }
        return false;
    }

//    AnimationTimer balloomMove = new AnimationTimer() {
//        @Override
//        public void handle(long now) {
//            int x = getX();
//            int y = getY();
//            move();
//            if (checkCollision()) {
//                setX(x);
//                setY(y);
//                direction = ((int) (Math.random() * 100) + 1) % 4;
//            }
//            update();
//        }
//    };

    public void stepMove() {
        String directionString = "";
        switch (direction) {
            case 0: case 3:
                directionString = "right";
                break;
            case 1: case 2:
                directionString = "left";
        }
        loadImage("balloom_" + directionString + ((int) step / 5 + 1) + ".png");
        step++;
        update();
    }

    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.035), event -> {
        int x = getX();
        int y = getY();
        move();
        stepMove();
        if (step == 15) {
            step = 0;
        }
        if (checkCollision()) {
            setX(x);
            setY(y);
            direction = ((int) (Math.random() * 100) + 1) % 4;
        }
        update();
    }));

    public void stepDead() {
        loadImage("balloom_dead" + ((int) step / 25) + ".png");
        step++;
        update();
    }

    AnimationTimer dead = new AnimationTimer() {
        @Override
        public void handle(long now) {
        if (step < 75) {
            stepDead();
        }
        if (step == 75) {
            remove();
            Main.enemies.remove(this);
            dead.stop();
        }
        }
    };

    @Override
    public void dead() {
        step = 0;
        timeline.stop();
        dead.start();
    }

    public void stopAnimation() {
        timeline.stop();
    }

    public void startAnimation() {
        timeline.play();
    }
}
