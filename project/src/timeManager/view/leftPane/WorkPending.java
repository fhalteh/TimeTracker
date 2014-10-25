package timeManager.view.leftPane;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;

import timeManager.ActionSt;
import timeManager.Model;
import timeManager.View;

/**
 * 
 * @Steve, @Faris
 */
@SuppressWarnings("serial")
public class WorkPending extends JDialog {

	private DefaultListModel<String> tasks;
	private DefaultListModel<Date> dates;

	private int list_size;
	
	private Model model;

	/**
	 * Get's a list of high priority tasks, then creates a new JFrame that shows
	 * a JList of high priority tasks
	 * 
	 * @Param list of task names
	 * @Param actions
	 */
	public WorkPending(Model model, JFrame mainWindow, int list_size, ActionSt actions) {
		super(mainWindow, View.bundle.getString("Show_selected"), true);
		
		this.list_size = list_size;
		this.model = model;
		
		tasks = new DefaultListModel<String>();
		dates = new DefaultListModel<Date>();

		//calls a method addTasksToLists in order to add the tasks and the dates
		//into the DefaultListModels!
		addTasksToLists();

		// Making lists
		JList<String> taskNameList = new JList<String>(tasks);
		taskNameList.setPreferredSize(new Dimension(150, 150));

		JList<Date> dueDateList = new JList<Date>(dates);
		dueDateList.setPreferredSize(new Dimension(150, 150));

		// Making the JLabels
		JLabel task_label = new JLabel(View.bundle.getString("Task"));
		JLabel due_date_label = new JLabel(View.bundle.getString("Due_Date"));

		// Making buttons
		JButton plusBtn = new JButton(actions.getPlusButton());
		JButton minusBtn = new JButton(actions.getMinusButton());

		/* SETTING UP THE FRAME / LAYOUT ********************* */
		this.setLayout(new GridBagLayout());
		this.setSize(400, 300);
		this.setMinimumSize(new Dimension(400, 300));		
		
		JPanel mainPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		
		mainPanel.setBorder(new CompoundBorder(BorderFactory
				.createTitledBorder(View.bundle.getString("Pending_tasks")),
				mainPanel.getBorder()));

		
		// Adding GridBagLayouts and creating a GridBagConstraint c
		mainPanel.setLayout(new GridBagLayout());
		buttonPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		// Adding the Task Label
		c.gridx = 0; // column 0
		c.gridy = 0; // row 0
		c.weightx = 1;
		c.weighty = 0;
		mainPanel.add(task_label, c);

		// Adding the Due Date Label
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0;
		mainPanel.add(due_date_label, c);

		// Adding the taskName List to mainPanel
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 0;
		mainPanel.add(taskNameList, c);

		// Adding the dueDate List to mainPanel
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 0;
		mainPanel.add(dueDateList, c);

		// Adding the plus button to the buttonPanel
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0;
		buttonPanel.add(plusBtn, c);

		// Adding the minus button to the buttonPanel
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0;
		buttonPanel.add(minusBtn, c);

		// ADDING THE PANELS TO THE DIALOG
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0;
		this.add(mainPanel, c);

		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 0;
		this.add(buttonPanel, c);
	}

	public void addTasksToLists() {
		// Get an array of high priority tasks, store them into a vector:
		Vector<Object[]> taskData = model.getWorkPending(list_size);
		
		list_size = taskData.size();
		
		tasks.clear();
		dates.clear();

		/*
		 * for loop in order to go through the taskData Vector to retrieve all
		 * the task names and the due dates and then add them to the model list
		 */
		for (int i = 0; i < taskData.size(); i++) {

			if (taskData.get(i) != null) {
				Object[] TaskName = taskData.get(i);

				tasks.addElement((String) TaskName[1]);
				dates.addElement((Date) TaskName[2]);
			}

		}
	}
	
	public void decrementListSize(){
		if(list_size>0)
		list_size--;
	}

	public void incrementListSize() {
		list_size++;
	}


}
