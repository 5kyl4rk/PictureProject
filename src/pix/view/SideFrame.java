package pix.view;

import javax.swing.JFrame;
import pix.controller.PixController;
import pix.view.editTools.*;

public class SideFrame extends JFrame
{
	private PixController app;
	private EditingTools appPanel;
	
	public SideFrame(PixController app)
	{
		super();
		
		this.setContentPane(appPanel);
		
	}
}
