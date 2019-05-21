package pix.model;

public class ScanlinesProfile implements EditProfile
{
	
	public static final int HORIZONTAL = 0;
	public static  final int VERTICAL = 1;
	public static final int LCD = 2;
	
	private int thickness;
	private int spread;
	private int direction;
	
	
	public ScanlinesProfile(int thickness, int spread, int direction)
	{
		this.setThickness(thickness);
		this.setSpread(spread);
		this.setDirection(direction);
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
}
