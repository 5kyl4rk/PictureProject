package pix.model;

public interface EditProfile
{
	public static final int ORIGINAL = -1;
	public static final int GLITCH = 0;
	public static final int MAKE3D = 1;
	public static final int SCANLINES = 2;
	public static final int GRAIN = 3;
	public static final int BLEED = 4;
	
	public int getType();
}
