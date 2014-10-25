package timeManager;

import timeManager.view.leftPane.SetLookAndFeel;

public class TimeManager {
	/**
	 * Initializes the MVC structure. 
	 */
	public static void main(String args[]) {
		

		ActionSt myActions = new ActionSt();
		View v = new View("Time Manager", myActions);
		Model m = new Model();
		new Controller(v, m, myActions);
	}
}
