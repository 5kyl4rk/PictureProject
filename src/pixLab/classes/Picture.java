package pixLab.classes;

import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.util.List; // resolves problem with java.awt.List and java.util.List

/**
 * A class that represents a picture. This class inherits from SimplePicture and
 * allows the student to add functionality to the Picture class.
 * 
 * @author Barbara Ericson ericson@cc.gatech.edu
 */
public class Picture extends SimplePicture
{
	///////////////////// constructors //////////////////////////////////

	/**
	 * Constructor that takes no arguments
	 */
	public Picture()
	{
		/*
		 * not needed but use it to show students the implicit call to super() child
		 * constructors always call a parent constructor
		 */
		super();
	}

	/**
	 * Constructor that takes a file name and creates the picture
	 * 
	 * @param fileName
	 *            the name of the file to create the picture from
	 */
	public Picture(String fileName)
	{
		// let the parent class handle this fileName
		super(fileName);
	}

	/**
	 * Constructor that takes the width and height
	 * 
	 * @param height
	 *            the height of the desired picture
	 * @param width
	 *            the width of the desired picture
	 */
	public Picture(int height, int width)
	{
		// let the parent class handle this width and height
		super(width, height);
	}

	/**
	 * Constructor that takes a picture and creates a copy of that picture
	 * 
	 * @param copyPicture
	 *            the picture to copy
	 */
	public Picture(Picture copyPicture)
	{
		// let the parent class do the copy
		super(copyPicture);
	}

	/**
	 * Constructor that takes a buffered image
	 * 
	 * @param image
	 *            the buffered image to use
	 */
	public Picture(BufferedImage image)
	{
		super(image);
	}

	////////////////////// methods ///////////////////////////////////////

	/**
	 * Method to return a string with information about this picture.
	 * 
	 * @return a string with information about the picture such as fileName, height
	 *         and width.
	 */
	public String toString()
	{
		String output = "Picture, filename " + getFileName() + " height " + getHeight() + " width " + getWidth();
		return output;

	}

	/** Method to set the blue to 0 */
	public void zeroBlue()
	{
		Pixel[][] pixels = this.getPixels2D();
		for (Pixel[] rowArray : pixels)
		{
			for (Pixel pixelObj : rowArray)
			{
				pixelObj.setBlue(0);
			}
		}
	}

	/** Method to set Red to 0 */
	public void zeroRed()
	{
		Pixel[][] pixels = this.getPixels2D();
		for (int row = 0; row < pixels.length; row++)
		{
			for (int col = 0; col < pixels[0].length; col++)
			{
				// int randomRow = (int)(Math.random() * pixels.length);
				// int randomCol = (int)(Math.random() * pixels[0].length);
				Pixel currentValue = pixels[row][col];
				currentValue.setRed(0);
				currentValue = pixels[row][col];
			}
		}
	}

	/** Method to set Green to 0 */
	public void zeroGreen()
	{
		Pixel[][] pixels = this.getPixels2D();
		for (Pixel[] rowArray : pixels)
		{
			for (Pixel currentPixel : rowArray)
			{
				currentPixel.setGreen(0);
			}
		}
	}

	/**
	 * Picks a random number
	 * 
	 * @param negativeIsOK
	 *            the value can be negative
	 * @param max
	 *            the highest possible number it can pick <i>(will also be the
	 *            lowest negative value if allowed)</i>
	 * @return an Int value that will be either positive or negative
	 */
	public static int pickRandomNumber(boolean negativeIsOK, int max)
	{
		final int COIN_FLIP = (int) (Math.random() * 2);
		int randomNumber = (int) (Math.random() * max);
		if (negativeIsOK)
		{
			if (COIN_FLIP == 1)
			{
				randomNumber *= -1;
			}
		}

		return randomNumber;
	}

