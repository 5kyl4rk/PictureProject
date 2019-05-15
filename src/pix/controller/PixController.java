package pix.controller;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import pix.view.GlitchFrame;
import pixLab.classes.*;

public class PixController
{
	private BasicDebug print;
	IOController appIO;
	private Picture activeImage;
	private Picture originalImage;
	/**
	 * index 0 is the top of the stack
	 */
	private ArrayList<Picture> editStack;
	private GlitchFrame appFrame;
	private Dimension currentImageSize;
	private Dimension minimumFrameSize;
	private int maxMemory; 
	private boolean fileLoaded;
	private int logTracker;
	private int currentStackIndex;
	private String pictureTitle;

	public PixController()
	{

		
		fileLoaded = false;
		pictureTitle = "owo";
		print = new BasicDebug();
		appFrame = new GlitchFrame(this);
		currentImageSize = new Dimension();
		minimumFrameSize = new Dimension();
		editStack = new ArrayList<Picture>();
		appIO = new IOController(this);
		print.setState(true);
		currentStackIndex = 0;
		logTracker = 0;
		appIO.loadConfig();
		appFrame.setMinimumSize(getMinimumSize());
		appFrame.setVisible(true);

	}

	public void start()
	{
		
	}

	// ==== IO Handling ====
	/**
	 * Loads an image<br>
	 * calls {@link pix.controller.IOController#loadImage() loadImage()}
	 */
	public void loadImage()
	{
		appIO.loadImage();
	}
	
	/**
	 * Saves an image<br>
	 * calls {@link pix.controller.IOController#saveImage() saveImage}
	 */
	public void saveImage()
	{
		appIO.saveImage();
	}

	// ===== Image Altering =====
	/**
	 * 
	 * Uses the {@link pixLab.classes.Picture#glitch() glitch()} method from Picture
	 */
	public void glitch()
	{
		Picture temp = new Picture(getLastEdit(currentStackIndex));
		temp.glitch();
		addToStack(currentStackIndex, temp);
		this.setCurrentImage(temp);
		appFrame.updateDisplay();
	}

	/**
	 * Uses the {@link pixLab.classes.Picture#make3D(int, int, int) make3D()} method
	 * from Picture
	 */
	public void make3D(int shiftX, int shiftY, int color)
	{
		Picture temp = new Picture(getLastEdit(currentStackIndex));
		temp.make3D(color, shiftX, 0);
		temp.make3D(color, shiftY, 1);
		this.setCurrentImage(temp);
		appFrame.updateDisplay();
	}

	public void scanline(int thickness, int spread, Color color, int type)
	{
		Picture temp = new Picture(getLastEdit(currentStackIndex));
		if (type == 1)
		{
			temp.verticalScanlines(spread, thickness, color);

		}
		else if(type == 2)
		{
			temp.lcd(spread, thickness, color);
		}
		else
		{
			temp.scanlines(spread, thickness, color);
		}
		
		this.setCurrentImage(temp);
		appFrame.updateDisplay();
	}

	// ==== Stack Management ===
	/**
	 * adds an image to the stack
	 * 
	 * @param editToAdd
	 *            image to add to the stack
	 */
	public void addToStack(Picture editToAdd)
	{
		addToStack(0, editToAdd);
	}

	/**
	 * adds an image to a specific index of the stack and removes anything on top of
	 * it, making it the last placed item in the stack
	 * 
	 * @param index
	 *            the slot where the image needs to go
	 * @param editToAdd
	 *            the image to add to the stack
	 */
	public void addToStack(int index, Picture editToAdd)
	{
		Picture temp = new Picture(editToAdd);

		temp.setTitle("temp" + logTracker);
		logTracker++;
		
		editStack.add(index, temp);

		for (int stackIndex = index - 1; stackIndex >= 0; stackIndex--)
		{
			editStack.remove(stackIndex);
		}

		

		if (editStack.size() >= maxMemory)
		{
			editStack.remove(getStackSize() - 1);
		}
	}

	/**
	 * sets the currentStackindex back to the top (aka index = 0)
	 */
	public void restartStackIndex()
	{
		currentStackIndex = 0;
	}

	/**
	 * traverse up the stack
	 */
	public void goUpStack()
	{
		currentStackIndex--;
	}

	/**
	 * traverse down the stack
	 */
	public void goDownStack()
	{
		currentStackIndex++;
	}

	/**
	 * clears the stack, but adds back in the original image
	 */
	public void restartStack()
	{
		clearStack();
		restartStackIndex();
		editStack.add(originalImage);
	}

	/**
	 * Clear the stack
	 */
	public void clearStack()
	{
		for (int index = 0; index < editStack.size(); index++)
		{
			editStack.remove(index);
		}
	}

	// ==== View Methods ====
	public void updateDisplay()
	{
		appFrame.updateDisplay();
	}

	public void recenter()
	{
		appFrame.recenter();
	}


	public void print(String words)
	{
		print.out(words);
	}

	// ===== Get/Set =====


	public Picture getCurrentImage()
	{
		return activeImage;
	}

	public Picture getOriginal()
	{
		return originalImage;
	}

	public Picture getLastEdit()
	{
		return editStack.get(0);
	}

	public Picture getLastEdit(int index)
	{
		if (index < 0)
		{
			index = 0;
		}
		else if (index >= editStack.size())
		{
			index = editStack.size() - 1;
		}

		return editStack.get(index);
	}

	public int getStackSize()
	{
		return editStack.size();
	}


	public String getPictureTitle()
	{
		return pictureTitle;
	}

	public boolean isFileLoaded()
	{
		return fileLoaded;
	}

	public Dimension getPictureSize()
	{
		currentImageSize.setSize(activeImage.getWidth(), activeImage.getHeight());
		return currentImageSize;
	}

	public int getCurrentStackIndex()
	{
		return currentStackIndex;
	}

	public void setFileLoaded(boolean state)
	{
		fileLoaded = state;
	}

	public void setCurrentImage(Picture imageToDisplay)
	{
		activeImage = new Picture(imageToDisplay);
	}

	public void setCurrentImage(String imageToLoad)
	{
		activeImage = new Picture(imageToLoad);
	}

	public void setOriginalImage(Picture image)
	{
		originalImage = new Picture(image);
	}

	public void setOriginalImage(String imageToLoad)
	{
		originalImage = new Picture(imageToLoad);
	}

	public void setPictureTitle(String name)
	{
		pictureTitle = name;
	}
	
	public void setMaxMemory(int value)
	{
		if(value > 25)
		{
			maxMemory = 25;
		}
		else if(value <= 0)
		{
			maxMemory = 2;
		}
		else
		{
			maxMemory = value;
		}
	}

	public GlitchFrame getFrame()
	{
		return appFrame;
	}
	
	public Dimension getToolPanelSize()
	{
		return appFrame.getToolPanelSize();
	}
	
	public Dimension getMinimumSize()
	{
		return minimumFrameSize;
	}
	
	public void setMinimumSize(int width, int height)
	{
		minimumFrameSize = new Dimension(width,height);
	}
}
