package com.example.demo;

public class Wall extends Entity{
    public Wall() {
        super();
        loadImage("wall.png");
    }

    public Wall(int x, int y) {
        super();
        loadImage("wall.png");
        setX(x);
        setY(y);
    }

}