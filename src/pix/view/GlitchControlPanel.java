package pix.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import pix.controller.PixController;

public class GlitchControlPanel extends JPanel
{
	private PixController app;
	private JButton load;
	private JButton save;
	private JButton glitch;// subjected to change, for testing purposes
	private JButton compareChanges;
	private boolean edited;

	private SpringLayout appLayout;

	public GlitchControlPanel(PixController app)
	{
		super();

		this.app = app;

		edited = false;
		load = new JButton("Load");
		save = new JButton("Save");
		glitch = new JButton("Glitch");
		compareChanges = new JButton("Show Original");
		appLayout = new SpringLayout();
		appLayout.putConstraint(SpringLayout.WEST, compareChanges, 23, SpringLayout.WEST, this);
		appLayout.putConstraint(SpringLayout.SOUTH, compareChanges, -45, SpringLayout.SOUTH, this);
		appLayout.putConstraint(SpringLayout.NORTH, glitch, 93, SpringLayout.SOUTH, load);
		appLayout.putConstraint(SpringLayout.WEST, glitch, 44, SpringLayout.WEST, this);
		appLayout.putConstraint(SpringLayout.WEST, save, 0, SpringLayout.EAST, load);
		appLayout.putConstraint(SpringLayout.NORTH, save, 10, SpringLayout.NORTH, this);
		appLayout.putConstraint(SpringLayout.NORTH, load, 0, SpringLayout.NORTH, save);
		appLayout.putConstraint(SpringLayout.WEST, load, 10, SpringLayout.WEST, this);
		appLayout.putConstraint(SpringLayout.EAST, save, -10, SpringLayout.EAST, this);
		setupPanel();
		setupListeners();
	}

	private void setupPanel()
	{
		this.setLayout(appLayout);
		this.setPreferredSize(new Dimension(180, 300));
		this.add(load);
		this.add(save);
		this.add(glitch);
		this.add(compareChanges);
		compareChanges.setVisible(false);
		save.setVisible(false);
		glitch.setVisible(false);
	}

	private void setupLayout()
	{

	}

	private void setupListeners()
	{
		load.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				app.loadImage();
				showTools(true);
			}
		});

		save.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				app.saveImage();
			}
		});

		glitch.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				app.glitch();
				compareChanges.setVisible(true);
				repaint();
				save.setVisible(true);
				edited = true;
			}
		});

		compareChanges.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				if (edited == true)
				{
					app.setCurrentImage(app.getOriginal());
					app.updateDisplay();
					compareChanges.setText("Show Edited");
					save.setVisible(false);
					showTools(false);
					repaint();
					edited = false;
				}
				else
				{
					app.setCurrentImage(app.getAltered());
					app.updateDisplay();
					compareChanges.setText("Show Original");
					save.setVisible(true);
					showTools(true);
					repaint();
					edited = true;
				}
			}
		});
	}

	private void showTools(boolean state)
	{
		glitch.setVisible(state);
		repaint();
	}
	public Dimension getControlSize()
	{
		return this.getPreferredSize();
	}

}
