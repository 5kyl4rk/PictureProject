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
		this.setMinimumSize(new Dimension(100,100));
		this.setResizable(true);
		this.setVisible(true);
	}
	
	public void updateDisplay()
	{
		this.setSize(app.getPictureSize());
		appPanel.updateDisplay();
	}
}
