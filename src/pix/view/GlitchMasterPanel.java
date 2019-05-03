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
	private JScrollPane controlPane;

	public GlitchMasterPanel(PixController app)
	{
		super();
		this.app = app;
		image = new ImageIcon();
		appLayout = new SpringLayout();
		displayImage = new JLabel(image);
		padding = 10;
		tools = new GlitchControlPanel(app);
		
		controlPane = new JScrollPane();

		setupPanel();
		setupLayout();
		setupScrollPane();
	}

	private void setupPanel()
	{
		this.setLayout(appLayout);
		this.add(displayImage);
		this.add(controlPane);
	}

	private void setupLayout()
	{
		appLayout.putConstraint(SpringLayout.NORTH, controlPane, 0, SpringLayout.NORTH, displayImage);
		appLayout.putConstraint(SpringLayout.WEST, controlPane, padding, SpringLayout.EAST, displayImage);
		appLayout.putConstraint(SpringLayout.EAST, controlPane, -padding, SpringLayout.EAST, this);
		appLayout.putConstraint(SpringLayout.NORTH, displayImage, padding, SpringLayout.NORTH, this);
		appLayout.putConstraint(SpringLayout.WEST, displayImage, padding, SpringLayout.WEST, this);
		appLayout.putConstraint(SpringLayout.SOUTH, displayImage, -padding, SpringLayout.SOUTH, this);
		
		
	}
	
	private void updateImage()
	{
		image.setImage(app.getCurrentImage().getImage());
	}
	
	private void setupScrollPane()
	{
		controlPane.setViewportView(tools);
		controlPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		controlPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
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
