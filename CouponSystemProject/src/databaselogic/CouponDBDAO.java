package databaselogic;
import java.sql.Connection;


import java.sql.PreparedStatement;
import java.util.Date;


import beans.Coupon;
import connection.ConnectionPool;
import enum_package.CouponType;
import enum_package.ErrorDeclaration;
import exceptions.DBException;
import interfaces.CouponDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author marina daich
 * class implements CouponDAO interface
 * all actions ith specific coupon
 *
 */
public class CouponDBDAO implements CouponDAO {
	
	ConnectionPool pool;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	
	public CouponDBDAO(){	
	try {
		pool = ConnectionPool.getInstance();
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

}
//creating coupon
	@Override
	public void createCoupon(Coupon coupon) throws DBException {			
		PreparedStatement preparedStatement = null;
		Date current_date = new java.util.Date();
		try 
		{			
			Connection conn = null;
			conn = pool.getConnection();
			long key = -1;
			
			String sql = "INSERT INTO Coupon (TITLE,START_DATE,END_DATE,AMOUNT,TYPE,MESSAGE,PRICE,IMAGE) "
						+ "VALUES (?,?,?,?,?,?,?,?)";

			preparedStatement=conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS); 

			preparedStatement.setString(1,coupon.getTitle());
			preparedStatement.setDate(2,java.sql.Date.valueOf(sdf.format(coupon.getStartDate())));
			preparedStatement.setDate(3,java.sql.Date.valueOf(sdf.format(coupon.getEndDate())));
			preparedStatement.setInt(4,coupon.getAmount());
			preparedStatement.setString(5,coupon.getType().toString());
			preparedStatement.setString(6,coupon.getMessage());
			preparedStatement.setDouble(7,coupon.getPrice());
			preparedStatement.setString(8,coupon.getImage());
			
			
if(java.sql.Date.valueOf(sdf.format(coupon.getStartDate())).before(java.sql.Date.valueOf(sdf.format(coupon.getEndDate())))
		 && java.sql.Date.valueOf(sdf.format(coupon.getEndDate())).after(java.sql.Date.valueOf(sdf.format(current_date))))

			preparedStatement.executeUpdate();
else
	 throw new DBException(ErrorDeclaration.INCORRECT_DATE,"failed because date is wrong");

		      ResultSet rs = preparedStatement.getGeneratedKeys();
		      if (rs != null && rs.next()) {
		  
	          key = rs.getLong(1);	
	          coupon.setId(key);	          
	          }      	      
		      
		      ConnectionPool.getInstance().returnConnection(conn);
		      
	} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			 throw new DBException(ErrorDeclaration.COUPON_ALREADY_EXIST,"failed because"+e.getMessage());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} 	
	}
