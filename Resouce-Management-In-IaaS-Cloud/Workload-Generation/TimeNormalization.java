import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeNormalization {
	/**
	 * @param args
	 * @throws SQLException 
	 * @throws ParseException 
	 */

	public static Date UnixTimetoDate(Integer subtime) {

		Date date = new Date(subtime*1000L);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z"); // the format of your date
		sdf.setTimeZone(TimeZone.getTimeZone("GMT")); 
		String formattedDate = sdf.format(date);
		Date d = null;
		try {
			d = sdf.parse(formattedDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(formattedDate);
		return d;
	}

	public static long DatetoUnixtime(String subDate){
		SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		Date date = null;
		try {
			date = sdf.parse(subDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long timeInMillisSinceEpoch = date.getTime(); 
		long timeInSecondsSinceEpoch = timeInMillisSinceEpoch / (1000);
		return timeInSecondsSinceEpoch;
	}


}
