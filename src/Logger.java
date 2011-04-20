import java.io.*;

public class Logger {
	
	String savePath = "D:\\crawler\\logs\\";
	String repetitiveLogFile = savePath + "repetitive.txt";
	String emptyListPageLogFile = savePath + "emptylist.txt";
	String timeNotMatchLogFile = savePath + "notontime.txt";
	String goodRunLogFile = savePath + "goodRun.txt";
	
	public Logger(String savePath){
			if(savePath != null){
				this.savePath = savePath;
			}
			//create the save path if it does not exist
			File temp = new File(this.savePath);
			temp.mkdirs();
		}
	public void logRepetitive(String log) throws IOException{
		FileWriter fw = new FileWriter(new File(repetitiveLogFile), true);
		fw.write(log + "\n");
		fw.flush();
		fw.close();
	}

	public void logEmptyListPage(int id) throws IOException {
		FileWriter fw = new FileWriter(new File(emptyListPageLogFile), true);
		fw.write("http://www.mehrnews.ir/txtNewsView_fa.aspx?t=News&Page=" + id + "\n");
		fw.flush();
		fw.close();
	}
	
	public void logTimeNotMatch(String log) throws IOException{
		FileWriter fw = new FileWriter(new File(emptyListPageLogFile), true);
		fw.write(log + "\n");
		fw.flush();
		fw.close();
	}
	
	public void logGoodRun(String log) throws IOException{
		FileWriter fw = new FileWriter(new File(goodRunLogFile), true);
		fw.write(log + "\n");
		fw.flush();
		fw.close();
	}
}