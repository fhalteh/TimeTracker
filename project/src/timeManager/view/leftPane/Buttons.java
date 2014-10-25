package timeManager.view.leftPane;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import timeManager.ActionSt;

/**
 * A JPanel which contains the buttons to add and remove tasks.
 *
 */
@SuppressWarnings("serial")
public class Buttons extends JPanel {

	private JButton addTaskButton;
	private JButton removeTaskButton;
	private JButton showSelected;

	/**
	 * Initializes the the button panel, which contains a number of JButtons.
	 * @param action - action is added to each button on creation as a parameter
	 */
	public Buttons(ActionSt myActions) {

		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		// Adding the button to create a task
		ImageIcon addIcon = new ImageIcon(getClass().getResource("Add24.gif"));
		this.addTaskButton = new JButton(myActions.getAddTask());
		this.addTaskButton.setIcon(addIcon);
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		c.insets = new Insets(0, 0, 3, 0);
		this.add(addTaskButton, c);

		// Adding the button to remove a task.
		ImageIcon deleteIcon = new ImageIcon(getClass().getResource(
				"Delete24.gif"));
		this.removeTaskButton = new JButton(myActions.getRemoveTask());
		this.removeTaskButton.setIcon(deleteIcon);
		c.gridx = 0;
		c.gridy = 1;
		this.add(removeTaskButton, c);
		
		//Buttons for pop-up window, top 5 high priority tasks / selected task
		this.showSelected = new JButton(myActions.getShowSelected());
		c.gridx = 0; //column 0
		c.gridy = 3; //row 3
		c.insets = new Insets(3, 0, 0, 0); //padding (top, left, bottom, right)
		this.add(showSelected, c);
	}
}