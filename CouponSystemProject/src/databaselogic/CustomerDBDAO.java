package databaselogic;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import beans.Coupon;
import beans.Customer;
import connection.ConnectionPool;
import enum_package.CouponType;
import enum_package.ErrorDeclaration;
import exceptions.DBException;
import interfaces.CustomerDAO;

/**
 * @author marina daich
 *class implemens CustomerDAO interface
 *all actions by customer
 */
public class CustomerDBDAO implements CustomerDAO {
	ConnectionPool pool;	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	//logged_in_customer  get 0 before getting incremental id after inserting data in Customer table
	private long  logged_in_customer = 0;

	public CustomerDBDAO()
{
	try {
	pool = ConnectionPool.getInstance();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {	
				e.printStackTrace();
		}
}
//create new customer
	@Override
	public void createCustomer(Customer customer) throws DBException {
		PreparedStatement preparedStatement = null;
		try 
		{
			
			long key=-1;
			
			Connection conn = null;
			conn = pool.getConnection();			
		
			String sql = "INSERT INTO Customer  (CUST_NAME,PASSWORD) VALUES (?,?)";
		      
			preparedStatement=conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1,customer.getCustName());
			preparedStatement.setString(2,customer.getPassword() );
			preparedStatement.executeUpdate();     
				      ResultSet rs = preparedStatement.getGeneratedKeys();
				      if (rs != null && rs.next())
				      {
			          key = rs.getLong(1);	
			          customer.setId(key);
			          }
		      
		      ConnectionPool.getInstance().returnConnection(conn);
		      
	} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			throw new DBException(ErrorDeclaration.CUSTOMER_ALREADY_EXIST,"failed because"+e.getMessage());
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}	
		
	}
