package timeManager.view.leftPane;

import javax.swing.JSlider;

/**
 * 
 * a Slider to set the priority of a task inside the AddTask and EditTask classes
 * 
 */
@SuppressWarnings("serial")
public class Slider extends JSlider {
	/**
	 * 
	 * @param minValue - minimum value of the slider
	 * @param maxValue - maximum value of the slider
	 * @param initialValue - initialvalue of the slider
	 */
	public Slider(int minValue, int maxValue, int initialValue) {
		super(JSlider.HORIZONTAL, minValue, maxValue, initialValue);
		this.setVisible(true);
		this.setMinorTickSpacing(1);
		this.setPaintTicks(true);

	}
}