	/**
	 * Utilizes helper methods to make an image look "glitchy"
	 * 
	 * @param overlaysOn
	 *            picks a random overlay to apply to the image
	 */
	public void glitch()
	{
		int height = this.getHeight();
		int width = this.getWidth();
		int pick3DColor = (int) ((Math.random() * 100) % 6);
		int shiftPercent = (int) (Math.round(width * 0.5)); // can only shift at a max of half the image size
		int shift3D = pickRandomNumber(true, shiftPercent);
		int pick3DEffect = pickRandomNumber(false, 2);
		int randoCycle = pickRandomNumber(false, 6);
		int pickDirection3D = pickRandomNumber(false, 3);
		
		if (pick3DEffect == 0)
		{
			// shifts only the second
			
			this.make3D(pick3DColor, shift3D, pickDirection3D);
		}
		else
		{
			// shifts the second layer randomly

			this.scramble3D(pick3DColor, randoCycle, shift3D);
		}

		int determineFirstEffect = pickRandomNumber(false, 2);

		if (determineFirstEffect == 0)
		{
			// applies dead pixels
			randoCycle = pickRandomNumber(false, 6);
			int selectRangeH = (int) (Math.round(height * 0.05));
			int selectRangeW = (int) (Math.round(width * 0.05));
			int pickDirectionColor = pickRandomNumber(false, 2);
			int pointA = 0;
			int pointB = 0;
			for (int cycles = 0; cycles < randoCycle; cycles++)
			{

				int pickColor = (int) ((Math.random() * 100) % 6);
				/*
				 * if ((pickColor + 3) % 6 == pick3DColor) { pickColor = (int) ((Math.random() *
				 * 100) % 6); }
				 */
				if (pickDirectionColor == 0)
				{
					pointA = pickRandomNumber(false, height);
					pointB = pointA + (pickRandomNumber(false, selectRangeH));
					rowColor(pointA, pointB, pickColor);
				}
				else
				{
					pointA = pickRandomNumber(false, width);
					pointB = pointA + (pickRandomNumber(false, selectRangeW));
					colColor(pointA, pointB, pickColor);
				}
			}
		}
		else
		{
			int bleedPercentage = (int)(Math.round(width * 0.07));
			int bleedPercentageV = (int)(Math.round(height * .07));
			if(pickDirection3D == 0)
			{
				if(shift3D > 0)
				{
					this.bleed(pickRandomNumber(false,bleedPercentage),0);
				}
				else
				{
					this.bleed((width-pickRandomNumber(false,bleedPercentage)), 1);
				}
			}
			else if(pickDirection3D == 1)
			{
				if(shift3D > 0)
				{
					this.verticalBleed(pickRandomNumber(false,bleedPercentageV),0);
				}
				else
				{
					this.verticalBleed((height-pickRandomNumber(false,bleedPercentageV)), 1);
				}
			}
			else
			{
				if(shift3D > 0)
				{
					this.bleed(pickRandomNumber(false,bleedPercentage),0);
					this.verticalBleed(pickRandomNumber(false,bleedPercentageV),0);
				}
				else
				{
					this.bleed((width - pickRandomNumber(false,bleedPercentageV)),1);
					this.verticalBleed((height - pickRandomNumber(false,bleedPercentageV)), 1);
				}
			}
		}
		// applies noise
		int pickEffect = pickRandomNumber(false, 2);
		int totalArea = width * height;
		int pickHardness = pickRandomNumber(false, 100);
		if (pickEffect == 0)
		{
			this.grain(pickHardness);
		}
		else
		{
			double pickPercent = Math.random() * 50;
			this.noise(pickHardness, pickPercent);
		}

		// applies overlay
		int pickOverlay = (int) (Math.random() * 3);
		int pickSpread = pickRandomNumber(false, 2) + 1;
		int pickThick = pickRandomNumber(false, 2) + 1;
		if (pickOverlay == 0)
		{
			this.scanlines(pickSpread, pickThick);
		}
		else if (pickOverlay == 1)
		{
			this.verticalScanlines(pickSpread, pickThick);
		}
		else if (pickOverlay == 2)
		{
			this.lcd(1, pickThick);
		}

	}

	// <------Overlays------>
	/**
	 * displays basic scanlines
	 */
	public void scanlines()
	{
		scanlines(1, 1);
	}

	/**
	 * displays scanlines of a desired color
	 * 
	 * @param shading
	 *            the color of the lines <i>(must be of the Color class)</i>
	 */
	public void scanlines(Color shading)
	{
		scanlines(1, 1, shading);
	}

	/**
	 * adds horizontal lines over the image
	 * 
	 * @param spread
	 *            spacing of the lines <i>(in pixels)</i>
	 * @param thickness
	 *            how thick each line is <i>(in pixels)</i>
	 */
	public void scanlines(int spread, int thickness)
	{
		scanlines(spread, thickness, Color.BLACK);
	}

	/**
	 * adds horizontal lines over the image
	 * 
	 * @param spread
	 *            spacing of the lines <i>(in pixels)</i>
	 * @param thickness
	 *            how thick each line is <i>(in pixels)</i>
	 * @param shading
	 *            the color of the lines <i>(must be of the Color class)</i>
	 */
	public void scanlines(int spread, int thickness, Color shading)
	{
		Pixel[][] pixels = this.getPixels2D();

		if (spread < 1)
		{
			spread = 1;
		}
		if (spread > this.getHeight())
		{
			spread = this.getHeight();
		}
		for (int row = 1; row < pixels.length; row++)
		{
			for (int col = 0; col < pixels[0].length; col++)
			{
				if (row % (thickness + spread) >= spread)
				{
					pixels[row][col].setColor(shading);

				}
			}
		}
	}

	/**
	 * displays vertical scanlines
	 */
	public void verticalScanlines()
	{
		verticalScanlines(1, 1);
	}

	/**
	 * displays vertical scanlines of a desired color
	 * 
	 * @param shading
	 *            the color of the lines <i>(must be of the Color class)</i>
	 */
	public void verticalScanlines(Color shading)
	{
		verticalScanlines(1, 1, shading);
	}

	/**
	 * add vertical lines over the image
	 * 
	 * @param spread
	 *            spacing between each lines <i>(in pixels)</i>
	 * @param thickness
	 *            how thick each line is <i>(in pixels)</i>
	 */
	public void verticalScanlines(int spread, int thickness)
	{
		verticalScanlines(spread, thickness, Color.BLACK);
	}

	/**
	 * add vertical lines over the image
	 * 
	 * @param spread
	 *            spacing between each lines <i>(in pixels)</i>
	 * @param thickness
	 *            how thick each line is <i>(in pixels)</i>
	 * @param shading
	 *            the color of the lines <i>(must be of the Color class)</i>
	 */
	public void verticalScanlines(int spread, int thickness, Color shading)
	{
		Pixel[][] pixels = this.getPixels2D();

		if (spread < 1)
		{
			spread = 1;
		}
		if (spread > this.getWidth())
		{
			spread = this.getWidth();
		}
		for (int row = 0; row < pixels.length; row++)
		{
			for (int col = 0; col < pixels[0].length; col++)
			{
				if (col % (thickness + spread) >= spread)
				{
					pixels[row][col].setColor(shading);
				}
			}
		}
	}

	/**
	 * displays a LCD look
	 */
	public void lcd()
	{
		lcd(1, 1);
	}

