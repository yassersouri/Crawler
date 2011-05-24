package persiantools;

import static persiantools.HTMLCharacterHelper.*;
import java.io.*;

public class Program {
	public static void main(String args[]){
		removeAll();
	}
	
	
	public static void removeAll(){
		File dir = new File("D:\\sample\\");
		File[] allFiles = dir.listFiles();
		
		for(int i = 0; i < allFiles.length; i++){
			try {
				removeAll_NBSP_ZWNJ_QUOT(allFiles[i]);
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Done file: " + allFiles[i].toString());
		}
	}
}
