package pix.controller;

import java.awt.Dimension;
import java.io.*;
import java.util.ArrayList;
import pix.view.GlitchFrame;
import pixLab.classes.*;

public class PixController
{
	private BasicDebug print;
	private IOController appIO;
	private Picture activeImage;
	private Picture originalImage;
	private String recentLoadPath;
	private String recentSavePath;
	private String extension;
	/**
	 * index 0 is the top of the stack
	 */
	private ArrayList<Picture> editStack;
	private GlitchFrame appFrame;
	private Dimension currentSize;
	private String startPath;
	private final int MAX_MEMORY = 6; // in theory, this could be a greater number, but it has to stop somewhere
	private boolean fileLoaded;
	private int logTracker;
	private int currentStackIndex;
	private String pictureTitle;

	public PixController()
	{

		startPath = System.getProperty("user.dir");
		recentLoadPath = startPath + "/src/pixLab/images";
		recentSavePath = startPath + "/savedImages/";
		fileLoaded = false;
		pictureTitle = "owo";
		extension = ".jpg";
		currentSize = new Dimension();
		editStack = new ArrayList<Picture>();
		appFrame = new GlitchFrame(this);
		appIO = new IOController(this);
		print = new BasicDebug();
		print.setState(true);
		currentStackIndex = 0;
		logTracker = 0;

	}

	public void start()
	{

	}

	// ==== IO Handling ====
	public void loadImage()
	{
		appIO.loadImage();
	}

	public void saveImage()
	{
		appIO.saveImage();
	}

	// ===== Image Altering =====
	/**
	 * Uses the {@link pixLab.classes.Picture#glitch() glitch()} method from Picture
	 */
	public void glitch()
	{
		Picture temp = new Picture(activeImage);
		temp.glitch();
		addToStack(currentStackIndex, temp);
		this.setCurrentImage(temp);
		appFrame.updateDisplay();
	}

	/**
	 * Uses the {@link pixLab.classes.Picture#make3D(int, int, int) make3D()} method
	 * from Picture
	 */
	public void make3D(int shiftValue)
	{
		Picture temp = new Picture(getLastEdit());
		temp.make3D(0, shiftValue, 0);
		this.setCurrentImage(temp);
		appFrame.updateDisplay();
	}

	// ==== Stack Management ===
	/**
	 * adds an image to the stack
	 * @param editToAdd image to add to the stack
	 */
	public void addToStack(Picture editToAdd)
	{
		addToStack(0,editToAdd);
	}

	/**
	 * adds an image to a specific index of the stack and removes anything on top of it,
	 * making it the last placed item in the stack
	 * @param index the slot where the image needs to go
	 * @param editToAdd the image to add to the stack
	 */
	public void addToStack(int index, Picture editToAdd)
	{
		Picture temp = new Picture(editToAdd);

		temp.setTitle("temp" + logTracker);
		logTracker++;

		for (int stackIndex = 0; stackIndex < index; stackIndex++)
		{
			editStack.remove(stackIndex);
		}

		editStack.add(0, temp);

		if (editStack.size() >= MAX_MEMORY)
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

	protected String findActualFileName(String path)
	{
		String directory = File.separator;
		int start = path.lastIndexOf(directory);
		int end = path.lastIndexOf(extension);
		String name = path.substring(start + 1, end);

		return name;
	}
	
	public void print(String words)
	{
		print.out(words);
	}

	// ===== Get/Set =====

	public String getRecentLoadPath()
	{
		return recentLoadPath;
	}

	public String getRecentSavePath()
	{
		return recentSavePath;
	}

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

	public String getExtension()
	{
		return extension;
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
		currentSize.setSize(activeImage.getWidth(), activeImage.getHeight());
		return currentSize;
	}
	
	public int getCurrentStackIndex()
	{
		return currentStackIndex;
	}

	public void setRecentSavePath(String path)
	{
		if (path.contains(File.separator))
		{
			recentSavePath = path;
		}

	}

	public void setRecentLoadPath(String path)
	{

		if (path.contains(File.separator))
		{
			recentLoadPath = path;
		}

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
	

	public void setExtension(String end)
	{
		if (end.trim().indexOf(".") == 0)
		{
			extension = end;
		}
	}

	public GlitchFrame getFrame()
	{
		return appFrame;
	}
}
