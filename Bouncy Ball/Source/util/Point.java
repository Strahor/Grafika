package util;

public class Point
{
	private double x, y;
	
	public Point(double _x, double _y)
	{
		x = _x;
		y = _y;
	}
	
	public double getX()
	{
		return x;
	}
	
	public double getY()
	{
		return y;
	}
	
	public void move(double _x, double _y)
	{
		x = _x;
		y = _y;
	}
}
