/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import camera.BaseCamera;
import camera.FocusedCamera;
import camera.SphericalCamera;
import camera.TopDownCamera;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.AmbientLight;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import sprites.figures.ClassicFigure;
import sprites.figures.Dice;
import sprites.figures.Figure;
import sprites.figures.RoundFigure;
import sprites.figures.SimpleFigure;
import sprites.figures.SquareFigure;
import sprites.table.Arrow;
import sprites.table.Table;
import util.Utils;
import util.Utils.Player;
import util.Utils.cameraMovement;

/**
 *
 * @author Aleksandar
 */



public class CoveceNeLjutiSe extends Application
{
    private Group root3D;
    private Group root2D;
    private Group selection;
    SubScene scene3D;
    
    private Table table;
    private Dice dice;
    
    private Text rolled;
    private Text turn;
    private Text victor;
    
    Button addPlayer, start;
    
    private boolean started = false;
    public int currentPlayer = 0;
    private int rolls = 3;
    
    private cameraMovement moveY = cameraMovement.STATIC, moveX = cameraMovement.STATIC;
    private double cameraAngleY = 0, cameraAngleX = -45;
    
    private BaseCamera cam;
    private SphericalCamera sc;
    private TopDownCamera tc;
    private FocusedCamera fc;
    
    
    public static CoveceNeLjutiSe game;
    
    public void start(Stage primaryStage) throws Exception {
        root3D = new Group();
        
        table = new Table();
        table.getTransforms().setAll(new Rotate(-90, Rotate.X_AXIS), new Translate(0, 0));
        
        dice = new Dice();
        
        sc = new SphericalCamera();
        tc = new TopDownCamera();
        fc = new FocusedCamera();
        cam = sc;
        
        PointLight light = new PointLight(Color.WHITE);
        light.getTransforms().setAll(new Translate(0, -100, 0));
        
        AmbientLight ambient = new AmbientLight(new Color(0.35, 0.35, 0.35, 1));
        
        root3D.getChildren().addAll(table, dice, light, ambient);
        
        
        placeFigures();
        
        changeColour(0);
        
        scene3D = new SubScene(root3D, 800, 800, true, SceneAntialiasing.BALANCED);
        
        scene3D.setCamera(cam);
        
        root2D = createHUD();
        
        Group root = new Group();
        root.getChildren().addAll(scene3D, root2D);
        
        Scene scene = new Scene(root, Color.DARKGRAY);
        
        primaryStage.setScene(scene);
        primaryStage.show();
        
        scene3D.setOnKeyPressed(e -> onKeyPressed(e));
        scene3D.setOnKeyReleased(e -> onKeyReleased(e));
        
        
        new AnimationTimer() {
            long prevTime = -1;
            @Override
            public void handle(long currentNanoTime) {
                
                if (prevTime != -1)
                    update((currentNanoTime - prevTime) / 1000000);
                prevTime = currentNanoTime;
            }
        }.start();
        
        game = this;
    }
    
    private void update(long time_ms)
    {
        cam.update(time_ms);
    }
    
    
    private void onKeyPressed(KeyEvent e)
    {
        switch (e.getCode())
        {
            case LEFT:
                cam.setDirectionLeft();
                break;
            case RIGHT:
                cam.setDirectionRight();
                break;
            case UP:
                cam.setDirectionUp();
                break;
            case DOWN:
                cam.setDirectionDown();
                break;
            case ADD:
                cam.setZoomIn();
                break;
            case SUBTRACT:
                cam.setZoomOut();
                break;
            case F2:
                cam = tc;
                scene3D.setCamera(cam);
                break;
            case F1:
                cam = sc;
                scene3D.setCamera(cam);
                break;    
            case F3:
                cam = fc;
                scene3D.setCamera(cam);
                break;
        }
    }
    
    private void onKeyReleased(KeyEvent e)
    {
        switch (e.getCode())
        {
            case LEFT:
                cam.clearDirectionLeft();
                break;
            case RIGHT:
                cam.clearDirectionRight();
                break;
            case UP:
                cam.clearDirectionUp();
                break;
            case DOWN:
                cam.clearDirectionDown();
                break;
            case ADD:
                cam.clearZoomIn();
                break;
            case SUBTRACT:
                cam.clearZoomOut();
                break;
        }
    }
    
    public void changeFigures(int player)
    {
        root3D.getChildren().removeAll(Utils.players[player].figures);
        for (int i = 0; i < 4; ++i)
        {
            switch(Utils.players[player].figureType)
            {
                case 0:
                    Utils.players[player].figures[i] = new ClassicFigure(10, Utils.playerColours[Utils.players[currentPlayer].colour]);
                    break;
                case 1:
                    Utils.players[player].figures[i] = new RoundFigure(10, Utils.playerColours[Utils.players[currentPlayer].colour]);
                    break;
                case 2:
                    Utils.players[player].figures[i] = new SimpleFigure(10, Utils.playerColours[Utils.players[currentPlayer].colour]);
                    break;
                case 3:
                    Utils.players[player].figures[i] = new SquareFigure(10, Utils.playerColours[Utils.players[currentPlayer].colour]);
                    break;
                default:
                    return;
            }
            Utils.players[player].figures[i].index = i;
            Utils.players[player].figures[i].setTranslateX(Utils.houses[player][i][0]);
            Utils.players[player].figures[i].setTranslateY(-3);
            Utils.players[player].figures[i].setTranslateZ(Utils.houses[player][i][1]);
        }
        root3D.getChildren().addAll(Utils.players[player].figures);
    }
    
