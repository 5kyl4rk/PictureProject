package pix.controller;

public class BasicDebug
{
	private boolean printOn;
	public BasicDebug()
	{
		printOn = false;
	}
	
	public void setState(boolean state)
	{
		printOn = state;
	}
	public void out(String phrase)
	{
		if(printOn)
		System.out.println(phrase);
	}
}
