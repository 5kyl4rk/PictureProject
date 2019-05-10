package pix.view.editTools;

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
	private GridLayout mainPanel;
	private JPanel sliderPanel;
	private TextBox shiftX;
	private int width;
	private int height;

	public UniversalEditingTools(PixController app)
	{
		super();
		this.app = app;
		width = -99;
		height = -99;
		mainPanel = new GridLayout(0, 1);
		shiftX = new TextBox(app,"x-Axis:");
		sliderPanel = new JPanel(new GridLayout(1, 0));
		xAxis = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);
		setupLayout();
		setupListeners();
	}

	private void setupLayout()
	{
		this.setLayout(mainPanel);
		this.add(sliderPanel);
		sliderPanel.add(shiftX);
		sliderPanel.add(xAxis);

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

	public void updateDimensions()
	{
		width = (int) app.getPictureSize().getWidth();
		height = (int) app.getPictureSize().getHeight();
		xAxis.setMaximum(width / 2);
		xAxis.setMinimum(-width / 2);
	}
}
