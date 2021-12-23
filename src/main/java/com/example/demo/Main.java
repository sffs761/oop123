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
import javafx.scene.shape.Rectangle;
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
    public static Portal portal = new Portal();
    public static List<SpeedItem> speedItems = new ArrayList<>();
    public static List<FlameItem> flameItems = new ArrayList<>();
    public static List<BombItem> bombItems = new ArrayList<>();

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

    public void preRender() {
        for (Wall wall : walls) {
            wall.render();
        }

        for (Grass grass : grasses) {
            grass.render();
        }
    }

    public void render() {
        ArrayList<Integer> existedEntityIndexes = new ArrayList<>();

        int k = (int) (Math.random() * grasses.size());
        while (k == 0 || k == 1 || k == SCALE - 2) {
            k = (int) (Math.random() * grasses.size());
        };
        portal.setFrame(new Rectangle(0, 0, 0, 0));
        portal.setX(grasses.get(k).getX());
        portal.setY(grasses.get(k).getY());
        portal.render();
        Brick portalBrick = new Brick(grasses.get(k).getX(), grasses.get(k).getY());
        bricks.add(portalBrick);
        portalBrick.render();
        existedEntityIndexes.add(k);

        for (int i = 0; i < 49; i ++) {
            int j = (int) (Math.random() * grasses.size());
            while (j == 0 || j == 1 || j == SCALE - 2 || existedEntityIndexes.contains(j)) {
                j = (int) (Math.random() * grasses.size());
            };
            Brick newBrick = new Brick(grasses.get(j).getX(), grasses.get(j).getY());
            bricks.add(newBrick);
            newBrick.render();
            existedEntityIndexes.add(j);
        }
        if (player.getFrame() == null) {
            player.setFrame(new Rectangle(0, 0, 0, 0));
            if (player.isDead()) {
                player.setDead(false);
                player.setStep(0);
                player.loadImage("player.png");
            }
        }
        player.setX(SCALE);
        player.setY(SCALE);
        player.render();
        for (int i = 0; i < 6; i++) {
            int j = (int) (Math.random() * grasses.size());
            while (j == 0 || j == 1 || j == SCALE - 2 || existedEntityIndexes.contains(j)) {
                j = (int) (Math.random() * grasses.size());
            };
            Balloom newBalloom = new Balloom(grasses.get(j).getX(), grasses.get(j).getY());
            newBalloom.render();
            enemies.add(newBalloom);
            existedEntityIndexes.add(j);
        }

    }

    public void removeRender() {
        player.remove();
        portal.remove();
        while (!bricks.isEmpty()) {
            bricks.get(bricks.size() - 1).remove();
            bricks.remove(bricks.size() - 1);
        }
        while (!enemies.isEmpty()) {
            ((Balloom) enemies.get(enemies.size() - 1)).stopAnimation();
            enemies.get(enemies.size() - 1).remove();
            enemies.remove(enemies.size() - 1);
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
                        i--;
                    }
                }
                for (int i = 0; i < bricks.size(); i++) {
                    if (Collision.isCollision(flame, bricks.get(i))) {
                        bricks.get(i).destroy();
                        bricks.remove(i);
                        i--;
                    }
                }
                if (Collision.isCollision(flame, player)) {
                    player.setDead(true);
                    player.dead();
                }
                if (Collision.isCollision(flame, portal)) {
                    for (int i = 0; i < 6; i++) {
                        Balloom newBalloom = new Balloom(portal.getX(), portal.getY());
                        newBalloom.render();
                        enemies.add(newBalloom);
                    }
                }
                for (int i = 0; i < speedItems.size(); i++) {
                    if (Collision.isCollision(flame, speedItems.get(i))) {
                        speedItems.get(i).remove();
                        speedItems.remove(i);
                        i--;
                    }
                }
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

            if (enemies.isEmpty()) {
                if (Collision.isCollision(player, portal)) {
                    System.out.println("OK");
                }
            }



            for (int i = 0; i < speedItems.size(); i++) {
                if (player.isRendered()) {
                    if (Collision.isCollision(player, speedItems.get(i))) {
                        player.increaseSpeed();
                        speedItems.get(i).remove();
                        speedItems.remove(i);
                        i --;
                    }
                }
            }

            for (int i = 0; i < flameItems.size(); i++) {
                if (player.isRendered()) {
                    if (Collision.isCollision(player, flameItems.get(i))) {
                        Bomb.increaseRadius();
                        flameItems.get(i).remove();
                        flameItems.remove(i);
                        i--;
                    }
                }
            }

            for (int i = 0; i < bombItems.size(); i++) {
                if (player.isRendered()) {
                    if (Collision.isCollision(player, bombItems.get(i))) {
                        Bomb.increaseMaxBombs();
                        bombItems.get(i).remove();
                        bombItems.remove(i);
                        i--;
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
<<<<<<< HEAD
        readMap("PreRenderedMap.txt");
        preRender();
=======
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
>>>>>>> c6f07b55495fd398a5eb5cb336b83b87fbe6891e
        render();
        gRenderer.requestFocus();
        //primaryStage.setScene(scene);
        primaryStage.show();
        gamePlay.start();
        gRenderer.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.isControlDown()) {
                    if (keyEvent.getCode() == KeyCode.R) {
                        gamePlay.stop();
                        removeRender();
                        render();
                        gamePlay.start();
                    }
                }

                if (player.isRendered() && !player.isDead()) {
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