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
	private JButton glitch;
	private JButton grain;
	private JButton compareChanges;
	private boolean canEdit;
	private String[] genericOptions;
	private JButton undo;
	private JButton redo;
	private JButton restart;
	private JButton make3D;
	private JButton scanlines;
	private JPanel controlPanel;
	private EditingTools extraTools;//
	private SpringLayout controlLayout;
	private SpringLayout appLayout;

	/**
	 * the panel that will handle all button actions such as saving, loading, undo/redo, and image editing.
	 * @param app
	 */
	public GlitchControlPanel(PixController app)
	{
		super();

		this.app = app;

		controlPanel = new JPanel();
		appLayout = new SpringLayout();
		
		canEdit = false;
		saveLoadPanel = new JPanel(new GridLayout(1, 0));
		glitchPanel = new JPanel(new GridLayout(0, 1));
		switchPanel = new JPanel(new GridLayout(1, 0));
		load = new JButton("Load");
		save = new JButton("Save");
		glitch = new JButton("Glitch");
		undo = new JButton("Undo");
		redo = new JButton("Redo");
		grain = new JButton("Grain");
		make3D = new JButton("<html><font color=#ff0000>3</font><font color=#00ffff>D</font></html>");
		scanlines = new JButton("Scanlines");
		restart = new JButton("Clear");
		compareChanges = new JButton("Show Original");
		controlLayout = new SpringLayout();
		controlLayout.putConstraint(SpringLayout.SOUTH, switchPanel, 0, SpringLayout.SOUTH, controlPanel);
		controlLayout.putConstraint(SpringLayout.SOUTH, restart, -10, SpringLayout.NORTH, switchPanel);
		controlLayout.putConstraint(SpringLayout.SOUTH, compareChanges, 0, SpringLayout.NORTH, restart);
		controlLayout.putConstraint(SpringLayout.EAST, restart, 0, SpringLayout.EAST, compareChanges);
		controlLayout.putConstraint(SpringLayout.WEST, restart, 0, SpringLayout.WEST, compareChanges);
		controlLayout.putConstraint(SpringLayout.WEST, compareChanges, 20, SpringLayout.WEST, this);
		controlLayout.putConstraint(SpringLayout.WEST, glitchPanel, 40, SpringLayout.WEST, this);
		extraTools = new EditingTools(app);
		appLayout.putConstraint(SpringLayout.NORTH, extraTools, 60, SpringLayout.NORTH, this);
		appLayout.putConstraint(SpringLayout.WEST, extraTools, 85, SpringLayout.EAST, controlPanel);
		appLayout.putConstraint(SpringLayout.SOUTH, extraTools, 78, SpringLayout.NORTH, this);
		appLayout.putConstraint(SpringLayout.EAST, extraTools, -13, SpringLayout.EAST, controlPanel);
		genericOptions = new String[]{"Cancel","OK!" };

		setupPanel();
		setupLayout();
		setupListeners();
	}

	/**
	 * setsup the panel by adding all needed components
	 */
	private void setupPanel()
	{
		this.setLayout(appLayout);
		controlPanel.setLayout(controlLayout);
		this.add(controlPanel);
		this.add(extraTools);
		controlPanel.setPreferredSize(new Dimension(180, 350));
		switchPanel.setPreferredSize(new Dimension(180, 50));
		glitchPanel.setPreferredSize(new Dimension(90, 100));
		saveLoadPanel.setPreferredSize(new Dimension(180, 50));
		controlPanel.add(switchPanel);
		controlPanel.add(saveLoadPanel);
		controlPanel.add(glitchPanel);
		controlPanel.add(compareChanges);
		saveLoadPanel.add(load);
		saveLoadPanel.add(save);
		glitchPanel.add(glitch);
		switchPanel.add(undo);
		glitchPanel.add(make3D);
		glitchPanel.add(scanlines);
		glitchPanel.add(grain);
		switchPanel.add(redo);
		controlPanel.add(restart);
		compareChanges.setVisible(false);
		save.setVisible(false);
		restart.setVisible(false);
		showTools(false);
	}

	/**
	 * sets the constraints on key components
	 */
	private void setupLayout()
	{
		controlLayout.putConstraint(SpringLayout.NORTH, glitchPanel, 30, SpringLayout.SOUTH, saveLoadPanel);
		controlLayout.putConstraint(SpringLayout.EAST, switchPanel, 0, SpringLayout.EAST, saveLoadPanel);

		controlLayout.putConstraint(SpringLayout.SOUTH, restart, -10, SpringLayout.NORTH, switchPanel);
		controlLayout.putConstraint(SpringLayout.SOUTH, compareChanges, 0, SpringLayout.NORTH, restart);
		controlLayout.putConstraint(SpringLayout.EAST, restart, 0, SpringLayout.EAST, compareChanges);
		controlLayout.putConstraint(SpringLayout.WEST, restart, 0, SpringLayout.WEST, compareChanges);
		controlLayout.putConstraint(SpringLayout.WEST, compareChanges, 20, SpringLayout.WEST, this);
		controlLayout.putConstraint(SpringLayout.WEST, glitchPanel, 40, SpringLayout.WEST, this);
		appLayout.putConstraint(SpringLayout.NORTH, extraTools, 0, SpringLayout.NORTH, this);
		appLayout.putConstraint(SpringLayout.WEST, extraTools, 10, SpringLayout.EAST, controlPanel);
	}
	
	/**
	 * adds listeners to buttons, the buttons will be linked to methods from the controller
	 */
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
					extraTools.updateDimensions();

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
				extraTools.setMake3D();
				repaint();
				processToolRequest(1);
			}
		});
		
		scanlines.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				
				extraTools.setScanline();
				int result = JOptionPane.showOptionDialog(getSelf(), extraTools, "Scanlines", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, genericOptions, genericOptions[0]);
				processToolRequest(result);
			}
		});
		
		grain.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				
				extraTools.setGrain();
				int result = JOptionPane.showOptionDialog(getSelf(), extraTools, "Grainy", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, genericOptions, genericOptions[0]);
				processToolRequest(result);
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
				}
				else
				{
					app.setCurrentImage(app.getLastEdit(app.getCurrentStackIndex()));
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
					app.addToStack(app.getCurrentStackIndex(),app.getCurrentImage());
					app.updateDisplay();
					repaint();
					updateUndoRedo();
				}
			}
		});
	}

	/**
	 * shows/hides key components quickly and easily
	 * @param state show tools or not
	 */
	private void showTools(boolean state)
	{
		glitchPanel.setVisible(state);
		switchPanel.setVisible(state);
		repaint();
	}
	
	/**
	 * determines if undo/redo button should be enabled based on where it is currently in the stack
	 */
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

	/**
	 * shows certain components that only work if there was an edit made
	 */
	private void editMade()
	{
		compareChanges.setVisible(true);
		restart.setVisible(true);
		save.setVisible(true);
		canEdit = true;
		app.restartStackIndex();
		undo.setEnabled(true);
		repaint();
		
	}
	
	private void processToolRequest(int result)
	{
		if (result == 1)
		{
			app.addToStack(app.getCurrentStackIndex(),app.getCurrentImage());
			editMade();
			extraTools.restartPanel();
			updateUndoRedo();

		}
		else
		{
			extraTools.restartPanel();
			app.setCurrentImage(app.getLastEdit(app.getCurrentStackIndex()));
			app.updateDisplay();
		}
	}


	public Dimension getControlSize()
	{
		return controlPanel.getPreferredSize();
	}
	
	private Component getSelf()
	{
		return this;
	}

}
