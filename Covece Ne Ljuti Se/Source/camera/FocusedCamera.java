/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camera;

import javafx.geometry.Bounds;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import static main.CoveceNeLjutiSe.game;
import util.Utils;

/**
 *
 * @author Aleksandar
 */
public class FocusedCamera extends BaseCamera
{
    private int figure = -1, player = 0;
    
    private static final Transform[] transformations = {
        new Rotate(-45, Rotate.Y_AXIS),
        new Rotate(-72, Rotate.X_AXIS),
        new Translate(0, 0, -1.41 * Utils.dimensions / (2 * Math.cos(Math.toRadians(72)))),
        new Rotate(72, Rotate.X_AXIS),
        new Rotate(0, Rotate.Y_AXIS),
        new Rotate(-72, Rotate.X_AXIS),
    };
    
    public FocusedCamera()
    {
        super();
        setFarClip(2000);
        getTransforms().setAll(transformations);
    }
    
    @Override
    public void update(long milis)
    {
        if (player != game.currentPlayer)
        {
            player = game.currentPlayer;
            transformations[0] = new Rotate(-45 + player * 90, Rotate.Y_AXIS);
        }
        figure = Utils.Player.getNextFocus(figure);
        double figureX = 0, figureZ = 0;
        /*if (figure != -1)
        {
            Bounds fBounds = Utils.Player.getFigureBounds(figure);
            figureX = (fBounds.getMaxX() + fBounds.getMinX()) / 2;
            figureZ = (fBounds.getMaxZ() + fBounds.getMinZ()) / 2;
        }*/
        figureX = Utils.dimensions / 2;
        figureZ = Utils.dimensions / 2;
        
        double angle = 0;

        switch(player)
        {
            case 0:
                angle = 45 - Math.toDegrees(Math.atan(figureX / figureZ));
                break;
            case 1:
                angle = Math.toDegrees(Math.atan(figureX / figureZ)) - 45;
                break;
            case 2:
                angle = -45 + Math.toDegrees(Math.atan(figureX / figureZ));
                break;
            case 3:
                angle = -Math.toDegrees(Math.atan(figureX / figureZ)) + 45;
                break;
        }
        transformations[4] = new Rotate(angle, Rotate.Y_AXIS);
        getTransforms().setAll(transformations);
    }
    
    @Override
    public void setDirectionLeft()
    {
        if (figure != -1)
        {
            figure--;
            if (figure == -1)
                figure = 15;
            figure = Utils.Player.getNextFocus(figure);
        }
    }
    
    @Override
    public void setDirectionRight()
    {
        figure++;
        if (figure == 16)
            figure = 0;
        figure = Utils.Player.getNextFocus(figure);
    }
    
}