    public void placeFigures()
    {
        for (int player = 0; player < 4; ++player)
        {
            root3D.getChildren().removeAll(Utils.players[player].figures);
            for (int i = 0; i < 4; ++i)
            {
                switch(Utils.players[player].figureType)
                {
                    case 0:
                        Utils.players[player].figures[i] = new ClassicFigure(10, Color.DARKGRAY);
                        break;
                    case 1:
                        Utils.players[player].figures[i] = new RoundFigure(10, Color.DARKGRAY);
                        break;
                    case 2:
                        Utils.players[player].figures[i] = new SimpleFigure(10, Color.DARKGRAY);
                        break;
                    case 3:
                        Utils.players[player].figures[i] = new SquareFigure(10, Color.DARKGRAY);
                        break;
                    default:
                        return;
                }
                Utils.players[player].house[i] = i;
                Utils.players[player].figures[i].index = i;
                Utils.players[player].figures[i].setTranslateX(Utils.houses[player][i][0]);
                Utils.players[player].figures[i].setTranslateY(-3);
                Utils.players[player].figures[i].setTranslateZ(Utils.houses[player][i][1]);
            }
            root3D.getChildren().addAll(Utils.players[player].figures);
        }
    }
    
    public void changeColour(int player)
    {
        for (int i = 0; i < 4; ++i)
            Utils.players[player].figures[i].changeColours(Utils.playerColours[Utils.players[player].colour]);
        table.changePlayerColour(player);
    }
    
