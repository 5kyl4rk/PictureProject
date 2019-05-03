package pix.view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import pix.controller.PixController;

public class GlitchControlPanel extends JPanel
{
	private PixController app;
	private JPanel saveLoadPanel;
	private JPanel glitchPanel;
	private JPanel switchPanel;
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
		saveLoadPanel = new JPanel(new GridLayout(1,0));
		glitchPanel = new JPanel(new GridLayout(0,1));
		switchPanel = new JPanel(new GridLayout(1,0));
		load = new JButton("Load");
		save = new JButton("Save");
		glitch = new JButton("Glitch");
		undoRedo = new JButton("Undo");
		make3D = new JButton("3D");
		restart = new JButton("Restart");
		
		compareChanges = new JButton("Show Original");
		appLayout = new SpringLayout();
		sidebar = new UniversalEditingTools(app);
		
		setupPanel();
		setupLayout();
		setupListeners();
	}

	private void setupPanel()
	{
		this.setLayout(appLayout);
		this.setPreferredSize(new Dimension(180, 300));
		switchPanel.setPreferredSize(new Dimension(180,50));
		glitchPanel.setPreferredSize(new Dimension(90,100));
		saveLoadPanel.setPreferredSize(new Dimension(180,50));
		this.add(switchPanel);
		this.add(saveLoadPanel);
		this.add(glitchPanel);
		this.add(compareChanges);
		saveLoadPanel.add(load);
		saveLoadPanel.add(save);
		glitchPanel.add(glitch);
		switchPanel.add(undoRedo);
		glitchPanel.add(make3D);
		switchPanel.add(restart);
		compareChanges.setVisible(false);
		save.setVisible(false);
		restart.setVisible(false);
		showTools(false);
	}

	private void setupLayout()
	{
		appLayout.putConstraint(SpringLayout.WEST, glitchPanel, 20, SpringLayout.WEST, compareChanges);
		appLayout.putConstraint(SpringLayout.WEST, compareChanges, 20, SpringLayout.WEST, this);
		appLayout.putConstraint(SpringLayout.SOUTH, compareChanges, -6, SpringLayout.NORTH, switchPanel);
		appLayout.putConstraint(SpringLayout.NORTH, glitchPanel, 30, SpringLayout.SOUTH, saveLoadPanel);
		appLayout.putConstraint(SpringLayout.SOUTH, switchPanel, 0, SpringLayout.SOUTH, this);
		appLayout.putConstraint(SpringLayout.EAST, switchPanel, 0, SpringLayout.EAST, saveLoadPanel);
	}

	private void setupListeners()
	{
		load.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{

				app.loadImage();
				if (app.isFileLoaded())
				{
					app.recenter();
					undoRedo.setEnabled(false);
					compareChanges.setVisible(false);
					showTools(true);
					sidebar.updateDimensions();

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
					app.setCurrentImage(app.getLastEdit());
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
				
						app.setCurrentImage(app.getLastEdit(1));
						app.updateDisplay();
						undoRedo.setText("Redo");
						repaint();
						revertMade = true;
					}
					else
					{
						app.setCurrentImage(app.getLastEdit());
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
					app.clearLog();
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