//removing customer and all coupons belonging to him
	@Override
	public void removeCustomer(Customer customer) throws DBException {
		PreparedStatement preparedStatement = null;
		try 
		{

			Connection conn = null;
			conn = pool.getConnection();
		
		      String sql="DELETE FROM Customer,Customer_Coupon"
		      		+ "LEFT JOIN Customer_Coupon ON  Customer_Coupon.CustomerId = Customer.ID WHERE CUST_NAME=?";
		      preparedStatement=conn.prepareStatement(sql);
		      preparedStatement.setString(1,customer.getCustName());	      
		      preparedStatement.executeUpdate();
		      
		      ConnectionPool.getInstance().returnConnection(conn);
		     
	} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (SQLException e) {
			throw new DBException(ErrorDeclaration.CUSTOMER_DOESNT_EXIST,"failed because"+e.getMessage());
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		
	}
//update customer 
	@Override
	public void updateCustomer(Customer customer) throws DBException {
		PreparedStatement preparedStatement = null;
		try
		{
		
		Connection conn = null;
		conn = pool.getConnection();
		
		String sql = "UPDATE Customer SET password=?  WHERE CUST_NAME =?";
		preparedStatement=conn.prepareStatement(sql);
		preparedStatement.setString(1,customer.getPassword());
		preparedStatement.setString(2,customer.getCustName());
		preparedStatement.executeUpdate();
	      
		ConnectionPool.getInstance().returnConnection(conn);
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			throw new DBException(ErrorDeclaration.CUSTOMER_DOESNT_EXIST,"failed because"+e.getMessage());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
//getting  customer by id
	@Override
	public Customer getCustomer(Long id) throws DBException {
		Customer cust = null;
		PreparedStatement preparedStatement = null;
		try 
		{
			
			Connection conn = null;
			conn = pool.getConnection();
		
			String sql = "SELECT * FROM Company WHERE ID=?";
				preparedStatement = conn.prepareStatement(sql);
				preparedStatement.setLong(1, id);
				ResultSet rs =  preparedStatement.executeQuery();
	      
		      while (rs != null && rs.next())
		      {
		    	  String custName = rs.getString("CUST_NAME");
		    	  String password = rs.getString( "PASSWORD");
		    			    	  
		    	  cust = new Customer(id, custName, password);
		      }
		      
		      ConnectionPool.getInstance().returnConnection(conn);
		      
		      return cust;

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println(e);		
		} catch (SQLException e) {
			throw new DBException(ErrorDeclaration.CUSTOMER_DOESNT_EXIST,"failed because"+e.getMessage());
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println(e);
		}
		
		return cust;
	}
//getting collection of all customers	
	@Override
	public Collection<Customer> getAllCustomers() throws DBException {
		ArrayList<Customer> custarr = new ArrayList<Customer>();
		PreparedStatement preparedStatement = null;
		try
		{
		
		Connection conn = null;
		conn = pool.getConnection();
		
	
	      
	    		  String sql = "SELECT * FROM Customer";	
	      preparedStatement = conn.prepareStatement(sql);
	      
	      ResultSet rs =  preparedStatement.executeQuery();
	      while (rs != null && rs.next())
	      {

	    	  Long id = rs.getLong("ID")  ;
	    	  String name = rs.getString("CUST_NAME")  ;
	    	  String password = rs.getString("PASSWORD");
	    	  
	    	  
	    	  Customer  resultarr = new Customer(id, name, password);
		      custarr.add(resultarr);

	      	}
	      
	      
	      ConnectionPool.getInstance().returnConnection(conn);
	      

	      return custarr;
	      }
			catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println(e);		
		} catch (SQLException e) {
			throw new DBException(ErrorDeclaration.TABLE_DOESNT_EXIST,"failed because"+e.getMessage());

		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println(e);
		}
	return custarr;
	}
	
//login and checks if customer is in the DB
	@Override
	public boolean login(String custName, String password) throws DBException {
		try
		{ 
		boolean result = false;
		Statement stmt = null;
		Connection conn = null;
		conn = pool.getConnection();
		stmt = conn.createStatement();
     
	      ResultSet rs = stmt.executeQuery ("SELECT * FROM Customer WHERE CUST_NAME="
	      		+ "'"+ custName+"'and  PASSWORD = '"+password+"'");	      
	     
	      while (rs!= null && rs.next())
	      {
	    	  logged_in_customer = rs.getLong("ID");
	    	  System.out.println("ID " +logged_in_customer);
	    	  result = true;
	    	  
	      }
      
	      ConnectionPool.getInstance().returnConnection(conn);
	      return result;
	      }
		catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println(e);
			
		} catch (SQLException e) {
			throw new DBException(ErrorDeclaration.INVALID_CREDENTIAL,"failed because"+e.getMessage());
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println(e);
		}
	return false;
	}
//purchase coupon by customer	
	@Override
	public void purchaseCoupon(Coupon coupon) throws DBException
	{
		PreparedStatement preparedStatement = null;
	try
	{	

		Connection conn = null;
		conn = pool.getConnection();
		
		String sql = "INSERT INTO Customer_Coupon (CustomerID,CouponID) VALUES (?,(select ID from Coupon where title = ?))";
		preparedStatement =  conn.prepareStatement(sql);
		
		preparedStatement.setLong(1, logged_in_customer);

		preparedStatement.setString(2,coupon.getTitle());
		
		//System.out.println("coupon.getId()" +coupon.getId());

		preparedStatement.executeUpdate();
	      ConnectionPool.getInstance().returnConnection(conn);
		
		
	}
	catch (ClassNotFoundException e) {
		e.printStackTrace();

		
	} catch (SQLException e) {
		throw new DBException(ErrorDeclaration.COUPON_ALREADY_PURCHASED,"failed because "+e.getMessage());
	
	} catch (InterruptedException e) {
		e.printStackTrace();

	}
	
	}
	//get collection coupons per customer
	@Override
	public Collection<Coupon> getCoupons() throws DBException {
		 ArrayList<Coupon> getcoupon = new ArrayList<Coupon>();
		 PreparedStatement preparedStatement =null;
	
		 Coupon coupon = new Coupon(); 
		 try
		 {
				Connection conn = null;
				conn = pool.getConnection();
			String sql ="SELECT * FROM Coupon LEFT JOIN"
					+ " Customer_Coupon on Customer_Coupon.CouponID = Coupon.ID "
					+ "WHERE CustomerID = ?";
			preparedStatement = conn.prepareStatement(sql)  ;
			preparedStatement.setLong(1, logged_in_customer);
		    ResultSet rs =  preparedStatement.executeQuery();
		      while (rs != null && rs.next())
		      {	
		    	   
		    	  coupon.setTitle(rs.getString("TITLE"));
		    	  coupon.setStartDate(rs.getDate("START_DATE"));
		    	  coupon.setEndDate(rs.getDate("END_DATE"));
		    	  coupon.setAmount(rs.getInt("AMOUNT"));
		    	  coupon.setType(CouponType.valueOf(rs.getString("TYPE")));
		    	  coupon.setMessage(rs.getString("MESSAGE"));
		    	  coupon.setPrice(rs.getDouble("PRICE"));
		    	  coupon.setImage(rs.getString("IMAGE"));
		    	
		      
		      getcoupon.add(coupon);
		       System.out.println(getcoupon);
		      }
		      
		      ConnectionPool.getInstance().returnConnection(conn);
		      
		      return getcoupon;
		
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
} catch (SQLException e) {
	throw new DBException(ErrorDeclaration.TABLE_DOESNT_EXIST,"failed because"+e.getMessage());
} catch (InterruptedException e) {
		e.printStackTrace();
}
return getcoupon;
}
//get purchased coupos by Type	
public void getAllPurchasedCouponsByType(CouponType type) throws DBException{
		 ArrayList<Coupon> getallcoupons = new ArrayList<Coupon>();
		 PreparedStatement preparedStatement =null;

		 
		try{		

			Connection conn = null;
			conn = pool.getConnection();

	    		
		    		String sql = "SELECT * FROM"
		    		+ "(SELECT * FROM coupon "
		    		+ "INNER JOIN customer_coupon "
		    		+ "ON coupon.ID = customer_coupon.CouponID WHERE coupon.type = ?) as couponselect;";
		    		preparedStatement =  conn.prepareStatement(sql);
		    		preparedStatement.setString(1,type.toString());
		    		ResultSet rs = preparedStatement.executeQuery();
		    		
	      while (rs != null && rs.next())
	      {    	  		    	  
	    	  Coupon coupon = new Coupon();  
		    	coupon.setTitle(rs.getString("TITLE"));
		    	coupon.setStartDate(rs.getDate("START_DATE"));
		    	coupon.setEndDate(rs.getDate("END_DATE"));
		    	coupon.setAmount(rs.getInt("AMOUNT"));
		    	coupon.setType(CouponType.valueOf(rs.getString("TYPE")));
		    	coupon.setMessage(rs.getString("MESSAGE"));
		    	coupon.setPrice(rs.getDouble("PRICE"));
		    	coupon.setImage(rs.getString("IMAGE"));

		    	getallcoupons.add(coupon);
		    	
		     
	      	}
	      System.out.println(getallcoupons);      
	      ConnectionPool.getInstance().returnConnection(conn);

	} 
	catch (ClassNotFoundException e) {
		e.printStackTrace();
		System.out.println(e);		
	} catch (SQLException e) {
		throw new DBException(ErrorDeclaration.COUPON_DOESNT_EXIST,"failed because"+e.getMessage());
	} catch (InterruptedException e) {
		e.printStackTrace();
		System.out.println(e);
	}	
	}
//	get all coupons by maximal price
	public void getAllPurchasedCouponsByPrice(double price) throws DBException{
		 ArrayList<Coupon> getallcoupons = new ArrayList<Coupon>();
		 PreparedStatement preparedStatement =null;
		 
		 
		try{		
			
			Connection conn = null;
			conn = pool.getConnection();
	
	    		
		    		String sql = "SELECT * FROM"
		    		+ "(SELECT * FROM coupon "
		    		+ "INNER JOIN customer_coupon "
		    		+ "ON coupon.ID = customer_coupon.CouponID WHERE "
		    		+ "coupon.price < ?) as couponselect;";
		    		preparedStatement =  conn.prepareStatement(sql);
		    		preparedStatement.setDouble(1,price);
		    		ResultSet rs = preparedStatement.executeQuery();
		    		
	      while (rs != null && rs.next())
	      {    	  		    	  
	    	  
	    	  Coupon coupon = new Coupon();  
		    	coupon.setTitle(rs.getString("TITLE"));
		    	coupon.setStartDate(rs.getDate("START_DATE"));
		    	coupon.setEndDate(rs.getDate("END_DATE"));
		    	coupon.setAmount(rs.getInt("AMOUNT"));
		    	coupon.setType(CouponType.valueOf(rs.getString("TYPE")));
		    	coupon.setMessage(rs.getString("MESSAGE"));
		    	coupon.setPrice(rs.getDouble("PRICE"));
		    	coupon.setImage(rs.getString("IMAGE"));

		    	getallcoupons.add(coupon);
		    	System.out.println(getallcoupons);	
	      	}
	      
	    	
	      ConnectionPool.getInstance().returnConnection(conn);

	} 
	catch (ClassNotFoundException e) {
		e.printStackTrace();
		System.out.println(e);		
	} catch (SQLException e) {
		throw new DBException(ErrorDeclaration.COUPON_DOESNT_EXIST,"failed because"+e.getMessage());
	} catch (InterruptedException e) {
		e.printStackTrace();
		System.out.println(e);
	}	
	}
	
//	get all coupons by maximal price
	public void getAllPurchasedCouponsByDate(Date date) throws DBException{
		 ArrayList<Coupon> getallcoupons = new ArrayList<Coupon>();
		 PreparedStatement preparedStatement =null;
		 
		 
		try{		
			
			Connection conn = null;
			conn = pool.getConnection();
	
			

		    		String sql = "SELECT * FROM"
		    		+ "(SELECT * FROM coupon "
		    		+ "INNER JOIN customer_coupon "
		    		+ "ON coupon.ID = customer_coupon.CouponID WHERE "
		    		+ "coupon.END_DATE < ?) as couponselect;";
		    		preparedStatement =  conn.prepareStatement(sql);
		    		preparedStatement.setDate(1,java.sql.Date.valueOf(sdf.format(date)));
		    		ResultSet rs = preparedStatement.executeQuery();
		    		
	      while (rs != null && rs.next())
	      {    	  		    	  
	    	  
	    	  Coupon coupon = new Coupon();  
		    	coupon.setTitle(rs.getString("TITLE"));
		    	coupon.setStartDate(rs.getDate("START_DATE"));
		    	coupon.setEndDate(rs.getDate("END_DATE"));
		    	coupon.setAmount(rs.getInt("AMOUNT"));
		    	coupon.setType(CouponType.valueOf(rs.getString("TYPE")));
		    	coupon.setMessage(rs.getString("MESSAGE"));
		    	coupon.setPrice(rs.getDouble("PRICE"));
		    	coupon.setImage(rs.getString("IMAGE"));

		    	getallcoupons.add(coupon);
		    	System.out.println(getallcoupons);	
	      	}
	      
	    	
	      ConnectionPool.getInstance().returnConnection(conn);

	} 
	catch (ClassNotFoundException e) {
		e.printStackTrace();
		System.out.println(e);		
	} catch (SQLException e) {
		throw new DBException(ErrorDeclaration.COUPON_DOESNT_EXIST,"failed because"+e.getMessage());
	} catch (InterruptedException e) {
		e.printStackTrace();
		System.out.println(e);
	}	
	}
}



