package timeManager.view.rightPane;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableRowSorter;

import timeManager.View;
import timeManager.TodoTableModel;
import timeManager.view.rightPane.ColoredTable;

/**
 * Sets up the right portion of the view.
 * 
 */
@SuppressWarnings("serial")
public class RightPane extends JPanel {
	private TodoTableModel model;
	private JMenuItem removeItem;
	private JMenuItem editItem;
	private JMenuItem showItem;
	private JTable table;

	public TodoTableModel getTableModel() {
		return this.model;
	}

	/**
	 * Creates the rightpane containing a list of tasks to be performed.
	 */
	public RightPane() {
		// Setting up the panel.
		this.setLayout(new GridBagLayout());
		this.setSize(450, 300);

		// Setting up gridbagconstraints.
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;

		model = new TodoTableModel();
		// Creating a sorter for tablemodel
		TableRowSorter<TodoTableModel> sorter = new TableRowSorter<TodoTableModel>(model);

		// Creating popup menu
		JPopupMenu popup = new JPopupMenu();
		removeItem = new JMenuItem(View.bundle.getString("Remove"));
		editItem = new JMenuItem(View.bundle.getString("Edit"));
		showItem = new JMenuItem(View.bundle.getString("Show_task"));
		popup.add(showItem);
		popup.add(editItem);
		popup.add(removeItem);

		// Set up table
		table = new ColoredTable(model);
		table.setComponentPopupMenu(popup);
		table.removeColumn(table.getColumn("ID"));
		table.getColumnModel().getColumn(0).setMaxWidth(20);
		table.setRowSorter(sorter);
		// Add listeners to select a right clicked row.
		table.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					Point p = e.getPoint();
					int row = table.rowAtPoint(p);
					table.setRowSelectionInterval(row, row);
				}

			}
			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					Point p = e.getPoint();
					int row = table.rowAtPoint(p);
					table.setRowSelectionInterval(row, row);
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}

		});

		// Add table to scroll pane and add whole to rightpane
		JScrollPane tableScroll = new JScrollPane(table);
		this.add(tableScroll, c);
	}
	
	/**
	 * Set listener for the edit item popup window.
	 * @param al edit item actionlistener.
	 */
	public void setEditItemListener(ActionListener al) {
		editItem.addActionListener(al);
	}

	/**
	 * Set listener for the show item popup window.
	 * @param al show item actionlistener.
	 */
	public void setShowItemListener(ActionListener al) {
		showItem.addActionListener(al);
	}
	
	/**
	 * Set listener for the remove item popup window.
	 * @param al remove item actionlistener.
	 */
	public void setRemoveItemListener(ActionListener al) {
		removeItem.addActionListener(al);
	}
	
	/**
	 * Gets the currently selected rows task id.
	 * @return The id of the selected task.
	 */
	public int getSelectedRowId() {
		return (int) model.getValueAt(table.getSelectedRow(), 6);
	}

}
