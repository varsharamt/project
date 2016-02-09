
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;


public class GenWorkload {

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {


		ArrayList <Integer> Grid5000AdmissiblejobIDs = (new FindAdmissible()).FindJobs("Grid5000.sqlite", "Jobs");
		ArrayList <Integer> Grid5000FilteredjobIDs = (new JobFilter()).tbFilter("Grid5000.sqlite", "Jobs");
		ArrayList <Integer> AuverGridAdmissiblejobIDs = (new FindAdmissible()).FindJobs("AuverGrid.db3", "Jobs");
		ArrayList <Integer> AuverGridFilteredjobIDs = (new JobFilter()).tbFilter("AuverGrid.db3", "Jobs");
		ArrayList <Integer> LCGAdmissiblejobIDs= (new FindAdmissible()).FindJobs("LCG.db3", "Jobs");
		ArrayList <Integer> LCGFilteredjobIDs = (new JobFilter()).tbFilter("LCG.db3", "Jobs");
		ArrayList <Integer> NorduGridAdmissiblejobIDs = (new FindAdmissible()).FindJobs("NorduGrid.db3", "Jobs");
		ArrayList <Integer> NorduGridFilteredjobIDs = (new JobFilter()).tbFilter("NorduGrid.db3", "Jobs");
		ArrayList <Integer> SHARCNetAdmissiblejobIDs = (new FindAdmissible()).FindJobs("SHARCNet.db3", "Jobs");
		ArrayList <Integer>  SHARCNetFilteredjobIDs = (new JobFilter()).tbFilter("SHARCNet.db3", "Jobs");

		ArrayList<Integer> Grid5000FinalJobIDs = new ArrayList<Integer>();
		Grid5000FinalJobIDs.addAll(Grid5000AdmissiblejobIDs);
		Grid5000FinalJobIDs.addAll(Grid5000FilteredjobIDs);
		Collections.sort(Grid5000FinalJobIDs);
		RetrieveWorkloads("Grid5000.sqlite", "Jobs", Grid5000FinalJobIDs);
		

		ArrayList<Integer> AuverGridFinalJobIDs = new ArrayList<Integer>();
		AuverGridFinalJobIDs.addAll(AuverGridAdmissiblejobIDs);
		AuverGridFinalJobIDs.addAll(AuverGridFilteredjobIDs);
		Collections.sort(AuverGridFinalJobIDs);
		RetrieveWorkloads("AuverGrid.db3", "Jobs", AuverGridFinalJobIDs);
		

		ArrayList<Integer> LCGFinalJobIDs = new ArrayList<Integer>();
		LCGFinalJobIDs.addAll(LCGAdmissiblejobIDs);
		LCGFinalJobIDs.addAll(LCGFilteredjobIDs);
		Collections.sort(LCGFinalJobIDs);
		RetrieveWorkloads("LCG.db3", "Jobs", LCGFinalJobIDs);
		


		ArrayList<Integer> NorduGridFinalJobIDs = new ArrayList<Integer>();
		NorduGridFinalJobIDs.addAll(NorduGridAdmissiblejobIDs);
		NorduGridFinalJobIDs.addAll(NorduGridFilteredjobIDs);
		Collections.sort(NorduGridFinalJobIDs);
		RetrieveWorkloads("NorduGrid.db3", "Jobs", NorduGridFinalJobIDs);
		
		ArrayList<Integer> SHARCNetFinalJobIDs = new ArrayList<Integer>();
		SHARCNetFinalJobIDs.addAll(SHARCNetAdmissiblejobIDs);
		SHARCNetFinalJobIDs.addAll(SHARCNetFilteredjobIDs);
		Collections.sort(SHARCNetFinalJobIDs);
		RetrieveWorkloads("SHARCNet.db3", "Jobs", SHARCNetFinalJobIDs);
		
		UserIDNormalization.Normalization("FinalWorkloads.sqlite", "NJobs");
		
	}

	public static void RetrieveWorkloads(String dbName, String tableName, ArrayList<Integer> id) throws SQLException{
		Connection c = GridDBManager.dbConnect(dbName);
		ResultSet r = null;
		
		try {
			Statement psmt = c.createStatement();
			String parameters  = id.toString();
			parameters = parameters.replace("[", "");
			parameters = parameters.replace("]", "");
			String sql = "select * from Jobs where JobID in ("+ parameters+")";
			r = psmt.executeQuery(sql);
		}catch(SQLException e){
			e.printStackTrace();
		}
		//c.close();
		System.out.println("Filtered Workloads Retrieved From: " + dbName+". Writing to Output Database !");
		WriteToTable(c, r);

	}


	public static void WriteToTable(Connection con, ResultSet r) throws SQLException{
		String tableName = "NJobs";
		String finalDB = "FinalWorkloads.sqlite";
		Connection outDB = GridDBManager.dbConnect(finalDB);
		outDB.setAutoCommit(false);
		ResultSetMetaData meta = r.getMetaData();
		String sql = "Insert into "+ tableName+ "(`SubmitTime`, `WaitTime`,`RunTime`,`NProc`,`UsedCPUTime`," +
				"`UsedMemory`, `ReqNProcs`, `ReqTime`, `ReqMemory`,`Status`, `UserID`,`GroupID`,`ExecutableID`," +
				"`QueueID`,`PartitionID`,`OrigSiteID`,`LastRunSiteID`,`JobStructure`,`JobStructureParams`," +
				"`UsedNetwork`,`UsedLocalDiskSpace`,`UsedResources`,`ReqPlatform`,`ReqNetwork`," +
				" `ReqLocalDiskSpace`, `ReqResources`, `VOID`, `ProjectID` ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement prep = outDB.prepareStatement(sql);
		while (r.next()) {
			for (int i = 1; i < meta.getColumnCount(); i++){
				prep.setObject(i, r.getObject(i+1));
			}
			prep.addBatch();
		}
		int[] count = prep.executeBatch();
		
		outDB.commit();
		System.out.println("Copied "+count.length + " batches to Output table: "+tableName + " !");
		outDB.close();
	}



}






