package com.example.demo;

import javafx.animation.AnimationTimer;

import java.util.ArrayList;

public class Bomb extends Entity {
    private int step;
    private static int radius = 2;
    private boolean walkAble = true;
    private ArrayList<Flame> currentFlames = new ArrayList<>();

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
                trigger();
            }
        }
    };

    public void trigger() {
        step = 0;
        for (Flame flame : currentFlames) {
            Main.flames.add(flame);
            flame.render();
            flame.start();
        }
        timer.start();
        timerBomb.stop();
    }

    public void stepExplode() {
        loadImage("bomb_exploded" + ((int) step / 100) + ".png");
        step++;
        update();
    }

    private void removeBomb() {
        Main.bombList.remove(this);
    }

    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            if (step < 300) {
                stepExplode();
            }
            if (step == 300) {
                removeBomb();
                for (Flame flame : currentFlames) {
                    Main.flames.remove(flame);
                }
                remove();
                timer.stop();
            }
        }
    };

    public void explode() {
        step = 0;
        timerBomb.start();
        currentFlames.add(new Flame("mid", getX(), getY()));
        boolean skip = false;
        for (int i = 1; i < radius; i++) {
            for (Wall wall : Main.walls) {
                if (wall.getX() == this.getX() && wall.getY() == this.getY() + 16 * i) {
                    skip = true;
                    break;
                }
            }
            if (skip) {
                break;
            }
            currentFlames.add(new Flame("vertical", getX(), getY() + 16 * i));
            for (Brick brick : Main.bricks) {
                if (brick.getX() == this.getX() && brick.getY() == this.getY() + 16 * i) {
                    skip = true;
                    break;
                }
            }
            if (skip) {
                break;
            }
        }
        if (!skip) {
            for (Wall wall : Main.walls) {
                if (wall.getX() == this.getX() && wall.getY() == this.getY() + 16 * radius) {
                    skip = true;
                    break;
                }
            }
            if (!skip) {
                currentFlames.add(new Flame("vertical_down_last", getX(),getY() + 16 * radius));
            }
        }
        skip = false;
        for (int i = 1; i < radius; i++) {
            for (Wall wall : Main.walls) {
                if (wall.getX() == this.getX() && wall.getY() == this.getY() - 16 * i) {
                    skip = true;
                    break;
                }
            }
            if (skip) {
                break;
            }
            currentFlames.add(new Flame("vertical", getX(), getY() - 16 * i));
            for (Brick brick : Main.bricks) {
                if (brick.getX() == this.getX() && brick.getY() == this.getY() - 16 * i) {
                    skip = true;
                    break;
                }
            }
            if (skip) {
                break;
            }
        }
        if (!skip) {
            for (Wall wall : Main.walls) {
                if (wall.getX() == this.getX() && wall.getY() == this.getY() - 16 * radius) {
                    skip = true;
                    break;
                }
            }
            if (!skip) {
                currentFlames.add(new Flame("vertical_top_last", getX(), getY() - 16 * radius));
            }
        }
        skip = false;
        for (int i = 1; i < radius; i++) {
            for (Wall wall : Main.walls) {
                if (wall.getX() - 16 * i == this.getX() && wall.getY() == this.getY()) {
                    skip = true;
                    break;
                }
            }
            if (skip) {
                break;
            }
            currentFlames.add(new Flame("horizontal", getX() + 16 * i, getY()));
            for (Brick brick : Main.bricks) {
                if (brick.getX() == this.getX() + 16 * i && brick.getY() == this.getY()) {
                    skip = true;
                    break;
                }
            }
            if (skip) {
                break;
            }
        }
        if (!skip) {
            for (Wall wall : Main.walls) {
                if (wall.getX() == this.getX() + 16 * radius && wall.getY() == this.getY()) {
                    skip = true;
                    break;
                }
            }
            if (!skip) {
                currentFlames.add(new Flame("horizontal_right_last", getX() + 16 * radius, getY()));
            }
        }
        skip = false;
        for (int i = 1; i < radius; i++) {
            for (Wall wall : Main.walls) {
                if (wall.getX() + 16 * i == this.getX() && wall.getY() == this.getY()) {
                    skip = true;
                    break;
                }
            }
            if (skip) {
                break;
            }
            currentFlames.add(new Flame("horizontal", getX() - 16 * i, getY()));
            for (Brick brick : Main.bricks) {
                if (brick.getX() == this.getX() - 16 * i && brick.getY() == this.getY()) {
                    skip = true;
                    break;
                }
            }
            if (skip) {
                break;
            }
        }
        if (!skip) {
            for (Wall wall : Main.walls) {
                if (wall.getX() == this.getX() - 16 * radius && wall.getY() == this.getY()) {
                    skip = true;
                    break;
                }
            }
            if (!skip) {
                currentFlames.add(new Flame("horizontal_left_last", getX() - 16 * radius, getY()));
            }
        }
    }
}
