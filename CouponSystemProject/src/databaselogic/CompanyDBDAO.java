package databaselogic;
import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import beans.Company;
import beans.Coupon;
import connection.ConnectionPool;
import enum_package.CouponType;
import enum_package.ErrorDeclaration;
import exceptions.DBException;
import interfaces.CompanyDAO;


/**
 * @author marina daich
 * implements interface CompanyDaO 
 * all actions about company
 */
public class CompanyDBDAO implements CompanyDAO {
	
	ConnectionPool pool;
	//logged_in_company  get 0 before getting incremental id after inserting data in Company table
	private long logged_in_company = 0;
	
	public CompanyDBDAO(){	
	try {
	//getting instance for DB connection	
		pool = ConnectionPool.getInstance();
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	} catch (SQLException e) {
		e.printStackTrace();
	}

}
	//creating company
	@Override
	public void createCompany(Company company) throws DBException{
		PreparedStatement preparedStatement = null;
		Connection conn = null;
		try{

			//getting connection to DB
			conn = pool.getConnection();
			//key for generating id
			long key = -1;

			String sql = "INSERT INTO Company  (COMP_NAME,PASSWORD,EMAIL) VALUES (?,?,?)";
		      				      
			preparedStatement=conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);	
			preparedStatement.setString(1, company.getCompName());
			preparedStatement.setString(2, company.getPassword());
			preparedStatement.setString(3, company.getEmail());
			preparedStatement.executeUpdate();
		      ResultSet rs = preparedStatement.getGeneratedKeys();
		    //checks if our insert was successful
		      if (rs != null && rs.next()) {
		    //getting generated id	  
	          key = rs.getLong(1);	
	          company.setId(key);
	          
	          
	          ConnectionPool.getInstance().returnConnection(conn);
		      }  
		}
		catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (SQLException e) {
			throw new DBException(ErrorDeclaration.COMPANY_ALREADY_EXIST,"failed because"+e.getMessage());
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}     
	
	}
//removing Company from DB 
	@Override
	public void removeCompany(Company company) throws DBException {
		PreparedStatement preparedStatement = null;
		try 
		{
			Connection conn = null;
			conn = pool.getConnection();


			String sql = "DELETE Company,Company_Coupon,Customer_Coupon "
					+ "FROM Company LEFT JOIN Company_Coupon "
					+ "ON Company_Coupon.CompanyID=Company.ID "
					+ "LEFT JOIN Customer_Coupon ON  Customer_Coupon.CouponID = Company_Coupon.CouponID "
					+ "WHERE Company.COMP_NAME = ?";
		      				      
			preparedStatement=conn.prepareStatement(sql);	
			preparedStatement.setString(1, company.getCompName());
			System.out.println(company.getCompName());
			preparedStatement.executeUpdate();
     //returning connection
	      ConnectionPool.getInstance().returnConnection(conn);

		     
	     } catch (ClassNotFoundException e) {

			e.printStackTrace();
		 } catch (SQLException e) {
			 throw new DBException(ErrorDeclaration.COMPANY_DOESNT_EXIST,"failed because"+e.getMessage());
		 } catch (InterruptedException e) {

			e.printStackTrace();
		}

	}
//update table Company if any changes 
	@Override
	public void updateCompany(Company company) throws DBException 
	{
		PreparedStatement preparedStatement = null;
		try
		{
		
		Connection conn = null;
		conn = pool.getConnection();
		String sql = "UPDATE Company SET PASSWORD =?,EMAIL=? WHERE COMP_NAME =?";
		
		preparedStatement=conn.prepareStatement(sql);     
		
		
		preparedStatement.setString(1,company.getPassword());		
		preparedStatement.setString(2, company.getEmail());	
		preparedStatement.setString(3,company.getCompName());
		
		
		preparedStatement.executeUpdate(); 
	   
		ConnectionPool.getInstance().returnConnection(conn);
		}
		catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (SQLException e) {
			 throw new DBException(ErrorDeclaration.COMPANY_DOESNT_EXIST,"failed because"+e.getMessage());
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}

	}
//brinning company with id
	@Override
	public Company getCompany(Long id) throws DBException {	
		Company comp = null;
		PreparedStatement preparedStatement = null;
		try 
		{
			
			Connection conn = null;
			conn = pool.getConnection();
			
		      String sql = "SELECT * FROM Company WHERE ID=?";
		      preparedStatement = conn.prepareStatement(sql);
		      preparedStatement.setLong(1,id);
		      
		      ResultSet rs = preparedStatement.executeQuery();
		      while (rs != null && rs.next())
		      {
		    	 String name = rs.getString("COMP_NAME");
		    	  
		    	 String psswrd =  rs.getString("PASSWORD");
		    	
		    	 String email =  rs.getString("EMAIL");
		    	
		    	 comp = new Company (id,name,psswrd,email,null);
		      }
		     
		      
		      ConnectionPool.getInstance().returnConnection(conn);
		    
		      return comp;

		}
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
			System.out.println(e);
			
		} catch (SQLException e) {
			 throw new DBException(ErrorDeclaration.COMPANY_DOESNT_EXIST,"failed because"+e.getMessage());
		} catch (InterruptedException e) {

			e.printStackTrace();
			System.out.println(e);
		}
		
		return comp;
	}
