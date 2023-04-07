package Connect.SQL;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public class database {
	public static Connection getConnection() {
		Connection c=null;
		try {
	
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			String url ="jdbc:mySQL://localhost/highscore";
			String username="root";
			String password="";
			c=DriverManager.getConnection(url,username,password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
	}
	public static void closeConnection(Connection c) {
		try {
			if(c!=null)
				c.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}