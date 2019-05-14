package pix.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import pix.controller.PixController;
import pix.view.editTools.EditingTools;

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
	private JButton undo;
	private JButton redo;
	private JButton restart;
	private JButton make3D;
	private JButton scanlines;
	private EditingTools sidebar;//
	private SpringLayout appLayout;

	/**
	 * the panel that will handle all button actions such as saving, loading, undo/redo, and image editing.
	 * @param app
	 */
	public GlitchControlPanel(PixController app)
	{
		super();

		this.app = app;

		revertMade = false;
		canEdit = false;
		saveLoadPanel = new JPanel(new GridLayout(1, 0));
		glitchPanel = new JPanel(new GridLayout(0, 1));
		switchPanel = new JPanel(new GridLayout(1, 0));
		load = new JButton("Load");
		save = new JButton("Save");
		glitch = new JButton("Glitch");
		undo = new JButton("Undo");
		redo = new JButton("Redo");
		make3D = new JButton("3D");
		scanlines = new JButton("Scanlines");
		restart = new JButton("Clear");
		compareChanges = new JButton("Show Original");
		appLayout = new SpringLayout();
		appLayout.putConstraint(SpringLayout.SOUTH, restart, -10, SpringLayout.NORTH, switchPanel);
		appLayout.putConstraint(SpringLayout.SOUTH, compareChanges, 0, SpringLayout.NORTH, restart);
		appLayout.putConstraint(SpringLayout.EAST, restart, 0, SpringLayout.EAST, compareChanges);
		appLayout.putConstraint(SpringLayout.WEST, restart, 0, SpringLayout.WEST, compareChanges);
		appLayout.putConstraint(SpringLayout.WEST, compareChanges, 20, SpringLayout.WEST, this);
		appLayout.putConstraint(SpringLayout.WEST, glitchPanel, 40, SpringLayout.WEST, this);
		sidebar = new EditingTools(app);

		setupPanel();
		setupLayout();
		setupListeners();
	}

	private void setupPanel()
	{
		this.setLayout(appLayout);
		this.setPreferredSize(new Dimension(180, 350));
		switchPanel.setPreferredSize(new Dimension(180, 50));
		glitchPanel.setPreferredSize(new Dimension(90, 100));
		saveLoadPanel.setPreferredSize(new Dimension(180, 50));
		this.add(switchPanel);
		this.add(saveLoadPanel);
		this.add(glitchPanel);
		this.add(compareChanges);
		saveLoadPanel.add(load);
		saveLoadPanel.add(save);
		glitchPanel.add(glitch);
		switchPanel.add(undo);
		glitchPanel.add(make3D);
		glitchPanel.add(scanlines);
		switchPanel.add(redo);
		this.add(restart);
		compareChanges.setVisible(false);
		save.setVisible(false);
		restart.setVisible(false);
		showTools(false);
	}

	private void setupLayout()
	{
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
					app.restartStack();
					undo.setEnabled(false);
					redo.setEnabled(false);
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
				updateUndoRedo();
			}
		});

		make3D.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				String[] option = {"Cancel","OK!" };
				sidebar.setMake3D();
				int result = JOptionPane.showOptionDialog(getSelf(), sidebar, "3D Effect", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, option, option[0]);
				if (result == 1)
				{
					app.addToStack(app.getCurrentImage());
					editMade();
					sidebar.restartPanel();
					updateUndoRedo();

				}
				else
				{
					sidebar.restartPanel();
					app.setCurrentImage(app.getLastEdit(app.getCurrentStackIndex()));
					app.updateDisplay();
				}
			}
		});
		
		scanlines.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				String[] option = {"Cancel","OK!" };
				sidebar.setScanline();
				
				int result = JOptionPane.showOptionDialog(getSelf(), sidebar, "Scanlines", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, option, option[0]);
				if (result == 1)
				{
					app.addToStack(app.getCurrentStackIndex(),app.getCurrentImage());
					editMade();
					sidebar.restartPanel();
					updateUndoRedo();

				}
				else
				{
					sidebar.restartPanel();
					app.setCurrentImage(app.getLastEdit(app.getCurrentStackIndex()));
					app.updateDisplay();
				}
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

		undo.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				if (canEdit == true)
				{
					if (app.getCurrentStackIndex() < app.getStackSize() - 1)
					{
						app.goDownStack();
						redo.setEnabled(true);
						app.setCurrentImage(app.getLastEdit(app.getCurrentStackIndex()));

					}

					if (app.getCurrentStackIndex() == app.getStackSize() - 1)
					{
						undo.setEnabled(false);
					}

					app.updateDisplay();
					repaint();

				}
			}
		});

		redo.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				if (canEdit == true)
				{
					if (app.getCurrentStackIndex() > 0)
					{
						app.goUpStack();
						undo.setEnabled(true);
						app.setCurrentImage(app.getLastEdit(app.getCurrentStackIndex()));

					}
					if (app.getCurrentStackIndex() == 0)
					{
						redo.setEnabled(false);
					}

					app.updateDisplay();
					repaint();
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
					app.addToStack(app.getCurrentImage());
					app.updateDisplay();
					repaint();
					restartUndoRedo();
					updateUndoRedo();
				}
			}
		});
	}

	private void showTools(boolean state)
	{
		glitch.setVisible(state);
		make3D.setVisible(state);
		scanlines.setVisible(state);
		undo.setVisible(state);
		redo.setVisible(state);
		repaint();
	}

	private void restartUndoRedo()
	{
		revertMade = false;
		undo.setEnabled(true);
		repaint();
	}
	
	private void updateUndoRedo()
	{
		if(app.getCurrentStackIndex() == 0)
		{
			redo.setEnabled(false);
		}
		else if(app.getCurrentStackIndex() == app.getStackSize() - 1)
		{
			undo.setEnabled(false);
		}
		repaint();
	}

	private void editMade()
	{
		compareChanges.setVisible(true);
		restart.setVisible(true);
		save.setVisible(true);
		canEdit = true;
		app.restartStackIndex();
		repaint();
		restartUndoRedo();
	}

	public Dimension getControlSize()
	{
		return this.getPreferredSize();
	}
	
	private Component getSelf()
	{
		return this;
	}

}
