package timeManager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * CustomPanel class, for a custom timer.
 * 
 * 
 */
@SuppressWarnings("serial")
public class CustomPanel extends JPanel implements Runnable {
	int i = 0;
	boolean started = false;
	private Thread thread;

	/**
	 * Initializes the timer component.
	 */
	public CustomPanel() {
		setBorder(BorderFactory.createLineBorder(Color.black));
		// Sets a runnable thread for the timer.
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
		// Adds a mouse listener for handling click events
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				// LMB starts or stops the timer
				if (e.getButton() == MouseEvent.BUTTON1) {
					started = !started;
				}
				// RMB Pauses the timer
				if (e.getButton() == MouseEvent.BUTTON3) {
					i = 0;
					repaint();
				}
			}
		});
	}

	/**
	 * Returns the CustomPanels preferred size.
	 */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(300, 80);
	}

	/**
	 * Handles actual painting of the CustomPanel
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Get timeunits for the timer.
		long hours = TimeUnit.MILLISECONDS.toHours(i) % 24;
		long mins = TimeUnit.MILLISECONDS.toMinutes(i) % 60;
		long secs = TimeUnit.MILLISECONDS.toSeconds(i) % 60;
		// Get height and width
		int width = this.getWidth();
		int height = this.getHeight();
		// Draw the timer.
		drawTime(width / 2 - 3, height / 2 - 10, hours, mins, secs, g);
		// Draw a clock with a second hand
		drawClock(30, height / 2 - 10, 50, g);

		// Print instructions
		Font f = new Font("Tahoma", 9, 9);
		f = f.deriveFont(Font.BOLD);
		g.setFont(f);
		g.drawString("Timer - LeftClick: Start/Stop RightClick: Reset", 35, 75);

		// if started increment the time.
		if (started) {
			i += 1000;
		}

	}

	/**
	 * Draws a "digital" looking time in the format HH:MM:SS
	 * 
	 * @param x
	 *            X-Coordinate of the timer.
	 * @param y
	 *            Y-coordinate of the timer.
	 * @param hours
	 *            The number of hours.
	 * @param mins
	 *            The number of minutes
	 * @param secs
	 *            The number of seconds
	 * @param g
	 *            Graphic object for drawing the data on the panel.
	 */
	private void drawTime(int x, int y, long hours, long mins, long secs,
			Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		// Draw seconds
		drawNumber((int) secs % 10, x * 2 - 30, y - 20, g);
		drawNumber((int) (secs / 10 % 6), x * 2 - 64, y - 20, g);
		// Draw second and minute divider
		Shape smdivt = new Rectangle2D.Double(x * 2 - 75, y - 7, 5, 5);
		Shape smdivb = new Rectangle2D.Double(x * 2 - 75, y + 7, 5, 5);
		g2.fill(smdivt);
		g2.fill(smdivb);
		g2.draw(smdivt);
		g2.draw(smdivb);
		// Draw minutes
		drawNumber((int) mins % 10, x * 2 - 108, y - 20, g);
		drawNumber((int) (mins / 10 % 6), x * 2 - 142, y - 20, g);
		// Draw minute and hour divider
		Shape mhdivt = new Rectangle2D.Double(x * 2 - 152, y - 7, 5, 5);
		Shape mhdivb = new Rectangle2D.Double(x * 2 - 152, y + 7, 5, 5);
		g2.fill(mhdivt);
		g2.fill(mhdivb);
		g2.draw(mhdivt);
		g2.draw(mhdivb);
		// Draw hours
		drawNumber((int) hours % 10, x * 2 - 186, y - 20, g);
		drawNumber((int) (hours / 10 % 24), x * 2 - 220, y - 20, g);
	}

	/**
	 * Draws a clock with a second hand.
	 * 
	 * @param x
	 *            X-coordinate of the clock.
	 * @param y
	 *            Y-coordinate of the clock.
	 * @param diam
	 *            The diameter of the click.
	 * @param g
	 *            Graphic object for drawing the data on the panel.
	 */
	private void drawClock(int x, int y, int diam, Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		// Get offsets for second hand pointer
		int secDir[] = getTimeCoords(diam);
		// Draw frame
		g2.draw(new Ellipse2D.Double(x - diam / 2, y - diam / 2, diam, diam));
		// Draw second hand
		g2.draw(new Line2D.Double(x, y, x + secDir[0], y + secDir[1]));
	}

	/**
	 * Draws a "digital" looking number.
	 * 
	 * @param num
	 *            The number to be displayed
	 * @param x
	 *            X-coordinate of the number.
	 * @param y
	 *            Y-coordinate of the number.
	 * @param g
	 *            Graphic object for drawing the data on the panel.
	 */
	private void drawNumber(int num, int x, int y, Graphics g) {
		// Prevent num from going past 9.
		num = num % 10;
		Graphics2D g2 = (Graphics2D) g;
		// Define blocks for drawing.
		Shape lt = new Rectangle2D.Double(x, y + 3, 3, 15);
		Shape lb = new Rectangle2D.Double(x, y + 24, 3, 15);
		Shape rt = new Rectangle2D.Double(x + 24, y + 3, 3, 15);
		Shape rb = new Rectangle2D.Double(x + 24, y + 24, 3, 15);
		Shape ct = new Rectangle2D.Double(x + 4, y - 2, 19, 3);
		Shape cm = new Rectangle2D.Double(x + 4, y + 19, 19, 3);
		Shape cb = new Rectangle2D.Double(x + 4, y + 41, 19, 3);
		// Determine which blocks should be filled.
		switch (num) {
		case 0:
			g2.fill(lt);
			g2.fill(lb);
			g2.fill(rt);
			g2.fill(rb);
			g2.fill(ct);
			g2.fill(cb);
			break;
		case 1:
			g2.fill(rt);
			g2.fill(rb);
			break;
		case 2:
			g2.fill(lb);
			g2.fill(rt);
			g2.fill(ct);
			g2.fill(cb);
			g2.fill(cm);
			break;
		case 3:
			g2.fill(rt);
			g2.fill(rb);
			g2.fill(ct);
			g2.fill(cm);
			g2.fill(cb);
			break;
		case 4:
			g2.fill(lt);
			g2.fill(rt);
			g2.fill(rb);
			g2.fill(cm);
			break;
		case 5:
			g2.fill(lt);
			g2.fill(rb);
			g2.fill(ct);
			g2.fill(cm);
			g2.fill(cb);
			break;
		case 6:
			g2.fill(lt);
			g2.fill(lb);
			g2.fill(rb);
			g2.fill(ct);
			g2.fill(cm);
			g2.fill(cb);
			break;
		case 7:
			g2.fill(rt);
			g2.fill(rb);
			g2.fill(ct);
			break;
		case 8:
			g2.fill(lt);
			g2.fill(lb);
			g2.fill(rt);
			g2.fill(rb);
			g2.fill(ct);
			g2.fill(cm);
			g2.fill(cb);
			break;
		case 9:
			g2.fill(lt);
			g2.fill(rt);
			g2.fill(rb);
			g2.fill(ct);
			g2.fill(cm);
			break;
		default:
			break;
		}
		// Draw the blocks
		g2.draw(lt);
		g2.draw(lb);
		g2.draw(rt);
		g2.draw(rb);
		g2.draw(ct);
		g2.draw(cb);
		g2.draw(cm);

	}

	/**
	 * Gets the coordinate offsets for a clock hand based on its diameter.
	 * 
	 * @param clockDiam
	 *            Diameter of a clock.
	 * @return Returns the x- and y-offset for a clock hand.
	 */
	private int[] getTimeCoords(int clockDiam) {
		int coords[] = { 0, 0 };
		long secs = TimeUnit.MILLISECONDS.toSeconds(i) % 60;
		int deg = (int) (secs * 6) - 90;
		double yoffset = Math.sin(Math.toRadians(deg)) * (clockDiam / 2);
		double xoffset = Math.cos(Math.toRadians(deg)) * (clockDiam / 2);
		coords[0] = ((int) xoffset);
		coords[1] = ((int) yoffset);
		return coords;
	}

	/**
	 * Increases counter and repaints the CustomPanel.
	 */
	@Override
	public void run() {
		while (true) {
			this.repaint();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