	/**
	 * displays a LCD look of a desired color
	 * 
	 * @param shading
	 *            the color of the lines <i>(must be of the Color class)</i>
	 */
	public void lcd(Color shading)
	{
		lcd(1, 1, shading);
	}

	/**
	 * adds a grid pattern to the image
	 * 
	 * @param spread
	 *            size of square <i>(in pixels)</i>
	 * @param thickness
	 *            size of lines <i>(in pixels)</i>
	 */
	public void lcd(int spread, int thickness)
	{
		this.scanlines(spread, thickness, Color.BLACK);
		this.verticalScanlines(spread, thickness, Color.BLACK);
	}

	/**
	 * adds a grid pattern to the image
	 * 
	 * @param spread
	 *            size of square <i>(in pixels)</i>
	 * @param thickness
	 *            size of lines <i>(in pixels)</i>
	 * @param shading
	 *            the color of the lines <i>(must be of the Color class)</i>
	 */
	public void lcd(int spread, int thickness, Color shading)
	{
		this.scanlines(spread, thickness, shading);
		this.verticalScanlines(spread, thickness, shading);
	}

	/**
	 * Separates the image into two color channels and shifts it slightly
	 * 
	 * @param baseColor
	 *            the color of the first layer that doesn't shift
	 *            <ul>
	 *            <li><b>Red<b> = 0</li>
	 *            <li><b>Green<b> = 1</li>
	 *            <li><b>Blue<b> = 2</li>
	 *            <li><b>Cyan<b> = 3</li>
	 *            <li><b>Magenta<b> = 4</li>
	 *            <li><b>Yellow<b> = 5</li>
	 *            </ul>
	 */
	public void make3D(int baseColor)
	{
		int smallPercent = (int) (Math.round(this.getWidth() * .03));
		make3D(baseColor, pickRandomNumber(true, smallPercent), 0);
	}

	/**
	 * Separates the image into two color channels and shifts it slightly
	 * 
	 * @param shift
	 *            how much the second layer is shifted <i>(in pixels)</i>
	 * @param baseColor
	 *            the color of the first layer that doesn't shift
	 *            <ul>
	 *            <li><b>Red<b> = 0</li>
	 *            <li><b>Green<b> = 1</li>
	 *            <li><b>Blue<b> = 2</li>
	 *            <li><b>Cyan<b> = 3</li>
	 *            <li><b>Magenta<b> = 4</li>
	 *            <li><b>Yellow<b> = 5</li>
	 *            </ul>
	 */
	public void make3D(int baseColor, int shift, int direction)
	{
		final int RED = 0;
		final int GREEN = 1;
		final int BLUE = 2;
		final int CYAN = 3;
		final int MAGENTA = 4;
		final int YELLOW = 5;

		if (baseColor > 5 || baseColor < 0)
		{
			baseColor = (int) ((Math.random() * 100) % 6);
		}
		Pixel[][] pixels = this.getPixels2D();
		Picture layer1Temp = new Picture(this);
		Picture layer2Temp = new Picture(this);
		Pixel[][] layer1 = layer1Temp.getPixels2D();
		Pixel[][] layer2 = layer2Temp.getPixels2D();

		for (int row = 0; row < pixels.length; row++)
		{
			for (int col = 0; col < pixels[0].length; col++)
			{
				if (baseColor == RED)
				{
					layer2[row][col].setRed(0);
					layer1[row][col].setGreen(0);
					layer1[row][col].setBlue(0);
				}

				else if (baseColor == GREEN)
				{
					layer1[row][col].setRed(0);
					layer2[row][col].setGreen(0);
					layer1[row][col].setBlue(0);
				}

				else if (baseColor == BLUE)
				{
					layer1[row][col].setRed(0);
					layer1[row][col].setGreen(0);
					layer2[row][col].setBlue(0);
				}
				else if (baseColor == CYAN)
				{
					layer1[row][col].setRed(0);
					layer2[row][col].setGreen(0);
					layer2[row][col].setBlue(0);
				}

				else if (baseColor == MAGENTA)
				{
					layer2[row][col].setRed(0);
					layer1[row][col].setGreen(0);
					layer2[row][col].setBlue(0);
				}
				else if (baseColor == YELLOW)
				{
					layer2[row][col].setRed(0);
					layer2[row][col].setGreen(0);
					layer1[row][col].setBlue(0);
				}

			}
		}
		if (direction == 1)
		{
			layer2Temp.shiftUpDown(shift);
		}
		else if (direction == 2)
		{
			layer2Temp.shiftUpDown(shift);
			layer2Temp.shiftLeftRight(shift);
		}
		else
		{
			layer2Temp.shiftLeftRight(shift);
		}

		for (int row = 0; row < pixels.length; row++)
		{
			for (int col = 0; col < pixels[0].length; col++)
			{
				int mergeRed = layer1[row][col].getRed() + layer2[row][col].getRed();
				int mergeGreen = layer1[row][col].getGreen() + layer2[row][col].getGreen();
				int mergeBlue = layer1[row][col].getBlue() + layer2[row][col].getBlue();

				pixels[row][col].setRed(mergeRed);
				pixels[row][col].setGreen(mergeGreen);
				pixels[row][col].setBlue(mergeBlue);
			}
		}
	}

