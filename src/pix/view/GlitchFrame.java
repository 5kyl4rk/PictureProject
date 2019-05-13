package pix.view;
import java.awt.Dimension;

import javax.swing.JFrame;
import pix.controller.PixController;

public class GlitchFrame extends JFrame
{
	private PixController app;
	private GlitchMasterPanel appPanel;
	/**
	 * the main container for the program.  Where all the components will be displayed
	 * @param app reference to the controller
	 */
	public GlitchFrame(PixController app)
	{
		super();
		
		this.app = app;
		appPanel = new GlitchMasterPanel(app);
		setupFrame();
	}
	
	/**
	 * sets up frame properties such as size and current panel
	 */
	private void setupFrame()
	{
		this.setContentPane(appPanel);
		this.setMinimumSize(appPanel.getToolPanelSize());
		this.setLocationRelativeTo(null);
		this.setResizable(true);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * resizes the frame to fit the current image size and calls the panel's {@link pix.view.GlitchMasterPanel#updateDisplay() updateDisplay()}
	 */
	public void updateDisplay()
	{
		int width = (int)(appPanel.getToolPanelSize().getWidth()+app.getPictureSize().width);
		int height = 0;
		if(appPanel.getToolPanelSize().getHeight() > app.getPictureSize().getHeight())
		{
			height = (int) appPanel.getToolPanelSize().getHeight();
		}
		else
		{
			height = (int) app.getPictureSize().getHeight();
		}
		this.setSize(width+(3*appPanel.getPadding()), height+(4 * appPanel.getPadding()));
		
		appPanel.updateDisplay();
	}
	
	/**
	 * puts the frame back to the center of the screen
	 */
	public void recenter()
	{
		this.setLocationRelativeTo(null);
	}
}
