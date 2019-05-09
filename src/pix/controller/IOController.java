package pix.controller;

import java.io.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import pixLab.classes.Picture;

public class IOController
{
	private PixController app;

	private FileFilter filter;

	/**
	 * handles loading and saving
	 * 
	 * @param app
	 */
	public IOController(PixController app)
	{
		this.app = app;
		filter = new ImageFilter();
	}

	// ===== IO Handling =====
	/**
	 * Allows user to open an image file
	 */
	public void loadImage()
	{
		JFileChooser explorer = new JFileChooser(app.getRecentLoadPath());
		explorer.setDialogTitle("What image do you want to load?");
		explorer.setFileFilter(filter);
		int result = explorer.showOpenDialog(app.getFrame());
		if (result == JFileChooser.APPROVE_OPTION)
		{

			String fileName = explorer.getSelectedFile().getAbsolutePath();
			app.setRecentLoadPath(explorer.getCurrentDirectory().toString());
			app.setCurrentImage(fileName);
			app.setOriginalImage(fileName);

			app.restartStack();

			app.setExtension(fileName.substring(fileName.lastIndexOf(".")));
			app.setPictureTitle(app.findActualFileName(fileName));

			app.updateDisplay();
			app.setFileLoaded(true);
		}

	}

	/**
	 * Saves the current image into a selected directory
	 */
	public void saveImage()
	{
		String[] option = {"No", "Yes" };
		int save = JOptionPane.showOptionDialog(null, "Do you want to save this image?", "Save?", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, option[0]);
		if (save == 1)
		{
			JFileChooser explorer = new JFileChooser(app.getRecentSavePath());
			explorer.setDialogTitle("Where do you want to save?");
			String nameGlitch = app.getPictureTitle() + "-glitched" + app.getExtension();
			File saveFile = new File(nameGlitch);
			explorer.setSelectedFile(saveFile);

			int result = explorer.showSaveDialog(app.getFrame());

			if (result == JFileChooser.APPROVE_OPTION)
			{
				String writeTo = explorer.getSelectedFile().getAbsolutePath();

				if (app.getCurrentImage().write(writeTo))
				{
					app.setRecentSavePath(explorer.getCurrentDirectory().toString());
					JOptionPane.showMessageDialog(app.getFrame(), "Save successful");
				}
				else
				{
					JOptionPane.showMessageDialog(app.getFrame(), "Error saving");
				}
			}

		}
	}

	private class ImageFilter extends FileFilter
	{
		private String[] imageExtensions = { ".jpg", ".png", ".tiff", ".jpeg", ".bmp" };

		public ImageFilter()
		{
			super();
		}

		public boolean accept(File pathname)
		{
			boolean fileOk = false;

			for (String extension : imageExtensions)
			{
				if (pathname.getName().toLowerCase().endsWith(extension))
				{
					fileOk = true;
				}
			}

			return fileOk;
		}

		private String printExtensions()
		{
			String list = "";
			
			for (int index = 0; index < imageExtensions.length; index++)
			{
				list += "*"+imageExtensions[index];
				if(index < imageExtensions.length - 1)
				{
					list+=", ";
				}
			}
			
			return list;
		}

		public String getDescription()
		{
			// TODO Auto-generated method stub
			return printExtensions();
		}

	}

}