	/**
	 * Makes two layers of opposite color channels with the second layer being
	 * "scrambled"
	 * 
	 * @param baseColor
	 *            the color of the first layer that doesn't shift
	 *            <ul>
	 *            <li><b>Red<b> = 0</li>
	 *            <li><b>Green<b> = 1</li>
	 *            <li><b>Blue<b> = 2</li>
	 *            <li><b>Cyan<b> = 3</li>
	 *            <li><b>Magenta<b> = 4</li>
	 *            <li><b>Yellow<b> = 5</li>
	 *            </ul>
	 * @param times
	 *            how many times the second layer gets shifted
	 * @param slightShiftOn
	 *            Shifts the second layer slightly before "scramble" to ensure the
	 *            whole image gets a 3D effect
	 */
	public void scramble3D(int baseColor, int times, boolean slightShiftOn)
	{
		int slightShift = pickRandomNumber(true, ((int) (Math.round(this.getWidth() * .05))));
		if (slightShiftOn)
		{
			this.scramble3D(baseColor, times, slightShift);
		}
		else
		{
			this.scramble3D(baseColor, times, 0);
		}
	}

	/**
	 * Makes two layers of opposite color channels with the second layer being
	 * "scrambled"
	 * 
	 * @param baseColor
	 *            the color of the first layer that doesn't shift
	 *            <ul>
	 *            <li><b>Red<b> = 0</li>
	 *            <li><b>Green<b> = 1</li>
	 *            <li><b>Blue<b> = 2</li>
	 *            <li><b>Cyan<b> = 3</li>
	 *            <li><b>Magenta<b> = 4</li>
	 *            <li><b>Yellow<b> = 5</li>
	 *            </ul>
	 * @param times
	 *            how many times the second layer gets shifted
	 * @param shift
	 *            how much the second layer should be shifted before scramble
	 */
	public void scramble3D(int baseColor, int times, int shift)
	{
		final int RED = 0;
		final int GREEN = 1;
		final int BLUE = 2;
		final int CYAN = 3;
		final int MAGENTA = 4;
		final int YELLOW = 5;

		if (baseColor > 5 || baseColor < 0)
		{
			baseColor = (int) ((Math.random() * 100) % 6);
		}
		Pixel[][] pixels = this.getPixels2D();
		Picture layer1Temp = new Picture(this);
		Picture layer2Temp = new Picture(this);
		Pixel[][] layer1 = layer1Temp.getPixels2D();
		Pixel[][] layer2 = layer2Temp.getPixels2D();
		int height = this.getHeight();
		int width = this.getWidth();
		for (int row = 0; row < pixels.length; row++)
		{
			for (int col = 0; col < pixels[0].length; col++)
			{
				if (baseColor == RED)
				{
					layer2[row][col].setRed(0);
					layer1[row][col].setGreen(0);
					layer1[row][col].setBlue(0);
				}

				else if (baseColor == GREEN)
				{
					layer1[row][col].setRed(0);
					layer2[row][col].setGreen(0);
					layer1[row][col].setBlue(0);
				}

				else if (baseColor == BLUE)
				{
					layer1[row][col].setRed(0);
					layer1[row][col].setGreen(0);
					layer2[row][col].setBlue(0);
				}
				else if (baseColor == CYAN)
				{
					layer1[row][col].setRed(0);
					layer2[row][col].setGreen(0);
					layer2[row][col].setBlue(0);
				}

				else if (baseColor == MAGENTA)
				{
					layer2[row][col].setRed(0);
					layer1[row][col].setGreen(0);
					layer2[row][col].setBlue(0);
				}
				else if (baseColor == YELLOW)
				{
					layer2[row][col].setRed(0);
					layer2[row][col].setGreen(0);
					layer1[row][col].setBlue(0);
				}

			}
		}

		int shiftRange = (int) (Math.round(height * 0.30));
		int shiftValue = (int) (Math.round(width * 0.25));

		layer2Temp.shiftLeftRight(shift);

		int pointA = 0;
		int pointB = 0;
		for (int cycles = 0; cycles < times; cycles++)
		{

			pointA = pickRandomNumber(false, height);
			pointB = pointA + (pickRandomNumber(false, shiftRange));
			int randoShift = pickRandomNumber(true, shiftValue);

			layer2Temp.shiftLeftRight(randoShift, pointA, pointB);
		}

		for (int row = 0; row < pixels.length; row++)
		{
			for (int col = 0; col < pixels[0].length; col++)
			{
				int mergeRed = layer1[row][col].getRed() + layer2[row][col].getRed();
				int mergeGreen = layer1[row][col].getGreen() + layer2[row][col].getGreen();
				int mergeBlue = layer1[row][col].getBlue() + layer2[row][col].getBlue();

				pixels[row][col].setRed(mergeRed);
				pixels[row][col].setGreen(mergeGreen);
				pixels[row][col].setBlue(mergeBlue);
			}
		}
	}

	/**
	 * makes the image look slightly grainy
	 */
	public void grain()
	{
		grain(20);
	}

	/**
	 * increase/decrease each color channel for <i>every</i> pixel
	 * 
	 * @param hardness
	 *            the max value that will either be added or subtracted randomly to
	 *            each color channel of the pixel
	 */
	public void grain(int hardness)
	{
		Pixel[][] pixels = this.getPixels2D();

		if (hardness > 255)
		{
			hardness = 255;
		}

		for (Pixel[] rowArray : pixels)
		{
			for (Pixel currentPix : rowArray)
			{
				currentPix.setRed((currentPix.getRed() + (pickRandomNumber(true, hardness))));
				currentPix.setGreen((currentPix.getGreen() + (pickRandomNumber(true, hardness))));
				currentPix.setBlue((currentPix.getBlue() + (pickRandomNumber(true, hardness))));
			}
		}

	}

