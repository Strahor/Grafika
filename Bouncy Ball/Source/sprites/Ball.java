package sprites;

import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import util.*;

public class Ball extends Group{
	private Vector speed;
	private Point center;
	
	public Ball(Point position)
	{
		center = position;
		speed = new Vector(0, 0);
		
		Circle c = new Circle(0, 0, 10);
		c.setFill(Color.CORNFLOWERBLUE);
		getChildren().add(c);
	}
	
	public void move(long time_ms)
	{
		double newX = center.getX() + speed.getX() * time_ms / 1000;
		double newY = center.getY() + speed.getY() * time_ms / 1000;
		center.move(newX, newY);
	}
	
	
	
	
	
	public Point getPosition()
	{
		return center;
	}
	
	public Vector getSpeed()
	{
		return speed;
	}
	
	public void setSpeed(Vector newSpeed)
	{
		speed = newSpeed;
	}
	
	public void setPosition(Point newPosition)
	{
		center = newPosition;
	}
}
