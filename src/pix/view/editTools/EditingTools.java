package pix.view.editTools;

import java.awt.Color;
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
	private JPanel scanPanel;
	private JPanel rgbPanel;
	private TextBox redBox;
	private TextBox greenBox;
	private TextBox blueBox;
	private TextBox shiftX;
	private TextBox shiftY;
	private JPanel buttonPanel;
	private JButton redButton;
	private JButton greenButton;
	private JButton blueButton;
	private JButton cyanButton;
	private JButton magentaButton;
	private JButton yellowButton;
	private JButton horizontalButton;
	private JButton verticalButton;
	private JButton lcdButton;
	private int currentBaseColor;
	private int currentDirection;
	private final int MAKE3D = 0;
	private final int SCANLINE = 1;
	private final int RED = 0;
	private final int GREEN = 1;
	private final int BLUE = 2;
	private final int CYAN = 3;
	private final int MAGENTA = 4;
	private final int YELLOW = 5;
	private final int HORIZONTAL = 0;
	private final int VERTICAL = 1;
	private final int LCD = 2;
	private Color currentColor;
	private int currentEditMode; 
	private int width;
	private int height;

	/**
	 * Common editing components that will be reused
	 * @param app a reference to the main controller
	 */
	public EditingTools(PixController app)
	{
		super();
		this.app = app;
		currentDirection = HORIZONTAL;
		currentEditMode = -1;
		currentBaseColor = RED;
		currentColor = new Color(0,0,0);
		width = -99;
		height = -99;
		mainLayout = new GridLayout(0,1);
		make3DPanel = new JPanel(new GridLayout(0, 1));
		scanPanel = new JPanel(new GridLayout(1,0));
		
		horizontalButton = new JButton("Horizontal");
		verticalButton = new JButton("Vertical");
		lcdButton = new JButton("LCD");
		
		shiftX = new TextBox(app, "x-Axis:");
		shiftY = new TextBox(app, "y-Axis:");
		redBox = new TextBox(app, "Red:");
		greenBox = new TextBox(app, "Green:");
		blueBox = new TextBox(app, "Blue:");
		
		xAxisPanel = new JPanel(new GridLayout(1, 0));
		yAxisPanel = new JPanel(new GridLayout(1, 0));
		
		redButton = new JButton("Red");
		greenButton = new JButton("Green");
		blueButton = new JButton("Blue");
		cyanButton = new JButton("Cyan");
		magentaButton = new JButton("Magenta");
		yellowButton = new JButton("Yellow");
		
		buttonPanel = new JPanel(new GridLayout(3,2));
		rgbPanel = new JPanel(new GridLayout(1,0));
		
		xAxis = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);
		yAxis = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);
		setupPanel();
		setupListeners();
		
	}

	/**
	 * adds components to their necessary panels
	 */
	private void setupPanel()
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
		
		rgbPanel.add(redBox);
		rgbPanel.add(greenBox);
		rgbPanel.add(blueBox);
		
		make3DPanel.add(xAxisPanel, 0);
		make3DPanel.add(yAxisPanel, 1);
		
		scanPanel.add(horizontalButton);
		scanPanel.add(verticalButton);
		scanPanel.add(lcdButton);


	}

	/**
	 * sets up listener for buttons and sliders, can't be bias towards any particular editing methods, so they each have to call {@link #applyEdit(int)} instead 
	 */
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
		
		redBox.getTextField().addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent enter)
			{
				
				updateColor();
				applyEdit(currentEditMode);
			}
		});
		
		greenBox.getTextField().addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent enter)
			{
			
				updateColor();
				applyEdit(currentEditMode);
			}
		});

		blueBox.getTextField().addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent enter)
			{
				updateColor();
				applyEdit(currentEditMode);
			}
		});
		
		horizontalButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				currentDirection = HORIZONTAL;
				applyEdit(currentEditMode);

			}
		});
		
		verticalButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				currentDirection = VERTICAL;
				applyEdit(currentEditMode);

			}
		});
		
		lcdButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				currentDirection = LCD;
				applyEdit(currentEditMode);

			}
		});
		
	}

	/**
	 * removes each component from the main panel, leaving it a blank slate
	 */
	public void restartPanel()
	{
		for (int index = this.getComponentCount()-1; index >= 0; index--)
		{
			this.remove(index);
		}
		
		reloadColor();
		
		this.repaint();
		
	}
	
	/**
	 * gets values from certain components and calls the appropriate edit method based off those values
	 * @param type what kind of edit it is
	 */
	private void applyEdit(int type)
	{
		if(type == MAKE3D)
		{
			app.make3D((shiftX.getCurrentValue() * -1),(shiftY.getCurrentValue()),currentBaseColor);
		}
		else if(type == SCANLINE)
		{
			app.scanline(shiftX.getCurrentValue(), shiftY.getCurrentValue(),currentColor,currentDirection);
		}
	}
	
	/**
	 * sets the main panel to view components needed for {@link pix.controller.PixController#make3D(int, int, int) make3D()}
	 */
	public void setMake3D()
	{
		currentEditMode = MAKE3D;
		shiftX.setText("x-Axis");
		shiftY.setText("y-Axis");
		shiftX.setCurrentValue(0);
		shiftY.setCurrentValue(0);
		
		xAxis.setValue(0);
		yAxis.setValue(0);
		
		xAxis.setMaximum(width / 2);
		xAxis.setMinimum(-width / 2);

		yAxis.setMinimum(-height / 2);
		yAxis.setMaximum(height / 2);
		
		
		this.add(make3DPanel,0);
		this.add(buttonPanel,1);
	}
	
	/**
	 * sets the main panel to view components needed for {@link pix.controller.PixController#scanline(int, int, Color, int) scanline()}
	 */
	public void setScanline()
	{
		reloadColor();
		shiftX.setText("Thickness:");
		shiftY.setText("Spread:");
		shiftX.setCurrentValue(1);
		shiftY.setCurrentValue(1);
		
		xAxis.setValue(1);
		yAxis.setValue(1);
		
		xAxis.setMinimum(1);
		yAxis.setMinimum(1);
		
		xAxis.setMaximum(10); //TODO: find a consistent formula for finding a good amount
		yAxis.setMaximum(10);
		this.add(make3DPanel,0);
		this.add(rgbPanel,1);
		this.add(scanPanel,2);
		currentEditMode = SCANLINE;
		this.applyEdit(currentEditMode);
	}
	
	/**
	 * setups listeners for basic color buttons that are used in {@link pix.controller.PixController#make3D(int, int, int) make3D()}
	 */
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
	
	/**
	 * sets the the values to be the currentColor's RGB values
	 */
	private void reloadColor()
	{
		redBox.setCurrentValue(currentColor.getRed());
		greenBox.setCurrentValue(currentColor.getGreen());
		blueBox.setCurrentValue(currentColor.getBlue());
	}
	
	/**
	 * sets the current color values
	 */
	private void updateColor()
	{
		
		redBox.setCurrentValue(redBox.getTextFieldText());
		if(redBox.getCurrentValue() < 0)
		{
			redBox.setCurrentValue(0);
		}
		else if(redBox.getCurrentValue() > 255)
		{
			redBox.setCurrentValue(255);
		}
		
		greenBox.setCurrentValue(greenBox.getTextFieldText());
		if(greenBox.getCurrentValue() < 0)
		{
			greenBox.setCurrentValue(0);
		}
		else if(greenBox.getCurrentValue() > 255)
		{
			greenBox.setCurrentValue(255);
		}
		
		blueBox.setCurrentValue(blueBox.getTextFieldText());
		if(blueBox.getCurrentValue() < 0)
		{
			blueBox.setCurrentValue(0);
		}
		else if(blueBox.getCurrentValue() > 255)
		{
			blueBox.setCurrentValue(255);
		}
		
		currentColor = new Color(redBox.getCurrentValue(),greenBox.getCurrentValue(), blueBox.getCurrentValue());
	}

	/**
	 * sets the width and height to match that of the current image
	 */
	public void updateDimensions()
	{
		width = (int) app.getPictureSize().getWidth();
		height = (int) app.getPictureSize().getHeight();
	}
}
