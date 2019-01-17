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

	private int pickRandomNumber(boolean negativeIsOK, int max)
	{
		
		final int COIN_FLIP = (int)(Math.random() * 2);
		int randomNumber = (int)(Math.random() * max);
		if(negativeIsOK)
		{
			if(COIN_FLIP == 1)
			{
				randomNumber *= -1;
			}
		}
		
		return randomNumber;
	}

	public void glitch()
	{
		int pickColor = (int) ((Math.random() * 100) % 6);
		int width = this.getWidth();
		this.make3D(pickColor,pickRandomNumber(true,width));
		
	}

	// <------Overlays------>
	public void scanlines()
	{
		scanlines(1, 1);
	}

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

	public void verticalScanlines()
	{
		verticalScanlines(1, 1);
	}

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

	public void lcd()
	{
		lcd(1, 1);
	}

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

	public void lcd(int spread, int thickness, Color shading)
	{
		this.scanlines(spread, thickness, shading);
		this.verticalScanlines(spread, thickness, shading);
	}
	
	public void make3D(int baseColor)
	{
		make3D(baseColor, pickRandomNumber(true, 10));
	}

	public void make3D(int baseColor, int shift)
	{
		final int RED = 0;
		final int GREEN = 1;
		final int BLUE = 2;
		final int CYAN = 3;
		final int MAGENTA = 4;
		final int YELLOW = 5; 
		
		if(baseColor > 5 || baseColor < 0)
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

		layer2Temp.shiftLeftRight(shift);

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
	// <------Shifting------>
	public void shiftLeftRight(int amount)
	{
		Pixel[][] pixels = this.getPixels2D();
		Picture temp = new Picture(this);
		Pixel[][] copied = temp.getPixels2D();

		int width = pixels[0].length;
		
		if (Math.abs(amount) > width)
		{
			if (amount < 0)
			{
				amount = (-1 * Math.abs(amount % width));
			}
			else
			{
				amount = (amount % width);
			}
		}
		
		int shiftedValue = amount;

		for (int row = 0; row < pixels.length; row++)
		{
			for (int col = 0; col < pixels[0].length; col++)
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

	public void shiftUpDown(int amount)
	{
		Pixel[][] pixels = this.getPixels2D();
		Picture temp = new Picture(this);
		Pixel[][] copied = temp.getPixels2D();

		int height = pixels.length;
		if (Math.abs(amount) > height)
		{
			if (amount < 0)
			{
				amount = (-1 * Math.abs(amount % height));
			}
			else
			{
				amount = (amount % height);
			}
		}
		
		int shiftedValue = amount;
		
		for (int row = 0; row < pixels.length; row++)
		{
			for (int col = 0; col < pixels[0].length; col++)
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
	
//<------Mirroring----->
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
		if(direction == 1)
		{
			startPoint = point;
			endPoint = pixels[0].length;
		}
		else
		{
			startPoint = 0;
			endPoint = point;
		}
		
		for(int row = 0; row < pixels.length; row++)
		{
	
			for(int col = startPoint; col < endPoint; col++)
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
		if(direction == 1)
		{
			startPoint = point;
			endPoint = pixels.length;
		}
		else
		{
			startPoint = 0;
			endPoint = point;
		}
		for(int row = startPoint; row < endPoint; row++)
		{
			
			for(int col = 0; col < pixels[0].length; col++)
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

	/*
	 * Main method for testing - each class in Java can have a main method
	 */
	public static void main(String[] args)
	{
		Picture beach = new Picture("beach.jpg");
		beach.explore();
		beach.bleed(20,0);
		beach.verticalBleed(20,0);
		beach.glitch();
		beach.explore();
	}

} // this } is the end of class Picture, put all new methods before this
