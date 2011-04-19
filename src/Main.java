import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Timer;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;


public class Main extends TimerTask {
	static String defaultDBPath = "D:\\crawler\\crawler.db";
	static String defaultSavePath = "D:\\crawler\\sites\\mehrnews";
	static int currentPageID = 2930;
	static Mehrnews mn;
	
	public static void main(String[] args){
		//***********************
		//  DataBase File Path
		//***********************
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Select your database file");
		FileNameExtensionFilter ff = new FileNameExtensionFilter("*.db", "db");
		fc.setFileFilter(ff);
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setVisible(true);
		fc.showOpenDialog(null);
		File f = fc.getSelectedFile();
		
		DataBase db;
		if(f != null){
			defaultDBPath = f.getPath();
		}
		else{
			String dbDefaultMessage = "You Did not specify any path for the db file. So we assume that it is located in: " + defaultDBPath + "\n we are going to try to create that file for you if it does not exists!";
			JOptionPane.showMessageDialog(null, dbDefaultMessage, "Notice", JOptionPane.WARNING_MESSAGE);
			
			//create the file if it does not exists
			String dir = defaultDBPath.substring(0,defaultDBPath.lastIndexOf('\\'));
			File tempDir = new File(dir);
			tempDir.mkdirs();
			try {
				new File(defaultDBPath).createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		db = new DataBase(defaultDBPath);
		
		
		//***********************
		//  Save File Path
		//***********************
		JFileChooser dc = new JFileChooser();
		dc.setDialogTitle("Select your saving path");
		dc.setAcceptAllFileFilterUsed(false);
		dc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		dc.showOpenDialog(null);
		File d = dc.getSelectedFile();
		
		
		if(d != null){
			defaultSavePath = d.getPath();
		}
		else{
			String savePathDefaultMessage = "You Did not specify any saving path. So we assume that the saving path is: " + defaultSavePath + "\n we are going to try to create that directory for you if it does not exists!";
			JOptionPane.showMessageDialog(null, savePathDefaultMessage, "Notice", JOptionPane.WARNING_MESSAGE);
			//create the path if it does not exists
			String dir = defaultSavePath + "\\";
			File tempDir = new File(dir);
			tempDir.mkdirs();
		}
		mn = new Mehrnews(defaultDBPath, defaultSavePath);
		
		//Scheduler
		Main main = new Main();
		Timer timer = new Timer();
		long delay = 0;
		long period = 1000*5;
		
		//the run
		try {
			if(!db.tableExists("mehrnews")){
				mn.createTable();
			}
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		timer.scheduleAtFixedRate(main, delay, period);
	}

	@Override
	public void run() {
		if(currentPageID > 0){
			if(timeIsOK()){
				//do one page fetch
				try {
					mn.getListPage(currentPageID);
				} catch (Exception e) {
					e.printStackTrace();
				}
				//decreasing the <<currentPageID>>
				currentPageID--;
			}
		}
		else{
			System.out.println("Crawler finished work!");
			System.exit(0);
		}
	}

	private boolean timeIsOK() {
		SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
		SimpleDateFormat parser2 = new SimpleDateFormat("HH:mm:ss");
		Date one = null;
		Date six = null;
		Date current = null;
		Calendar cal = null;
		try {
			one = parser.parse("00:00"); //because of DST --> it really is 01:00
			six = parser.parse("05:00"); //because of DST --> it really is 06:00
			cal = Calendar.getInstance();
			current = parser.parse(parser.format(cal.getTime()));//be careful: DST
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		if((one.before(current) || one.equals(current)) && (current.before(six) || current.equals(six))){
			return true;
		}
		else{
			try {
				current = parser2.parse(parser2.format(cal.getTime()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			int position = current.toString().indexOf(':');
			String time = current.toString().substring(position-2, position+6);
			System.out.println("On Pause. Current time --> " + time);
			return false;
		}
	}
}
