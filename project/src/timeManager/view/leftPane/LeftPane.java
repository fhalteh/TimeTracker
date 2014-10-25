package timeManager.view.leftPane;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import timeManager.ActionSt;

/**
 * Main container for the Left portion of the applications view.
 * 
 */
@SuppressWarnings("serial")
public class LeftPane extends JPanel {
	private Buttons buttons;
	private Filter filter;

	/**
	 * Gets the panel containing the left panes buttons.
	 * 
	 * @return Button panel
	 */
	public Buttons getButtons() {
		return buttons;
	}

	/**
	 * Initializes the left pane.
	 * 
	 * @param myActions
	 *            Actions for performing interaction between the view and model.
	 */
	public LeftPane(ActionSt myActions) {
		this.setBorder(BorderFactory.createEtchedBorder());
		this.setSize(200, 300);
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		// Add Time panel.
		c.fill = GridBagConstraints.BOTH;
		c.ipady = 40;
		c.weightx = 1;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 0;
		this.add(new CurrentTime(), c);
		// Add filter panel.
		c.weighty = 1;
		c.gridy = 1;
		this.add(filter = new Filter(myActions), c);
		// Add button panel.
		c.weighty = 0;
		c.gridy = 2;
		c.ipady = 15;
		this.add(buttons = new Buttons(myActions), c);
	}

	/**
	 * Gets the filter panel.
	 * 
	 * @return Filter panel.
	 */
	public Filter getFilter() {
		return filter;
	}
}
