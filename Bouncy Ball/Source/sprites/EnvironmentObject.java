package sprites;

import util.*;

import javafx.scene.Group;

public abstract class EnvironmentObject extends Group{
	protected Point position;
	
	public abstract Vector checkForCollision(Ball b);
	public abstract void interract(Ball b);
}