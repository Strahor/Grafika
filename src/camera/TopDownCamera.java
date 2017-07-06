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

/**
 *
 * @author Aleksandar
 */
public class TopDownCamera extends BaseCamera
{
    double posX = 0, posY = 0;
    
    private static final Transform[] transformations = {new Rotate(-90, Rotate.X_AXIS), new Translate(0, 0, -1000)};
    
    public TopDownCamera()
    {
        super();
        setFarClip(2000);
        getTransforms().setAll(transformations);
    }
    
    @Override
    public void update(long time_ms)
    {
        boolean change = false;
        if (vertical == Utils.cameraMovement.DOWN)
        {
            posY += (double)50 * (double)time_ms / (double) 1000;
            if (posY >= 250)
                posY = 250;
            change = true;
        }
        if (vertical == Utils.cameraMovement.UP)
        {
            posY -= (double)50 * (double)time_ms / (double) 1000;
            if (posY <= -250)
                posY = -250;
            change = true;
        }
        if (horizontal == Utils.cameraMovement.LEFT)
        {
            posX -= (double) 50 * (double)time_ms / (double) 1000;
            if (posX <= -250)
                posX = -250;
            change = true;
        }
        if (horizontal == Utils.cameraMovement.RIGHT)
        {
            posX += (double) 50 * (double)time_ms / (double) 1000;
            if (posX >= 250)
                posX = 250;
            change = true;
        }
        
        if (change)
        {
            transformations[1] = new Translate(posX, posY, -1000);
            getTransforms().setAll(transformations);
        }
    }
    
}
