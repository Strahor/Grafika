/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprites.table;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import util.Utils;

/**
 *
 * @author Aleksandar
 */
public class Table extends Group
{
    private final Cylinder houses[] = new Cylinder[4];
    private final Arrow arrows[] = new Arrow[4];
    Cylinder[] fields;
    
    public Table()
    {
        Box base = new Box(Utils.dimensions, Utils.dimensions, 12);
        base.setMaterial(new PhongMaterial(Color.LIGHTSALMON));
        base.setTranslateZ(6);
        
        
        getChildren().addAll(base);
        
        createArrows();
        
        createHouses();
        
        createPath();
    }
    
    private void createArrows()
    {
        for (int i = 0; i < 4; ++i)
        {
            arrows[i] = new Arrow(3f * (float)Utils.width, 3f * (float)Utils.width, 5.5f *(float)Utils.width, 3);
            
            arrows[i].setMaterial(new PhongMaterial(Color.DARKGRAY));
            
            arrows[i].getTransforms().setAll(new Rotate((i - 1) * 90));
        }
        getChildren().addAll(arrows);
    }
    
    private void createHouses()
    {
        for (int i = 0; i < 4; ++i)
        {
            houses[i] = new Cylinder(Utils.radius, 3);
            houses[i].getTransforms().setAll(new Translate(Utils.housesCenter[i][0], -Utils.housesCenter[i][1], 0), new Rotate(90, Rotate.X_AXIS));
            
            houses[i].setMaterial(new PhongMaterial(Color.DARKGRAY));
        }
        getChildren().addAll(houses);
    }
    
    public void resetFields()
    {
        PhongMaterial mat = new PhongMaterial(Color.DARKGRAY);
        
        for (int player = 0; player < 4; ++player)
        {
            arrows[player].setMaterial(mat);
            houses[player].setMaterial(mat);
            fields[Utils.start[player]].setMaterial(mat);
        }
    }
    
    private void createPath()
    {
        PhongMaterial mat[] = {new PhongMaterial(Color.BURLYWOOD), new PhongMaterial(Color.AZURE)};
        fields = new Cylinder[48];        
        
        for (int i = 0; i < 48; ++i)
        {
            fields[i] = new Cylinder(Utils.width / 2, 3);
            fields[i].setTranslateX(Utils.fields[i][0]);
            fields[i].setTranslateY(Utils.fields[i][1]);
            fields[i].setTranslateZ(-1.5);
            fields[i].setRotationAxis(Rotate.X_AXIS);
            fields[i].setRotate(90);
            fields[i].setMaterial(mat[i % 2]);
        }
        
        for (int i = 0; i < 4; ++i)
        {
            fields[Utils.start[i]].setMaterial(new PhongMaterial(Color.DARKGRAY));
        }
        
        getChildren().addAll(fields);
    }
    
    public void changePlayerColour(int player)
    {
        PhongMaterial mat = new PhongMaterial(Utils.playerColours[Utils.players[player].colour]);
        
        arrows[player].setMaterial(mat);
        houses[player].setMaterial(mat);
        fields[Utils.start[player]].setMaterial(mat);
    }
    
    public void highlightPlayer(int player)
    {
        PhongMaterial mat = new PhongMaterial(Utils.fieldColours[Utils.players[player].colour]);
        
        houses[player].setMaterial(mat);
    }
    
    public void stopHighlight(int player)
    {
        PhongMaterial mat = new PhongMaterial(Utils.playerColours[Utils.players[player].colour]);
        
        houses[player].setMaterial(mat);
    }
}
