package timeManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.Action;

public class ActionSt {

	//private ResourceBundle bundle;
	
	/* ACTIONS: Private variables */
	private Action addTask;
	private Action editTask;
	private Action showTask;
	private Action removeTask;
	private Action submit;
	private Action submitEdit;
	private Action clearFilter;
	private Action cancel;
	private Action cancelEdit;
	private Action showSelected;
	private Action okShow;
	private Action Metal;
	private Action Ocean;
	private Action plusButton;
	private Action minusButton;
	private Action latinLang;
	private Action englishLang;
	// To pass action(s) into appropriate class.
	private Action[] action;
	// Need the listeners to add to the actionPerformed event
	private ActionListener addTaskListener;
	private ActionListener remTaskListener;
	private ActionListener ediTaskListener;
	private ActionListener submitListener;
	private ActionListener submitEditListener;
	private ActionListener clearListener;
	private ActionListener cancelListener;
	private ActionListener cancelEditListener;
	private ActionListener showSelectedListener;
	private ActionListener showTaskListener;
	private ActionListener okShowListener;
	private ActionListener metalListener;
	private ActionListener oceanListener;
	private ActionListener plusButtonListener;
	private ActionListener minusButtonListener;
	private ActionListener latinLangListener;
	private ActionListener englishLangListener;

	/* END ACTIONS */
	
	/**
	 *  Get the action listeners from the controller, 
	 *  so they can be called within in the Actions
	 * @param al : An array of Actions
	 */
	public void setActionListeners(ActionListener[] al) {
		this.addTaskListener = al[0];
		this.remTaskListener = al[1];
		this.ediTaskListener = al[2];
		this.submitListener = al[3];
		this.clearListener = al[4];
		this.cancelListener = al[5];
		this.showSelectedListener = al[6];
		this.cancelEditListener = al[7];
		this.submitEditListener = al[8];
		this.showTaskListener = al[9];
		this.okShowListener = al[10];
		this.oceanListener = al[11];
		this.metalListener = al[12];
		this.plusButtonListener = al[13];
		this.minusButtonListener = al[14];
		this.latinLangListener = al[15];
		this.englishLangListener = al[16];
	}
	
