package connection;

import java.sql.Connection;
import java.sql.SQLException;

import enum_package.ErrorDeclaration;
import exceptions.DBException;
import exceptions.InvalidLoginException;
import exceptions.ShutdownException;
import facade.AdminFacade;
import facade.CompanyFacade;
import facade.CustomerFacade;
import interfaces.CouponClientFacade;

/**
 * @author marina daich
 * coupon system allows to all subscriptions to do actions according 
 * identity
 *
 */
public class CouponSystem {
	private static CouponSystem instance =null;
	
	ConnectionPool pool;
	Connection conn = null;
	private CouponSystem(){
		
	}
	//getting instance
	public static synchronized CouponSystem getInstance(){
		if (instance==null)
			instance=new CouponSystem();
		return instance;
	}
	//login according client type
	public CouponClientFacade login(String name, String pwd, String type) throws InvalidLoginException, DBException {
			
			CouponClientFacade result = null;
			switch (type)
			{
				case "Company":					
					result = new CompanyFacade();
					break;
				case "Customer":					
					result = new CustomerFacade();
					break;
				case "Admin":
					result = new AdminFacade();
					
					break;				
			}
			
			CouponClientFacade loginResult = result.login(name, pwd, type);
			
			return loginResult;
			
				
		}
	
	//close connection and shutdown all tasks 
	public void shutdown() throws SQLException, ShutdownException{
		pool.CloseAllConnections();
		DailyCouponExpirationTask s = new DailyCouponExpirationTask();
		if (s.thread.isInterrupted())
			throw new ShutdownException(ErrorDeclaration.CONNECTION_SHUTDOWN,"failed because connection is shutdowned");
		
			s.StopTask();
	}
	}

