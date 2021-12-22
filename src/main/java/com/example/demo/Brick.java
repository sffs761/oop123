package com.example.demo;

import javafx.animation.AnimationTimer;

public class Brick extends Entity {
    private int step;

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

    private void stepDestroy() {
        loadImage("brick_exploded" + ((int) step / 25) + ".png");
        step++;
        update();
    }

    AnimationTimer destroy = new AnimationTimer() {
        @Override
        public void handle(long now) {
            if (step < 75) {
                stepDestroy();
            }
            if (step == 75) {
                remove();
                Main.bricks.remove(this);
                destroy.stop();
            }
        }
    };

    public void destroy() {
        step = 0;
        destroy.start();
    }
}
