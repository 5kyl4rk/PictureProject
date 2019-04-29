package pix.view;

import pixLab.classes.PictureFrame;
import pix.controller.PixController;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.*;
import pixLab.classes.DigitalPicture;

public class GlitchMasterPanel extends JPanel
{
	private PixController app;
	private JLabel displayImage;
	private ImageIcon image;
	private SpringLayout appLayout;
	private GlitchControlPanel tools;
	private Image current;
	private int padding;

	public GlitchMasterPanel(PixController app)
	{
		super();
		this.app = app;
		image = new ImageIcon();
		appLayout = new SpringLayout();
		displayImage = new JLabel(image);
		padding = 10;
		appLayout.putConstraint(SpringLayout.NORTH, displayImage, padding, SpringLayout.NORTH, this);
		appLayout.putConstraint(SpringLayout.WEST, displayImage, padding, SpringLayout.WEST, this);
		appLayout.putConstraint(SpringLayout.SOUTH, displayImage, -padding, SpringLayout.SOUTH, this);
		tools = new GlitchControlPanel(app);
		appLayout.putConstraint(SpringLayout.NORTH, tools, 0, SpringLayout.NORTH, displayImage);
		appLayout.putConstraint(SpringLayout.WEST, tools, padding, SpringLayout.EAST, displayImage);
		appLayout.putConstraint(SpringLayout.SOUTH, tools, 0, SpringLayout.SOUTH, displayImage);
		appLayout.putConstraint(SpringLayout.EAST, tools, -padding, SpringLayout.EAST, this);
		
		setupPanel();
	}

	private void setupPanel()
	{
		this.setLayout(appLayout);
		this.add(displayImage);
		this.add(tools);
	}

	private void updateImage()
	{
		image.setImage(app.getCurrentImage().getImage());
	}
	
	
	protected void updateDisplay()
	{
		updateImage();
		repaint();
	}
	
	public Dimension getToolPanelSize()
	{
		return tools.getControlSize();
	}
	
	public int getPadding()
	{
		return padding;
	}
}