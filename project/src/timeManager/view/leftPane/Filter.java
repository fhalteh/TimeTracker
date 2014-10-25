package timeManager.view.leftPane;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import timeManager.ActionSt;
import timeManager.View;

/**
 * Panel for keeping view elements associated with filtering.
 * 
 */

@SuppressWarnings("serial")
public class Filter extends JPanel {
	private Categories categories;
	private TimePriority timepriority;

	/**
	 * A panel to hold everything associated with filtering out the tasks.
	 */
	public Filter(ActionSt myActions) {

		// Setting up the pane.
		this.setBorder(BorderFactory.createEtchedBorder());
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0;

		// Adds a title label.
		JLabel filter = new JLabel(View.bundle.getString("Filter"));
		filter.setFont(filter.getFont().deriveFont(16.0f));
		this.add(filter);

		// Adds the priority filter.
		c.gridy = 5;
		this.timepriority = new TimePriority();
		this.add(timepriority, c);

		// Adds the categories filter.
		c.weighty = 1;
		c.gridy = 10;
		this.categories = new Categories();
		this.add(categories, c);

		// Adds a clear button.
		JButton clear = new JButton(myActions.getClearFilter());
		c.gridy = 20;
		c.weighty = 0;
		c.insets = new Insets(0, 0, 10, 0);
		this.add(clear, c);
	}

	/**
	 * Gets the panel containing the categories.
	 * 
	 * @return Categories panel.
	 */
	public Categories getCategories() {
		return this.categories;
	}

	/**
	 * Gets the TimePriority Panel.
	 * 
	 * @return TimPriority Panel.
	 */
	public TimePriority getSearchData() {
		return this.timepriority;
	}
}
