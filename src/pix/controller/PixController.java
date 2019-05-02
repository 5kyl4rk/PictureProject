package pix.controller;

import java.awt.Dimension;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import pix.view.GlitchFrame;
import pixLab.classes.*;

public class PixController
{
	private Picture activeImage;
	private Picture originalImage;
	private Picture alteredImage;
	private String recentLoadPath;
	private String recentSavePath;
	private String extension;
	private GlitchFrame appFrame;
	private Dimension currentSize;
	private Picture lastChange;
	private String startPath;
	private boolean fileLoaded;

	public PixController()
	{
		
		startPath = System.getProperty("user.dir");
		recentLoadPath = startPath+"/src/pixLab/images";
		recentSavePath = startPath+"/savedImages/";
		fileLoaded = false;
		extension = ".jpg";
		currentSize = new Dimension();
		appFrame = new GlitchFrame(this);
	

	}

	public void start()
	{

	}
	//===== IO Handling =====
	/**
	 * Allows user to open an image file
	 */
	public void loadImage()
	{
		JFileChooser explorer = new JFileChooser(recentLoadPath);
		explorer.setDialogTitle("What image do you want to load?");
		int result = explorer.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION)
		{
	
			String fileName = explorer.getSelectedFile().getAbsolutePath();
			recentLoadPath = explorer.getCurrentDirectory().toString();
			activeImage = new Picture (fileName);
			originalImage = new Picture(activeImage);
			extension = fileName.substring(fileName.lastIndexOf("."));
			appFrame.updateDisplay();
			fileLoaded = true;
		}

	}

	/**
	 * Saves the current image into a selected directory
	 */
	public void saveImage()
	{
		String[] option = { "Yes", "No" };
		int save = JOptionPane.showOptionDialog(null, "Do you want to save this image?", "Save?", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, option[0]);
		if (save == 0)
		{
			JFileChooser explorer = new JFileChooser(recentSavePath);
			explorer.setDialogTitle("Where do you want to save?");
			String nameGlitch = "-glitched" + extension;
			File saveFile = new File(nameGlitch);
			explorer.setSelectedFile(saveFile);

			int result = explorer.showSaveDialog(null);

			if (result == JFileChooser.APPROVE_OPTION)
			{
				String writeTo = explorer.getSelectedFile().getAbsolutePath();
				
				if (activeImage.write(writeTo))
				{
					recentSavePath = explorer.getCurrentDirectory().toString();
					JOptionPane.showMessageDialog(null, "Save successful");
				}
			}

		}
	}

	//===== Image Altering =====
	/**
	 * Uses the {@link pixLab.classes.Picture#glitch() glitch()} method from Picture
	 */
	public void glitch()
	{
		copyLastEdit();
		activeImage.glitch();
		alteredImage = new Picture(activeImage);
		appFrame.updateDisplay();
	}
	
	/**
	 * Uses the {@link pixLab.classes.Picture#make3D(int, int, int) make3D()} method from Picture
	 */
	public void make3D(int shiftValue)
	{
		activeImage.make3D(0,shiftValue,0);
		alteredImage = new Picture(activeImage);
		appFrame.updateDisplay();
	}
	
	private void copyLastEdit()
	{
		lastChange = new Picture(activeImage);
	}

	public void updateDisplay()
	{
		appFrame.updateDisplay();
	}

	//===== Get/Set =====
	public DigitalPicture getCurrentImage()
	{
		return activeImage;
	}

	public DigitalPicture getOriginal()
	{
		return originalImage;
	}

	public DigitalPicture getAltered()
	{
		return alteredImage;
	}

	public DigitalPicture getLastChange()
	{
		return lastChange;
	}
	
	public boolean isFileLoaded()
	{
		return fileLoaded;
	}

	public void setCurrentImage(DigitalPicture imageToDisplay)
	{
		activeImage = (Picture) imageToDisplay;
	}

	public Dimension getPictureSize()
	{
		currentSize.setSize(activeImage.getWidth(), activeImage.getHeight());
		return currentSize;
	}

	public GlitchFrame getFrame()
	{
		return appFrame;
	}
}
