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

	/**
	 * The main panel that will hold all sub panels and such
	 * @param app
	 */
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
		//setupScrollPane();
	}
	
	/**
	 * adds components to panel
	 */
	private void setupPanel()
	{
		this.setLayout(appLayout);
		this.add(displayImage);
		this.add(controlPane);
		this.add(tools);
	}

	/**
	 * puts constraints on components
	 */
	private void setupLayout()
	{
		appLayout.putConstraint(SpringLayout.NORTH, tools, 0, SpringLayout.NORTH, displayImage);
		appLayout.putConstraint(SpringLayout.WEST, tools, padding, SpringLayout.EAST, displayImage);
		appLayout.putConstraint(SpringLayout.EAST, tools, -padding, SpringLayout.EAST, this);
		appLayout.putConstraint(SpringLayout.NORTH, displayImage, padding, SpringLayout.NORTH, this);
		appLayout.putConstraint(SpringLayout.WEST, displayImage, padding, SpringLayout.WEST, this);
		appLayout.putConstraint(SpringLayout.SOUTH, displayImage, -padding, SpringLayout.SOUTH, this);
	}
	
	/**
	 * changes the ImageIcon on the JLabel to be the current image selected by the controller
	 */
	private void updateImage()
	{
		image.setImage(app.getCurrentImage().getImage());
	}
	
	/**
	 * Sets up a scroll pane, <b>Not Implemented</b>
	 */
	private void setupScrollPane()
	{
		controlPane.setViewportView(displayImage);
		controlPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		controlPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	}
	
	/**
	 * calls {@link #updateImage() updateImage()} and {@link #repaint() repaint()} to "refresh" the panel and all it's components
	 */
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
