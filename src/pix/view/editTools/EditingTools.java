package pix.view.editTools;

import java.awt.Component;
import java.awt.Dimension;
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
public class EditingTools extends JPanel
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
	private JPanel buttonPanel;
	private JButton redButton;
	private JButton greenButton;
	private JButton blueButton;
	private JButton cyanButton;
	private JButton magentaButton;
	private JButton yellowButton;
	private int currentBaseColor;
	private final int MAKE3D = 0;
	private final int RED = 0;
	private final int GREEN = 1;
	private final int BLUE = 2;
	private final int CYAN = 3;
	private final int MAGENTA = 4;
	private final int YELLOW = 5;

	private int currentEditMode; 
	private int width;
	private int height;

	public EditingTools(PixController app)
	{
		super();
		this.app = app;
		
		currentEditMode = -1;
		currentBaseColor = RED;
		width = -99;
		height = -99;
		mainLayout = new GridLayout(0,1);
		make3DPanel = new JPanel(new GridLayout(0, 1));
		shiftX = new TextBox(app, "x-Axis:");
		shiftY = new TextBox(app, "y-Axis:");
		xAxisPanel = new JPanel(new GridLayout(1, 0));
		yAxisPanel = new JPanel(new GridLayout(1, 0));
		redButton = new JButton("Red");
		greenButton = new JButton("Green");
		blueButton = new JButton("Blue");
		cyanButton = new JButton("Cyan");
		magentaButton = new JButton("Magenta");
		yellowButton = new JButton("Yellow");
		buttonPanel = new JPanel(new GridLayout(3,2));
		xAxis = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);
		yAxis = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);
		setupLayout();
		setupListeners();
		
	}

	private void setupLayout()
	{
		this.setLayout(mainLayout);
		xAxisPanel.add(shiftX, 0);
		xAxisPanel.add(xAxis, 1);
		yAxisPanel.add(shiftY, 0);
		yAxisPanel.add(yAxis, 1);
		
		buttonPanel.add(redButton);
		buttonPanel.add(cyanButton);
		buttonPanel.add(greenButton);
		buttonPanel.add(magentaButton);
		buttonPanel.add(blueButton);
		buttonPanel.add(yellowButton);
		
		make3DPanel.add(xAxisPanel, 0);
		make3DPanel.add(yAxisPanel, 1);
		


	}

	private void setupListeners()
	{
		setupColorButtons();
		
		xAxis.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent slide)
			{
				shiftX.setCurrentValue(xAxis.getValue());
				applyEdit(currentEditMode);
			}
		});

		shiftX.getTextField().addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent enter)
			{
				shiftY.setCurrentValue(shiftY.getTextFieldText());
				yAxis.setValue(shiftY.getCurrentValue());
				applyEdit(currentEditMode);
			}
		});
		
		yAxis.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent slide)
			{
				shiftY.setCurrentValue(yAxis.getValue());
				applyEdit(currentEditMode);
			}
		});

		shiftY.getTextField().addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent enter)
			{
				shiftY.setCurrentValue(shiftY.getTextFieldText());
				yAxis.setValue(shiftY.getCurrentValue());
				applyEdit(currentEditMode);
			}
		});
	}

	public void restartPanel()
	{
		for (Component current : this.getComponents())
		{
			this.remove(current);
		}
	}
	
	private void applyEdit(int type)
	{
		if(type == MAKE3D)
		{
			app.make3D((shiftX.getCurrentValue() * -1),(shiftY.getCurrentValue() * -1),currentBaseColor);
		}
	}
	public void setMake3D()
	{
//		this.setPreferredSize(new Dimension(250,75));
		this.add(make3DPanel,0);
		this.add(buttonPanel,1);
		currentEditMode = MAKE3D;
	}
	
	private void setupColorButtons()
	{
		redButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				currentBaseColor = RED;
				applyEdit(currentEditMode);

			}
		});
		greenButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				currentBaseColor = GREEN;
				applyEdit(currentEditMode);

			}
		});
		blueButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				currentBaseColor = BLUE;
				applyEdit(currentEditMode);

			}
		});
		cyanButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				currentBaseColor = CYAN;
				applyEdit(currentEditMode);

			}
		});
		magentaButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				currentBaseColor = MAGENTA;
				applyEdit(currentEditMode);

			}
		});
		yellowButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				currentBaseColor = YELLOW;
				applyEdit(currentEditMode);

			}
		});
	}

	public void updateDimensions()
	{
		width = (int) app.getPictureSize().getWidth();
		height = (int) app.getPictureSize().getHeight();

		xAxis.setMaximum(width / 2);
		xAxis.setMinimum(-width / 2);

		yAxis.setMinimum(-height / 2);
		yAxis.setMaximum(height / 2);
	}
}
