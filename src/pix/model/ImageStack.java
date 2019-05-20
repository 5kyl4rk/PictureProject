package pix.model;

import pixLab.classes.Picture;
import pix.controller.PixController;

import java.util.ArrayList;


import java.io.Serializable;
import java.util.ArrayList;

public class ImageStack implements Serializable
{
	private int currentIndex;
	private int maxMemory;
	private ArrayList<Picture> editStack;
	private PixController app;
	private Picture originalImage;
	
	public ImageStack(int size, PixController app)
	{
		editStack = new ArrayList<Picture>();
		maxMemory = size;
		this.app = app;
		currentIndex = 0;
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

			editStack.add(index, temp);

			for (int stackIndex = index - 1; stackIndex >= 0; stackIndex--)
			{
				editStack.remove(stackIndex);
			}

			if (editStack.size() >= maxMemory)
			{
				editStack.remove(getSize() - 1);
			}
		}

		/**
		 * sets the currentStackindex back to the top (aka index = 0)
		 */
		public void restartStackIndex()
		{
			currentIndex = 0;
		}

		/**
		 * traverse up the stack
		 */
		public void goUpStack()
		{
			currentIndex--;
		}

		/**
		 * traverse down the stack
		 */
		public void goDownStack()
		{
			currentIndex++;
		}

		/**
		 * clears the stack, but adds back in the original image
		 */
		public void restartStack()
		{
			clearStack();
			restartStackIndex();
			editStack.add(app.getOriginal());
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
		
		public int getSize()
		{
			return editStack.size();
		}

		public int getCurrentIndex()
		{
			return currentIndex;
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
		
		public void setCurrentIndex(int index)
		{
			if(index < 0)
			{
				index = 0;
			}
			else if(index >= editStack.size())
			{
				index = editStack.size()-1;
			}
			
			currentIndex = index;
		}
		
		public Picture getOriginalImage()
		{
			return originalImage;
		}
		
		public void setOriginalImage(Picture image)
		{
			originalImage = new Picture(image);
		}

		public void setOriginalImage(String imageToLoad)
		{
			originalImage = new Picture(imageToLoad);
		}

}
