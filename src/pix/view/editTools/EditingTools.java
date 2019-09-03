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

import pix.model.BleedProfile;
import pix.model.EditProfile;
import pix.model.Make3DProfile;
import pix.model.ScanlinesProfile;
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
	private SpringLayout mainLayout;
	private JPanel mainPanel;
	private JPanel sliderPanel;
	private JPanel xAxisPanel;
	private JPanel yAxisPanel;
	private JPanel scanPanel;
	private JPanel rgbPanel;
	private JPanel bleedDirectionPanel;
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
	private JButton left;
	private JButton right;
	private JButton up;
	private JButton down;
	private int currentBaseColor;
	private int currentDirection;
	private Color currentColor;
	private int currentEditMode;
	private int width;
	private int height;
	private GridLayout toolLayout;
	private int currentPoint;

	/**
	 * Common editing components that will be reused
	 * 
	 * @param app
	 *            a reference to the main controller
	 */
	public EditingTools(PixController app)
	{
		super();
		this.app = app;
		
		toolLayout = new GridLayout(0,1);
		mainPanel = new JPanel(new GridLayout(0, 1));
		currentDirection = ScanlinesProfile.HORIZONTAL;
		currentEditMode = -1;
		currentBaseColor = Make3DProfile.RED;
		currentColor = new Color(0, 0, 0);
		width = -99;
		height = -99;
		currentPoint = 0;
		bleedDirectionPanel = new JPanel(new GridLayout(1,0));
		sliderPanel = new JPanel(new GridLayout(0, 1));
		scanPanel = new JPanel(new GridLayout(1, 0));
		mainLayout = new SpringLayout();
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

		buttonPanel = new JPanel(new GridLayout(3, 2));
		rgbPanel = new JPanel(new GridLayout(1, 0));

		xAxis = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);
		yAxis = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);
		
		left = new JButton("Left");
		right = new JButton("Right");
		up = new JButton("Up");
		down = new JButton("Down");
		


		setupPanel();
		setupLayout();
		setupListeners();

	}
	
	private void setupLayout()
	{

		mainLayout.putConstraint(SpringLayout.WEST, bleedDirectionPanel, 250, SpringLayout.WEST, this);
		mainLayout.putConstraint(SpringLayout.SOUTH, bleedDirectionPanel, -10, SpringLayout.SOUTH, this);
		mainLayout.putConstraint(SpringLayout.EAST, bleedDirectionPanel, -10, SpringLayout.EAST, this);
		mainLayout.putConstraint(SpringLayout.NORTH, bleedDirectionPanel, 15, SpringLayout.SOUTH, mainPanel);
		mainLayout.putConstraint(SpringLayout.NORTH, mainPanel, 10, SpringLayout.NORTH, this);
		mainLayout.putConstraint(SpringLayout.WEST, mainPanel, 10, SpringLayout.WEST, this);
		mainLayout.putConstraint(SpringLayout.SOUTH, mainPanel, 310, SpringLayout.NORTH, this);
		mainLayout.putConstraint(SpringLayout.EAST, mainPanel, -10, SpringLayout.EAST, this);
	}

	/**
	 * adds components to their necessary panels
	 */
	private void setupPanel()
	{
		this.setLayout(toolLayout);

		bleedDirectionPanel.add(left);
		bleedDirectionPanel.add(right);
		bleedDirectionPanel.add(up);
		bleedDirectionPanel.add(down);
		
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

		sliderPanel.add(xAxisPanel, 0);
		sliderPanel.add(yAxisPanel, 1);

		scanPanel.add(horizontalButton);
		scanPanel.add(verticalButton);
		scanPanel.add(lcdButton);

	}

	/**
	 * sets up listener for buttons and sliders, can't be bias towards any
	 * particular editing methods, so they each have to call {@link #applyEdit(int)}
	 * instead
	 */
	private void setupListeners()
	{
		setupColorButtons();
		
		setupDirection();

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
				shiftX.setCurrentValue(shiftX.getTextFieldText());
				if(shiftX.getCurrentValue() > xAxis.getMaximum())
				{
					shiftX.setCurrentValue(xAxis.getMaximum());
					xAxis.setValue(xAxis.getMaximum());
				}
				else if (shiftX.getCurrentValue() < xAxis.getMinimum())
				{
					shiftX.setCurrentValue(xAxis.getMinimum());
					xAxis.setValue(xAxis.getMinimum());
				}
				else
				{
					xAxis.setValue(shiftX.getCurrentValue());
				}

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
				if(shiftY.getCurrentValue() > yAxis.getMaximum())
				{
					shiftY.setCurrentValue(yAxis.getMaximum());
					yAxis.setValue(yAxis.getMaximum());
				}
				else if (shiftY.getCurrentValue() < yAxis.getMinimum())
				{
					shiftY.setCurrentValue(yAxis.getMinimum());
					yAxis.setValue(yAxis.getMinimum());
				}
				else
				{
					yAxis.setValue(shiftY.getCurrentValue());
				}
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
				currentDirection = ScanlinesProfile.HORIZONTAL;
				applyEdit(currentEditMode);

			}
		});

		verticalButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				currentDirection = ScanlinesProfile.VERTICAL;
				applyEdit(currentEditMode);

			}
		});

		lcdButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				currentDirection = ScanlinesProfile.LCD;
				applyEdit(currentEditMode);

			}
		});

	}
	
	/**
	 * sets up buttons used for Scanlines direction
	 */
	private void setupDirection()
	{
		up.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				showXAxis(false);
				showYAxis(true);
				currentDirection = BleedProfile.UP;
				applyEdit(currentEditMode);

			}
		});
		down.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				showXAxis(false);
				showYAxis(true);
				currentDirection = BleedProfile.DOWN;
				applyEdit(currentEditMode);

			}
		});
		left.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				showXAxis(true);
				showYAxis(false);
				currentDirection = BleedProfile.LEFT;
				applyEdit(currentEditMode);

			}
		});
		right.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				showXAxis(true);
				showYAxis(false);
				currentDirection = BleedProfile.RIGHT;
				applyEdit(currentEditMode);

			}
		});
	}

	/**
	 * removes each component from the main panel, leaving it a blank slate
	 */
	public void restartPanel()
	{
		for (int index = this.getComponentCount() - 1; index >= 0; index--)
		{
			this.remove(index);
		}
		showXAxis(true);
		showYAxis(true);

		shiftX.setCurrentValue(0);
		shiftY.setCurrentValue(0);

		xAxis.setValue(0);
		yAxis.setValue(0);

		reloadColor();

		this.repaint();

	}

	/**
	 * gets values from certain components and calls the appropriate edit method
	 * based off those values
	 * 
	 * @param type
	 *            what kind of edit it is
	 */
	private void applyEdit(int type)
	{
		if (type == EditProfile.MAKE3D)
		{
			app.make3D((shiftX.getCurrentValue() * -1), (shiftY.getCurrentValue()), currentBaseColor);
		}
		else if (type == EditProfile.SCANLINES)
		{
			app.print("thickness:" + shiftX.getCurrentValue());
			app.print("Spread: " + shiftY.getCurrentValue());
			app.scanline(shiftX.getCurrentValue(), shiftY.getCurrentValue(), currentColor, currentDirection);
			

			app.print("thickness:" + shiftX.getCurrentValue());
			app.print("Spread: " + shiftY.getCurrentValue());
		}
		else if (type == EditProfile.GRAIN)
		{
			app.grain(shiftX.getCurrentValue());
		}
		else if(type == EditProfile.BLEED)
		{
			if(currentDirection == BleedProfile.LEFT || currentDirection == BleedProfile.RIGHT)
			{
				currentPoint = shiftX.getCurrentValue();
			}
			else
			{
				currentPoint = shiftY.getCurrentValue();
			}
			
			app.bleed(currentPoint,currentDirection);
		}
//		else if (type == NOISE)
//		{
//			app.noise(shiftX.getCurrentValue(), shiftY.getCurrentValue(), currentColor);
//		}
	}

	/**
	 * sets the main panel to view components needed for
	 * {@link pix.controller.PixController#make3D(int, int, int) make3D()}
	 */
	public void setMake3D()
	{
		currentEditMode = EditProfile.MAKE3D;
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

		this.add(sliderPanel, 0);
		this.add(buttonPanel, 1);
	}

	/**
	 * sets the main panel to view components needed for
	 * {@link pix.controller.PixController#scanline(int, int, Color, int)
	 * scanline()}
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

		xAxis.setMaximum(10); // TODO: find a consistent formula for finding a good amount
		yAxis.setMaximum(10);
		this.add(sliderPanel, 0);
		this.add(rgbPanel, 1);
		this.add(scanPanel, 2);
		currentEditMode = EditProfile.SCANLINES;
		this.applyEdit(currentEditMode);
	}

	/**
	 * sets the main panel to view components needed for
	 * {@link pix.controller.PixController#grain(int)
	 * grain()}
	 */
	public void setGrain()
	{
		showYAxis(false);
		shiftX.setText("Hardness:");
		shiftX.setCurrentValue(0);
		xAxis.setValue(0);

		xAxis.setMinimum(-255);
		xAxis.setMaximum(255);
		currentEditMode = EditProfile.GRAIN;
		this.add(sliderPanel);

	}
	
	/**
	 * sets the main panel to view components needed for
	 * {@link pix.controller.PixController#bleed(int, int) bleed()}
	 */
	public void setBleed()
	{
		showYAxis(false);
		this.add(sliderPanel);
		this.add(bleedDirectionPanel);
		shiftX.setText("x-Pos:");
		shiftY.setText("y-Pos:");
		shiftX.setCurrentValue(0);
		shiftY.setCurrentValue(0);
		
		xAxis.setValue(0);
		yAxis.setValue(0);
		xAxis.setMinimum(0);
		xAxis.setMaximum(width-1);
		yAxis.setMinimum(0);
		yAxis.setMaximum(height-1);
		currentEditMode = EditProfile.BLEED;
		
	}
	
	private void showXAxis(boolean state)
	{
		sliderPanel.getComponent(0).setVisible(state);
	}
	private void showYAxis(boolean state)
	{
		sliderPanel.getComponent(1).setVisible(state);
	}

	/**
	 * setups listeners for basic color buttons that are used in
	 * {@link pix.controller.PixController#make3D(int, int, int) make3D()}
	 */
	private void setupColorButtons()
	{
		redButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				currentBaseColor = Make3DProfile.RED;
				applyEdit(currentEditMode);

			}
		});
		greenButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				currentBaseColor = Make3DProfile.GREEN;
				applyEdit(currentEditMode);

			}
		});
		blueButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				currentBaseColor = Make3DProfile.BLUE;
				applyEdit(currentEditMode);

			}
		});
		cyanButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				currentBaseColor = Make3DProfile.CYAN;
				applyEdit(currentEditMode);

			}
		});
		magentaButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				currentBaseColor = Make3DProfile.MAGENTA;
				applyEdit(currentEditMode);

			}
		});
		yellowButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				currentBaseColor = Make3DProfile.YELLOW;
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
		if (redBox.getCurrentValue() < 0)
		{
			redBox.setCurrentValue(0);
		}
		else if (redBox.getCurrentValue() > 255)
		{
			redBox.setCurrentValue(255);
		}

		greenBox.setCurrentValue(greenBox.getTextFieldText());
		if (greenBox.getCurrentValue() < 0)
		{
			greenBox.setCurrentValue(0);
		}
		else if (greenBox.getCurrentValue() > 255)
		{
			greenBox.setCurrentValue(255);
		}

		blueBox.setCurrentValue(blueBox.getTextFieldText());
		if (blueBox.getCurrentValue() < 0)
		{
			blueBox.setCurrentValue(0);
		}
		else if (blueBox.getCurrentValue() > 255)
		{
			blueBox.setCurrentValue(255);
		}

		currentColor = new Color(redBox.getCurrentValue(), greenBox.getCurrentValue(), blueBox.getCurrentValue());
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
