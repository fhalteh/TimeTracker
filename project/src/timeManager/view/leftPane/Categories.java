package timeManager.view.leftPane;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import timeManager.View;

/**
 * Panel containing a list of categories used for filtering tasks.
 * 
 */
@SuppressWarnings("serial")
public class Categories extends JPanel {
	private DefaultTableModel model;

	/**
	 * Creates a table which contains all categories for sorting out the tasks.
	 */
	public Categories() {
		// Setting up the pane.
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		// Creating a table with checkboxes and categories.
		String colnames[] = { " ", View.bundle.getString("Category") };
		model = new DefaultTableModel(null, colnames) {
			@Override
			public Class<?> getColumnClass(int colIndex) {
				return getValueAt(0, colIndex).getClass();
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return (column == 0);
			}
		};

		// Adding a sorter and a scrollpane to the table.
		RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
		JTable table = new JTable(model);
		table.getColumnModel().getColumn(0).setMaxWidth(20);
		table.setRowSorter(sorter);
		JScrollPane cat = new JScrollPane(table);

		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		this.add(cat, c);
	}

	/**
	 * Sets all checkboxes to checked status.
	 */
	public void reset() {
		int numRows = model.getRowCount();
		for (int i = 0; i < numRows; i++) {
			model.setValueAt(true, i, 0);
		}
	}

	/**
	 * Gets the tablemodel.
	 * 
	 * @return Returns the tablemodel of the categories table.
	 */
	public DefaultTableModel getTableModel() {
		return this.model;
	}

	/**
	 * Gets a list of selected categories.
	 * 
	 * @return Vector of selected category names.
	 */
	public Vector<String> getActive() {
		int numRows = model.getRowCount();
		Vector<String> active = new Vector<String>();
		for (int i = 0; i < numRows; i++) {
			if ((boolean) model.getValueAt(i, 0) == true) {
				active.add((String) model.getValueAt(i, 1));
			}
		}
		return active;
	}

	/**
	 * Sets a tablemodellistener for the category tables model.
	 * 
	 * @param categoryTableListener
	 *            TableListener to be set.
	 */
	public void setTableListener(TableModelListener categoryTableListener) {
		model.addTableModelListener(categoryTableListener);
	}

}
