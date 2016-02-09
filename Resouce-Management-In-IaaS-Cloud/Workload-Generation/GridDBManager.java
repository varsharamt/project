import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class GridDBManager {

	/**
	 * @param args
	 */
	public static Connection dbConnect(String dbname){

		Connection c = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:/home/user/Downloads/anon_jobs_sqlite/"+dbname);
			
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return c;
		
	}
	
	public static void dbClose(Connection c){
		try {
			c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
