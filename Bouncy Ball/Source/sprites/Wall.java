package sprites;

import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import util.*;

public class Wall extends EnvironmentObject
{
	double top, left, right; //bounds
	
	public Wall(Point topLeft, double width, double height)
	{
		top = topLeft.getY();
		left = topLeft.getX();
		right = left + width;
		
		Rectangle top = new Rectangle(-width / 2, - height / 2 - 20, width, 20);
		top.setFill(Color.DARKGRAY);
		
		Rectangle left = new Rectangle(- width / 2 - 20, - height / 2 - 20, 20, height);
		left.setFill(Color.DARKGRAY);
		
		Rectangle right = new Rectangle(width / 2, - height / 2 - 20, 20, height);
		right.setFill(Color.DARKGRAY);
		
		getChildren().add(top);
		getChildren().add(left);
		getChildren().add(right);
	}
	
	@Override
	public Vector checkForCollision(Ball b) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void interract(Ball b) {
		// TODO Auto-generated method stub
		
	}
	
}
