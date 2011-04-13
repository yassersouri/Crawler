import java.io.*;


public class Logger {
	String savePath = "D:\\sites\\";
	String outputfile = savePath + "repetitive.txt";
	
	public void LogRepetitive(String log) throws IOException{
		FileWriter fw = new FileWriter(new File(outputfile), true);
		fw.write(log + "\n");
		fw.flush();
		fw.close();
	}
	
}
