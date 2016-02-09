import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class JobFilter {

	/**
	 * @param args
	 * @throws SQLException 
	 */

	static Connection con = null;

	public ArrayList<Integer> tbFilter(String dbname, String tableName) throws SQLException{
		con = GridDBManager.dbConnect(dbname);
		Statement stmt = con.createStatement();
		ArrayList<Integer> jobIDs = new ArrayList<>();
		ResultSet rs = stmt.executeQuery( "select JobID,SubmitTime,RunTime,NProc,ReqTime,UserID,Status from " +tableName );
		while ( rs.next() ) {
			int id = rs.getInt("JobID");
			int subtime = rs.getInt("SubmitTime");
			int runtime = rs.getInt("RunTime");
			int nproc = rs.getInt("NProc");
			int reqtime = rs.getInt("ReqTime");
			String  userid = rs.getString("UserID");
			int status  = rs.getInt("Status");
			if(id>0 && subtime>=0 && runtime > 0 && nproc > 0 && reqtime > 0 && status != 0 && status != 4 && status !=5 && runtime <= reqtime && userid != null){
				jobIDs.add(id);

			}

		}
		con.close();
		System.out.println("Number of Filtered Jobs: "+jobIDs.size() + " from Database: " + dbname);
		return jobIDs;

	}


}
