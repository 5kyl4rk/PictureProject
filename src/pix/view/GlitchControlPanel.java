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
	private boolean canEdit;
	private boolean revertMade;
	private JButton undoRedo;
	private JButton restart;
	private JButton make3D;
	private UniversalEditingTools sidebar;//
	private SpringLayout appLayout;

	public GlitchControlPanel(PixController app)
	{
		super();

		this.app = app;

		revertMade = false;
		canEdit = false;
		load = new JButton("Load");
		save = new JButton("Save");
		glitch = new JButton("Glitch");
		undoRedo = new JButton("Undo");
		make3D = new JButton("3D");
		restart = new JButton("Restart");

		compareChanges = new JButton("Show Original");
		appLayout = new SpringLayout();
		appLayout.putConstraint(SpringLayout.NORTH, restart, 6, SpringLayout.SOUTH, compareChanges);
		appLayout.putConstraint(SpringLayout.WEST, restart, 0, SpringLayout.WEST, save);
		appLayout.putConstraint(SpringLayout.EAST, restart, 2, SpringLayout.EAST, compareChanges);
		appLayout.putConstraint(SpringLayout.NORTH, undoRedo, 6, SpringLayout.SOUTH, compareChanges);
		appLayout.putConstraint(SpringLayout.WEST, undoRedo, 0, SpringLayout.WEST, load);
		appLayout.putConstraint(SpringLayout.EAST, undoRedo, 54, SpringLayout.WEST, compareChanges);
		appLayout.putConstraint(SpringLayout.NORTH, make3D, 1, SpringLayout.SOUTH, glitch);
		appLayout.putConstraint(SpringLayout.WEST, make3D, 0, SpringLayout.WEST, glitch);
		appLayout.putConstraint(SpringLayout.EAST, make3D, 0, SpringLayout.EAST, glitch);
		appLayout.putConstraint(SpringLayout.WEST, compareChanges, 23, SpringLayout.WEST, this);
		appLayout.putConstraint(SpringLayout.SOUTH, compareChanges, -45, SpringLayout.SOUTH, this);
		appLayout.putConstraint(SpringLayout.NORTH, glitch, 93, SpringLayout.SOUTH, load);
		appLayout.putConstraint(SpringLayout.WEST, glitch, 44, SpringLayout.WEST, this);
		appLayout.putConstraint(SpringLayout.WEST, save, 0, SpringLayout.EAST, load);
		appLayout.putConstraint(SpringLayout.NORTH, save, 10, SpringLayout.NORTH, this);
		appLayout.putConstraint(SpringLayout.NORTH, load, 0, SpringLayout.NORTH, save);
		appLayout.putConstraint(SpringLayout.WEST, load, 10, SpringLayout.WEST, this);

		sidebar = new UniversalEditingTools(app);
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
		this.add(undoRedo);
		this.add(make3D);
		this.add(restart);
		compareChanges.setVisible(false);
		save.setVisible(false);
		restart.setVisible(false);
		showTools(false);
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
				if (app.getFileLoaded())
				{
					undoRedo.setEnabled(false);
					compareChanges.setVisible(false);
					showTools(true);
				}
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
				editMade();
			}
		});

		make3D.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				JOptionPane.showMessageDialog(app.getFrame(), sidebar, "3D Effect", JOptionPane.DEFAULT_OPTION);
				editMade();
			}
		});

		compareChanges.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				if (canEdit == true)
				{
					app.setCurrentImage(app.getOriginal());
					app.updateDisplay();
					compareChanges.setText("Show Edited");
					save.setVisible(false);
					showTools(false);
					repaint();
					canEdit = false;
					restartUndoRedo();
				}
				else
				{
					app.setCurrentImage(app.getAltered());
					app.updateDisplay();
					compareChanges.setText("Show Original");
					save.setVisible(true);
					showTools(true);
					repaint();
					canEdit = true;
				}
			}
		});

		undoRedo.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				if (canEdit == true)
				{
					if (!revertMade)
					{
						app.setCurrentImage(app.getLastChange());
						app.updateDisplay();
						undoRedo.setText("Redo");
						repaint();
						revertMade = true;
					}
					else
					{
						app.setCurrentImage(app.getAltered());
						app.updateDisplay();
						restartUndoRedo();
					}
				}
			}
		});

		restart.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				if (canEdit == true)
				{

					app.setCurrentImage(app.getOriginal());
					app.updateDisplay();
					repaint();
					restartUndoRedo();

				}
			}
		});
	}

	private void showTools(boolean state)
	{
		glitch.setVisible(state);
		make3D.setVisible(state);
		undoRedo.setVisible(state);
		repaint();
	}

	private void restartUndoRedo()
	{
		revertMade = false;
		undoRedo.setText("Undo");
		undoRedo.setEnabled(true);
		repaint();
	}

	private void editMade()
	{
		compareChanges.setVisible(true);
		restart.setVisible(true);
		save.setVisible(true);
		canEdit = true;
		repaint();
		restartUndoRedo();
	}

	public Dimension getControlSize()
	{
		return this.getPreferredSize();
	}

}
