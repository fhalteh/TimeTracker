package timeManager.view.rightPane;

import java.awt.Color;
import java.awt.Component;
import java.text.ParseException;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import timeManager.Controller;
import timeManager.TodoTableModel;

/**
 * Custom table used to color rows based on status.
 *
 */
@SuppressWarnings("serial")
public class ColoredTable extends JTable {
	TodoTableModel model;
	
	public ColoredTable(TodoTableModel model) {
		super(model);
		this.model = model;
	}
	
	public Component prepareRenderer(TableCellRenderer renderer, int row,
			int column) {
		Component stamp = super.prepareRenderer(renderer, row, column);
		try {
			// If a tasks is overdue, its status is displayed as overdue unless
			// it is not marked with the status "-"
			if(((String)model.getValueAt(row, 5)).equals("-") &&
					(Controller.format.parse((String)model.getValueAt(row, 2))).getTime() < 
					System.currentTimeMillis()) {
				model.setValueAt("Overdue", row, 5);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// If a row has the status done it becomes green.
		if (((String)model.getValueAt(row, 5)).equals("Done"))
			stamp.setBackground(Color.green);
		// If a row has the status Overdue it becomes red.
		else if(((String)model.getValueAt(row, 5)).equals("Overdue"))
			stamp.setBackground(Color.red);
		// If a row has the status "Work pending" it becomes cyan, unless it is
		// over due in which case it becomes orange.
		else if(((String)model.getValueAt(row, 5)).equals("Work pending"))
			try {
				if((Controller.format.parse((String)model.getValueAt(row, 2))).getTime() < 
						System.currentTimeMillis())
					stamp.setBackground(Color.orange);
				else
					stamp.setBackground(Color.cyan);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		else
			stamp.setBackground(this.getBackground());
		return stamp;
	}

}
