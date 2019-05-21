package pix.model;

public class BleedProfile implements EditProfile
{
	public static final int UP = -1;
	public static final int DOWN = 2;
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	
	private int startPoint;
	private int direction;
	
	public BleedProfile(int startPoint, int direction)
	{
		this.setStartPoint(startPoint);
		this.setDirection(direction);
	}
	
	
	public int getType()
	{
		return EditProfile.BLEED;
	}


	public int getStartPoint()
	{
		return startPoint;
	}


	public void setStartPoint(int startPoint)
	{
		this.startPoint = startPoint;
	}


	public int getDirection()
	{
		return direction;
	}


	public void setDirection(int direction)
	{
		this.direction = direction;
	}
}
