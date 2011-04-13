import java.sql.*;


public class DataBase {
	String fileName = "D:\\crawler.db";
	
	public DataBase(){ 
	}
	
	public DataBase dropTable(String tableName) throws ClassNotFoundException, SQLException{
		Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + fileName);
        Statement stat = conn.createStatement();
        stat.executeUpdate("drop table if exists " + tableName);
        stat.close();
        conn.close();
		return this;
	}
	
	public boolean tableExists(String tableName) throws ClassNotFoundException, SQLException{
		Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + fileName);
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='" + tableName +"';");
        boolean result = rs.next();
        rs.close();
        stat.close();
        conn.close();
        return result;
	}
	//fields are like "url, time" without the quotation
	public DataBase createTable(String tableName, String fields) throws ClassNotFoundException, SQLException{
		Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + fileName);
        Statement stat = conn.createStatement();
        stat.executeUpdate("drop table if exists " + tableName);
        stat.executeUpdate("create table " + tableName + " (" + fields + ");");
        stat.close();
        conn.close();
		return this;
	}
	//fields is a string containing sorted attributes of the raw that will be inserted
	//e.g: "'http://asldfkjasdflasdj.com', '29 june 2323 alkjdf'" without the double quotes, with single quotes
	public DataBase insert(String tableName, String fields) throws ClassNotFoundException, SQLException{
		Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + fileName);
        Statement stat = conn.createStatement();
        
        stat.execute("insert into " + tableName + " values (" + fields + ");");
        
        stat.close();
        conn.close();
		return this;
	}
	
	public boolean doesLinkExistsInDatabase(String tableName, String newsID) throws ClassNotFoundException, SQLException{
		Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + fileName);
        Statement stat = conn.createStatement();
        
        ResultSet rs = stat.executeQuery("SELECT id FROM " + tableName + " WHERE id=" + newsID + " LIMIT 1 ;");
        boolean result = rs.next();
        rs.close();
        stat.close();
        conn.close();
		return result;
	}
}
