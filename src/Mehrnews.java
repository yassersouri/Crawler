import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;


public class Mehrnews extends Site {
	
	String tableName= "mehrnews";
	DataBase db;
	Logger logger = new Logger();
	String tableNameFields = "id, dateOfFetch";
	Vector<URL> urls;
	public Mehrnews(){
		title = "Mehrnews";
		url = "http://www.mehrnews.ir/txtNewsView_fa.aspx?t=News&Page=1";
		savePath = "D:\\sites\\mehrnews\\";
		db = new DataBase();
		urls = new Vector<URL>(100);
	}
	
	//gets the listPage of the address http://www.mehrnews.ir/txtNewsView_fa.aspx?t=News&Page=#{id}
	//inserts all the links into a vector of URLs and invokes a method that tries to save each link.
	public String getListPage(int id) throws Exception{
		String className = "news_title";
		String format = url.substring(0, url.length()-1) + id;
		String dateOfThePage = "";
		String result = savePath + dateOfThePage + ".html";
		URL mehrnews = new URL(format);
		
		CleanerProperties cp = new CleanerProperties();
		cp.setRecognizeUnicodeChars(true);
		
		HtmlCleaner cleaner = new HtmlCleaner(cp);
		TagNode rootNode = cleaner.clean(mehrnews);
		
		TagNode aElements[] = rootNode.getElementsByName("a", true);
		String classType;
		String href;
		for(int i = 0; aElements != null && i < aElements.length; i++){
			classType = aElements[i].getAttributeByName("class");
			if(classType != null && classType.equals(className)){
				href = aElements[i].getAttributeByName("href");
				href = "http://www.mehrnews.ir/" + href;
				urls.add(new URL(href));
			}
		}
		this.getPagesInVector();
		return result;
	}
	
	private void getPagesInVector() throws Exception {
		if(urls.isEmpty()){
			throw new Exception("no links are in vector.");
		}
		String className = "news_body_print";
		URL url;
		
		for(int k = 0; k < urls.size(); k++){
			url = urls.elementAt(k);

			String newsID = url.toString().substring(45);
			newsID = "'" + newsID + "'";
			if(!db.doesLinkExistsInDatabase(tableName, newsID)){
				//save the page
				String outputfile = savePath + url.toString().substring(45) + ".txt";
				
				FileOutputStream fos = new FileOutputStream(outputfile);
				Writer out = new OutputStreamWriter(fos, "UTF8");
				
				CleanerProperties cp = new CleanerProperties();
				cp.setRecognizeUnicodeChars(true);
				HtmlCleaner cleaner = new HtmlCleaner(cp);
				TagNode rootNode = cleaner.clean(url);
				
				TagNode spanElements[] = rootNode.getElementsByName("span", true);
				String classType;
				TagNode[] children;
				StringBuffer sb;
				for(int i = 0; spanElements != null && i < spanElements.length; i++){
					classType = spanElements[i].getAttributeByName("class");
					if(classType != null && classType.equals(className)){
						children = spanElements[i].getChildTags();
						for(int j = 0; j < children.length; j++){
							sb = children[j].getText();
							out.write(sb.toString());
							//System.out.println(url.toString());
						}
					}
				}
				out.flush();
				out.close();
				fos.close();
				Date currentTime = new Date(System.currentTimeMillis());
				//newsID already has single quotation!
				String insertValue = "" + newsID + ",'" + currentTime.toString() + "'";
				//add link to database
				db.insert(tableName, insertValue );
				System.out.println(url.toString());
			}
			else{
				logger.LogRepetitive(url.toString());
			}
		}
		urls.clear();
	}

	public Mehrnews createTable() throws ClassNotFoundException, SQLException{
		if(!db.tableExists(tableName)){
			db.createTable(tableName, tableNameFields);
		}	
		return this;
	}
}
