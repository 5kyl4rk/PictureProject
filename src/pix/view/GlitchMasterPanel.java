package pix.view;

import pixLab.classes.PictureFrame;
import pix.controller.PixController;

import java.awt.Dimension;

import javax.swing.*;
import pixLab.classes.DigitalPicture;

public class GlitchMasterPanel extends JPanel
{
	private PixController app;
	private JLabel displayImage;
	private ImageIcon image;
	private SpringLayout appLayout;
	private GlitchControlPanel tools;

	public GlitchMasterPanel(PixController app)
	{
		super();
		this.app = app;
		image = new ImageIcon();
		appLayout = new SpringLayout();
		displayImage = new JLabel(image);
		appLayout.putConstraint(SpringLayout.NORTH, displayImage, 10, SpringLayout.NORTH, this);
		appLayout.putConstraint(SpringLayout.WEST, displayImage, 10, SpringLayout.WEST, this);
		appLayout.putConstraint(SpringLayout.SOUTH, displayImage, -10, SpringLayout.SOUTH, this);
		tools = new GlitchControlPanel(app);
		appLayout.putConstraint(SpringLayout.NORTH, tools, 0, SpringLayout.NORTH, displayImage);
		appLayout.putConstraint(SpringLayout.WEST, tools, 10, SpringLayout.EAST, displayImage);
		appLayout.putConstraint(SpringLayout.SOUTH, tools, 0, SpringLayout.SOUTH, displayImage);
		appLayout.putConstraint(SpringLayout.EAST, tools, -10, SpringLayout.EAST, this);
		
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
		image.setImage(app.getPicture().getImage());
	}
	
	protected void updateDisplay()
	{
		updateImage();
	}
	
	public Dimension getToolPanelSize()
	{
		return tools.getControlSize();
	}
}
