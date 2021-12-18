package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

public abstract class Entity {
    protected Rectangle frame;
    protected Image texture;
    protected ImageView imageView;

    public Entity() {
        frame = new Rectangle(0, 0, 0, 0);
        texture = null;
    }

    public void loadImage(String filePath) {
        try {
            texture = new Image(Objects.requireNonNull(
                    this.getClass().getResource(filePath)).toExternalForm(),
                    Main.SCALE, Main.SCALE, true, true);
            frame.setWidth(texture.getWidth());
            frame.setHeight(texture.getHeight());
        } catch (Exception e) {
            System.out.println("Fatal Error!!!");
            System.out.println("Can't load: " + filePath);
        }
    }

    public void render() {
        imageView = new ImageView(texture);
        imageView.setX(frame.getX());
        imageView.setY(frame.getY());
        Main.gRenderer.getChildren().add(imageView);
    }

    public void update() {
        imageView.setX(frame.getX());
        imageView.setY(frame.getY());
        imageView.setImage(texture);
    }

    public void remove() {
        imageView.setImage(null);
    }


    public Rectangle getFrame() {
        return frame;
    }

    public int getX() {
        return (int) frame.getX();
    }

    public void setX(int x) {
        frame.setX(x);
    }

    public int getY() {
        return (int) frame.getY();
    }

    public void setY(int y) {
        frame.setY(y);
    }

    public int getHeight() {
        return (int) frame.getHeight();
    }

    public void setHeight(int height) {
        frame.setHeight(height);
    }

    public int getWidth() {
        return (int) frame.getWidth();
    }

    public void setWidth(int width) {
        frame.setWidth(width);
    }
}
