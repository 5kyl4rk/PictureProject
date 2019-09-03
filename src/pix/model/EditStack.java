package pix.model;

import java.util.ArrayList;

import pix.controller.PixController;
import pixLab.classes.Picture;

public class EditStack
{
	private ArrayList<EditProfile> editList;
	private PixController app;
	private int currentIndex;
	
	
	public EditStack(PixController app)
	{
		this.app = app;
		editList = new ArrayList<EditProfile>();
		currentIndex = 0;
	}
	
	// === Stack Management ===
	/**
	 * adds a profile to stack
	 * @param editToAdd
	 */
	public void addToStack(EditProfile editToAdd)
	{
		addToStack(0, editToAdd);
	}
	
	/**
	 * adds profile to a specific index in the stack
	 * @param index
	 * @param editToAdd
	 */
	public void addToStack(int index, EditProfile editToAdd)
	{
		editList.add(index, editToAdd);
		
		for (int stackIndex = index - 1; stackIndex >= 0; stackIndex--)
		{
			editList.remove(stackIndex);
		}
	}
	
	public void restartStackIndex()
	{
		currentIndex = 0;
	}
	
	public void goUpStack()
	{
		currentIndex--;
		if(currentIndex < 0)
		{
			currentIndex = 0;
		}
	}
	
	public void goDownStack()
	{
		currentIndex++;
		if(currentIndex >= editList.size())
		{
			currentIndex = editList.size()-1;
		}
	}
	
	public void clearStack()
	{
		for(int index = editList.size()-1; index >= 0; index--)
		{
			editList.remove(index);
		}
	}
	
	public void restartStack()
	{
		clearStack();
		restartStackIndex();
	}
	
	public int getSize()
	{
		return editList.size();
	}

	public int getCurrentIndex()
	{
		return currentIndex;
	}
	public EditProfile getLastEdit()
	{
		return editList.get(0);
	}
	
	public EditProfile getLastEdit(int index)
	{
		if (index < 0)
		{
			index = 0;
		}
		else if (index >= editList.size())
		{
			index = editList.size() - 1;
		}

		return editList.get(index);
	}
	
	public void setCurrentIndex(int index)
	{
		if(index < 0)
		{
			index = 0;
		}
		else if(index >= editList.size())
		{
			index = editList.size()-1;
		}
		
		currentIndex = index;
	}
	
	
}
