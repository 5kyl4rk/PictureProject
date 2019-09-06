package pix.controller;

import java.io.*;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import pix.model.ImageStack;

public class IOController
{
	private PixController app;

	private FileFilter filter;
	private boolean canRestore;
	private String startPath;
	private String recentSavePath;
	private String recentLoadPath;
	private String extension;
	private String restoreSave;
	private String currentOS;

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
		restoreSave = "stack.pix";
		canRestore = new File(startPath+File.separator+restoreSave).exists();
		extension = ".jpg";
		currentOS = System.getProperty("os.name");
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
		String[] option = { "No", "Yes" };
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
//					saveStack();
				}
				else
				{
					JOptionPane.showMessageDialog(app.getFrame(), "Error saving");
				}
			}

		}
	}
	
	/*TODO: fix saving/loading workspace
	public void saveStack()
	{
		try
		{
			FileOutputStream saveStream = new FileOutputStream(restoreSave);
			ObjectOutputStream output = new ObjectOutputStream(saveStream);
			output.writeObject(app.getStack());
			output.close();
			saveStream.close();
		}
		catch (IOException error)
		{
			JOptionPane.showMessageDialog(null, "error occured while saving stack", "File Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public ImageStack loadStack()
	{
		ImageStack saved = null;
		try
		{
			FileInputStream inputStream = new FileInputStream(restoreSave);
			ObjectInputStream input = new ObjectInputStream(inputStream);
			saved = (ImageStack) input.readObject();
			input.close();
			inputStream.close();
		
		}
		catch (IOException error)
		{
			JOptionPane.showMessageDialog(null, "No Save file", "Loading images", JOptionPane.INFORMATION_MESSAGE);

		}
		catch (ClassNotFoundException notFound)
		{
			JOptionPane.showMessageDialog(null, notFound.getMessage(), "Type Error", JOptionPane.ERROR_MESSAGE);
		}
		
		return saved;
	}
	*/

	// ===== Config loading =====
	/**
	 * Loads a config file for the program, file must be found at the root of the
	 * project and must be called "pix.config"
	 */
	public void loadConfig()
	{
		File configFile = new File(startPath + File.separator + "pix.config");
		Scanner configReader;
		try
		{
			configReader = new Scanner(configFile);
			while (configReader.hasNextLine())
			{
				String current = configReader.nextLine();
				app.print(current);
				processInfo(current);
			}

			configReader.close();

		}
		catch (FileNotFoundException e)
		{
			JOptionPane.showMessageDialog(null, "Config file couldn't be read, reverting back to default settings","Loading...",JOptionPane.INFORMATION_MESSAGE);
			try
			{
				PrintWriter defaultConfig = new PrintWriter(configFile);
				defaultConfig.println("minimumSize=300x300");
				defaultConfig.println("saveFolder=./savedImages/");
				defaultConfig.println("loadFolder=./src/pixLab/images");
				defaultConfig.println("maxStackMemory=10");
				defaultConfig.close();
				loadConfig();
			}
			catch (IOException error)
			{
				JOptionPane.showMessageDialog(null, "Failed to create a config file");

			}

		}
	}

	/**
	 * takes lines given by the Scanner in {@link #loadConfig()} and determines what
	 * helper methods to use to set data
	 * 
	 * @param data
	 *            the line of String that contains info
	 */
	private void processInfo(String data)
	{
		int splitIndex = data.indexOf("=") + 1;
		String keyData = data.substring(splitIndex);
		if (data.contains("minimumSize"))
		{
			setMinimumSize(keyData);
		}
		else if (data.contains("saveFolder"))
		{
			setSaveFolder(keyData);
		}
		else if (data.contains("loadFolder"))
		{
			setLoadFolder(keyData);
		}
		else if (data.contains("maxStackMemory"))
		{
			setMaxMemory(keyData);
		}
	}

	/**
	 * takes a file path and determines what the selected files 'readable' name is
	 * 
	 * @param path
	 *            the path of the selected file
	 * @return a readable name for the file
	 */
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

	private void setSaveFolder(String data)
	{
		String path = "";
		data = data.trim();
		if (currentOS.startsWith("Windows"))
		{
			try {
			data = data.replaceAll("/", File.separator);
			}
			catch (Exception e)
			{
				
			}

		}
		else
		{
			try
			{
			data = data.replaceAll("\\\\", File.separator);
			}
			catch (Exception e)
			{
				
			}

		}

		if (data.startsWith("."))
		{
			path = startPath + data.substring(1);
		}
		else
		{
			path = data;
		}
		recentSavePath = path;
		app.print(path);
	}

	private void setLoadFolder(String data)
	{

		String path = "";
		data = data.trim();
		if (data.startsWith("."))
		{
			path = startPath + data.substring(1);
		}
		else
		{
			path = data;
		}
		recentLoadPath = path;
		app.print(path);
	}

	private void setMaxMemory(String data)
	{
		data = data.trim();
		try
		{
			app.setMaxMemory(Integer.parseInt(data));
		}
		catch (NumberFormatException wrong)
		{

		}
	}

	private void setMinimumSize(String data)
	{
		data = data.trim().toLowerCase();
		int width = (int) app.getToolPanelSize().getWidth();
		int height = (int) app.getToolPanelSize().getHeight();
		if (data.contains("x"))
		{
			int splitIndex = data.indexOf("x");
			try
			{
				width = Integer.parseInt(data.substring(0, splitIndex));
				height = Integer.parseInt(data.substring(splitIndex + 1));
			}
			catch (NumberFormatException wrong)
			{

			}
		}

		app.setMinimumSize(width, height);

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
	
	public Boolean canRestore()
	{
		return canRestore;
	}

	public String getRecentSavePath()
	{
		return recentSavePath;
	}

	/**
	 * A simple image extension filter
	 * 
	 * @author Skylark
	 *
	 */
	private class ImageFilter extends FileFilter
	{
		private String[] imageExtensions = { ".jpg", ".png", ".tiff", ".jpeg", ".bmp" };

		/**
		 * A filter that contains only acceptable image files
		 */
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

		/**
		 * converts the list of extensions to a human-readable String
		 * 
		 * @return a readable String of extensions
		 */
		private String printExtensions()
		{
			String list = "";

			for (int index = 0; index < imageExtensions.length; index++)
			{
				list += "*" + imageExtensions[index];
				if (index < imageExtensions.length - 1)
				{
					list += ", ";
				}
			}

			return list;
		}

		public String getDescription()
		{
			return printExtensions();
		}

	}

}
