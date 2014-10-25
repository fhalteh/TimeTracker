package timeManager;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

/**
 * Custom TableModel for the main todo-task table.
 * 
 */
@SuppressWarnings("serial")
public class TodoTableModel extends AbstractTableModel {
	// Names of columns in the rightpane
	final String colnames[] = { " ", View.bundle.getString("Task"),
			View.bundle.getString("Due_Date"),
			View.bundle.getString("Category"),
			View.bundle.getString("Priority"), View.bundle.getString("Status"),
			"ID" };

	// Vector for keeping rows.
	Vector<Object[]> data = new Vector<Object[]>();

	/**
	 * Get the class of a column.
	 */
	@Override
	public Class<?> getColumnClass(int colIndex) {
		return getValueAt(0, colIndex).getClass();
	}

	/**
	 * Check if cell is editable. (Only row 0 is editable).
	 */
	@Override
	public boolean isCellEditable(int row, int column) {
		return (column == 0);
	}

	/**
	 * Get number of columns.
	 */
	@Override
	public int getColumnCount() {
		return colnames.length;
	}

	/**
	 * Get column name.
	 */
	@Override
	public String getColumnName(int col) {
		return colnames[col];
	}

	/**
	 * Remove row with id id.
	 * 
	 * @param row
	 *            Row to be removed.
	 */
	public void removeRow(int row) {
		data.remove(row);
		fireTableRowsDeleted(row, row);
	}

	/**
	 * Add row to model.
	 * 
	 * @param nrow
	 *            Object array containing a row, see column names for details.
	 */
	public void addRow(Object[] nrow) {
		data.add(nrow);
		fireTableDataChanged();
	}

	/**
	 * Get number of rows.
	 */
	@Override
	public int getRowCount() {
		return data.size();
	}

	/**
	 * Get value of a cell.
	 */
	@Override
	public Object getValueAt(int row, int col) {
		return data.get(row)[col];
	}

	/**
	 * Set value of a cell.
	 */
	@Override
	public void setValueAt(Object val, int r, int c) {
		data.get(r)[c] = val;
		fireTableCellUpdated(r, c);
	}
};
