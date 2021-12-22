package com.example.demo;

import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.List;

public class Bomber extends Entity {
    private double speed;
    private int step = 0;
    private boolean isDead = false;

    public Bomber() {
        super();
        loadImage("player.png");
        speed = 0.5;
    }

    private void placeBomb() {
        Bomb bomb = new Bomb();
        double x = getX() % Main.SCALE < Main.SCALE / 2 ? getX() / Main.SCALE * Main.SCALE
                : (getX() / Main.SCALE + 1) * Main.SCALE;
        double y = getY() % Main.SCALE < Main.SCALE / 2 ? getY() / Main.SCALE * Main.SCALE
                : (getY() / Main.SCALE + 1) * Main.SCALE;
        bomb.setX((int) x);
        bomb.setY((int) y);
        Main.bombList.add(bomb);
        bomb.render();
        bomb.explode();
    }

    public void handleEvent(KeyEvent event) {
        int x = getX();
        int y = getY();
        boolean collisionPrecheck = false;
        for (Bomb bomb : Main.bombList) {
            if (Collision.isCollision(this, bomb)) {
                collisionPrecheck = true;
                break;
            }
        }
        if (event.getCode() == KeyCode.UP) {
            if (frame.getY() > 0) {
                frame.setY(frame.getY() - speed * Main.SCALE);
            }
            loadImage("player_up_" + step + ".png");
        } else if (event.getCode() == KeyCode.DOWN) {
            if (frame.getY() + frame.getHeight() < Main.SCREEN_HEIGHT) {
                frame.setY(frame.getY() + speed * Main.SCALE);
            }
            loadImage("player_down_" + step + ".png");
        } else if (event.getCode() == KeyCode.LEFT) {
            if (frame.getX() > 0) {
                frame.setX(frame.getX() - speed * Main.SCALE);
            }
            loadImage("player_left_" + step + ".png");
        } else if (event.getCode() == KeyCode.RIGHT) {
            if (frame.getX() + frame.getWidth() < Main.SCREEN_WIDTH) {
                frame.setX(frame.getX() + speed * Main.SCALE);
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
                if (!collisionPrecheck) {
                    setX(x);
                    setY(y);
                }
            }
        }
        update();
    }

    public void stepDead() {
        loadImage("player_dead" + ((int) step / 30) + ".png");
        step++;
        update();
    }

    AnimationTimer dead = new AnimationTimer() {
        @Override
        public void handle(long now) {
            if (step < 90) {
                stepDead();
            }
            if (step == 90) {
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

    public double getSpeed() {
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
