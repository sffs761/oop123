package com.example.demo;

import javafx.animation.AnimationTimer;

public class Balloom extends Enemy {
    private int step = 0;

    public Balloom() {
        super();
        loadImage("balloom.png");
        balloomMove.start();
    }

    public Balloom(int x, int y) {
        super();
        setX(x);
        setY(y);
        loadImage("balloom.png");
        balloomMove.start();
    }

    @Override
    public void move() {
        switch (direction) {
            case 0:
                if (frame.getY() > 0) {
                    frame.setY(frame.getY() - speed);
                }
                break;
            case 1:
                if (frame.getY() + frame.getHeight() < 240) {
                    frame.setY(frame.getY() + speed);
                }
                break;
            case 2:
                if (frame.getX() > 0) {
                    frame.setX(frame.getX() - speed);
                }
                break;
            case 3:
                if (frame.getX() + frame.getWidth() < 320) {
                    frame.setX(frame.getX() + speed);
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
        for (Enemy otherEnemy : Main.enemies) {
            if (otherEnemy != this && Collision.isCollision(this, otherEnemy)) {
                return true;
            }
        }
        for (Brick brick : Main.bricks) {
            if (Collision.isCollision(this, brick)) {
                return true;
            }
        }
        return false;
    }

    AnimationTimer balloomMove = new AnimationTimer() {
        @Override
        public void handle(long now) {
            int x = getX();
            int y = getY();
            move();
            if (checkCollision()) {
                setX(x);
                setY(y);
                direction = ((int) (Math.random() * 100) + 1) % 4;
            }
            update();
        }
    };

    public void stepDead() {
        loadImage("balloom_dead" + ((int) step / 100) + ".png");
        step++;
        update();
    }

    AnimationTimer dead = new AnimationTimer() {
        @Override
        public void handle(long now) {
            if (step < 300) {
                stepDead();
            }
            if (step == 300) {
                remove();
                Main.enemies.remove(this);
                dead.stop();
            }
        }
    };

    @Override
    public void dead() {
        step = 0;
        balloomMove.stop();
        dead.start();
    }
}
