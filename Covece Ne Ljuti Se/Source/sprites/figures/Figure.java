/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprites.figures;

import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import static main.CoveceNeLjutiSe.game;
import util.Utils;

/**
 *
 * @author Aleksandar
 */
public abstract class Figure extends Group
{
    private boolean marked = false;
    protected double height;
    public int index;
    
    public abstract void changeColours(Color c);
    
    public void mark()
    {
        marked = true;
    }
    
    public void unMark()
    {
        marked = false;
    }
    
    public void move(double x, double z, boolean notify)
    {
        //Moving to the X and Z coordinates
        TranslateTransition horizontal = new TranslateTransition(Duration.seconds(1), this);
        horizontal.setToX(x);
        horizontal.setToZ(z);
        horizontal.setInterpolator(Interpolator.LINEAR);
        
        //Moving up and down
        TranslateTransition up = new TranslateTransition(Duration.seconds(0.5), this);
        up.setToY(-Utils.width - 20);
        up.setInterpolator(Interpolator.EASE_OUT);
        TranslateTransition down = new TranslateTransition(Duration.seconds(0.5), this);
        down.setToY(-3);
        down.setInterpolator(Interpolator.EASE_IN);
        SequentialTransition vertical = new SequentialTransition(up, down);
        
        //Merge into one animation
        ParallelTransition animation = new ParallelTransition(horizontal, vertical);
        if (notify)
            animation.setOnFinished(e -> game.onFigureMoved());
        animation.play();
    }
    
    protected void onClicked(MouseEvent e)
    {
        if (e.getButton() == MouseButton.PRIMARY && marked)
            game.moveFigure(index);
    }
}
