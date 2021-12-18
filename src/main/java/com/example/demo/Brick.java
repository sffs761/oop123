package com.example.demo;

public class Brick extends Entity {
    public Brick() {
        super();
        loadImage("brick.png");
    }

    public Brick(int x, int y) {
        super();
        loadImage("brick.png");
        setX(x);
        setY(y);
    }
}
