/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camera;

import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import util.Utils;
import util.Utils.cameraZoom;

/**
 *
 * @author Aleksandar
 */
public class SphericalCamera extends BaseCamera
{
    double angleY = 0, angleX = -45;
    double distance = 1000;
    
    private static final Transform[] transformations = {new Rotate(0, Rotate.Y_AXIS), new Rotate(-45, Rotate.X_AXIS), new Translate(0, 0, -1000)};
    
    public SphericalCamera()
    {
        super();
        setFarClip(2000);
        getTransforms().setAll(transformations);
    }
    
    @Override
    public void update(long time_ms)
    {
        boolean change = false;
        if (horizontal == Utils.cameraMovement.LEFT)
        {
            angleY = (angleY - (double)30 * (double)time_ms / (double) 1000) % 360;
            change = true;
        } 
        if (horizontal == Utils.cameraMovement.RIGHT)
        {
            angleY = (angleY + (double)30 * (double)time_ms / (double) 1000) % 360;
            change = true;
        }
        if (vertical == Utils.cameraMovement.DOWN && angleX != 0)
        {
            angleX += (double)30 * (double)time_ms / (double) 1000;
            if (angleX >= 0)
                angleX = 0;
            change = true;
        }
        if (vertical == Utils.cameraMovement.UP && angleX != -90)
        {
            angleX -= (double)30 * (double)time_ms / (double) 1000;
            if (angleX <= -90)
                angleX = -90;
            change = true;
        }
        if (zoom == cameraZoom.IN)
        {
            distance -= (double) 100 * (double)time_ms / (double) 1000;
            if (distance < 400)
                distance = 400;
            change = true;
        }
        if (zoom == cameraZoom.OUT)
        {
            distance += (double) 100 * (double)time_ms / (double) 1000;
            if (distance > 1600)
                distance = 1600;
            change = true;
        }
        
        if (change)
        {
            transformations[0] = new Rotate(angleY, Rotate.Y_AXIS);
            transformations[1] = new Rotate(angleX, Rotate.X_AXIS);
            transformations[2] = new Translate(0, 0, -distance);
            getTransforms().setAll(transformations);
        }
    }
    
}
