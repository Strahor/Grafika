package util;



public class Vector
{
	private double x, y;
	
	public Vector(double intensity, double angle)
	{
		x = intensity * Math.sin(angle);
		y = intensity * Math.cos(angle);
	}
	
	public void setAngle(double angle)
	{
		double intensity = getIntensity();
		x = intensity * Math.sin(angle);
		y = intensity * Math.cos(angle);
	}	
	
	public double getX()
	{
		return x;
	}
	
	public double getY()
	{
		return y;
	}
	
	public double getAngle()
	{
		double tang = x / y;
		return Math.atan(tang);
	}
	
	public double getIntensity()
	{
		return Math.sqrt(x * x + y * y);
	}
}
