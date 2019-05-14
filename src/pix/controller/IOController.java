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

	private String startPath;
	private String recentSavePath;
	private String recentLoadPath;
	private String extension;

	/**
	 * handles loading and saving
	 * 
	 * @param app
	 */
	public IOController(PixController app)
	{
		this.app = app;
		filter = new ImageFilter();
		startPath = System.getProperty("user.dir");
		recentLoadPath = startPath + "/src/pixLab/images";
		recentSavePath = startPath + "/savedImages/";
		extension = ".jpg";
	}

	// ===== IO Handling =====
	/**
	 * Allows user to open an image file
	 */
	public void loadImage()
	{
		JFileChooser explorer = new JFileChooser(getRecentLoadPath());
		explorer.setDialogTitle("What image do you want to load?");
		explorer.setFileFilter(filter);
		int result = explorer.showOpenDialog(app.getFrame());
		if (result == JFileChooser.APPROVE_OPTION)
		{

			String fileName = explorer.getSelectedFile().getAbsolutePath();
			setRecentLoadPath(explorer.getCurrentDirectory().toString());
			app.setCurrentImage(fileName);
			app.setOriginalImage(fileName);

			app.restartStack();

			app.appIO.setExtension(fileName.substring(fileName.lastIndexOf(".")));
			app.setPictureTitle(findActualFileName(fileName));

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
			JFileChooser explorer = new JFileChooser(getRecentSavePath());
			explorer.setDialogTitle("Where do you want to save?");
			String nameGlitch = app.getPictureTitle() + "-glitched" + getExtension();
			File saveFile = new File(nameGlitch);
			explorer.setSelectedFile(saveFile);

			int result = explorer.showSaveDialog(app.getFrame());

			if (result == JFileChooser.APPROVE_OPTION)
			{
				String writeTo = explorer.getSelectedFile().getAbsolutePath();

				if (app.getCurrentImage().write(writeTo))
				{
					setRecentSavePath(explorer.getCurrentDirectory().toString());
					JOptionPane.showMessageDialog(app.getFrame(), "Save successful");
				}
				else
				{
					JOptionPane.showMessageDialog(app.getFrame(), "Error saving");
				}
			}

		}
	}
	
	public void loadConfig()
	{
		
	}
	

	private String findActualFileName(String path)
	{
		String directory = File.separator;
		int start = path.lastIndexOf(directory);
		int end = path.lastIndexOf(extension);
		String name = path.substring(start + 1, end);

		return name;
	}

	public void setExtension(String end)
	{
		if (end.trim().indexOf(".") == 0)
		{
			extension = end;
		}
	}
	
	public void setRecentLoadPath(String path)
	{

		if (path.contains(File.separator))
		{
			recentLoadPath = path;
		}

	}
	
	public void setRecentSavePath(String path)
	{
		if (path.contains(File.separator))
		{
			recentSavePath = path;
		}

	}
	
	public String getExtension()
	{
		return extension;
	}
	
	public String getRecentLoadPath()
	{
		return recentLoadPath;
	}

	public String getRecentSavePath()
	{
		return recentSavePath;
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
