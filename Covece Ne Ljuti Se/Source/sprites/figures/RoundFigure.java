/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprites.figures;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;

/**
 *
 * @author Aleksandar
 */
public class RoundFigure extends Figure
{
    private Cylinder base, body;
    private Sphere head;
    
    public RoundFigure(double radius, Color c)
    {
        height = 4 * radius;
        PhongMaterial mat = new PhongMaterial(c);
        
        base = new Cylinder(radius, radius / 5);
        base.setMaterial(mat);
        base.setTranslateY(-radius / 10);
        
        body = new Cylinder(radius / 2, 3 * radius);
        body.setMaterial(mat);
        body.setTranslateY(-1.5 * radius);
        
        head = new Sphere(radius);
        head.setMaterial(mat);
        head.setTranslateY(-3 * radius);
        
        getChildren().addAll(base, body, head);
        
        this.setOnMouseClicked(e -> onClicked(e));
    }

    @Override
    public void changeColours(Color c) {
        PhongMaterial mat = new PhongMaterial(c);
        base.setMaterial(mat);
        body.setMaterial(mat);
        head.setMaterial(mat);
    }
}