	/**
	 * Creates all of the actions using the default language. 
	 */
	@SuppressWarnings("serial")
	public void createActions(ResourceBundle bundle) {		
		/**
		 * Executes the Add Task Button's action listener, which is located inside the controller.
		 * @param - Takes the language bundle, to appropriately set the name of menu items or 
		 * button's that this action is added to. 
		 */
		addTask = new AbstractAction(bundle.getString("Add_task")) {
	
			@Override
			public void actionPerformed(ActionEvent e) {
				addTaskListener.actionPerformed(null);
			}
		};
	
		/**
		 * Executes the Edit Task Button's action listener, which is located inside the controller.
		 * @param - Takes the language bundle, to appropriately set the name of menu items or 
		 * button's that this action is added to. 
		 */
		editTask = new AbstractAction(bundle.getString("Edit_task")) {
	
			@Override
			public void actionPerformed(ActionEvent e) {
				ediTaskListener.actionPerformed(null);
			}
		};
	
		/**
		 * Executes the Remove Task Button's action listener, which is located inside the controller.
		 * @param - Takes the language bundle, to appropriately set the name of menu items or 
		 * button's that this action is added to. 
		 */
		removeTask = new AbstractAction(bundle.getString("Remove_tasks")) {
	
			@Override
			public void actionPerformed(ActionEvent e) {
				remTaskListener.actionPerformed(null);
			}
		};
	
		/**
		 * Executes the show Task Button's action listener, which is located inside the controller.
		 * @param - Takes the language bundle, to appropriately set the name of menu items or 
		 * button's that this action is added to. 
		 */
		showTask = new AbstractAction(bundle.getString("Show_task")) {
	
			@Override
			public void actionPerformed(ActionEvent e) {
				showTaskListener.actionPerformed(null);
			}
		};
	
		
		
		/**
		 * Executes the submit Button for editing's action listener, which is located inside the controller.
		 * Also re-enables the add and edit task buttons, in the case that they have been
		 * disabled. 
		 * @param - Takes the language bundle, to appropriately set the name of menu items or 
		 * button's that this action is added to. 
		 */
		submitEdit = new AbstractAction(bundle.getString("Submit")) {
	
			@Override
			public void actionPerformed(ActionEvent e) {			
				submitEditListener.actionPerformed(null);
				// Ensure that the addTask and editTask buttons are enabled.
				addTask.setEnabled(true);
				editTask.setEnabled(true);
			}
		};
			
		/**
		 * Executes the submit Button's action listener, which is located inside the controller.
		 * Also re-enables the add and edit task buttons, in the case that they have been
		 * disabled. 
		 * @param - Takes the language bundle, to appropriately set the name of menu items or 
		 * button's that this action is added to. 
		 */
		submit = new AbstractAction(bundle.getString("Submit")) {
	
			@Override
			public void actionPerformed(ActionEvent e) {			
				submitListener.actionPerformed(null);
				// Ensure that the addTask and editTask buttons are enabled.
				addTask.setEnabled(true);
				editTask.setEnabled(true);
			}
		};
		/**
		 * Executes the ok Button inside ShowTask's action listener, which is located inside the controller.
		 * Also re-enables the add and edit task buttons, in the case that they have been
		 * disabled. 
		 * @param - Takes the language bundle, to appropriately set the name of menu items or 
		 * button's that this action is added to. 
		 */
		okShow = new AbstractAction(bundle.getString("Ok")) {
	
			@Override
			public void actionPerformed(ActionEvent e) {			
				okShowListener.actionPerformed(null);
			}
		};
		
		/**
		 * Executes the clear filter button's action listener, which is located inside the controller.
		 * @param - Takes the language bundle, to appropriately set the name of menu items or 
		 * button's that this action is added to. 
		 */
		clearFilter = new AbstractAction(bundle.getString("Clear")) {
	
			@Override
			public void actionPerformed(ActionEvent e) {
				clearListener.actionPerformed(null);
				System.out.println("Clear the filter");
			}
		};
		
		/**
		 * Executes the cancel button's action listener, which is located inside the controller.
		 * @param - Takes the language bundle, to appropriately set the name of menu items or 
		 * button's that this action is added to. 
		 */
		cancel = new AbstractAction(bundle.getString("Cancel")) {
	
			@Override
			public void actionPerformed(ActionEvent e) {
				cancelListener.actionPerformed(null);
			}
		};
		
		/**
		 * Executes the cancel button inside EditTask's action listener, which is located inside the controller.
		 * @param - Takes the language bundle, to appropriately set the name of menu items or 
		 * button's that this action is added to. 
		 */
		cancelEdit = new AbstractAction(bundle.getString("Cancel")) {
	
			@Override
			public void actionPerformed(ActionEvent e) {
				cancelEditListener.actionPerformed(null);
			}
		};
				
		/**
		 * Executes the show selected button's action listener, which is located inside the controller.
		 * @param - Takes the language bundle, to appropriately set the name of menu items or 
		 * button's that this action is added to. 
		 */
		showSelected = new AbstractAction(bundle.getString("Show_selected")) {
	
			@Override
			public void actionPerformed(ActionEvent e) {
				showSelectedListener.actionPerformed(null);
			}
		};

		Metal = new AbstractAction(bundle.getString("Metal")){
			@Override
			public void actionPerformed(ActionEvent e){
				metalListener.actionPerformed(null);
			}
		};
		Ocean = new AbstractAction(bundle.getString("Ocean")){
			@Override
			public void actionPerformed(ActionEvent e){
				oceanListener.actionPerformed(null);
			}
		};
		/**
		 * Executes the Plus Button's action listener, which is located inside the controller.
		 * @param - Takes the string + which is universal for all languages
		 */
		plusButton = new AbstractAction("+") {
	
			@Override
			public void actionPerformed(ActionEvent e) {
				plusButtonListener.actionPerformed(null);
			}
		};
	
		/**
		 * Executes the Minus Button's action listener, which is located inside the controller.
		 * @param - Takes the string - which is universal for all languages
		 */
		minusButton = new AbstractAction("-") {
	
			@Override
			public void actionPerformed(ActionEvent e) {
				minusButtonListener.actionPerformed(null);

			}
		};
		
		/**
		 * Executes the latin lang's menuItems action listener, which is located inside the controller
		 * @param takes the language bundle to appropriate set the name of the menu
		 */
		latinLang = new AbstractAction(bundle.getString("Latin")) {
			@Override
			public void actionPerformed(ActionEvent e) {
				latinLangListener.actionPerformed(null);
			}
		};
		
		/**
		 * Executes the english lang's menuItems action listener, which is located inside the controller
		 * @param takes the language bundle to appropriate set the name of the menu
		 */
		
		englishLang = new AbstractAction(bundle.getString("English")) {
			@Override
			public void actionPerformed(ActionEvent e) {
				englishLangListener.actionPerformed(null);
			}
		};
	
		// Create action array

		action = new Action[15];
		action[0] = addTask;
		action[1] = editTask;
		action[2] = removeTask;
		action[3] = submit;
		action[4] = clearFilter;
		action[5] = cancel;
		action[6] = showSelected;
		action[7] = Metal;
		action[8] = cancelEdit;
		action[9] = submitEdit;
		action[10] = okShow;
		action[11] = showTask;
		action[12] = Ocean;
		action[13] = plusButton;
		action[14] = minusButton;
}
	