	/**
	 * increase/decrease each color channel for <i><b>every</b></i> pixel
	 * 
	 * @param hardness
	 *            the max value that will either be added or subtracted to each
	 *            color channel of the pixel
	 * @param exposure
	 *            makes it lighter or darker
	 *            <ul>
	 *            <li><b>0</b> = increase the color channel (makes it brighter)</li>
	 *            <li><b>1</b> = decreases the color channel (makes it darker)</li>
	 *            </ul>
	 */
	public void grain(int hardness, int exposure)
	{
		Pixel[][] pixels = this.getPixels2D();
		int negative = 1;
		if (hardness > 255)
		{
			hardness = 255;
		}
		if (exposure != 0)
		{
			negative = -1;
		}

		for (Pixel[] rowArray : pixels)
		{
			for (Pixel currentPix : rowArray)
			{
				currentPix.setRed((currentPix.getRed() + (pickRandomNumber(false, hardness) * negative)));
				currentPix.setGreen((currentPix.getGreen() + (pickRandomNumber(false, hardness) * negative)));
				currentPix.setBlue((currentPix.getBlue() + (pickRandomNumber(false, hardness) * negative)));
			}
		}
	}

	/**
	 * makes the image slightly noise
	 */
	public void noise()
	{
		int area = (this.getHeight() * this.getWidth());
		int percentage = (int) (Math.round(.20 * area));

		noise(20, percentage);
	}

	/**
	 * increase/decrease each color channel for <i><b>random</b></i> pixels
	 * 
	 * @param hardness
	 *            the max value that will either be added or subtracted to each
	 *            color channel of the pixel
	 */
	public void noise(int hardness, double noisePercent)
	{
		Pixel[][] pixels = this.getPixels2D();
		int negative = 1;

		if (hardness > 255)
		{
			hardness = 255;
		}

		noisePercent *= 0.01;
		int area = (this.getHeight() * this.getWidth());
		int percentage = (int) (Math.round(noisePercent * area));

		for (int cycles = 0; cycles < percentage; cycles++)
		{
			int randRow = (int) (Math.random() * pixels.length);
			int randCol = (int) (Math.random() * pixels[0].length);

			pixels[randRow][randCol].setRed((pixels[randRow][randCol].getRed() + (pickRandomNumber(true, hardness))));
			pixels[randRow][randCol].setGreen((pixels[randRow][randCol].getGreen() + (pickRandomNumber(true, hardness))));
			pixels[randRow][randCol].setBlue((pixels[randRow][randCol].getBlue() + (pickRandomNumber(true, hardness))));
		}
	}

	/**
	 * increase/decrease each color channel for <i><b>random</b></i> pixels
	 * 
	 * @param hardness
	 *            the max value that will either be added or subtracted to each
	 *            color channel of the pixel
	 * @param exposure
	 *            makes it lighter or darker
	 *            <ul>
	 *            <li><b>0</b> = increase the color channel (makes it brighter)</li>
	 *            <li><b>1</b> = decreases the color channel (makes it darker)</li>
	 *            </ul>
	 */
	public void noise(int hardness, int exposure, double noisePercent)
	{
		Pixel[][] pixels = this.getPixels2D();
		int negative = 1;

		if (hardness > 255)
		{
			hardness = 255;
		}

		if (exposure != 0)
		{
			negative = -1;
		}
		noisePercent *= 0.01;
		int area = (this.getHeight() * this.getWidth());
		int percentage = (int) (Math.round(noisePercent * area));

		for (int cycles = 0; cycles < percentage; cycles++)
		{
			int randRow = (int) (Math.random() * pixels.length);
			int randCol = (int) (Math.random() * pixels[0].length);

			pixels[randRow][randCol].setRed((pixels[randRow][randCol].getRed() + (pickRandomNumber(false, hardness) * negative)));
			pixels[randRow][randCol].setGreen((pixels[randRow][randCol].getGreen() + (pickRandomNumber(false, hardness) * negative)));
			pixels[randRow][randCol].setBlue((pixels[randRow][randCol].getBlue() + (pickRandomNumber(false, hardness) * negative)));
		}

	}

	/**
	 * Randomly makes a pixel become a certain color
	 * 
	 * @param baseColor
	 *            the color to change the pixel <i>(must be of the Color class)</i>
	 * @param noisePercent
	 *            how much of the image will be potential affected <br>
	 *            <i>(ex: 20 = 20% of the image could be changed)</i>
	 */
	public void noise(Color baseColor, double noisePercent)
	{
		Pixel[][] pixels = this.getPixels2D();

		noisePercent *= 0.01;
		int area = (this.getHeight() * this.getWidth());
		int percentage = (int) (Math.round(noisePercent * area));

		for (int cycles = 0; cycles < percentage; cycles++)
		{
			int randRow = (int) (Math.random() * pixels.length);
			int randCol = (int) (Math.random() * pixels[0].length);

			pixels[randRow][randCol].setColor(baseColor);

		}
	}

	public void dissolve(double noiseLevel)
	{
		Pixel[][] pixels = this.getPixels2D();

		noiseLevel *= 10000;

		for (int cycles = 0; cycles < noiseLevel; cycles++)
		{
			int randRow = (int) (Math.random() * pixels.length);
			int randCol = (int) (Math.random() * pixels[0].length);
			int randRow2 = (int) (Math.random() * pixels.length);
			int randCol2 = (int) (Math.random() * pixels[0].length);

			pixels[randRow][randCol].setColor(pixels[randRow2][randCol2].getColor());
		}
	}

	// <------Shifting------>
	/**
	 * shifts the image by a certain amount
	 * 
	 * @param amount
	 *            how much the image will shift <i>(in pixels)</i>
	 */
	public void shiftLeftRight(int amount)
	{
		shiftLeftRight(amount, 0, this.getHeight());
	}

