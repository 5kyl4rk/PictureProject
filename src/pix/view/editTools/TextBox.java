package pix.view.editTools;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import pix.controller.PixController;

public class TextBox extends JPanel
{
	private JLabel text;
	private JTextField field;
	private int currentValue;
	private GridLayout appLayout;
	
	public TextBox(PixController app, String name)
	{
		super();
		
		text = new JLabel(name);
		field = new JTextField("0");
		appLayout = new GridLayout(1,0);
		currentValue = 0;
		setupPanel();
		
	}
	
	private void setupPanel()
	{
		this.setLayout(appLayout);
		this.setPreferredSize(new Dimension(100,50));
		this.add(text);
		this.add(field);
	}
	
	public JTextField getTextField()
	{
		return field;
	}
	
	public int getCurrentValue()
	{
		return currentValue;
	}
	
	public void setCurrentValue(String input)
	{
		try
		{
			setCurrentValue(Integer.parseInt(input.trim()));
		}
		catch(NumberFormatException notInt)
		{
			setCurrentValue(currentValue);
		}
	}
	public void setCurrentValue(int num)
	{
		field.setText(num+"");
		currentValue = num;
	}	
	
	public void setText(String title)
	{
		text.setText(title);
	}
	
	public String getTextFieldText()
	{
		return field.getText();
	}
	
	
}
