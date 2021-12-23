package com.example.demo;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.*;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    // Constant Atributes

    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
    public static final int SCALE = 40;
    public static final int SCREEN_WIDTH = SCALE * WIDTH;
    public static final int SCREEN_HEIGHT = SCALE * HEIGHT;

    // Pane

    public static AnchorPane gRenderer = new AnchorPane();

    // list of entities

    public static List<Wall> walls = new ArrayList<>();
    public static List<Grass> grasses = new ArrayList<>();
    public static Bomber player = new Bomber();
    public static List<Character> enemies = new ArrayList<>();
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

//                        case 'p':
//                            player.setX(j * SCALE);
//                            player.setY(i * SCALE);
//                            break;
//                        case 'e':
//                            Balloom e = new Balloom();
//                            e.setX(j * SCALE);
//                            e.setY(i * SCALE);
//                            enemies.add(e);
//                            break;
//                        case '*':
//                            bricks.add(new Brick(j * SCALE, i * SCALE));
//                            break;

                        default:
                            grasses.add(new Grass(j * SCALE, i * SCALE));
                            break;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

//    public boolean containsBrick(ArrayList<Integer> l, int x) {
//        for (Integer i : l) {
//            if (i == x) {
//                return true;
//            }
//        }
//        return false;
//    }

    public void render() {

//        for (int i = 0; i < WIDTH; i++) {
//            for (int j = 0; j < HEIGHT; j++) {
//                Grass g = new Grass();
//                g.setX(i * SCALE);
//                g.setY(j * SCALE);
//                g.render();
//            }
//        }

        for (Wall wall : walls) {
            wall.render();
        }

        for (Grass grass : grasses) {
            grass.render();
        }

        ArrayList<Integer> existedEntityIndexes = new ArrayList<>();

        for (int i = 0; i < 50; i ++) {
            int j = (int) (Math.random() * grasses.size());
            while (((grasses.get(j).getX() == SCALE || grasses.get(j).getX() == 2 * SCALE)
                    && grasses.get(j).getY() == SCALE) || (grasses.get(j).getX() == SCALE
                    && grasses.get(j).getY() == 2 * SCALE) || existedEntityIndexes.contains(j)) {
                j = (int) (Math.random() * grasses.size());
            };
            Brick newBrick = new Brick(grasses.get(j).getX(), grasses.get(j).getY());
            bricks.add(newBrick);
            newBrick.render();
            existedEntityIndexes.add(j);
        }

//        for (Enemy enemy : enemies) {
//            enemy.render();
//        }

        player.setX(SCALE);
        player.setY(SCALE);
        player.render();

//        for (Brick brick : bricks) {
//            brick.render();
//        }

        for (int i = 0; i < 6; i++) {
            int j = (int) (Math.random() * grasses.size());
            while (((grasses.get(j).getX() == SCALE || grasses.get(j).getX() == 2 * SCALE)
                    && grasses.get(j).getY() == SCALE) || (grasses.get(j).getX() == SCALE
                    && grasses.get(j).getY() == 2 * SCALE) || existedEntityIndexes.contains(j)) {
                j = (int) (Math.random() * grasses.size());
            };
            Balloom newBalloom = new Balloom(grasses.get(j).getX(), grasses.get(j).getY());
            newBalloom.render();
            enemies.add(newBalloom);
            existedEntityIndexes.add(j);
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
                    player.setDead(true);
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
                    bomb.disableWalkability();
                }
            }

            if (!player.isDead()) {
                for (Character enemy : enemies) {
                    if (Collision.isCollision(enemy, player)) {
                        player.setDead(true);
                        player.dead();
                    }
                }
            }

        }

    };

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Bomberman");
        Scene mainMenu;
        Scene scene = new Scene(gRenderer, SCREEN_WIDTH, SCREEN_HEIGHT);
        try {
            Parent menu = FXMLLoader.load(this.getClass().getResource("main-menu.fxml"));
            mainMenu = new Scene(menu);
            primaryStage.setScene(mainMenu);
            menu.requestFocus();
            menu.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    primaryStage.setScene(scene);
                    primaryStage.setX((mainMenu.getX() + mainMenu.getWidth() / 2) );

                }
            });
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //Scene scene = new Scene(gRenderer, SCREEN_WIDTH, SCREEN_HEIGHT);



        readMap("level0.txt");
        render();
        gRenderer.requestFocus();
        //primaryStage.setScene(scene);
        primaryStage.show();
        gamePlay.start();
        gRenderer.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {

                //                if (keyEvent.getCode() == KeyCode.D) {
                //                    System.out.println(bombList.size());
                //                }

                if (!player.isDead()) {
                    player.handleEvent(keyEvent);
                }
            }
        });

        gRenderer.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (!player.isDead()) {
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
            }
        });

    }

}