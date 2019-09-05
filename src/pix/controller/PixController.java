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
	private EditStack editStack;
	private EditProfile currentProfile;
	private GlitchFrame appFrame;
	private Dimension currentImageSize;
	private Dimension minimumFrameSize;
	// EditStack will only hold information, which shouldn't cause too much data
	// overflow
	// TODO: set a cap for EditStack (100 should be enough?)
	private int maxMemory;
	private boolean fileLoaded;
	private String pictureTitle;

	/**
	 * The main controller, handles all access between classes
	 */
	public PixController()
	{
		print = new BasicDebug();
		editStack = new EditStack(this);
		currentProfile =(EditProfile) new OriginalProfile();
		appFrame = new GlitchFrame(this);
		currentImageSize = new Dimension();
		minimumFrameSize = new Dimension(appFrame.getToolPanelSize());
		appIO = new IOController(this);
		print.setState(false);

		fileLoaded = false;
		pictureTitle = "owo";

		appIO.loadConfig();

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

	public Picture recreateEdits(int stackIndex)
	{
		Picture temp = originalImage;

		for (int index = 0; index <= stackIndex; index++)
		{
			temp = this.applyEditProfile(editStack.getLastEdit(index), temp);
		}

		return temp;
	}

	public Picture applyEditProfile(EditProfile profile, Picture imageToApply)
	{
		Picture temp = imageToApply;

		switch (profile.getType())
		{
		case EditProfile.GLITCH:
			temp.glitch();
			break;
		case EditProfile.BLEED:
			temp = this.bleed(temp, ((BleedProfile) profile).getStartPoint(), ((BleedProfile) profile).getDirection());
			break;
		case EditProfile.MAKE3D:
			temp = this.make3D(temp, ((Make3DProfile) profile).getXAxis(), ((Make3DProfile) profile).getYAxis(), ((Make3DProfile) profile).getBaseColor());
			break;
		case EditProfile.SCANLINES:
			temp = this.scanline(temp,((ScanlineProfile) profile).getThickness(), ((ScanlineProfile) profile).getSpread(), ((ScanlineProfile) profile).getColor(),
					((ScanlineProfile) profile).getDirection());
			break;
		case EditProfile.GRAIN:
			temp = this.grain(temp,((GrainProfile) profile).getHardness());
		default:
			break;
		}

		return temp;
	}


	/**
	 * Uses the {@link pixLab.classes.Picture#make3D(int, int, int) make3D()} method
	 * from Picture
	 */
	public Picture make3D(Picture imageToEdit, int shiftX, int shiftY, int color)
	{
		imageToEdit.make3D(color, shiftX, 0);
		imageToEdit.make3D(color, shiftY, 1);
		return imageToEdit;
	}

	/**
	 * Uses the {@link pixLab.classes.Picture#scanlines(int, int, Color)
	 * scanlines()} method and its varieties from Picture
	 */
	public Picture scanline(Picture imageToEdit, int thickness, int spread, Color color, int type)
	{
		if (type == ScanlineProfile.VERTICAL)
		{
			imageToEdit.verticalScanlines(spread, thickness, color);

		}
		else if (type == ScanlineProfile.LCD)
		{
			imageToEdit.lcd(spread, thickness, color);
		}
		else
		{
			imageToEdit.scanlines(spread, thickness, color);
		}

		//DEBUG STUFF
		print("app spread: " + spread);
		print("app thickness: " + thickness);
		
		return imageToEdit;

		
	}

	/**
	 * Uses the {@link pixLab.classes.Picture#grain(int, int) grain()} method to
	 * make the image look grainy
	 */
	public Picture grain(Picture imageToEdit, int hardness)
	{
		int expose = 0;
		if (hardness < 0)
		{
			expose = 1;
		}
		imageToEdit.grain(hardness, expose);
	
		return imageToEdit;

	}

	/**
	 * <i><b>Not Implemented Yet</b></i><br>
	 * Uses the {@link pixLab.classes.Picture#noise(Color, double) noise()} method
	 * to make the image have 'noise'
	 */
	public Picture noise(Picture imageToEdit, int hardness, int percent, Color color)
	{

		imageToEdit.noise(color, (double) percent);
		return imageToEdit;

	}

	/**
	 * Uses the {@link pixLab.classes.Picture#bleed(int, int) bleed()} method to
	 * stretch a section of the image
	 */
	public Picture bleed(Picture imageToEdit, int point, int direction)
	{
		if (direction == BleedProfile.LEFT || direction == BleedProfile.RIGHT)
		{
			imageToEdit.bleed(point, direction);
		}
		else
		{
			imageToEdit.verticalBleed(point, direction);
		}
		
		return imageToEdit;

	}
	
	public GlitchProfile createGlitchProfile()
	{
		GlitchProfile temp = new GlitchProfile();
		return temp;
	}
	
	public Make3DProfile createMake3DProfile(int shiftX, int shiftY, int color)
	{
		Make3DProfile temp = new Make3DProfile(shiftX,shiftY,color);
		return temp;
	}
	
	public ScanlineProfile createScanlineProfile(int thickness, int spread, Color color, int type)
	{
		ScanlineProfile temp = new ScanlineProfile(thickness,spread,type,color);
		return temp;
	}
	
	public BleedProfile createBleedProfile(int point, int direction)
	{
		BleedProfile temp = new BleedProfile(point, direction);
		return temp;
	}
	
	public GrainProfile createGrainProfile(int hardness)
	{
		GrainProfile temp = new GrainProfile(hardness);
		return temp;
	}

	// ==== Stack Management ===
	/**
	 * adds an image to the stack
	 * 
	 * @param editToAdd
	 *            image to add to the stack
	 */
	public void addToStack(EditProfile editToAdd)
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
	public void addToStack(int index, EditProfile editToAdd)
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
	
	public EditProfile getCurrentProfile()
	{
		return currentProfile;
	}

	public Picture getOriginal()
	{
		return originalImage;
	}

	public EditProfile getLastEdit()
	{
		return getLastEdit(editStack.getCurrentIndex());
	}

	public EditProfile getLastEdit(int stackIndex)
	{
		return editStack.getLastEdit(stackIndex);
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
	
	public void setCurrentProfile(EditProfile profile)
	{
		currentProfile = profile;
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

	public EditStack getStack()
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
