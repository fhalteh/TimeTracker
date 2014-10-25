package timeManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.SpinnerDateModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import timeManager.view.leftPane.EditTask;
import timeManager.view.leftPane.SetLookAndFeel;
import timeManager.view.leftPane.WorkPending;
import timeManager.view.leftPane.Slider;
import timeManager.view.leftPane.AddTask;
import timeManager.view.rightPane.ShowTask;

/**
 * The Controller class handles the interaction between model and view.
 * 
 */
public class Controller {
	// Formatter for dates.
	public static SimpleDateFormat format = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm");
	private View view;
	private Model model;
	private AddTask a;
	private WorkPending workPending;
	private ActionSt myActions;
	private EditTask editTask;
	private ShowTask showTask;

	/**
	 * Listens to the category model and refreshes it upon change.
	 */
	private TableModelListener categoryTableListener = new TableModelListener() {
		@Override
		public void tableChanged(TableModelEvent e) {
			refreshTasks();
		}
	};

	/**
	 * Listens to changes in the filter data and refreshes the tasks from the
	 * database upon change.
	 */
	private ChangeListener filterDataChangeListener = new ChangeListener() {
		@Override
		public void stateChanged(ChangeEvent e) {
			refreshTasks();
		}
	};

	/**
	 * Listens to the submit button in a AddTask object and, performs the
	 * addition and disposal of new task window.
	 */
	private ActionListener submitListener = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			Object[] data = a.getData();
			addTask(false, ((JTextField) data[0]).getText(),
					((SpinnerDateModel) data[2]).getDate(),
					((JComboBox<?>) data[1]).getSelectedItem().toString(),
					(((Slider) data[3]).getValue()), "-",
					((JTextArea) data[4]).getText());
			a.dispose();
		}
	};

	/**
	 * Listens to the submit button in a EditTask object and, performs the
	 * update and disposal of new task window.
	 */
	private ActionListener submitEditListener = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			Object[] data = editTask.getData();
			model.updateTask((int) data[5], ((JTextField) data[0]).getText(),
					((SpinnerDateModel) data[2]).getDate(),
					((JComboBox<?>) data[1]).getSelectedItem().toString(),
					(((Slider) data[3]).getValue()), ((JComboBox<?>) data[6])
							.getSelectedItem().toString(),
					((JTextArea) data[4]).getText());
			refreshTasks();
			editTask.dispose();
		}
	};

	/**
	 * Listens to the new task button and launches a AddTask object upon click.
	 */
	private ActionListener addTaskListener = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			a = new AddTask(model.getCategories(), myActions, view);
			a.setVisible(true);
		}
	};

	/**
	 * Listens to the remove tasks button and removes all selected tasks in the
	 * right pane upon click.
	 */
	private ActionListener remTaskListener = new ActionListener() {

		public void actionPerformed(ActionEvent arg0) {
			// When pressed it calls the function to remove selected tasks.
			removeTasks();
		}
	};

	/**
	 * Listens to the right click popup in the right pane and removes the
	 * selected row from the database and model.
	 */
	private ActionListener remSingleTaskListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			int id = view.getRightPane().getSelectedRowId();
			model.removeTask(id);
			refreshTasks();
		}

	};

	/**
	 * Listens to the edit task button.
	 */
	private ActionListener ediTaskListener = new ActionListener() {

		public void actionPerformed(ActionEvent arg0) {
			int id = view.getRightPane().getSelectedRowId();
			System.out.println(id);
			editTask = new EditTask(model.getCategories(), myActions, view,
					model.getTask(id), id);
			editTask.setVisible(true);

		}
	};

	/**
	 * Listens to the rightclick on a task's popup to show a task.
	 */
	private ActionListener showTaskListener = new ActionListener() {

		public void actionPerformed(ActionEvent arg0) {
			int id = view.getRightPane().getSelectedRowId();
			System.out.println(id);
			showTask = new ShowTask(myActions, view, model.getTask(id));
			showTask.setVisible(true);

		}
	};

	/**
	 * Listens to the clear filter button and resets the filter to default
	 * values upon click.
	 */
	private ActionListener clearFilterListener = new ActionListener() {

		public void actionPerformed(ActionEvent arg0) {
			// Reset the filter.
			view.getLeftPane().getFilter().getCategories().reset();
			view.getLeftPane().getFilter().getSearchData().reset();
			refreshTasks();
		}
	};
	/**
	 * Listens to the cancel button in a EditTask frame and removes the frame
	 * upon click.
	 */
	private ActionListener cancelEditListener = new ActionListener() {

		public void actionPerformed(ActionEvent arg0) {
			editTask.dispose();
		}
	};

	/**
	 * Listens to the ok button in a ShowTask frame and removes the frame upon
	 * click.
	 */
	private ActionListener okShowListener = new ActionListener() {

		public void actionPerformed(ActionEvent arg0) {
			showTask.dispose();
		}
	};
	/**
	 * Listens to the cancel button in a AddTask frame and removes the frame
	 * upon click.
	 */
	private ActionListener cancelListener = new ActionListener() {

		public void actionPerformed(ActionEvent arg0) {
			a.dispose();
		}
	};

	/**
	 * Listens to the Show Selected button and creates a popup window to contain
	 * a list of selected tasks.
	 */
	private ActionListener showSelected = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("test show selected");
			JDialog.setDefaultLookAndFeelDecorated(true);
			workPending = new WorkPending(model, view, 5, myActions);
			workPending.setVisible(true);
			workPending.setDefaultLookAndFeelDecorated(false);
		}
	};
	
	/**
	 * Listens to the Show High Priority button and creates a popup window
	 * to contain a list of high priority tasks.
	 */
	private ActionListener Metal = new ActionListener() {
			
		public void actionPerformed(ActionEvent e) {
			
			Properties THEMES = new Properties();
			OutputStream output = null;
			try {
				File properties = new File(System.getProperty("user.home"),
						".TODO-Group6-themes");
				output = new FileOutputStream(properties);
				THEMES.setProperty("THEME", "DefualtMetal");
				THEMES.setProperty("LOOKANDFEEL", "Metal");
				THEMES.store(output, null);
				SetLookAndFeel.initLookAndFeel();
			} catch (IOException er) {
				er.printStackTrace();
				System.out.print("error: Theme properties");
			}
		}
	};
	private ActionListener Ocean = new ActionListener() {
		
		public void actionPerformed(ActionEvent e) {
			Properties THEMES = new Properties();
			OutputStream output = null;
			try {
				File properties = new File(System.getProperty("user.home"),
						".TODO-Group6-themes");
				output = new FileOutputStream(properties);
				THEMES.setProperty("THEME", "Ocean");
				THEMES.setProperty("LOOKANDFEEL", "Motif");
				THEMES.store(output, null);
				SetLookAndFeel.initLookAndFeel();
			} catch (IOException er) {
				er.printStackTrace();
				System.out.print("error: Theme properties");
			}
		}
	};	
	
	
	/**
	 * Listens to the plus button and repopulates the JLists with 1 
	 * additional task each time. 
	 */
	private ActionListener plusButtonListener = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			workPending.incrementListSize();
			workPending.addTasksToLists();
		}
	};
	
	/**
	 * Listens to the plus button and repopulates the JLists with 1 
	 * less task each time. 
	 */
	private ActionListener minusButtonListener = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			workPending.decrementListSize();
			workPending.addTasksToLists();
		}
	};
	
	
	private ActionListener englishLangListener = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			Properties window = new Properties();
			OutputStream output = null;
			try {
				File properties = new File(System.getProperty("user.home"),
						".TODO-Group6-language");
				output = new FileOutputStream(properties);
				window.setProperty("language", "en");
				window.store(output, null);
				JOptionPane.showMessageDialog(null,
						View.bundle.getString("language_change"));
			} catch (IOException ex) {
				ex.printStackTrace();
				System.out.print("error: language property");
			}
		}
	};
	
	/**
	 * Listens to the menuItem for setting the language to latin.
	 */
	private ActionListener latinLangListener = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			Properties window = new Properties();
			OutputStream output = null;
			try {
				File properties = new File(System.getProperty("user.home"),
						".TODO-Group6-language");
				output = new FileOutputStream(properties);
				window.setProperty("language", "la");
				window.store(output, null);
				JOptionPane.showMessageDialog(null,
						View.bundle.getString("language_change"));
			} catch (IOException ex) {
				ex.printStackTrace();
				System.out.print("error: language property");
			}

		}
	};

	/**
	 * Initializes the controller, view and model.
	 * 
	 * @param v
	 *            The view to be associated with the controller.
	 * @param m
	 *            The model to be associated with the controller.
	 */
	public Controller(View v, Model m, ActionSt myActions) {
		this.view = v;
		this.model = m;
		this.myActions = myActions;

		// Actionlisteners for handling view interactions.
		ActionListener[] al = { addTaskListener, remTaskListener,
				ediTaskListener, submitListener, clearFilterListener,
				cancelListener, showSelected, cancelEditListener,
				submitEditListener, showTaskListener, okShowListener, Ocean, Metal, 
				plusButtonListener, minusButtonListener, latinLangListener, englishLangListener};
		
		// Send listeners to the view, so actions can use them:
		myActions.setActionListeners(al);
		v.getLeftPane().getFilter().getSearchData()
				.setListeners(filterDataChangeListener);
		v.getLeftPane().getFilter().getCategories()
				.setTableListener(categoryTableListener);
		v.getRightPane().setRemoveItemListener(remSingleTaskListener);
		v.getRightPane().setEditItemListener(ediTaskListener);
		v.getRightPane().setShowItemListener(showTaskListener);
		
		// Populate all categories and tasks.
		this.refreshCategories();
		this.refreshTasks();
	}

	/**
	 * Adds a new task to the RightPane and database.
	 * 
	 * @param checkBox
	 *            - Value of the checkbox
	 * @param title
	 *            - Name of the task.
	 * @param date
	 *            - Deadline of the task.
	 * @param category
	 *            - Category of the task
	 * @param priority
	 *            - Priority of the task.
	 * @param status
	 *            - Status of the task.
	 */
	public void addTask(Boolean checkBox, String title, Date date,
			String category, int priority, String status, String description) {
		model.addTask(title, date, category, priority, status, description);
		this.refreshTasks();
	}

	/**
	 * Adds and displays a task in the right pane.
	 * 
	 * @param checkBox
	 *            Iinitial value of the tasks checkbox.
	 * @param title
	 *            Title of the task.
	 * @param date
	 *            Due date of the task.
	 * @param category
	 *            Category to which the task belongs
	 * @param priority
	 *            The tasks priority.
	 * @param status
	 *            The status of the task.
	 * @param id
	 *            The tasks id.
	 */
	public void showTask(Boolean checkBox, String title, Date date,
			String category, int priority, String status, int id) {
		TodoTableModel model = view.getRightPane().getTableModel();
		Object[] task = { checkBox, title, format.format(date), category,
				priority, status, id };
		model.addRow(task);
	}

	/**
	 * Removes all tasks selected in the right pane from the table and database.
	 * 
	 * @return true if deletion of selected tasks succeeds, else false.
	 */
	public void removeTasks() {
		// Fetch table model
		AbstractTableModel tbmodel = view.getRightPane().getTableModel();
		// Get number of rows in model.
		int numRows = tbmodel.getRowCount();
		try {
			// Remove all selected tasks from the database.
			for (int i = 0; i < numRows; i++) {
				if ((boolean) tbmodel.getValueAt(i, 0) == true)
					this.model.removeTask((int) tbmodel.getValueAt(i, 6));
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		// Reload tasks from database.
		this.refreshTasks();
	}

	/**
	 * Removes all selected tasks from rightpanes model.
	 * 
	 * @return True upon success, else false.
	 */
	public boolean removeAllTasks() {
		// Fetch right panes model
		TodoTableModel model = view.getRightPane().getTableModel();
		int numRows = model.getRowCount();
		try {
			// Remove all rows from right panes model.
			for (int i = 0; i < numRows; i++) {
				model.removeRow(0);
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Refreshes tasks from the database, filtering out those not matching
	 * search Requirements.
	 */
	public void refreshTasks() {
		// Remove all tasks from right panes model.
		removeAllTasks();

		// Get necessary data to perform fetch from database.
		int[] priorities = view.getLeftPane().getFilter().getSearchData()
				.getPriorities();
		Date[] dates = view.getLeftPane().getFilter().getSearchData()
				.getDates();
		Vector<String> categories = view.getLeftPane().getFilter()
				.getCategories().getActive();

		// Get tasks from database.
		Vector<Object[]> tasks = model.getTasks(priorities, dates, categories);

		// Get number of tasks
		int numTasks = tasks.size();

		// Display fetched tasks on right pane
		for (int i = 0; i < numTasks; i++) {
			this.showTask((Boolean) tasks.get(i)[0], (String) tasks.get(i)[1],
					(Date) tasks.get(i)[2], (String) tasks.get(i)[3],
					(int) tasks.get(i)[4], (String) tasks.get(i)[5],
					(int) tasks.get(i)[6]);
		}

	}

	/**
	 * Removes all categories from the category model.
	 */
	public void removeAllCategories() {
		// Get categories model.
		DefaultTableModel m = view.getLeftPane().getFilter().getCategories()
				.getTableModel();
		int numRows = m.getRowCount();
		// Remove all rows from the categories model.
		for (int i = 0; i < numRows; i++) {
			m.removeRow(0);
		}
	}

	/**
	 * Reloads all categories from the database and adds them to the categories
	 * model.
	 */
	public void refreshCategories() {
		// Get categories model.
		DefaultTableModel m = view.getLeftPane().getFilter().getCategories()
				.getTableModel();
		// Remove all existant rows from the categories model.
		this.removeAllCategories();
		// Fetch categories from the database.
		Vector<String> categories = model.getCategories();
		int numRows = categories.size();
		// Add fetched categories to the categories model.
		for (int i = 0; i < numRows; i++) {
			Object[] data = { true, categories.get(i) };
			m.addRow(data);
		}

	}
}
