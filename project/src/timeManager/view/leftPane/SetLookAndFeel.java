
package timeManager.view.leftPane;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import javax.swing.plaf.metal.*;

public class SetLookAndFeel {
	
	public static void initLookAndFeel(){
		String LookAndFeel = null;
		Properties THEMES = new Properties();
		
		try {
			File Properties = new File(System.getProperty("user.home"),
					".TODO-Group6-themes");
			FileInputStream input = new FileInputStream(Properties);
			try {
				THEMES.load(input);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		if((THEMES.getProperty("LOOKANDFEEL")) != null)
			if(THEMES.getProperty("LOOKANDFEEL").equals("Metal")){
			LookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
			}
			else if(THEMES.getProperty("LOOKANDFEEL").equals("Motif")){
				LookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
			}
			else{
				System.err.println("Unexpected value on LookAndFeel" + THEMES.getProperty("LOOKANDFEEL"));
			}
	try{
		UIManager.setLookAndFeel(LookAndFeel);
			if(THEMES.getProperty("LOOKANDFEEL").equals("Metal")){
				if(THEMES.getProperty("THEME").equals("defaultMetal")){
					MetalLookAndFeel.setCurrentTheme(new DefaultMetalTheme());				
				}
				else {
					MetalLookAndFeel.setCurrentTheme(new DefaultMetalTheme());
				}
			}else if(THEMES.getProperty("LOOKANDFEEL").equals("Motif")){
				if(THEMES.getProperty("THEME").equals("Ocean")){
					MetalLookAndFeel.setCurrentTheme(new OceanTheme());
				}
				else{
					MetalLookAndFeel.setCurrentTheme(new OceanTheme());
				}
			}
		}catch(Exception e){
			System.err.println("try set LookAndFeel "+" " + THEMES.getProperty("LOOKANDFEEL") +" "+ THEMES.getProperty("THEME"));
		}
	}
}


