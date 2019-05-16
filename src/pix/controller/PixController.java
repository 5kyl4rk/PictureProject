package pix.controller;

import java.awt.Color;
import java.awt.Dimension;
import pix.view.GlitchFrame;
import pixLab.classes.*;
import pix.model.*;

public class PixController
{
	private BasicDebug print;
	IOController appIO;
	private Picture activeImage;
	private Picture originalImage;
	/**
	 * index 0 is the top of the stack
	 */
	private ImageStack editStack;
	private GlitchFrame appFrame;
	private Dimension currentImageSize;
	private Dimension minimumFrameSize;
	private int maxMemory;
	private boolean fileLoaded;
	private String pictureTitle;

	/**
	 * The main controller, handles all access between classes
	 */
	public PixController()
	{
		print = new BasicDebug();
		appFrame = new GlitchFrame(this);
		currentImageSize = new Dimension();
		minimumFrameSize = new Dimension(appFrame.getToolPanelSize());
		appIO = new IOController(this);
		print.setState(true);

		fileLoaded = false;
		pictureTitle = "owo";

		appIO.loadConfig();
		if(appIO.canRestore())
		{
			editStack = appIO.loadStack();
		}
		else
		{
			editStack = new ImageStack(maxMemory,this);

		}
		appFrame.setMinimumSize(getMinimumSize());
		appFrame.setVisible(true);

	}

	/**
	 * the method that gets called be the runner, would normally contain code that
	 * would run on startup, but since the methods are being called by JPanels, it
	 * will most likely remain empty
	 */
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
		Picture temp = new Picture(getLastEdit(getCurrentStackIndex()));
		temp.glitch();
		addToStack(getCurrentStackIndex(), temp);
		this.setCurrentImage(temp);
		appFrame.updateDisplay();
	}

	/**
	 * Uses the {@link pixLab.classes.Picture#make3D(int, int, int) make3D()} method
	 * from Picture
	 */
	public void make3D(int shiftX, int shiftY, int color)
	{
		Picture temp = new Picture(getLastEdit(getCurrentStackIndex()));
		temp.make3D(color, shiftX, 0);
		temp.make3D(color, shiftY, 1);
		this.setCurrentImage(temp);
		appFrame.updateDisplay();
	}

	/**
	 * Uses the {@link pixLab.classes.Picture#scanlines(int, int, Color)
	 * scanlines()} method and its varieties from Picture
	 */
	public void scanline(int thickness, int spread, Color color, int type)
	{
		Picture temp = new Picture(getLastEdit(getCurrentStackIndex()));
		if (type == 1)
		{
			temp.verticalScanlines(spread, thickness, color);

		}
		else if (type == 2)
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
	
	public void grain(int hardness)
	{
		Picture temp = new Picture(getLastEdit(getCurrentStackIndex()));
		int expose = 0;
		if(hardness < 0)
		{
			expose = 1;
		}
		temp.grain(hardness,expose);
		this.setCurrentImage(temp);
		appFrame.updateDisplay();

	}
	
	public void noise(int hardness, int percent, Color color)
	{
		Picture temp = new Picture(getLastEdit(getCurrentStackIndex()));
		temp.noise(color,(double) percent);
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
		editStack.addToStack(index, editToAdd);
	}

	/**
	 * sets the currentStackindex back to the top (aka index = 0)
	 */
	public void restartStackIndex()
	{
		editStack.restartStackIndex();
	}

	/**
	 * traverse up the stack
	 */
	public void goUpStack()
	{
		editStack.goUpStack();
	}

	/**
	 * traverse down the stack
	 */
	public void goDownStack()
	{
		editStack.goDownStack();
	}

	/**
	 * clears the stack, but adds back in the original image
	 */
	public void restartStack()
	{
		editStack.restartStack();
	}

	/**
	 * Clear the stack
	 */
	public void clearStack()
	{
		editStack.clearStack();
	}

	// ==== View Methods ====
	/**
	 * calls {@link pix.view.GlitchFrame#updateDisplay()
	 * GlitchFrame.updateDisplay()} to "refresh" the panel
	 */
	public void updateDisplay()
	{
		appFrame.updateDisplay();
	}

	/**
	 * Moves the frame back to the center of the screen
	 */
	public void recenter()
	{
		appFrame.recenter();
	}

	/**
	 * a very basic console printer for quick debugging
	 * 
	 * @param words
	 *            the phrase to print on console
	 */
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
		return editStack.getLastEdit();
	}

	public Picture getLastEdit(int index)
	{
		return editStack.getLastEdit(index);
	}

	public int getStackSize()
	{
		return editStack.getSize();
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
		return editStack.getCurrentIndex();
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
		if (value > 25)
		{
			maxMemory = 25;
		}
		else if (value <= 0)
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
	
	public ImageStack getStack()
	{
		return editStack;
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
		minimumFrameSize = new Dimension(width, height);
	}
}
