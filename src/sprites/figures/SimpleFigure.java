/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprites.figures;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;

/**
 *
 * @author Aleksandar
 */
public class SimpleFigure extends Figure
{
    Cylinder body;
    public SimpleFigure(double radius, Color c)
    {
        height = 4 * radius;
        body = new Cylinder(radius, 4 * radius);
        body.setTranslateY(-2 * radius);
        body.setMaterial(new PhongMaterial(c));
        
        getChildren().add(body);
        this.setOnMouseClicked(e -> onClicked(e));
    }

    @Override
    public void changeColours(Color c) {
        PhongMaterial mat = new PhongMaterial(c);
        body.setMaterial(mat);
    }
}
