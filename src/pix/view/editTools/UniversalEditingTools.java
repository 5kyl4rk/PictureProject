package pix.view.editTools;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import pix.controller.PixController;

/**
 * components that will be constantly reused
 * 
 * @author Skylark
 *
 */
public class UniversalEditingTools extends JPanel
{
	private PixController app;
	private JSlider xAxis;
	private JSlider yAxis;
	private GridLayout mainLayout;
	private JPanel make3DPanel;
	private JPanel xAxisPanel;
	private JPanel yAxisPanel;
	private TextBox shiftX;
	private TextBox shiftY;
	private int width;
	private int height;

	public UniversalEditingTools(PixController app)
	{
		super();
		this.app = app;
		width = -99;
		height = -99;
		mainLayout = new GridLayout(0, 1);
		make3DPanel = new JPanel(new GridLayout(0,1));
		shiftX = new TextBox(app,"x-Axis:");
		shiftY = new TextBox(app,"y-Axis:");
		xAxisPanel = new JPanel(new GridLayout(1, 0));
		yAxisPanel = new JPanel(new GridLayout(1, 0));
		xAxis = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);
		yAxis = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);
		setupLayout();
		setupListeners();
	}

	private void setupLayout()
	{
		this.setLayout(mainLayout);
		xAxisPanel.add(shiftX,0);
		xAxisPanel.add(xAxis,1);
		yAxisPanel.add(shiftY,0);
		yAxisPanel.add(yAxis,1);
		make3DPanel.add(xAxisPanel,0);
		make3DPanel.add(yAxisPanel,1);
		
	}

	private void setupListeners()
	{
		xAxis.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent slide)
			{
				shiftX.setCurrentValue(xAxis.getValue());
				app.make3D(xAxis.getValue()* -1);
			}
		});
		
		shiftX.getTextField().addActionListener(new ActionListener()
				{
			public void actionPerformed(ActionEvent enter)
			{
				shiftX.setCurrentValue(shiftX.getTextFieldText());
				xAxis.setValue(shiftX.getCurrentValue());
				app.make3D(shiftX.getCurrentValue() * -1);
			}
				});
	}
	
	public void restartPanel()
	{
		for(Component current : this.getComponents())
		{
			this.remove(current);
		}
	}
	
	public void setMake3D()
	{
		this.add(make3DPanel);
	}

	public void updateDimensions()
	{
		width = (int) app.getPictureSize().getWidth();
		height = (int) app.getPictureSize().getHeight();
		
		xAxis.setMaximum(width / 2);
		xAxis.setMinimum(-width / 2);
		
		yAxis.setMinimum(-height/2);
		yAxis.setMaximum(height/2);
	}
}
