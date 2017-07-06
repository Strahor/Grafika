/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprites.figures;

import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import main.CoveceNeLjutiSe;
import static main.CoveceNeLjutiSe.game;
import util.Utils;

/**
 *
 * @author Aleksandar
 */
public class Dice extends Group
{
    private int number = 0;
    private Group nodeY = new Group(), nodeZ = new Group();
    private Box body;
    
    private static double rotations[][] = {
        {0, 0},
        {0, -90},
        {90, 0},
        {-90, 0},
        {0, 90},
        {180, 0}
    };
    
    public Dice()
    {
        double width = 1.5 * Utils.width;
        body = new Box(width, width, width);
        body.setMaterial(new PhongMaterial(Color.WHITESMOKE));
        nodeY.getChildren().add(body);
        
        Cylinder dot;
        
        //One-side
        dot = createDot(width / 10);
        dot.setTranslateY(-width / 2);
        nodeY.getChildren().add(dot);
        
        //Two-side
        dot = createDot(width / 10);
        dot.getTransforms().setAll(new Rotate(90, Rotate.Z_AXIS), new Translate(-width / 3, -width / 2, width / 3));
        nodeY.getChildren().add(dot);
        
        dot = createDot(width / 10);
        dot.getTransforms().setAll(new Rotate(90, Rotate.Z_AXIS), new Translate(width / 3, -width / 2, -width / 3));
        nodeY.getChildren().add(dot);
        
        //Three-side
        dot = createDot(width / 10);
        dot.getTransforms().setAll(new Rotate(-90, Rotate.X_AXIS), new Translate(-width / 3, -width / 2, width / 3));
        nodeY.getChildren().add(dot);
        
        dot = createDot(width / 10);
        dot.getTransforms().setAll(new Rotate(-90, Rotate.X_AXIS), new Translate(width / 3, -width / 2, -width / 3));
        nodeY.getChildren().add(dot);
        
        dot = createDot(width / 10);
        dot.getTransforms().setAll(new Rotate(-90, Rotate.X_AXIS), new Translate(0, -width / 2, 0));
        nodeY.getChildren().add(dot);
        
        //Four-side
        dot = createDot(width / 10);
        dot.getTransforms().setAll(new Rotate(90, Rotate.X_AXIS), new Translate(width / 3, -width / 2, width / 3));
        nodeY.getChildren().add(dot);
        
        dot = createDot(width / 10);
        dot.getTransforms().setAll(new Rotate(90, Rotate.X_AXIS), new Translate(width / 3, -width / 2, -width / 3));
        nodeY.getChildren().add(dot);
        
        dot = createDot(width / 10);
        dot.getTransforms().setAll(new Rotate(90, Rotate.X_AXIS), new Translate(-width / 3, -width / 2, width / 3));
        nodeY.getChildren().add(dot);
        
        dot = createDot(width / 10);
        dot.getTransforms().setAll(new Rotate(90, Rotate.X_AXIS), new Translate(-width / 3, -width / 2, -width / 3));
        nodeY.getChildren().add(dot);
        
        //five-side
        dot = createDot(width / 10);
        dot.getTransforms().setAll(new Rotate(-90, Rotate.Z_AXIS), new Translate(0, -width / 2, 0));
        nodeY.getChildren().add(dot);
        
        dot = createDot(width / 10);
        dot.getTransforms().setAll(new Rotate(-90, Rotate.Z_AXIS), new Translate(width / 3, -width / 2, width / 3));
        nodeY.getChildren().add(dot);
        
        dot = createDot(width / 10);
        dot.getTransforms().setAll(new Rotate(-90, Rotate.Z_AXIS), new Translate(width / 3, -width / 2, -width / 3));
        nodeY.getChildren().add(dot);
        
        dot = createDot(width / 10);
        dot.getTransforms().setAll(new Rotate(-90, Rotate.Z_AXIS), new Translate(-width / 3, -width / 2, width / 3));
        nodeY.getChildren().add(dot);
        
        dot = createDot(width / 10);
        dot.getTransforms().setAll(new Rotate(-90, Rotate.Z_AXIS), new Translate(-width / 3, -width / 2, -width / 3));
        nodeY.getChildren().add(dot);
        
        //Six-side
        dot = createDot(width / 10);
        dot.getTransforms().setAll(new Rotate(180, Rotate.X_AXIS), new Translate(width / 3, -width / 2, width / 3));
        nodeY.getChildren().add(dot);
        
        dot = createDot(width / 10);
        dot.getTransforms().setAll(new Rotate(180, Rotate.X_AXIS), new Translate(width / 3, -width / 2, -width / 3));
        nodeY.getChildren().add(dot);
        
        dot = createDot(width / 10);
        dot.getTransforms().setAll(new Rotate(180, Rotate.X_AXIS), new Translate(-width / 3, -width / 2, width / 3));
        nodeY.getChildren().add(dot);
        
        dot = createDot(width / 10);
        dot.getTransforms().setAll(new Rotate(180, Rotate.X_AXIS), new Translate(-width / 3, -width / 2, -width / 3));
        nodeY.getChildren().add(dot);
        
        dot = createDot(width / 10);
        dot.getTransforms().setAll(new Rotate(180, Rotate.X_AXIS), new Translate(-width / 3, -width / 2, 0));
        nodeY.getChildren().add(dot);
        
        dot = createDot(width / 10);
        dot.getTransforms().setAll(new Rotate(180, Rotate.X_AXIS), new Translate(width / 3, -width / 2, 0));
        nodeY.getChildren().add(dot);
        
        nodeZ.getChildren().add(nodeY);
        getChildren().add(nodeZ);
        
        setTranslateY(-3 - width / 2);
        
        this.setOnMouseClicked(e -> onMouseClicked(e));
    }
    
