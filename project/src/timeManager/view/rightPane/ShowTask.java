package timeManager.view.rightPane;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;

import timeManager.ActionSt;
import timeManager.View;

@SuppressWarnings("serial")
public class ShowTask extends JDialog {
	private JTextField taskName;
	private JTextField category;
	private JTextField spinnerDate;
	private JTextField priority;
	private JTextArea description;
	private JTextField status;

	/**
	 * Creates a JDialog to show a task.
	 * <p>
	 * The JDialog will be able to show a task which contains: Name, deadline,
	 * priority, category, status and description of the task.
	 * 
	 * @param myActions - ActionSt object to be able to set the actionlistener for the ok button
	 * @param mainWindow - JFrame to have a "owner-window" for the dialog.
	 * @param task - Object array containing the different attributes a task have.
	 */
	public ShowTask(ActionSt myActions,
			JFrame mainWindow, Object[] task) {
		super(mainWindow, View.bundle.getString("Show_task"), true);

		// Setting up the frame.
		this.setLayout(new GridBagLayout());
		this.setSize(450, 400);
		this.setResizable(false);
		
		// Initializing the two main-panels
		JPanel topPanel = new JPanel();
		JPanel bottomPanel = new JPanel();

		// Adding GridBagLayouts and creating a GridBagConstraint c
		topPanel.setLayout(new GridBagLayout());
		bottomPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		// Showing the task name.
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0;
		this.taskName = new JTextField((String) task[1]);
		taskName.setEditable(false);
		taskName.setPreferredSize(new Dimension(200, 40));
		JPanel taskNamePanel = new JPanel();
		taskNamePanel.setBorder(new CompoundBorder(BorderFactory
				.createTitledBorder(View.bundle.getString("Task_name")),
				taskNamePanel.getBorder()));
		taskNamePanel.add(taskName);
		topPanel.add(taskNamePanel, c);

		// Showing date and time.
		c.gridx = 1;
		c.gridy = 0;
		this.spinnerDate = new JTextField(((Date) task[2]).toString());
		spinnerDate.setPreferredSize(new Dimension(200, 40));
		spinnerDate.setEditable(false);
		JPanel spinnerDatePanel = new JPanel();
		spinnerDatePanel.setBorder(new CompoundBorder(BorderFactory
				.createTitledBorder(View.bundle.getString("Time")),
				spinnerDatePanel.getBorder()));
		spinnerDatePanel.add(spinnerDate);
		topPanel.add(spinnerDatePanel, c);
		
		
		// Showing the category.
		c.gridx = 0;
		c.gridy = 1;
		this.category = new JTextField((String)task[3]);
		category.setEditable(false);
		category.setPreferredSize(new Dimension(200, 40));
		JPanel categoryPanel = new JPanel();
		categoryPanel.setBorder(new CompoundBorder(BorderFactory
				.createTitledBorder(View.bundle.getString("Category")),
				categoryPanel.getBorder()));
		categoryPanel.add(category);
		topPanel.add(categoryPanel, c);
		
		
		// Showing the priority
		c.gridx = 1;
		c.gridy = 1;
		this.priority = new JTextField(task[4].toString());
		priority.setEditable(false);
		priority.setPreferredSize(new Dimension(200, 40));
		JPanel priorityPanel = new JPanel();
		priorityPanel.setBorder(new CompoundBorder(BorderFactory
				.createTitledBorder(View.bundle.getString("Priority")),
				priorityPanel.getBorder()));
		priorityPanel.add(priority);
		topPanel.add(priorityPanel, c);
		

		// Showing the status
		c.gridx = 0;
		c.gridy = 2;
		this.status = new JTextField((String)task[5]);
		status.setEditable(false);
		status.setPreferredSize(new Dimension(200, 40));
		JPanel statusPanel = new JPanel();
		statusPanel.setBorder(new CompoundBorder(BorderFactory
				.createTitledBorder(View.bundle.getString("Status")),
				statusPanel.getBorder()));
		statusPanel.add(status);
		topPanel.add(statusPanel, c);

		// Showing the description.
		this.description = new JTextArea((String) task[6]);
		description.setSize(100, 100);
		description.setMinimumSize(new Dimension(200, 200));
		description.setLineWrap(true);
		description.setWrapStyleWord(true);
		description.setEditable(false);
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

		// Adding button to exit the dialog.
		c.weightx = 1;
		c.anchor = GridBagConstraints.PAGE_END;
		c.weighty = 0;
		c.gridwidth = 1;
		c.gridx = 0;
		c.ipady = 0;
		c.gridy = 1;
		c.insets = new Insets(3, 300, 3, 3);
		JButton okButton = new JButton(myActions.getOkShow());
		bottomPanel.add(okButton,c);

		// Adding the two panels to the dialog.
		c.insets = new Insets(3,3,3,3);
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
}
