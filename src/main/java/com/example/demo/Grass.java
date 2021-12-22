package com.example.demo;

public class Grass extends Entity {
    public Grass() {
        super();
        loadImage("grass.png");
    }

    public Grass(int x, int y) {
        super();
        loadImage("grass.png");
        setX(x);
        setY(y);
    }

}
