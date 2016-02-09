import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class UserIDNormalization {

	/**
	 * @param args
	 * @return 
	 */
	public static void Normalization(String dbName, String tableName) throws SQLException{


		Connection con = GridDBManager.dbConnect(dbName);
		Statement stmt = con.createStatement();
		ResultSet res = stmt.executeQuery("Select distinct UserID as `uid` from "+ tableName);
		String sql = "Update "+tableName+" set UserID = ? where UserID = ?";
		PreparedStatement prep = con.prepareStatement(sql);
		String oldUser;
		ArrayList<String> userList  = new ArrayList<String>();
		while(res.next()){
			userList.add(res.getString("uid"));
		}
		int i=1;
		while(userList.iterator().hasNext()){

			oldUser=userList.iterator().next();
			prep.setString(1, "UID_"+i);
			prep.setString(2, oldUser);
			//prep.executeUpdate();
			prep.addBatch();
			prep.executeBatch();
			i++;
		}
		prep.executeUpdate();

		System.out.println("User ID Normalization successful");
		con.close();
	}


}

