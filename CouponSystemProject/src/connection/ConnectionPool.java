package connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
//import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.ResultSet;
//import java.sql.Statement;
/**
 * @author Marina Daich
 * Connection pool class includes all credentials of DB connection
 * and actions 
 */
public class ConnectionPool {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/sys";
	//key of object
	public Object key = new Object();
	//maximum connections
	public static final int max_conn = 5;
	private ArrayList<Connection> arr_conn = new ArrayList<>();
		
	// Database credentials
	static final String USER = "root";
	static final String PASS = "Rohini108dd";
	// JDBC driver name and database URL	
	private static ConnectionPool instance =null;
	
	private ConnectionPool() throws ClassNotFoundException, SQLException{	
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			
			for (int i=0;i<max_conn;i++)
					{
				       arr_conn.add(DriverManager.getConnection(DB_URL, USER, PASS));
					}
		}
	//getting instance	
		public static synchronized ConnectionPool getInstance() throws ClassNotFoundException, SQLException{
			if (instance==null)
				instance=new ConnectionPool();
			return instance;			
		}
	//getting connection	
		public Connection getConnection() throws ClassNotFoundException, SQLException, InterruptedException
		{
			synchronized (key) {
				while (arr_conn.isEmpty())
				{
					key.wait();		
				}
				Connection result=arr_conn.get(0);			
				arr_conn.remove(0)	;
				return result;
			}
		}		
		//returning connection
		public void returnConnection(Connection conn)
		{
			synchronized (key) {
				arr_conn.add(conn);
				key.notify();
			}
		}
		//closing all connections
		public void CloseAllConnections() throws SQLException {
			for(Connection conn: arr_conn)
				{
				   conn.close();
				}
		}
}
