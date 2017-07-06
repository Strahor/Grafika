/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprites.figures;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Sphere;
import javafx.scene.shape.TriangleMesh;

/**
 *
 * @author Aleksandar
 */
public class ClassicFigure extends Figure
{
    private MeshView coneMesh[] = new MeshView[2];
    private Cylinder coneBase[] = new Cylinder[2];
    private Sphere head;
    
    public ClassicFigure(double radius, Color c)
    {
        height = 4 * radius;
        PhongMaterial mat = new PhongMaterial(c);
        Group lowerCone = makeCone((float)radius, 3 * (float)radius, mat, 0);
        
        Group upperCone = makeCone((float)radius, (float)radius / 2, mat, 1);
        upperCone.setTranslateY(-2 * radius);
        
        
        head = new Sphere(radius);
        head.setMaterial(mat);
        head.setTranslateY(-3 * radius);
        
        getChildren().addAll(lowerCone, upperCone, head);
        this.setOnMouseClicked(e -> onClicked(e));
    }
    
    private Group makeCone(float radius, float height, PhongMaterial material, int coneNum) {
        Group cone = new Group();

        int numberOfTriangles = 360;

        float[] points = new float[numberOfTriangles * 3 * 3];
        float[] textCoords = {
                0.5f, 0,
                0, 1,
                1, 1
        };
        int[] faces = new int[numberOfTriangles * 3 * 2];

        for (int i = 0; i < numberOfTriangles; i++) {
            int index = i * 9;
            points[index] = 0;
            points[index + 1] = -height;
            points[index + 2] = 0;
            points[index + 3] = (float)Math.cos(Math.toRadians(i)) * radius;
            points[index + 4] = 0;
            points[index + 5] = (float)Math.sin(Math.toRadians(i)) * radius;
            points[index + 6] = (float)Math.cos(Math.toRadians(i + 1)) * radius;
            points[index + 7] = 0;
            points[index + 8] = (float)Math.sin(Math.toRadians(i + 1)) * radius;
        }

        for (int i = 0; i < numberOfTriangles; i++) {
            int index = i * 6;
            faces[index] = i * 3;
            faces[index + 1] = 0;
            faces[index + 2] = i * 3 + 1;
            faces[index + 3] = 1;
            faces[index + 4] = i * 3 + 2;
            faces[index + 5] = 2;
        }

        TriangleMesh mesh = new TriangleMesh();
        mesh.getPoints().addAll(points);
        mesh.getTexCoords().addAll(textCoords);
        mesh.getFaces().addAll(faces);

        coneMesh[coneNum] = new MeshView();
        coneMesh[coneNum].setMesh(mesh);
        coneMesh[coneNum].setMaterial(material);

        coneBase[coneNum] = new Cylinder(radius, 0.1);
        coneBase[coneNum].setMaterial(material);
        coneBase[coneNum].setTranslateZ(0);


        cone.getChildren().addAll(coneMesh[coneNum], coneBase[coneNum]);

        return cone;
    }

    @Override
    public void changeColours(Color c) {
        PhongMaterial mat = new PhongMaterial(c);
        coneMesh[0].setMaterial(mat);
        coneMesh[1].setMaterial(mat);
        coneBase[0].setMaterial(mat);
        coneBase[1].setMaterial(mat);
        head.setMaterial(mat);
    }
}
