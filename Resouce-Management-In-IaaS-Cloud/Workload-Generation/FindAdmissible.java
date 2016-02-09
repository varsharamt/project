import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class FindAdmissible {

	/**
	 * @param args
	 * @return 
	 */

	static Connection con = null;

	public ArrayList<Integer> FindJobs(String dbName, String tableName) throws SQLException{
		con = GridDBManager.dbConnect(dbName);
		Statement stmt = con.createStatement();
		ArrayList<Integer> jobIDs = new ArrayList<>();
		ArrayList<Integer> years = new ArrayList<>();

		ResultSet cy = stmt.executeQuery("select distinct strftime('%Y',SubmitTime,'unixepoch') y from " + tableName);
		while(cy.next()){
			int yrs = cy.getInt("y");
			years.add(yrs);
		}
		if(years.size() > 1){
			ResultSet rs = stmt.executeQuery( "select min(JobID) as JobID, strftime('%w',SubmitTime,'unixepoch') d, strftime('%Y',SubmitTime,'unixepoch') y from " +tableName +" jobs where d like '1' group by y");
			while(rs.next()){
				jobIDs.add(rs.getInt("JobID"));
			}
			ResultSet rs2 = stmt.executeQuery( "select max(JobID) as JobID, strftime('%w',SubmitTime,'unixepoch') d, strftime('%Y',SubmitTime,'unixepoch') y from " +tableName +" jobs where d like '0' group by y");	
			while(rs2.next()){
				jobIDs.add(rs2.getInt("JobID"));
			}
		}else if (years.size() == 1){
			ResultSet rs3 = stmt.executeQuery( "select min(JobID) as JobID, strftime('%w',SubmitTime,'unixepoch') d, strftime('%W',SubmitTime,'unixepoch') W from " +tableName +" jobs where d like '1' group by W");
			while(rs3.next()){
				jobIDs.add(rs3.getInt("JobID"));
			}
			ResultSet rs4 = stmt.executeQuery( "select max(JobID) as JobID, strftime('%w',SubmitTime,'unixepoch') d, strftime('%W',SubmitTime,'unixepoch') W from " +tableName +" jobs where d like '0' group by W");
			while(rs4.next()){
				jobIDs.add(rs4.getInt("JobID"));
			}
		}
		
		System.out.println("Number of Admissible Jobs: "+jobIDs.size() + " from Database: " + dbName);
		return jobIDs;
	}

}
