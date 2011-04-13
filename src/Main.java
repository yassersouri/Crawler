import java.sql.SQLException;


public class Main {
	
	public static void main(String[] args){
		DataBase db = new DataBase();
		Mehrnews mn = new Mehrnews();
		try {
			if(!db.tableExists("mehrnews")){
				mn.createTable();
			}
			for(int i = 2917; i > 0; i--){
				try {
					mn.getListPage(i);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
