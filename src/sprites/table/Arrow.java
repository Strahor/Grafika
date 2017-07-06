/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprites.table;

import javafx.scene.Group;
import javafx.scene.paint.Material;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

/**
 *
 * @author Aleksandar
 */
public class Arrow extends Group
{
    private static float texturePoints[] = {
            0, 0,
            0, 1,
            1, 0,
            1, 1
        };
    
    private static int edges[] = {
        //Front
        7, 0, 13, 1, 8, 3, //arrow head
        12, 0, 11, 1, 9, 3,
        9, 0, 11, 1, 10, 3, //arrow body
        //Back
        0, 0, 1, 1, 6, 3,
        5, 0, 2, 1, 4, 3,
        2, 0, 3, 1, 4, 3,
        //Sides
        0, 0, 7, 1, 8, 3,
        0, 0, 8, 1, 1, 3,
        7, 0, 0, 1, 13, 3,
        0, 0, 6, 1, 13, 3,
        1, 0, 8, 1, 2, 3,
        8, 0, 9, 1, 2, 3,
        12, 0, 6, 1, 5, 3,
        12, 0, 13, 1, 6, 3,
        9, 0, 10, 1, 3, 3,
        3, 0, 2, 1, 9, 3,
        12, 0, 5, 1, 4, 3,
        12, 0, 4, 1, 11, 3,
        3, 0, 10, 1, 11, 3,
        11, 0, 4, 1, 3, 3
    };
    
    private MeshView arrow;
    
    public Arrow(float width, float height, float length, float thickness)
    {
        float points[] = {
            0, 0, 0,
            width/2, height/2, 0,
            width/6, height/2, 0,
            width/6, length, 0,
            -width/6, length, 0,
            -width/6, height/2, 0,
            -width/2, height/2, 0,
            0, 0, -thickness,
            width/2, height/2, -thickness,
            width/6, height/2, -thickness,
            width/6, length, -thickness,
            -width/6, length, -thickness,
            -width/6, height/2, -thickness,
            -width/2, height/2, -thickness
        };
        
        TriangleMesh mesh = new TriangleMesh();
        mesh.getPoints().setAll(points);
        mesh.getTexCoords().setAll(texturePoints);
        mesh.getFaces().setAll(edges);
        
        arrow = new MeshView();
        arrow.setMesh(mesh);
        
        getChildren().addAll(arrow);
    }
    
    public void setMaterial(Material m)
    {
        arrow.setMaterial(m);
    }
}
