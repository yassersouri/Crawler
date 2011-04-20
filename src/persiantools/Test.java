package persiantools;

import static persiantools.HTMLCharacterHelper.*;
import java.io.*;

public class Test {
	public static void main(String args[]){
		removeAll();
	}
	
	
	public static void removeAll(){
		File dir = new File("D:\\crawler\\sites\\mehrnews\\");
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
