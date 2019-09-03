package pix.model;

public class GrainProfile implements EditProfile
{
	private int hardness;
	public GrainProfile(int hardness)
	{
		this.setHardness(hardness);
	}
	
	public void setHardness(int hardness)
	{
		this.hardness = hardness;
	}
	
	public int getHardness()
	{
		return hardness;
	}
	
	public int getType()
	{
		return EditProfile.GRAIN;
	}
}
