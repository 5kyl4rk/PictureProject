package pix.model;

public class Make3DProfile implements EditProfile
{
	public final static int RED = 0;
	public final static int GREEN = 1;
	public final static int BLUE = 2;
	public final static int CYAN = 3;
	public final static int MAGENTA = 4;
	public final static int YELLOW = 5;
	
	private int xAxis;
	private int yAxis;
	private int baseColor;
	
	public Make3DProfile(int xAxis, int yAxis, int baseColor)
	{
		this.xAxis = xAxis;
		this.yAxis = yAxis;
		this.baseColor = baseColor;
	}
	
	public int getType()
	{
		return EditProfile.MAKE3D;
	}
	
	public int getXAxis()
	{
		return xAxis;
	}
	
	public int getYAxis()
	{
		return yAxis;
	}
	
	public int getBaseColor()
	{
		return baseColor;
	}
}
