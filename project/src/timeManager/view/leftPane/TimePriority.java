package timeManager.view.leftPane;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;

import timeManager.View;

/**
 * Filtering panel for date and priority ranges.
 * 
 */
@SuppressWarnings("serial")
public class TimePriority extends JPanel {
	private SpinnerDateModel dateStart;
	private SpinnerDateModel dateEnd;
	private SpinnerNumberModel priorityStartModel;
	private SpinnerNumberModel priorityEndModel;
	private JSpinner dueStart;
	private JSpinner dueEnd;
	private JSpinner priorityStart;
	private JSpinner priorityEnd;

	/**
	 * Initializes the TimePriority panel.
	 */
	public TimePriority() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		// Add due label
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0;
		c.fill = GridBagConstraints.BOTH;
		JLabel dueLabel = new JLabel(View.bundle.getString("Due_between"));
		this.add(dueLabel, c);

		// Add lower date range.
		c.gridwidth = 1;
		c.weightx = 0;
		c.gridy = 1;
		c.gridx = 0;
		dueStart = new JSpinner(dateStart = new SpinnerDateModel(
				new Date(new Date().getTime()
						- Double.valueOf("6.311E+10").longValue()),
				new Date(0L), new Date(Long.MAX_VALUE), Calendar.MINUTE));

		this.add(dueStart, c);

		// Add due date separation label.
		c.weightx = 0;
		c.gridy = 1;
		c.gridx = 1;
		JLabel dueAnd = new JLabel("~");
		this.add(dueAnd, c);

		// Add upper date range spinner.
		c.weightx = 0;
		c.gridy = 1;
		c.gridx = 2;
		dueEnd = new JSpinner(dateEnd = new SpinnerDateModel(
				new Date(new Date().getTime()
						+ Double.valueOf("6.311E+10").longValue()),
				new Date(0L), new Date(Long.MAX_VALUE), Calendar.MINUTE));
		this.add(dueEnd, c);

		// Add Priority label
		c.weightx = 0;
		c.gridy = 2;
		c.gridx = 0;
		c.gridwidth = 2;
		c.insets = new Insets(10, 0, 0, 0);
		JLabel priorityLabel = new JLabel(
				View.bundle.getString("Priority_between"));
		this.add(priorityLabel, c);
		c.insets = new Insets(0, 0, 10, 0);

		// Add lower priority range.
		c.weightx = 0;
		c.gridy = 3;
		c.gridx = 0;
		c.gridwidth = 1;
		priorityStart = new JSpinner(
				priorityStartModel = new SpinnerNumberModel(0, 0, 10, 1));
		this.add(priorityStart, c);

		// Add priority separation label.
		c.weightx = 0;
		c.gridy = 3;
		c.gridx = 1;
		JLabel priorityAnd = new JLabel("~");
		this.add(priorityAnd, c);

		// Add priority upper range spinner.
		c.weightx = 0;
		c.gridy = 3;
		c.gridx = 2;
		priorityEnd = new JSpinner(priorityEndModel = new SpinnerNumberModel(
				10, 0, 10, 1));
		this.add(priorityEnd, c);

	}

	/**
	 * Gets the selected dates in the date spinners.
	 * 
	 * @return {Lower Bound, Upper Bound}
	 */
	public Date[] getDates() {
		Date[] dates = { dateStart.getDate(), dateEnd.getDate() };
		return dates;
	}

	/**
	 * Gets the selected priorities in the priority spinners.
	 * 
	 * @return {Lower Bound, Upper Bound}
	 */
	public int[] getPriorities() {
		int[] dates = { priorityStartModel.getNumber().intValue(),
				priorityEndModel.getNumber().intValue() };
		return dates;
	}

	/**
	 * Sets the dates to approx. two years past and two years into the future.
	 * Priorities are reset to a lower bound of 0 and an upper bound of 10.
	 */
	public void reset() {
		priorityStart.setValue(0);
		priorityEnd.setValue(10);
		dateStart.setValue(new Date(new Date().getTime()
				- Double.valueOf("6.311E+10").longValue()));
		dateEnd.setValue(new Date(new Date().getTime()
				+ Double.valueOf("6.311E+10").longValue()));
	}

	/**
	 * Sets the change listeners to fire filtering on change.
	 * 
	 * @param cl
	 *            The change listener to be added.
	 */
	public void setListeners(ChangeListener cl) {
		priorityEnd.addChangeListener(cl);
		priorityStart.addChangeListener(cl);
		dueStart.addChangeListener(cl);
		dueEnd.addChangeListener(cl);
	}
}
