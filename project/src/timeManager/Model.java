package timeManager;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class Model {
	/**
	 * Initializes the model.
	 */
	public Model() {
		try {
			// Establishes connection to a h2 database called tmdb
			Class.forName("org.h2.Driver");
			Connection con = DriverManager.getConnection("jdbc:h2:tmdb",
					"tmdb", "");
			// Creates a new statement
			Statement stmt = con.createStatement();

			/*
			 * Create a new categories table if none exists with the appropriate
			 * fields.
			 */
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS categories ("
					+ "cat_id INTEGER PRIMARY KEY AUTO_INCREMENT, "
					+ "category VARCHAR(100) UNIQUE" + ")");

			// Inserts default categories into the categories table.
			stmt.executeUpdate("INSERT INTO categories (category) "
					+ "VALUES ('None'), " + "('Other'), " + "('School'), "
					+ "('Work'), " + "('Home')");

			// Creates a new tasks table if non exists
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS tasks ("
					+ "id INTEGER PRIMARY KEY AUTO_INCREMENT, "
					+ "task VARCHAR(100), " + "due TIMESTAMP, "
					+ "category INTEGER, " + "priority INTEGER, "
					+ "status VARCHAR(100), " + "description VARCHAR(500), "
					+ "FOREIGN KEY (category) REFERENCES categories (cat_id)"
					+ ")");

			// Closes the connection
			stmt.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * Adds a task to the database.
	 * 
	 * @param title
	 *            Name of the task.
	 * @param date
	 *            Due date of the task.
	 * @param category
	 *            Name of the category of the task.
	 * @param priority
	 *            Priority of the task (1-10)
	 * @param status
	 *            Status of the task.
	 * @param description
	 *            Description of the task.
	 * @return Returns true if the addition was successful.
	 */
	public boolean addTask(String title, java.util.Date date, String category,
			int priority, String status, String description) {
		try {
			// Establish database connection
			Class.forName("org.h2.Driver");
			Connection con = DriverManager.getConnection("jdbc:h2:tmdb",
					"tmdb", "");
			// Fetch the id of the category or return false if non-existant.
			PreparedStatement pstmt = con
					.prepareStatement("SELECT * FROM categories WHERE category = ?");
			pstmt.setString(1, category);
			ResultSet rs = pstmt.executeQuery();
			int id = 1;
			if (rs.next()) {
				id = rs.getInt("cat_id");
			} else {
				return false;
			}

			// Insert the tasks with the set values into the task table of db.
			pstmt = con.prepareStatement("INSERT INTO tasks"
					+ " ( task, due, category, priority, status, description) "
					+ "VALUES ( ?, ?, ?, ?, ?, ?)");

			pstmt.setString(1, title);
			pstmt.setTimestamp(2, new java.sql.Timestamp(date.getTime()));
			pstmt.setInt(3, id);
			pstmt.setInt(4, priority);
			pstmt.setString(5, status);
			pstmt.setString(6, description);
			boolean success = pstmt.execute();

			// Close connection
			pstmt.close();
			con.close();
			return success;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	/**
	 * Fetches all task matching the search requirements.
	 * 
	 * @param priorities
	 *            An array of length two with the first element being the lower
	 *            priority bound and the second being the upper bound.
	 * @param dates
	 *            An array of two dates, where the first is the lower bound and
	 *            the second the upper bound.
	 * @param categories
	 *            A vector of categories to be included.
	 * @return Returns an array containing tasks information.
	 */
	public Vector<Object[]> getTasks(int[] priorities, java.util.Date[] dates,
			Vector<String> categories) {
		Vector<Object[]> vector = new Vector<Object[]>();
		try {
			// Establish database connection.
			Class.forName("org.h2.Driver");
			Connection con = DriverManager.getConnection("jdbc:h2:tmdb",
					"tmdb", "");

			// Query the database for tasks matching the dates and priorities.
			PreparedStatement pstmt = con
					.prepareStatement("SELECT * FROM tasks, categories"
							+ " WHERE categories.cat_id = tasks.category AND due BETWEEN ? AND ?"
							+ " AND priority BETWEEN ? AND ?");
			pstmt.setTimestamp(1, new java.sql.Timestamp(dates[0].getTime()));
			pstmt.setTimestamp(2, new java.sql.Timestamp(dates[1].getTime()));
			pstmt.setInt(3, priorities[0]);
			pstmt.setInt(4, priorities[1]);
			ResultSet rs = pstmt.executeQuery();
			// While there is still a row....
			while (rs.next()) {
				/*
				 * If a result matches requested categories, add it to the list
				 * of tasks.
				 */
				if (categories.contains(rs.getString("categories.category"))) {
					Object[] task = { false, rs.getString("task"),
							new Date(rs.getTimestamp("due").getTime()),
							rs.getString("categories.category"),
							rs.getInt("priority"), rs.getString("status"),
							rs.getInt("id") };
					vector.add(task);
				}
			}
			// Close connection.
			pstmt.close();
			con.close();

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		// Return the results.
		return vector;
	}

	/**
	 * Fetches all categories from the database.
	 * 
	 * @return Returns a vector containing all categories names.
	 */
	public Vector<String> getCategories() {
		Vector<String> vector = new Vector<String>();
		try {
			// Establish a connection to the database.
			Class.forName("org.h2.Driver");
			Connection con = DriverManager.getConnection("jdbc:h2:tmdb",
					"tmdb", "");
			// Query the database for all categories
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM categories");
			// Add all categories to a result vector.
			while (rs.next()) {
				vector.add(rs.getString("category"));
			}
			// Close connection.
			stmt.close();
			con.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		// Return the resulting category names.
		return vector;
	}

	/**
	 * Removes a task from the database.
	 * 
	 * @param id
	 *            The id of the task to be removed.
	 */
	public void removeTask(int id) {
		try {
			// Establish a connection to the database.
			Class.forName("org.h2.Driver");
			Connection con = DriverManager.getConnection("jdbc:h2:tmdb",
					"tmdb", "");
			// Remove the task with id id from the database.
			PreparedStatement pstmt = con
					.prepareStatement("DELETE FROM tasks WHERE " + "id = ?");
			pstmt.setInt(1, id);
			// Close the connection
			pstmt.execute();
			pstmt.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * Returns a vector of the top x work pending tasks.
	 * In order of closest due date. 
	 * 
	 * @param x - number of tasks to retrieve
	 * @return Returns a vector of top x high priority tasks.
	 */
	public Vector<Object[]> getWorkPending(int x) {
		Vector<Object[]> vector = new Vector<Object[]>();
		try {
			// Establish database connection.
			Class.forName("org.h2.Driver");
			Connection con = DriverManager.getConnection("jdbc:h2:tmdb",
					"tmdb", "");

			// Query the database for tasks matching the dates and priorities.
			PreparedStatement pstmt = con
					.prepareStatement("SELECT * FROM tasks"
							+ " WHERE status = 'Work pending'"
							+ " ORDER BY due ASC LIMIT ?");

			pstmt.setInt(1, x);
			ResultSet rs = pstmt.executeQuery();

			// While there is still a row....
			while (rs.next()) {
				// Store all information about each high priority task
				Object[] task = { false, rs.getString("task"),
						new Date(rs.getTimestamp("due").getTime())};
				
				
				vector.add(task);

			}
			// Close connection.
			pstmt.close();
			con.close();

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		// Return the results.
		return vector;
	}

	/**
	 * Fetches a task from the database.
	 * 
	 * @param id
	 *            The id of the task to be returned.
	 */
	public Object[] getTask(int id) {
		try {
			Object[] task = new Object[8];
			// Establish a connection to the database.
			Class.forName("org.h2.Driver");
			Connection con = DriverManager.getConnection("jdbc:h2:tmdb",
					"tmdb", "");
			// Get the task with id id from the database.
			PreparedStatement pstmt = con
					.prepareStatement("SELECT * FROM tasks, categories"
							+ " WHERE categories.cat_id = tasks.category AND id = ?");
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				task[0] = false;
				task[1] = rs.getString("task");
				task[2] = new Date(rs.getTimestamp("due").getTime());
				task[3] = rs.getString("categories.category");
				task[4] = rs.getInt("priority");
				task[5] = rs.getString("status");
				task[6] = rs.getString("description");
				task[7] = rs.getInt("id");
			}
			// Close the connection
			pstmt.execute();
			pstmt.close();
			con.close();
			return task;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return new Object[8];
	}

	/**
	 * Updates a task to the database.
	 * 
	 * @param title
	 *            Name of the task.
	 * @param date
	 *            Due date of the task.
	 * @param category
	 *            Name of the category of the task.
	 * @param priority
	 *            Priority of the task (1-10)
	 * @param status
	 *            Status of the task.
	 * @param description
	 *            Description of the task.
	 * @return Returns true upon success.
	 */
	public boolean updateTask(int id, String title, java.util.Date date,
			String category, int priority, String status, String description) {
		try {
			// Establish database connection
			Class.forName("org.h2.Driver");
			Connection con = DriverManager.getConnection("jdbc:h2:tmdb",
					"tmdb", "");
			// Fetch the id of the category or return false if non-existant.
			PreparedStatement pstmt = con
					.prepareStatement("SELECT * FROM categories WHERE category = ?");
			pstmt.setString(1, category);
			ResultSet rs = pstmt.executeQuery();
			int cid = 1;
			if (rs.next()) {
				cid = rs.getInt("cat_id");
			} else {
				return false;
			}

			// Update the tasks with the set values into the task table of db.
			pstmt = con
					.prepareStatement("UPDATE tasks "
							+ "SET task = ?, due = ?, category = ?, priority = ?, status = ?, description = ? "
							+ "WHERE id = ?");

			pstmt.setString(1, title);
			pstmt.setTimestamp(2, new java.sql.Timestamp(date.getTime()));
			pstmt.setInt(3, cid);
			pstmt.setInt(4, priority);
			pstmt.setString(5, status);
			pstmt.setString(6, description);
			pstmt.setInt(7, id);
			boolean success = pstmt.execute();
			// Close connection
			pstmt.close();
			con.close();
			return success;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

}
