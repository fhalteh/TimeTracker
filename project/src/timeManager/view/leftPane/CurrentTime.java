package timeManager.view.leftPane;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import timeManager.CustomPanel;

/**
 * Displays the current time and date
 * and a custom timer object.
 */
public class CurrentTime extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;
	private Thread thread;
	private JLabel timeLabel, dateLabel;
	
	// Formatters for date and time.
	private static SimpleDateFormat dateformat = new SimpleDateFormat(
			"yyyy-MM-dd");
	private static SimpleDateFormat timeformat = new SimpleDateFormat(
			"HH:mm:ss");

	/**
	 * Initializes the display of the current time and the CustomPanel timer.
	 */
	public CurrentTime() {
		this.setBorder(BorderFactory.createEtchedBorder());
		this.setLayout(new GridBagLayout());
		// Add Labels for date and time.
		timeLabel = new JLabel();
		timeLabel.setFont(new Font("Serif", Font.PLAIN, 15));
		dateLabel = new JLabel();
		dateLabel.setFont(new Font("Serif", Font.PLAIN, 25));
		GridBagConstraints c = new GridBagConstraints();
		c.gridy = 0;
		this.add(dateLabel, c);
		c.gridy = 1;
		this.add(timeLabel, c);
		
		// Create and add CustomPanel timer.
		CustomPanel p = new CustomPanel();
		p.setMinimumSize(new Dimension(300,80));
		c.fill = GridBagConstraints.BOTH;
		c.gridy = 2;
		this.add(p, c);
		
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
		
		setVisible(true);
	}

	/**
	 * Updates the date and time labels every second.
	 */
	public void run() {
		while (true) {
			GregorianCalendar date = new GregorianCalendar();
			timeLabel.setText(timeformat.format(date.getTime()));
			dateLabel.setText(dateformat.format(date.getTime()));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
