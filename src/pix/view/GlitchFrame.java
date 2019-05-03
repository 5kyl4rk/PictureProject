package pix.view;
import java.awt.Dimension;

import javax.swing.JFrame;
import pix.controller.PixController;

public class GlitchFrame extends JFrame
{
	private PixController app;
	private GlitchMasterPanel appPanel;
	public GlitchFrame(PixController app)
	{
		super();
		
		this.app = app;
		appPanel = new GlitchMasterPanel(app);
		setupFrame();
	}
	
	private void setupFrame()
	{
		this.setContentPane(appPanel);
		this.setMinimumSize(appPanel.getToolPanelSize());
		this.setLocationRelativeTo(null);

		this.setResizable(true);
		this.setVisible(true);
	}
	
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
		this.setSize(width+(3*appPanel.getPadding()),height+(2*appPanel.getPadding()));
		//this.setMinimumSize(new Dimension());//need more infomation on expected size constriants before attempting
		this.setLocationRelativeTo(null);
		appPanel.updateDisplay();
		
	}
}
