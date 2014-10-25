package timeManager;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import timeManager.view.leftPane.LeftPane;
import timeManager.view.leftPane.SetLookAndFeel;
import timeManager.view.rightPane.RightPane;

/**
 * Class for handling the view.
 * 
 */
@SuppressWarnings("serial")
public class View extends JFrame {
	public static ResourceBundle bundle;

	private LeftPane leftPane;
	private RightPane rightPane;

	/**
	 * Gets the leftPane
	 * 
	 * @return returns the left view pane
	 */
	public LeftPane getLeftPane() {
		return leftPane;
	}

	/**
	 * Gets the rightPane.
	 * 
	 * @return returns the right view pane
	 */
	public RightPane getRightPane() {
		return rightPane;
	}

	/**
	 * Sets up the view; initializes the language, actions, menu bar, and adds
	 * the view panes to the gridbaglayout.
	 * 
	 * @param s
	 *            Used to set the title of the main window
	 */
	public View(String s, ActionSt myActions) {
		windowStarting();
		this.setTitle(s);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		// Load properties.
		Properties lang = new Properties();
		File properties = null;
		try {
			properties = new File(System.getProperty("user.home"),
					".TODO-Group6-language");
			FileInputStream input = new FileInputStream(properties);
			lang.setProperty("language", "en");
			lang.load(input);
			Locale.setDefault(new Locale(lang.getProperty("language")));
		} catch (IOException e) {
		}
		bundle = ResourceBundle
				.getBundle("ui.bundle.lang", Locale.getDefault());

		/*
		 * Important: Each time the view is instantiated this method also
		 * re-initializes the actions, so that the language remains correct!
		 */
		myActions.createActions(bundle);

		/* Create menu bar section ************************************** */
		// Sets up the menu bar and actions associated with the menu items.
		JMenuBar menubar = new JMenuBar();
		JMenu menu = new JMenu(bundle.getString("File"));
		menubar.add(menu);
		menu.addSeparator();

		JMenuItem menu_item_create_event = new JMenuItem(myActions.getAddTask());
		menu.add(menu_item_create_event);
		menu.addSeparator();

		JMenuItem menu_item_exit = new JMenuItem(View.bundle.getString("Exit"));
		menu_item_exit.setMnemonic(KeyEvent.VK_Q);
		menu_item_exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
				ActionEvent.CTRL_MASK));
		
		// Tooltip for exit option
		menu_item_exit.setToolTipText(bundle.getString("Exit_application"));

		menu_item_exit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
		menu.add(menu_item_exit);

		JMenu menu2 = new JMenu(bundle.getString("Edit"));
		menubar.add(menu2);
		JMenuItem menuItem3 = new JMenuItem(myActions.getRemoveTask());
		menu2.add(menuItem3);

		JMenu menu3 = new JMenu(bundle.getString("View"));
		menubar.add(menu3);
		JMenuItem menuItem4 = new JMenuItem(myActions.getClearFilter());
		menu3.add(menuItem4);
		JMenuItem menuItem401 = new JMenuItem(myActions.getShowSelected());
		menu3.add(menuItem401);
		JMenuItem menuItem5 = new JMenuItem(myActions.getMetal());
		menu3.add(menuItem5);
		JMenuItem menuItem6 = new JMenuItem(myActions.getOcean());
		menu3.add(menuItem6);
		/*JMenu menu4 = new JMenu(bundle.getString("Help"));
		menubar.add(menu4);
		JMenuItem menuItem5 = new JMenuItem(bundle.getString("About_us"));
		menu4.add(menuItem5);
		JMenuItem menuItem41 = new JMenuItem(bundle.getString("Help"));
		menu4.add(menuItem41);*/

		JMenu language = new JMenu(bundle.getString("Language"));
		JMenuItem englishLang = new JMenuItem(myActions.getEnglishLang());
		JMenuItem latinLang = new JMenuItem(myActions.getLatinLang());
		language.add(englishLang);
		language.add(latinLang);
		menubar.add(language);
		this.setJMenuBar(menubar);
		/* END: Create menu bar section! ************************************** */

		GridBagLayout g = new GridBagLayout();
		this.setLayout(g);
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		this.add(leftPane = new LeftPane(myActions), c);
		c.gridx = 1;
		c.weightx = 1;
		this.add(rightPane = new RightPane(), c);

		/**
		 * Listens to the windows close button. On close it stores information
		 * about the current height, width, and position of the window.
		 */
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Properties window = new Properties();
				OutputStream output = null;
				try {
					File properties = new File(System.getProperty("user.home"),
							".TODO-Group6");
					output = new FileOutputStream(properties);
					window.setProperty("height", "" + getBounds().height);
					window.setProperty("width", "" + getBounds().width);
					window.setProperty("x", "" + getBounds().x);
					window.setProperty("y", "" + getBounds().y);
					window.store(output, null);
				} catch (IOException ex) {
					ex.printStackTrace();
					System.out.print("error WindowPropertys");
				}
				System.exit(0);
			}
		});
		this.setVisible(true);
	}

	/**
	 * When the program is started, this function initializes the previous
	 * width, height, and position of the window.
	 */
	public void windowStarting() {
		Properties window = new Properties();
		window.setProperty("height", "" + 600);
		window.setProperty("width", "" + 900);
		window.setProperty("x", "" + 0);
		window.setProperty("y", "" + 0);
		try {
			File properties = new File(System.getProperty("user.home"),
					".TODO-Group6");
			FileInputStream input = new FileInputStream(properties);
			window.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setBounds(Integer.parseInt(window.getProperty("x")),
				Integer.parseInt(window.getProperty("y")),
				Integer.parseInt(window.getProperty("width")),
				Integer.parseInt(window.getProperty("height")));
		
		Properties THEMES = new Properties();
		THEMES.setProperty("LOOKANDFEEL", "Metal");
		
		try {
			File properties = new File(System.getProperty("user.home"),
					".TODO-Group6-themes");
			FileInputStream input = new FileInputStream(properties);
			THEMES.load(input);
		} catch (IOException x) {
			x.printStackTrace();
		}
				THEMES.setProperty("THEME", "DefaultMetal");
				THEMES.setProperty("LOOKANDFEEL", "Metal");
	}
		
	}

