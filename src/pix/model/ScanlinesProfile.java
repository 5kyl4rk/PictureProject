package pix.model;

public class ScanlinesProfile implements EditProfile
{
	
	public static final int HORIZONTAL = 0;
	public static  final int VERTICAL = 1;
	public static final int LCD = 2;

	public int getType()
	{
		return EditProfile.SCANLINES;
	}
}