    public Group createHUD()
    {
        Group hud = new Group();
        
        rolled = new Text(10, 25, "");
        rolled.setFont(Font.font(30));
        rolled.setStrokeWidth(3);
        
        turn = new Text(370, 25, "Player " + (currentPlayer + 1));
        turn.setFont(Font.font(30));
        turn.setStrokeWidth(3);
        turn.setStroke(Utils.playerColours[Utils.players[currentPlayer].colour]);
        
        victor = new Text(350, 50, "");
        
        selection = new Group();
        
        Rectangle backGround = new Rectangle(300, 300, 200, 200);
        backGround.setFill(new Color(0.8, 0.8, 0.8, 0.7));
        
        Text label1 = new Text(305, 320, "Select Colour:");
        Text label2 = new Text(305, 370, "Select Figure:");
        
        Text colourSelection = new Text(310, 340, Utils.colourNames[Utils.players[currentPlayer].colour]);
        Text figureSelection = new Text(310, 390, Utils.figureNames[Utils.players[currentPlayer].figureType]);
        colourSelection.setFont(Font.font(20));
        colourSelection.setStrokeWidth(2);
        colourSelection.setStroke(Utils.playerColours[Utils.players[currentPlayer].colour]);
        figureSelection.setFont(Font.font(20));
        figureSelection.setStrokeWidth(2);
        
        Button nextColour = new Button(">");
        nextColour.setTranslateX(400);
        nextColour.setTranslateY(320);
        nextColour.setFont(Font.font(15));
        nextColour.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Utils.players[currentPlayer].colour = (Utils.players[currentPlayer].colour + 1) % 4;
                while(Player.taken[Utils.players[currentPlayer].colour])
                {
                    Utils.players[currentPlayer].colour = (Utils.players[currentPlayer].colour + 1) % 4;
                }
                colourSelection.setText(Utils.colourNames[Utils.players[currentPlayer].colour]);
                colourSelection.setStroke(Utils.playerColours[Utils.players[currentPlayer].colour]);
                turn.setStroke(Utils.playerColours[Utils.players[currentPlayer].colour]);
                changeColour(currentPlayer);
            }
        });
        
        Button nextFigure = new Button(">");
        nextFigure.setTranslateX(400);
        nextFigure.setTranslateY(370);
        nextFigure.setFont(Font.font(15));
        nextFigure.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Utils.players[currentPlayer].figureType = (Utils.players[currentPlayer].figureType + 1) % 4;
                figureSelection.setText(Utils.figureNames[Utils.players[currentPlayer].figureType]);
                changeFigures(currentPlayer);
            }
        });
        
        start = new Button("Start game");
        start.setTranslateX(310);
        start.setTranslateY(460);
        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                selection.setVisible(false);
                started = true;
                scene3D.requestFocus();
                Utils.players[currentPlayer].playing = true;
                currentPlayer = 0;
                turn.setText("Player " + (currentPlayer + 1));
                turn.setStroke(Utils.playerColours[Utils.players[currentPlayer].colour]);
                table.highlightPlayer(currentPlayer);
            }
        });
        start.setDisable(true);
        
        addPlayer = new Button("Add new player");
        addPlayer.setTranslateX(310);
        addPlayer.setTranslateY(410);
        addPlayer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Player.taken[Utils.players[currentPlayer].colour] = true;
                Utils.players[currentPlayer].playing = true;
                currentPlayer++;
                
                turn.setText("Player " + (currentPlayer + 1));
                
                
                while(Player.taken[Utils.players[currentPlayer].colour])
                {
                    Utils.players[currentPlayer].colour = (Utils.players[currentPlayer].colour + 1) % 4;
                }
                
                turn.setStroke(Utils.playerColours[Utils.players[currentPlayer].colour]);
                
                colourSelection.setText(Utils.colourNames[Utils.players[currentPlayer].colour]);
                colourSelection.setStroke(Utils.playerColours[Utils.players[currentPlayer].colour]);
                figureSelection.setText(Utils.figureNames[Utils.players[currentPlayer].figureType]);
                
                changeColour(currentPlayer);
                if (currentPlayer == 3)
                    addPlayer.setDisable(true);
                if (currentPlayer == 1)
                    start.setDisable(false);
            }
        });
        
        selection.getChildren().addAll(backGround, colourSelection, figureSelection, nextColour, nextFigure, start, addPlayer, label1, label2, victor);
        hud.getChildren().addAll(selection, rolled, turn);
        
        return hud;
    }
    
    public void onRollFinished()
    {
        int roll = dice.getNumber();
        //rolled.setText("" + roll);
        
        if (!Utils.players[currentPlayer].markAvailable(roll))
        {
            //Check if player has more rolls available
            if ((!Utils.players[currentPlayer].getStarted()) && rolls > 1)
            {//Has more rolls
                rolls--;
                Utils.rolled = false;
            }
            else
            {//No more rolls, next player's turn
                rolls = 3;
                table.stopHighlight(currentPlayer);
                currentPlayer = (currentPlayer + 1) % 4;
                while (!Utils.players[currentPlayer].playing)
                    currentPlayer = (currentPlayer + 1) % 4;
                turn.setText("Player " + (currentPlayer + 1));
                turn.setStroke(Utils.playerColours[Utils.players[currentPlayer].colour]);
                Utils.rolled = false;
                table.highlightPlayer(currentPlayer);
            }
        }
    }
    
    public void moveFigure(int index)
    {
        int diceRoll = dice.getNumber();
        
        Player player = Utils.players[currentPlayer];
        
        if (player.positions[index] == -1)
        {
            int playerOnStart = Utils.positions[Utils.start[currentPlayer]];
            if (playerOnStart != -1)
                Utils.players[playerOnStart / 4].moveToHouse(playerOnStart % 4);
            player.moveFromHouse(index);
            player.unMarkAll();
        }
        else
        {
            int destination = player.positions[index] + diceRoll;
            int relative = 0;
            if (destination < Utils.start[currentPlayer])
                relative = destination + 48 - Utils.start[currentPlayer];
            else
                relative = destination - Utils.start[currentPlayer];
            if (relative < 48)
            {
                int playerOnDest = Utils.positions[destination % 48], figureOnDest = Utils.positions[destination % 48] % 4;
                if (playerOnDest >= 0)
                    playerOnDest /= 4;
                if (playerOnDest != -1)
                    Utils.players[playerOnDest].moveToHouse(figureOnDest);
            }
            player.moveToDest(diceRoll, index);
            player.unMarkAll();
        }
    }
    
    public void onFigureMoved()
    {
        if (Utils.players[currentPlayer].isFinished())
        {
            int i;
            for (i = 0; i < 48; ++i)
                Utils.positions[i] = -1;
            for (i = 0; i < 4; ++i)
            {
                if (i != 0)
                    Utils.players[i].playing = false;
                for (int j = 0; j < 4; ++j)
                {
                    Utils.players[i].house[j] = Utils.players[i].positions[j] = -1;
                    Utils.players[i].figureType = 0;
                }
                Player.taken[i] = false;
                Utils.players[i].colour = 0;
            }
            placeFigures();
            root2D.requestFocus();
            selection.setVisible(true);
            currentPlayer = 0;
            turn.setText("Player " + (currentPlayer + 1));
            turn.setStroke(Utils.playerColours[Utils.players[currentPlayer].colour]);
            table.resetFields();
            changeColour(currentPlayer);
            addPlayer.setDisable(false);
            start.setDisable(true);
        }
        if (dice.getNumber() != 6)
        {//Next player's turn
            table.stopHighlight(currentPlayer);
            currentPlayer = (currentPlayer + 1) % 4;
            while (!Utils.players[currentPlayer].playing)
                currentPlayer = (currentPlayer + 1) % 4;
            table.highlightPlayer(currentPlayer);
        }
        Utils.rolled = false;
        turn.setText("Player " + (currentPlayer + 1));
        turn.setStroke(Utils.playerColours[Utils.players[currentPlayer].colour]);
        rolls = 3;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }

    
}
