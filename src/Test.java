import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

public class Test {
    public static void main1(String[] args) throws Exception {
    	String fileName = "crawler.db";
    	String tableName = "visited";
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + fileName);
        Statement stat = conn.createStatement();
        stat.executeUpdate("drop table if exists " + tableName);
        stat.executeUpdate("create table " + tableName + " (url, time);");
        PreparedStatement prep = conn.prepareStatement(
            "insert into " + tableName + " values (?, ?);");

        prep.setString(1, "http://www.mehrnews.ir/NewsPrint.aspx?NewsID=374");
        Date currentTime = new Date(System.currentTimeMillis());
        prep.setString(2, currentTime.toString());
        prep.addBatch();

        conn.setAutoCommit(false);
        prep.executeBatch();
        conn.setAutoCommit(true);

        ResultSet rs = stat.executeQuery("select * from " + tableName + ";");
        while (rs.next()) {
            System.out.print("url = " + rs.getString("url") + " - - - ");
            System.out.println("job = " + rs.getString("time"));
        }
        rs.close();
        conn.close();
    }
    
    public static void main(String[] args){
    	//DataBase db = new DataBase();
    	Mehrnews mn = new Mehrnews();
    	try {
    		//mn.createTable();
    		mn.getListPage(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}