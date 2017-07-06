/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camera;

import javafx.scene.PerspectiveCamera;
import util.Utils.cameraMovement;
import util.Utils.cameraZoom;

/**
 *
 * @author Aleksandar
 */
public abstract class BaseCamera extends PerspectiveCamera
{
    protected cameraMovement horizontal = cameraMovement.STATIC, vertical = cameraMovement.STATIC;
    protected cameraZoom zoom = cameraZoom.STATIC;
    
    protected BaseCamera()
    {
        super(true);
    }
    
    public void setZoomIn()
    {
        zoom = cameraZoom.IN;
    }
    
    public void clearZoomIn()
    {
        if (zoom == cameraZoom.IN)
            zoom = cameraZoom.STATIC;
    }
    public void setZoomOut()
    {
        zoom = cameraZoom.OUT;
    }
    
    public void clearZoomOut()
    {
        if (zoom == cameraZoom.OUT)
            zoom = cameraZoom.STATIC;
    }
    
    public void setDirectionLeft()
    {
        horizontal = cameraMovement.LEFT;
    }
    
    public void clearDirectionLeft()
    {
        if (horizontal == cameraMovement.LEFT)
            horizontal = cameraMovement.STATIC;
    }
    
    public void setDirectionRight()
    {
        horizontal = cameraMovement.RIGHT;
    }
    
    public void clearDirectionRight()
    {
        if (horizontal == cameraMovement.RIGHT)
            horizontal = cameraMovement.STATIC;
    }
    
    public void setDirectionUp()
    {
        vertical = cameraMovement.UP;
    }
    
    public void clearDirectionUp()
    {
        if (vertical == cameraMovement.UP)
        vertical = cameraMovement.STATIC;
    }
    
    public void setDirectionDown()
    {
        vertical = cameraMovement.DOWN;
    }
    
    public void clearDirectionDown()
    {
        if (vertical == cameraMovement.DOWN)
        vertical = cameraMovement.STATIC;
    }
    
    public abstract void update(long milis);
}