    private Cylinder createDot(double width)
    {
        Cylinder dot = new Cylinder(width, 3);
        dot.setMaterial(new PhongMaterial(new Color(0.2, 0.2, 0.2, 1)));
        return dot;
    }
    
    public void roll()
    {
        int newNumber = (int)(Math.random() * 100) % 6;
        RotateTransition rotateX = new RotateTransition(Duration.seconds(1.5), this);
        rotateX.setAxis(Rotate.X_AXIS);
        rotateX.setFromAngle(rotations[number][0]);
        rotateX.setToAngle(rotations[newNumber][0] + 720 * (1 - 2 * (int)Math.random()));
        rotateX.setInterpolator(Interpolator.EASE_OUT);
        
        
        RotateTransition rotateZ = new RotateTransition(Duration.seconds(1.5), nodeZ);
        rotateZ.setAxis(Rotate.Z_AXIS);
        rotateZ.setFromAngle(rotations[number][1]);
        rotateZ.setToAngle(rotations[newNumber][1] + 720 * (1 - 2 * (int)Math.random()));
        rotateZ.setInterpolator(Interpolator.EASE_OUT);
        
        RotateTransition rotateY = new RotateTransition(Duration.seconds(1.5), nodeY);
        rotateY.setAxis(Rotate.Y_AXIS);
        rotateY.setByAngle(1080 * (1 - 2 * (int)Math.random()));
        rotateY.setInterpolator(Interpolator.EASE_OUT);
        
        ParallelTransition animation = new ParallelTransition(rotateX, rotateY, rotateZ);
        animation.setOnFinished(e -> game.onRollFinished());
        animation.play();
        
        
        number = newNumber;
    }
    
    private void onMouseClicked(MouseEvent e)
    {
        if (e.getButton() == MouseButton.PRIMARY && !Utils.rolled)
        {
            Utils.rolled = true;
            roll();
        }
    }
    
    public int getNumber()
    {
        return number + 1;
    }
}