//remove coupon from all tables
	@Override
	public void removeCoupon(Coupon coupon) throws DBException {
		PreparedStatement preparedStatement = null;
		try 
		{
			
			Connection conn = null;
			conn = pool.getConnection();
			
			String sql = "DELETE Coupon,Company_Coupon,Customer_Coupon "
					+ "FROM Coupon LEFT JOIN Company_Coupon "
					+ "ON Company_Coupon.CouponID=Coupon.ID "
					+ "LEFT JOIN Customer_Coupon ON  Customer_Coupon.CouponID = Coupon.ID "
					+ "WHERE Coupon.TITLE = ?";
		   
		     preparedStatement=conn.prepareStatement(sql);
		     preparedStatement.setString(1,coupon.getTitle());
			 preparedStatement.executeUpdate();
	      
		      ConnectionPool.getInstance().returnConnection(conn);
		     
	} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			throw new DBException(ErrorDeclaration.COUPON_DOESNT_EXIST,"failed because"+e.getMessage());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
//update coupon
	@Override
	public void updateCoupon(Coupon coupon) throws DBException {
		PreparedStatement preparedStatement = null;
		try
		{
		
		Connection conn = null;
		conn = pool.getConnection();

		
		String sql = "UPDATE Coupon " +
                "SET  AMOUNT =?,START_DATE=?,END_DATE=?,PRICE=? WHERE TITLE = ?";
		
		
		 preparedStatement=conn.prepareStatement(sql);
		 preparedStatement.setInt(1,coupon.getAmount());
		 preparedStatement.setDate(2,java.sql.Date.valueOf(sdf.format(coupon.getStartDate())));
	     preparedStatement.setDate(3,java.sql.Date.valueOf(sdf.format(coupon.getEndDate())));	
	     preparedStatement.setDouble(4,coupon.getPrice());
	     preparedStatement.setString(5,coupon.getTitle());
	     preparedStatement.executeUpdate();
	
		ConnectionPool.getInstance().returnConnection(conn);
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			throw new DBException(ErrorDeclaration.DB_UPDATE_FAIL,"failed because"+e.getMessage());

		} catch (InterruptedException e) {

			e.printStackTrace();
		}		
	}
//getting coupon by id
	@Override
	public Coupon getCoupon(Long id) throws DBException {
		Coupon coupon = null;
		PreparedStatement preparedStatement = null;
		try 
		{
			
			Connection conn = null;
			conn = pool.getConnection();
			
			String sql = "SELECT * FROM Coupon WHERE ID=?";
		      preparedStatement = conn.prepareStatement(sql);
		      preparedStatement.setLong(1, id);
		      ResultSet rs = preparedStatement.executeQuery();
		      while (rs != null && rs.next())
		      {
	    	  
		    	  	String title = rs.getString("TITLE");
		    	  	Date start_date = rs.getDate("START_DATE");
		    	  	Date end_date = rs.getDate("END_DATE");
		    	  	int amount = rs.getInt("AMOUNT");
		    	  	CouponType type = CouponType.valueOf(rs.getString("TYPE"));
		    	  	String message = rs.getString("MESSAGE");
		    	  	Double price = rs.getDouble("PRICE");
		    	  	String image = rs.getString("IMAGE");
		    	  	
		    	  	coupon = new Coupon(id,title,start_date,end_date,amount,type,message,price,image);
	    	 
		      }
		      
		      ConnectionPool.getInstance().returnConnection(conn);

		      return coupon;
	

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			throw new DBException(ErrorDeclaration.COMPANY_DOESNT_EXIST,"failed because"+e.getMessage());
		} catch (InterruptedException e) {
			e.printStackTrace();
			
		}
		
		return coupon;
	}
//getting coupon by Type
	@Override
	public Collection<Coupon> getCouponByType(CouponType type) throws DBException {
		 PreparedStatement preparedStatement = null;
		 ArrayList<Coupon> getCouponByType = new ArrayList<Coupon>();
		try{
		
		
		Connection conn = null;
		conn = pool.getConnection();
            
	    		  String sql = "SELECT * FROM Coupon where TYPE = ?";

	    		  preparedStatement = conn.prepareStatement(sql);
	    		  preparedStatement.setString(1,type.toString());	    		  
	    		  preparedStatement.executeQuery();
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

	      getCouponByType.add(coupon);

	      }
	      
	      
	      ConnectionPool.getInstance().returnConnection(conn);
	      
	      return getCouponByType;

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
	
	return getCouponByType;
	}
//getting all coupons
	@Override
	public Collection<Coupon> getAllCoupons() throws DBException {
		PreparedStatement preparedStatement = null;
		 Collection<Coupon> getCoupon = new ArrayList<Coupon>();
		try{
		
		
		Connection conn = null;
		conn = pool.getConnection();
           
	    		  String sql = "SELECT * FROM Coupon";

	    		  preparedStatement = conn.prepareStatement(sql);
	    	    		  
	    		  preparedStatement.executeQuery();
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

	    	getCoupon.add(coupon);
	    	

	      }
	      
	      
	      ConnectionPool.getInstance().returnConnection(conn);
	      System.out.println(getCoupon);
	      return getCoupon;

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
	
	return getCoupon;
	}
	//remove all not valid coupons
	public void removeNotValidCoupon(Collection<Coupon> c) throws DBException {
		PreparedStatement preparedStatement = null;
		
		try 
		{
			
			Connection conn = null;
			conn = pool.getConnection();
			//getting current date
			Date current_date =   new java.util.Date();
			java.sql.Date new_current_date = java.sql.Date.valueOf(sdf.format(current_date));

			String sql1 = "select count(*) As total FROM Coupon where Coupon.End_Date < ?";
			preparedStatement=conn.prepareStatement(sql1);
			preparedStatement.setDate(1,new_current_date );
			ResultSet rs = preparedStatement.executeQuery();
			
			
			if(rs != null && rs.next())
			{
				int total = rs.getInt("total");
				System.out.println(total);

				if(total>0){
					
			String sql = "DELETE Coupon,Company_Coupon,Customer_Coupon "
					+ "FROM Coupon LEFT JOIN Company_Coupon "
					+ "ON Company_Coupon.CouponID=Coupon.ID "
					+ "LEFT JOIN Customer_Coupon ON  Customer_Coupon.CouponID = Coupon.ID "
					+ "WHERE Coupon.End_Date < ? ";
			
			
		     preparedStatement=conn.prepareStatement(sql);
		     preparedStatement.setDate(1,new_current_date );
		     preparedStatement.executeUpdate();		    	 
			}
				ConnectionPool.getInstance().returnConnection(conn);
			}
		      
		     
	} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			throw new DBException(ErrorDeclaration.DB_DELETE_FAIL,"failed because "+e.getMessage());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
	}