	/**
	 * @return returns the addTask Action.
	 */
	public Action getAddTask() {
		return addTask;
	}

	/**
	 * @return returns the showTask Action.
	 */
	public Action getShowTask() {
		return showTask;
	}
	
	
	/**
	 * @return returns the editTask Action.
	 */
	public Action getEditTask() {
		return editTask;
	}

	/**
	 * @return returns the removeTask Action.
	 */
	public Action getRemoveTask() {
		return removeTask;
	}

	/**
	 * @return returns the submit Action for adding a task.
	 */
	public Action getSubmit() {
		return submit;
	}
	
	/**
	 * @return returns the submit Action for editing.
	 */
	public Action getSubmitEdit() {
		return submitEdit;
	}

	/**
	 * @return returns the clearFilter Action.
	 */
	public Action getClearFilter() {
		return clearFilter;
	}

	/**
	 * @return returns the cancel Action for adding a task.
	 */
	public Action getCancel() {
		return cancel;
	}
	
	/**
	 * 
	 * @return returns the Action for disposing showing task.
	 */
	public Action getOkShow() {
		return okShow;
	}
	/**
	 * @return returns the cancel Action for editing.
	 */
	public Action getCancelEdit() {
		return cancelEdit;
	}
	
	/**
	 * @return returns the showSelected Action.
	 */
	public Action getShowSelected() {
		return showSelected;
	}
	public Action getMetal(){
		return Metal;
	}
	public Action getOcean(){
		return Ocean;
	}
	
	/**
	 * @return returns the PlusButton Action.
	 */
	public Action getPlusButton() {
		return plusButton;
	}

	/**
	 * @return returns the MinusButton Action.
	 */
	public Action getMinusButton() {
		return minusButton;
	}
	/**
	 * 
	 * @return returns the LatinLang Action
	 */
	public Action getLatinLang() {
		return latinLang;
	}
	
	/** 
	 * @return returns the englishLang Action
	 */
	public Action getEnglishLang() {
		return englishLang;
	}
	
}