//get Collection of all companies
	@Override
	public Collection<Company> getAllCompanies() throws DBException {
		Collection<Company> companyarr = new ArrayList<Company>();
		PreparedStatement preparedStatement = null;
		Company company = null;
		try
		{
		Connection conn = null;		
		conn = pool.getConnection();

	    		  String sql = "SELECT * FROM Company";
	    		  preparedStatement=conn.prepareStatement(sql);
	    		  
	    		  ResultSet rs =  preparedStatement.executeQuery();

	      
	      while (rs != null && rs.next())
	      {
	    	  company= new Company();
	    	  company.setId(rs.getLong("ID"));
	    	  company.setCompName(rs.getString("COMP_NAME"));
	    	  company.setPassword(rs.getString("PASSWORD"));
	    	  company.setEmail(rs.getString("EMAIL"));	 
	    	  
		      companyarr.add(company);
		     
	      	}
	      
	      ConnectionPool.getInstance().returnConnection(conn);	      
	      return companyarr;
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
	
	return companyarr;
	}
//login and checking if company is in the DB
	@Override
	public boolean login(String compName, String password) throws DBException {
		boolean login_result = false;	
		PreparedStatement preparedStatement = null;
		try 
		{			
			Connection conn = null;
			conn = pool.getConnection();
			
			String sql = "SELECT * FROM Company WHERE COMP_NAME = '"+compName+"' AND PASSWORD = '"+password+"'";

			preparedStatement = conn.prepareStatement(sql);
			
//			preparedStatement.setString(1,compName);
//			preparedStatement.setString(2,password);
			
		      ResultSet rs =  preparedStatement.executeQuery(sql);
		      
		      while (rs!= null && rs.next())
		      {
		    	  logged_in_company = rs.getLong("ID");

		    	  login_result = true;	    			    	  
		      }
	      
		      ConnectionPool.getInstance().returnConnection(conn);
		      
		      return login_result;
		     
	} catch (ClassNotFoundException e) {
					e.printStackTrace();
		} catch (SQLException e) {
			throw new DBException(ErrorDeclaration.INVALID_CREDENTIAL,"failed because"+e.getMessage());
		} catch (InterruptedException e) {
					e.printStackTrace();
		}
	

		return login_result;
	}
//getting  collection of coupons belonging to specific company
	@Override
	public Collection<Coupon> getCoupons() throws DBException {
		Collection<Coupon> getCoupons = new ArrayList<Coupon>();	

		PreparedStatement preparedStatement = null;
		Coupon coupon = null;
		try 
		{		
			
			Connection conn = null;
			conn = pool.getConnection();
			
			String sql ="SELECT * FROM Company_Coupon WHERE CompanyID = ?";
			preparedStatement = conn.prepareStatement(sql)  ;
			preparedStatement.setLong(1, logged_in_company);
		    ResultSet rs =  preparedStatement.executeQuery();
		      while (rs != null && rs.next())
		      {	
		    	coupon = new Coupon();  
		    	coupon.setTitle(rs.getString("TITLE"));
		    	coupon.setStartDate(rs.getDate("START_DATE"));
		    	coupon.setEndDate(rs.getDate("END_DATE"));
		    	coupon.setAmount(rs.getInt("AMOUNT"));
		    	coupon.setType(CouponType.valueOf(rs.getString("TYPE")));
		    	coupon.setMessage(rs.getString("MESSAGE"));
		    	coupon.setPrice(rs.getDouble("PRICE"));
		    	coupon.setImage(rs.getString("IMAGE"));

		      getCoupons.add(coupon);
	
		      }
		      
		      ConnectionPool.getInstance().returnConnection(conn);
		      
		      return getCoupons;
		
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
} catch (SQLException e) {
	 throw new DBException(ErrorDeclaration.TABLE_DOESNT_EXIST,"failed because"+e.getMessage());
} catch (InterruptedException e) {
		e.printStackTrace();
}
return getCoupons;
}
	//adding new coupon to company
	public void addCouponToCompany(Coupon coupon) throws DBException
	{
		PreparedStatement preparedStatement = null;
	try
	{	

		Connection conn = null;
		conn = pool.getConnection();
		

		String sql = "INSERT INTO Company_Coupon  (CompanyId,CouponId)  "
				+ "VALUES (?,(select ID from Coupon where title=?))";
		preparedStatement =  conn.prepareStatement(sql);
		
		preparedStatement.setLong(1, logged_in_company);
		preparedStatement.setString(2, coupon.getTitle());
		preparedStatement.executeUpdate();
	      ConnectionPool.getInstance().returnConnection(conn);
		
		
	}
	catch (ClassNotFoundException e) {
		e.printStackTrace();

		
	} catch (SQLException e) {
		 throw new DBException(ErrorDeclaration.DUPLICATE_DATA,"failed because"+e.getMessage());
	
	} catch (InterruptedException e) {
		e.printStackTrace();

	}
	
	}	



}	