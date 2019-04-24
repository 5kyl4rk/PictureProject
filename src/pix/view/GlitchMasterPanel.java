package pix.view;

import pixLab.classes.PictureFrame;
import pix.controller.PixController;
import javax.swing.*;
import pixLab.classes.DigitalPicture;

public class GlitchMasterPanel extends JPanel
{
	private PixController app;
	private JLabel displayImage;
	private ImageIcon image;
	private SpringLayout appLayout;

	public GlitchMasterPanel(PixController app)
	{
		super();
		this.app = app;
		image = new ImageIcon();
		appLayout = new SpringLayout();
		displayImage = new JLabel(image);
		
		setupPanel();
	}

	private void setupPanel()
	{
		this.setLayout(appLayout);
		this.add(displayImage);
	}

	private void updateImage()
	{
		image.setImage(app.getPicture().getImage());
	}
	
	protected void updateDisplay()
	{
		updateImage();
	}
}
