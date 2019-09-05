package pix.model;

import java.awt.Color;

public class ScanlineProfile implements EditProfile
{
	
	public static final int HORIZONTAL = 0;
	public static  final int VERTICAL = 1;
	public static final int LCD = 2;
	
	private int thickness;
	private int spread;
	private int direction;
	private Color color;
	
	
	public ScanlineProfile(int thickness, int spread, int direction, Color color)
	{
		this.setThickness(thickness);
		this.setSpread(spread);
		this.setDirection(direction);
		this.setColor(color);
	}

	public int getType()
	{
		return EditProfile.SCANLINES;
	}

	public int getDirection()
	{
		return direction;
	}

	public void setDirection(int direction)
	{
		this.direction = direction;
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}

	public int getSpread()
	{
		return spread;
	}

	public void setSpread(int spread)
	{
		this.spread = spread;
	}

	public int getThickness()
	{
		return thickness;
	}

	public void setThickness(int thickness)
	{
		this.thickness = thickness;
	}
	
	public Color getColor()
	{
		return color;
	}
}