	/**
	 * shifts a specific range by a certain amount
	 * 
	 * @param amount
	 *            how much the image will shift <i>(in pixels)</i>
	 * @param startPoint
	 *            the being point of the selection
	 * @param endPoint
	 *            the end of the selection
	 */
	public void shiftLeftRight(int amount, int startPoint, int endPoint)
	{
		Pixel[][] pixels = this.getPixels2D();
		Picture temp = new Picture(this);
		Pixel[][] copied = temp.getPixels2D();

		int width = pixels[0].length;
		int height = pixels.length;

		if (Math.abs(amount) > width)
		{
			if (amount < 0)
			{
				amount = -1 * (Math.abs(amount) % width);
			}
			else
			{
				amount = (amount % width);
			}
		}

		if (startPoint < 0)
		{
			startPoint = 0;
		}
		else if (startPoint > height)
		{
			startPoint = height;
		}

		if (endPoint < 0)
		{
			endPoint = 0;
		}
		else if (endPoint > height)
		{
			endPoint = height;
		}

		int shiftedValue = amount;

		for (int row = startPoint; row < endPoint; row++)
		{
			for (int col = 0; col < width; col++)
			{
				shiftedValue = (col + (amount + width)) % width;

				copied[row][col].setColor(pixels[row][shiftedValue].getColor());

			}
		}

		for (int row = 0; row < pixels.length; row++)
		{
			for (int col = 0; col < pixels[0].length; col++)
			{

				pixels[row][col].setColor(copied[row][col].getColor());

			}
		}
	}

	public void rowColor(int startPoint, int endPoint, int baseColor)
	{
		shiftLRColor(0, startPoint, endPoint, baseColor);
	}

	public void shiftLRColor(int amount, int startPoint, int endPoint, int baseColor)
	{
		Pixel[][] pixels = this.getPixels2D();
		Picture temp = new Picture(this);
		Pixel[][] copied = temp.getPixels2D();

		final int RED = 0;
		final int GREEN = 1;
		final int BLUE = 2;
		final int CYAN = 3;
		final int MAGENTA = 4;
		final int YELLOW = 5;

		if (baseColor > 2 || baseColor < 0)
		{
			baseColor = (int) (Math.random() * 3);
		}
		int width = pixels[0].length;
		int height = pixels.length;

		if (Math.abs(amount) > width)
		{
			if (amount < 0)
			{
				amount = -1 * (Math.abs(amount) % width);
			}
			else
			{
				amount = (amount % width);
			}
		}

		if (startPoint < 0)
		{
			startPoint = 0;
		}
		else if (startPoint > height)
		{
			startPoint = height;
		}

		if (endPoint < 0)
		{
			endPoint = 0;
		}
		else if (endPoint > height)
		{
			endPoint = height;
		}

		int shiftedValue = amount;

		for (int row = startPoint; row < endPoint; row++)
		{
			for (int col = 0; col < width; col++)
			{
				shiftedValue = (col + (amount + width)) % width;

				copied[row][col].setColor(pixels[row][shiftedValue].getColor());
				if (baseColor == RED)
				{
					copied[row][col].setBlue(0);
					copied[row][col].setGreen(0);
				}
				else if (baseColor == GREEN)
				{
					copied[row][col].setBlue(0);
					copied[row][col].setRed(0);
				}
				else if (baseColor == BLUE)
				{
					copied[row][col].setRed(0);
					copied[row][col].setGreen(0);
				}
				else if (baseColor == CYAN)
				{
					copied[row][col].setRed(0);
				}
				else if (baseColor == MAGENTA)
				{
					copied[row][col].setGreen(0);
				}
				else if (baseColor == YELLOW)
				{
					copied[row][col].setBlue(0);
				}
			}
		}

		for (int row = 0; row < pixels.length; row++)
		{
			for (int col = 0; col < pixels[0].length; col++)
			{

				pixels[row][col].setColor(copied[row][col].getColor());

			}
		}
	}

	public void shiftUpDown(int amount)
	{
		shiftUpDown(amount, 0, this.getWidth());
	}

	public void shiftUpDown(int amount, int startPoint, int endPoint)
	{
		Pixel[][] pixels = this.getPixels2D();
		Picture temp = new Picture(this);
		Pixel[][] copied = temp.getPixels2D();

		int height = pixels.length;
		int width = pixels[0].length;

		if (Math.abs(amount) > height)
		{
			if (amount < 0)
			{
				amount = -1 * (Math.abs(amount) % height);
			}
			else
			{
				amount = (amount % height);
			}
		}
		if (startPoint < 0)
		{
			startPoint = 0;
		}
		else if (startPoint >= width)
		{
			startPoint = width;
		}

		if (endPoint < 0)
		{
			endPoint = 0;
		}
		else if (endPoint >= width)
		{
			endPoint = width;
		}

		int shiftedValue = amount;

		for (int row = 0; row < height; row++)
		{
			for (int col = startPoint; col < endPoint; col++)
			{
				shiftedValue = (row + (amount + height)) % height;

				copied[row][col].setColor(pixels[shiftedValue][col].getColor());

			}
		}
		for (int row = 0; row < pixels.length; row++)
		{
			for (int col = 0; col < pixels[0].length; col++)
			{

				pixels[row][col].setColor(copied[row][col].getColor());

			}
		}
	}

	public void colColor(int startPoint, int endPoint, int baseColor)
	{
		shiftUDColor(0, startPoint, endPoint, baseColor);
	}

