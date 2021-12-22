package com.example.demo;

import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.List;

public class Bomber extends Entity {
    private int speed;
    private int step = 0;
    private boolean isDead = false;

    public Bomber() {
        super();
        loadImage("player.png");
        speed = 4;
    }

    private void placeBomb() {
        Bomb bomb = new Bomb();
        double x = getX() % 16 < 8 ? getX() / 16 * 16 : (getX() / 16 + 1) * 16;
        double y = getY() % 16 < 8 ? getY() / 16 * 16 : (getY() / 16 + 1) * 16;
        bomb.setX((int) x);
        bomb.setY((int) y);
        Main.bombList.add(bomb);
        bomb.render();
        bomb.explode();
    }

    public void handleEvent(KeyEvent event) {
        int x = getX();
        int y = getY();
        if (event.getCode() == KeyCode.UP) {
            if (frame.getY() > 0) {
                frame.setY(frame.getY() - speed);
            }
            loadImage("player_up_" + step + ".png");
        } else if (event.getCode() == KeyCode.DOWN) {
            if (frame.getY() + frame.getHeight() < Main.SCREEN_HEIGHT) {
                frame.setY(frame.getY() + speed);
            }
            loadImage("player_down_" + step + ".png");
        } else if (event.getCode() == KeyCode.LEFT) {
            if (frame.getX() > 0) {
                frame.setX(frame.getX() - speed);
            }
            loadImage("player_left_" + step + ".png");
        } else if (event.getCode() == KeyCode.RIGHT) {
            if (frame.getX() + frame.getWidth() < Main.SCREEN_WIDTH) {
                frame.setX(frame.getX() + speed);
            }
            loadImage("player_right_" + step + ".png");
        }
        step++;
        if (step == 4) {
            step = 0;
        }
        if (event.getCode() == KeyCode.SPACE) {
            placeBomb();
        }
        for (Wall wall : Main.walls) {
            if (Collision.isCollision(this, wall)) {
                setX(x);
                setY(y);
            }
        }
        for (Brick brick : Main.bricks) {
            if (Collision.isCollision(this, brick)) {
                setX(x);
                setY(y);
            }
        }
        for (Bomb bomb : Main.bombList) {
            if (Collision.isCollision(this, bomb) && !bomb.isWalkAble()) {
                setX(x);
                setY(y);
            }
        }
        update();
    }

    public void stepDead() {
        loadImage("player_dead" + ((int) step / 100) + ".png");
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
                Main.gRenderer.getChildren().remove(this);
                dead.stop();
            }
        }
    };

    public void dead() {
        step = 0;
        dead.start();
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

}
