/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprites.figures;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;

/**
 *
 * @author Aleksandar
 */
public class SquareFigure extends Figure
{
    private Cylinder base;
    private Box head, neck;
    public SquareFigure(double radius, Color c)
    {
        height = 4 * radius;
        PhongMaterial mat = new PhongMaterial(c);
        
        base = new Cylinder(radius, radius);
        base.setTranslateY(-0.5 * radius);
        base.setMaterial(mat);
        
        neck = new Box(radius, radius, radius);
        neck.setTranslateY(-1.5 * radius);
        neck.setMaterial(mat);
        
        head = new Box(radius, 2 * radius, radius);
        head.setTranslateY(-3 * radius);
        head.setMaterial(mat);
        
        getChildren().addAll(base, neck, head);
        this.setOnMouseClicked(e -> onClicked(e));
    }

    @Override
    public void changeColours(Color c) {
        PhongMaterial mat = new PhongMaterial(c);
        base.setMaterial(mat);
        neck.setMaterial(mat);
        head.setMaterial(mat);
    }
}