	public void shiftUDColor(int amount, int startPoint, int endPoint, int baseColor)
	{
		Pixel[][] pixels = this.getPixels2D();
		Picture temp = new Picture(this);
		Pixel[][] copied = temp.getPixels2D();

		final int RED = 0;
		final int GREEN = 1;
		final int BLUE = 2;
		final int CYAN = 3;
		final int MAGENTA = 4;
		final int YELLOW = 5;

		if (baseColor > 2 || baseColor < 0)
		{
			baseColor = (int) (Math.random() * 3);
		}
		int width = pixels[0].length;
		int height = pixels.length;

		if (Math.abs(amount) > height)
		{
			if (amount < 0)
			{
				amount = -1 * (Math.abs(amount) % height);
			}
			else
			{
				amount = (amount % height);
			}
		}

		if (startPoint < 0)
		{
			startPoint = 0;
		}
		else if (startPoint > width)
		{
			startPoint = width;
		}

		if (endPoint < 0)
		{
			endPoint = 0;
		}
		else if (endPoint > width)
		{
			endPoint = width;
		}

		int shiftedValue = amount;

		for (int row = 0; row < height; row++)
		{
			for (int col = startPoint; col < endPoint; col++)
			{
				shiftedValue = (row + (amount + height)) % height;

				copied[row][col].setColor(pixels[shiftedValue][col].getColor());
				if (baseColor == RED)
				{
					copied[row][col].setBlue(0);
					copied[row][col].setGreen(0);
				}
				else if (baseColor == GREEN)
				{
					copied[row][col].setBlue(0);
					copied[row][col].setRed(0);
				}
				else if (baseColor == BLUE)
				{
					copied[row][col].setRed(0);
					copied[row][col].setGreen(0);
				}
				else if (baseColor == CYAN)
				{
					copied[row][col].setRed(0);
				}
				else if (baseColor == MAGENTA)
				{
					copied[row][col].setGreen(0);
				}
				else if (baseColor == YELLOW)
				{
					copied[row][col].setBlue(0);
				}
			}
		}

		for (int row = 0; row < pixels.length; row++)
		{
			for (int col = 0; col < pixels[0].length; col++)
			{

				pixels[row][col].setColor(copied[row][col].getColor());

			}
		}
	}

	// <------Mirroring----->
	/**
	 * Method that mirrors the picture around a vertical mirror in the center of the
	 * picture from left to right
	 */
	public void mirrorVertical()
	{
		Pixel[][] pixels = this.getPixels2D();
		Pixel leftPixel = null;
		Pixel rightPixel = null;
		int width = pixels[0].length;
		for (int row = 0; row < pixels.length; row++)
		{
			for (int col = 0; col < width / 2; col++)
			{
				leftPixel = pixels[row][col];
				rightPixel = pixels[row][width - 1 - col];
				rightPixel.setColor(leftPixel.getColor());
			}
		}
	}

	public void mirrorHorizontal()
	{
		Pixel[][] pixels = this.getPixels2D();
		Pixel topPixel = null;
		Pixel bottomPixel = null;
		int height = pixels.length;
		for (int row = 0; row < height / 2; row++)
		{
			for (int col = 0; col < pixels[0].length; col++)
			{
				topPixel = pixels[row][col];
				bottomPixel = pixels[height - 1 - row][col];
				bottomPixel.setColor(topPixel.getColor());
			}
		}
	}

	/** Mirrors in from right to left */
	public void reverseMirrorVertical()
	{
		Pixel[][] pixels = this.getPixels2D();
		Pixel leftPixel = null;
		Pixel rightPixel = null;
		int width = pixels[0].length;
		for (int row = 0; row < pixels.length; row++)
		{
			for (int col = 0; col < width / 2; col++)
			{
				leftPixel = pixels[row][col];
				rightPixel = pixels[row][width - 1 - col];
				leftPixel.setColor(rightPixel.getColor());
			}
		}
	}

	public void bleed(int point, int direction)
	{
		Pixel[][] pixels = this.getPixels2D();
		Pixel bleedPixel = null;
		int startPoint;
		int endPoint;
		if (direction > 0)
		{
			startPoint = point;
			endPoint = pixels[0].length;
		}
		else
		{
			startPoint = 0;
			endPoint = point;
		}

		for (int row = 0; row < pixels.length; row++)
		{

			for (int col = startPoint; col < endPoint; col++)
			{
				bleedPixel = pixels[row][point];
				pixels[row][col].setColor(bleedPixel.getColor());
			}
		}
	}

	public void verticalBleed(int point, int direction)
	{
		Pixel[][] pixels = this.getPixels2D();
		Pixel bleedPixel = null;
		int startPoint;
		int endPoint;
		if (direction > 0)
		{
			startPoint = point;
			endPoint = pixels.length;
		}
		else
		{
			startPoint = 0;
			endPoint = point;
		}
		for (int row = startPoint; row < endPoint; row++)
		{

			for (int col = 0; col < pixels[0].length; col++)
			{
				bleedPixel = pixels[point][col];
				pixels[row][col].setColor(bleedPixel.getColor());
			}

		}
	}

	public void mirrorGull()
	{
		Pixel[][] pixels = this.getPixels2D();
		Pixel bird = null;
		Pixel birdClone = null;
		int birdCount = 0;
		while (birdCount < 4)
		{
			int birdRow = (int) (Math.random() * (pixels.length - 88));
			int birdCol = (int) (Math.random() * (pixels[0].length - 109));
			int birdTemp = birdCol;
			for (int row = 234; row < 322; row++)
			{
				birdRow++;
				for (int col = 237; col < 346; col++)
				{
					birdCol++;
					bird = pixels[row][col];
					birdClone = pixels[birdRow][birdCol];
					birdClone.setColor(bird.getColor());

				}
				birdCol = birdTemp;
			}

			birdCount++;
		}

	}

