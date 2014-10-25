package timeManager.view.leftPane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.border.CompoundBorder;

import timeManager.ActionSt;
import timeManager.View;

/**
 * Dialog window for adding new tasks.
 * 
 */
@SuppressWarnings("serial")
public class AddTask extends JDialog {
	private JButton submitTaskButton;
	private JTextField taskName;
	private JComboBox<String> comboBox;
	private SpinnerDateModel spinnerDate;
	private Slider prioritySlider;
	private JTextArea description;

	/**
	 * Creates a JDialog to make it possible for the user to add new tasks.
	 * <p>
	 * The JDialog will be able to add a task which contains: Name, deadline,
	 * priority, category and description of the task.
	 * 
	 * @param categories
	 *            Categories to be choosable from a drop-down.
	 * @param myActions
	 *            Actions for handling interactions.
	 * @param mainWindow
	 *            Main window for locking the main window.
	 */
	public AddTask(Vector<String> categories, ActionSt myActions,
			JFrame mainWindow) {
		super(mainWindow, View.bundle.getString("Add_task"), true);

		// Setting up the frame
		this.setLayout(new GridBagLayout());
		this.setSize(400, 300);
		this.setResizable(false);
		JPanel topPanel = new JPanel();
		JPanel bottomPanel = new JPanel();

		// Adding GridBagLayouts and creating a GridBagConstraint c
		topPanel.setLayout(new GridBagLayout());
		bottomPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		// Adding a TextField for inserting the task name.
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0;
		this.taskName = new JTextField();
		taskName.setPreferredSize(new Dimension(160, 30));
		JPanel taskNamePanel = new JPanel();
		taskNamePanel.setBorder(new CompoundBorder(BorderFactory
				.createTitledBorder(View.bundle.getString("Task_name")),
				taskNamePanel.getBorder()));
		taskNamePanel.add(taskName);
		topPanel.add(taskNamePanel, c);

		// Adding a Spinner to set date and time.
		this.spinnerDate = new SpinnerDateModel();
		final JSpinner dateAndTimeSetter = new JSpinner(spinnerDate);
		dateAndTimeSetter.setPreferredSize(new Dimension(120, 30));
		JPanel timePanel = new JPanel();
		timePanel.setBorder(new CompoundBorder(BorderFactory
				.createTitledBorder(View.bundle.getString("Set_time")),
				timePanel.getBorder()));
		c.gridx = 1;
		c.gridy = 0;
		timePanel.add(dateAndTimeSetter);
		topPanel.add(timePanel, c);

		// Adding a slider to set the priority
		this.prioritySlider = new Slider(0, 10, 0);
		prioritySlider.setPreferredSize(new Dimension(120, 30));
		c.gridx = 1;
		c.gridy = 1;
		JPanel priorityPanel = new JPanel();
		priorityPanel.setBorder(new CompoundBorder(BorderFactory
				.createTitledBorder(View.bundle.getString("Priority")),
				priorityPanel.getBorder()));
		priorityPanel.add(prioritySlider);
		topPanel.add(priorityPanel, c);

		// Adding a combobox for categories.
		this.comboBox = new JComboBox<String>(categories);
		comboBox.setPreferredSize(new Dimension(160, 30));

		comboBox.setBackground(Color.white);
		JPanel categoryPanel = new JPanel();
		categoryPanel.setBorder(new CompoundBorder(BorderFactory
				.createTitledBorder(View.bundle.getString("Set_category")),
				categoryPanel.getBorder()));
		c.gridx = 0;
		c.gridy = 1;
		categoryPanel.add(comboBox);
		topPanel.add(categoryPanel, c);

		// Adding a TextArea for the description of the task
		this.description = new JTextArea();
		description.setSize(100, 100);
		description.setMinimumSize(new Dimension(200, 200));
		description.setLineWrap(true);
		description.setWrapStyleWord(true);
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 1;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.BOTH;

		// Adding a scroll to the description
		JScrollPane scrollDescription = new JScrollPane(description);
		scrollDescription.setMinimumSize(new Dimension(200, 200));
		scrollDescription.setBorder(new CompoundBorder(BorderFactory
				.createTitledBorder(View.bundle.getString("Description")),
				scrollDescription.getBorder()));
		bottomPanel.add(scrollDescription, c);

		// Adding buttons to cancel adding a task or to submit the task.
		c.weightx = 1;
		c.anchor = GridBagConstraints.PAGE_END;
		c.weighty = 0;
		c.gridwidth = 1;
		c.gridx = 0;
		c.ipady = 0;
		c.gridy = 1;
		c.insets = new Insets(3, 160, 3, 3);
		JButton cancel = new JButton(myActions.getCancel());

		bottomPanel.add(cancel, c);
		this.submitTaskButton = new JButton(myActions.getSubmit());
		c.insets = new Insets(3, 3, 3, 3);
		c.gridx = 1;
		c.gridy = 1;
		bottomPanel.add(submitTaskButton, c);

		// Adding the two panels to the dialog.
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0;
		this.add(topPanel, c);
		c.weightx = 0;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 1;
		this.add(bottomPanel, c);
	}

	/**
	 * Gets data submitted by the user in add task.
	 * 
	 * @return returns the data submitted by the user.
	 */
	public Object[] getData() {
		Object[] data = { taskName, comboBox, spinnerDate, prioritySlider,
				description };
		return data;
	}

}
