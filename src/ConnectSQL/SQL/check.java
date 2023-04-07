package Connect.SQL;

import java.sql.Connection;

public class check {
	
	public static void main(String args[])
	{
		Connection cn=database.getConnection();
		System.out.println(cn);
		
		database.closeConnection(cn);
	}
}