	/** Mirror just part of a picture of a temple */
	public void mirrorTemple()
	{
		int mirrorPoint = 276;
		Pixel leftPixel = null;
		Pixel rightPixel = null;
		int count = 0;
		Pixel[][] pixels = this.getPixels2D();

		// loop through the rows
		for (int row = 27; row < 97; row++)
		{
			// loop from 13 to just before the mirror point
			for (int col = 13; col < mirrorPoint; col++)
			{

				leftPixel = pixels[row][col];
				rightPixel = pixels[row][mirrorPoint - col + mirrorPoint];
				rightPixel.setColor(leftPixel.getColor());
			}
		}
	}

	/**
	 * copy from the passed fromPic to the specified startRow and startCol in the
	 * current picture
	 * 
	 * @param fromPic
	 *            the picture to copy from
	 * @param startRow
	 *            the start row to copy to
	 * @param startCol
	 *            the start col to copy to
	 */
	public void copy(Picture fromPic, int startRow, int startCol)
	{
		Pixel fromPixel = null;
		Pixel toPixel = null;
		Pixel[][] toPixels = this.getPixels2D();
		Pixel[][] fromPixels = fromPic.getPixels2D();
		for (int fromRow = 0, toRow = startRow; fromRow < fromPixels.length && toRow < toPixels.length; fromRow++, toRow++)
		{
			for (int fromCol = 0, toCol = startCol; fromCol < fromPixels[0].length && toCol < toPixels[0].length; fromCol++, toCol++)
			{
				fromPixel = fromPixels[fromRow][fromCol];
				toPixel = toPixels[toRow][toCol];
				toPixel.setColor(fromPixel.getColor());
			}
		}
	}

	/** Method to create a collage of several pictures */
	public void createCollage()
	{
		Picture flower1 = new Picture("flower1.jpg");
		Picture flower2 = new Picture("flower2.jpg");
		this.copy(flower1, 0, 0);
		this.copy(flower2, 100, 0);
		this.copy(flower1, 200, 0);
		Picture flowerNoBlue = new Picture(flower2);
		flowerNoBlue.zeroBlue();
		this.copy(flowerNoBlue, 300, 0);
		this.copy(flower1, 400, 0);
		this.copy(flower2, 500, 0);
		this.mirrorVertical();
		this.write("collage.jpg");
	}

	/**
	 * Method to show large changes in color
	 * 
	 * @param edgeDist
	 *            the distance for finding edges
	 */
	public void edgeDetection(int edgeDist)
	{
		Pixel leftPixel = null;
		Pixel rightPixel = null;
		Pixel[][] pixels = this.getPixels2D();
		Color rightColor = null;
		for (int row = 0; row < pixels.length; row++)
		{
			for (int col = 0; col < pixels[0].length - 1; col++)
			{
				leftPixel = pixels[row][col];
				rightPixel = pixels[row][col + 1];
				rightColor = rightPixel.getColor();
				if (leftPixel.colorDistance(rightColor) > edgeDist)
					leftPixel.setColor(Color.BLACK);
				else
					leftPixel.setColor(Color.WHITE);
			}
		}
	}

	public void chromakey(Picture replacement, Color changeColor)
	{
		Pixel[][] mainPixels = this.getPixels2D();
		Pixel[][] replacementPixels = replacement.getPixels2D();

		for (int row = 0; row < mainPixels.length; row++)
		{
			for (int col = 0; col < mainPixels[0].length; col++)
			{
				if (mainPixels[row][col].colorDistance(changeColor) < 0)
				{
					mainPixels[row][col].setColor(replacementPixels[row][col].getColor());
				}
			}

		}
	}

	public void hidePicture(Picture hidden)
	{
		Pixel[][] pixels = this.getPixels2D();
		Pixel[][] hiddenPixels = hidden.getPixels2D();

		for (int row = 0; row < pixels.length && row < hiddenPixels.length; row++)
		{
			for (int col = 0; col < pixels[0].length && col < hiddenPixels[0].length; col++)
			{
				// there is a message to hide
				if (hiddenPixels[row][col].colorDistance(Color.WHITE) > 5)
				{
					if (pixels[row][col].getRed() > 0 && pixels[row][col].getRed() % 2 != 1)
					{
						pixels[row][col].setRed(pixels[row][col].getRed() - 1);
					}
				}
				else if (pixels[row][col].getRed() > 0 && pixels[row][col].getRed() % 2 == 1)
				{
					pixels[row][col].setRed(pixels[row][col].getRed() - 1);
				}
			}
		}
	}

	public void revealPicture()
	{
		Pixel[][] pixels = this.getPixels2D();

		for (int row = 0; row < pixels.length; row++)
		{
			for (int col = 0; col < pixels[0].length; col++)
			{
				if (pixels[row][col].getRed() % 2 != 1)
				{
					pixels[row][col].setColor(new Color(255, 0, 68));
				}
				else if (pixels[row][col].getRed() % 2 == 1)
				{
					pixels[row][col].setColor(new Color(0, 255, 187));
				}
			}
		}
	}

	/*
	 * Main method for testing - each class in Java can have a main method
	 */
	public static void main(String[] args)
	{
		Picture beach = new Picture("wall turtle.jpg");
		beach.explore();
		beach.glitch();
		beach.explore();

	}

} // this } is the end of class Picture, put all new methods before this
