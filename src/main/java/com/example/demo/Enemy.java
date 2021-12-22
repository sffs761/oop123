package com.example.demo;

public abstract class Enemy extends Entity {
    protected double speed;
    protected int step = 0;
    protected int direction = 0;

    public Enemy() {
        super();
        speed = 1;
    }

    public abstract void move();

    public abstract void dead();

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

}
