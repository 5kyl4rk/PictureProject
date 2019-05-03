package pix.controller;

import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import pix.view.GlitchFrame;
import pixLab.classes.*;

public class PixController
{
	private Picture activeImage;
	private Picture originalImage;
	private String recentLoadPath;
	private String recentSavePath;
	private String extension;
	private ArrayList<Picture> editLog;
	private GlitchFrame appFrame;
	private Dimension currentSize;
	private String startPath;
	private final int MAX_MEMORY = 5; //in theory, this could be a greater number, but it has to stop somewhere
	private boolean fileLoaded;

	public PixController()
	{

		startPath = System.getProperty("user.dir");
		recentLoadPath = startPath + "/src/pixLab/images";
		recentSavePath = startPath + "/savedImages/";
		fileLoaded = false;
		extension = ".jpg";
		currentSize = new Dimension();
		editLog = new ArrayList<Picture>();
		appFrame = new GlitchFrame(this);

	}

	public void start()
	{

	}

	// ===== IO Handling =====
	/**
	 * Allows user to open an image file
	 */
	public void loadImage()
	{
		JFileChooser explorer = new JFileChooser(recentLoadPath);
		explorer.setDialogTitle("What image do you want to load?");
		int result = explorer.showOpenDialog(this.getFrame());
		if (result == JFileChooser.APPROVE_OPTION)
		{

			String fileName = explorer.getSelectedFile().getAbsolutePath();
			recentLoadPath = explorer.getCurrentDirectory().toString();
			activeImage = new Picture(fileName);
			originalImage = new Picture(activeImage);
			addToLog(originalImage);
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

			int result = explorer.showSaveDialog(this.getFrame());

			if (result == JFileChooser.APPROVE_OPTION)
			{
				String writeTo = explorer.getSelectedFile().getAbsolutePath();

				if (activeImage.write(writeTo))
				{
					recentSavePath = explorer.getCurrentDirectory().toString();
					JOptionPane.showMessageDialog(this.getFrame(), "Save successful");
				}
				else
				{
					JOptionPane.showMessageDialog(this.getFrame(), "Error saving");
				}
			}

		}
	}

	// ===== Image Altering =====
	/**
	 * Uses the {@link pixLab.classes.Picture#glitch() glitch()} method from Picture
	 */
	public void glitch()
	{
		activeImage.glitch();
		addToLog(activeImage);
		appFrame.updateDisplay();
	}

	/**
	 * Uses the {@link pixLab.classes.Picture#make3D(int, int, int) make3D()} method
	 * from Picture
	 */
	public void make3D(int shiftValue)
	{
		Picture temp = activeImage;
		if(shiftValue != 0)
		{
			temp.make3D(0, shiftValue, 0);
		}
		else
		{
			temp = getLastEdit();
		}
		
		this.setCurrentImage(temp);
		appFrame.updateDisplay();
	}

	private void addToLog(Picture editToAdd)
	{
		Picture temp = new Picture(editToAdd);
		editLog.add(0, temp);

		if (editLog.size() > MAX_MEMORY)
		{
			editLog.remove(getLogSize() - 1);
		}
	}
	
	public void clearLog()
	{
		for(int index = 0; index < editLog.size(); index++)
		{
			editLog.remove(index);
		}
	}

	public void updateDisplay()
	{
		appFrame.updateDisplay();
	}
	
	public void recenter()
	{
		appFrame.recenter();
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
		return editLog.get(0);
	}

	public Picture getLastEdit(int index)
	{
		if (index < 0)
		{
			index = 0;
		}
		else if (index >= editLog.size())
		{
			index = editLog.size() - 1;
		}

		return editLog.get(index);
	}

	public int getLogSize()
	{
		return editLog.size();
	}

	public boolean isFileLoaded()
	{
		return fileLoaded;
	}

	public void setCurrentImage(Picture imageToDisplay)
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
