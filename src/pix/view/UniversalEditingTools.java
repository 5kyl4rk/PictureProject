package pix.view;
import java.awt.GridLayout;

import javax.swing.*;
import pix.controller.PixController;
/**
 * components that will be constantly reused
 * @author Skylark
 *
 */
public class UniversalEditingTools extends JPanel
{
	private PixController app;
	private JSlider xAxis;
	private JSlider yAxis;
	private GridLayout mainPanel;
	private int width;
	private int height;
	
	public UniversalEditingTools(PixController app)
	{
		super();
		this.app = app;
		width = -99;
		height = -99;
		xAxis = new JSlider(JSlider.HORIZONTAL,-100,100,0);
		setupLayout();
	}
	
	private void setupLayout()
	{
		this.setLayout(mainPanel);
		this.add(xAxis);
	
	}
	
	private void updateDimensions()
	{
		width = (int) app.getPictureSize().getWidth();
		height = (int) app.getPictureSize().getHeight();
	}
}
