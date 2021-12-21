package com.example.demo;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.*;

public class Main extends Application {
    public static void main(String[ ] args) {
        launch(args);
    }

    //constant attitudes
    public static final int WIDTH = 20;
    public static final int HEIGHT = 15;
    public static final int SCREEN_WIDTH = 320;
    public static final int SCREEN_HEIGHT = 240;
    public static final int SCALE = 16;
    //main pane
    public static AnchorPane gRenderer = new AnchorPane();
    //list of entities
    public static List<Wall> walls = new ArrayList<>();
    public static Bomber player = new Bomber();
    public static List<Enemy> enemies = new ArrayList<>();
    public static List<Brick> bricks = new ArrayList<>();
    public static List<Bomb> bombList = new ArrayList<>();
    public static ArrayList<Flame> flames = new ArrayList<>();

    public void readMap(String filePath) {
        try {
            File file = new File(filePath);
            Scanner scan = new Scanner(file);
            for (int i = 0; i < HEIGHT; i++) {
                String s = scan.nextLine();
                for (int j = 0; j < WIDTH; j++) {
                    switch (s.charAt(j)) {
                        case '#':
                            walls.add(new Wall(j * SCALE, i * SCALE));
                            break;
                        case 'p':
                            player.setX(j * SCALE);
                            player.setY(i * SCALE);
                            break;
                        case 'e':
                            Balloom e = new Balloom();
                            e.setX(j * SCALE);
                            e.setY(i * SCALE);
                            enemies.add(e);
                            break;
                        case '*':
                            bricks.add(new Brick(j * SCALE, i * SCALE));
                            break;
                        default:
                            break;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void render() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                Grass g = new Grass();
                g.setX(i * SCALE);
                g.setY(j * SCALE);
                g.render();
            }
        }
        for (Wall wall : walls) {
            wall.render();
        }
        for (Enemy enemy : enemies) {
            enemy.render();
        }
        player.render();
        for (Brick brick : bricks) {
            brick.render();
        }
    }

    AnimationTimer gamePlay = new AnimationTimer() {
        @Override
        public void handle(long now) {
            for (Flame flame : flames) {
                for (int i = 0; i < enemies.size(); i++) {
                    if (Collision.isCollision(flame, enemies.get(i))) {
                        enemies.get(i).dead();
                        enemies.remove(i);
                    }
                }
                for (int i = 0; i < bricks.size(); i++) {
                    if (Collision.isCollision(flame, bricks.get(i))) {
                        bricks.get(i).destroy();
                        bricks.remove(i);
                    }
                }
                if (Collision.isCollision(flame, player)) {
                    player.dead();

                }
//                for (int i = 0; i < bombList.size(); i++) {
//                    if (Collision.isDuplicate(flame, bombList.get(i))) {
//                        bombList.get(i).trigger();
//                    }
//                }
            }
            for (Bomb bomb : bombList) {
                if (Collision.isCollision(player, bomb) && bomb.isWalkAble()) {
                    if (!Collision.isCollision(player, bomb)){
                        bomb.setWalkAble(false);
                    }
                }
            }
        }
    };

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Bomber man");
        Scene scene = new Scene(gRenderer, SCREEN_WIDTH, SCREEN_HEIGHT);
        readMap("level1.txt");
        render();
        gRenderer.requestFocus();
        primaryStage.setScene(scene);
        primaryStage.show();

        gamePlay.start();

        gRenderer.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.D) {
                    //System.out.println(bombList.size());
                }
                player.handleEvent(keyEvent);
            }
        });

        gRenderer.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.UP) {
                    player.loadImage("player_up_0.png");
                } else if (keyEvent.getCode() == KeyCode.DOWN) {
                    player.loadImage("player_down_0.png");
                } else if (keyEvent.getCode() == KeyCode.LEFT) {
                    player.loadImage("player_left_0.png");
                } else if (keyEvent.getCode() == KeyCode.RIGHT) {
                    player.loadImage("player_right_0.png");
                }
                player.update();
            }
        });
    }
}