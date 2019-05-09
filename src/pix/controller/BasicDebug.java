package pix.controller;

public class BasicDebug
{
	private boolean printOn;
	/**
	 * a really basic debugger, just a quick way to print to the console
	 */
	public BasicDebug()
	{
		printOn = false;
	}
	
	/**
	 * turn print on or off
	 * @param state true = on, false = off
	 */
	public void setState(boolean state)
	{
		printOn = state;
	}
	/**
	 * Prints to the console
	 * @param phrase the thing you need to print
	 */
	public void out(String phrase)
	{
		if(printOn)
		System.out.println(phrase);
	}
}
