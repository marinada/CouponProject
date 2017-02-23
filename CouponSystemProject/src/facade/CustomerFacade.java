package facade;

import java.util.Date;


import beans.Coupon;

import databaselogic.CustomerDBDAO;
import enum_package.CouponType;
import enum_package.ErrorDeclaration;
import exceptions.DBException;
import exceptions.InvalidLoginException;
import interfaces.CouponClientFacade;

/**
 * @author marina daich
 *	facade implements CouponClientFacade for customer actions
 */
public class CustomerFacade implements CouponClientFacade {

    private  CustomerDBDAO customerDBDAO;
    Long id;
	public CustomerFacade() 
	{

		customerDBDAO = new CustomerDBDAO();
	}	
//purchase coupon 	
   public void purchaseCoupon(Coupon coupon) throws DBException 
   {
	   
	   customerDBDAO.purchaseCoupon(coupon);
   }
   //get collection for all purchased coupons
   public void getAllPurchasedCoupons() throws DBException
   {
	   customerDBDAO.getCoupons();
   }
   //get all coupons by type
   public void getAllPurchasedCouponsByType(CouponType type) throws DBException
   {
	   customerDBDAO.getAllPurchasedCouponsByType(type);
   }
   //get all coupons by price
   public void getAllPurchasedCouponsByPrice(double price) throws DBException
   {
	   customerDBDAO.getAllPurchasedCouponsByPrice(price);
   }
   //get all coupons by date
   public void getAllPurchasedCouponsByDate(Date date) throws DBException
   {
	   customerDBDAO.getAllPurchasedCouponsByDate(date);
   } 
   
   //login for customer and checking credentials
	@Override
	public CouponClientFacade login(String name, String password, String clientType) throws InvalidLoginException, DBException {
		boolean result = customerDBDAO.login(name, password);
		if (result)
		{
			return this;}
		else
			  throw new InvalidLoginException(ErrorDeclaration.INVALID_CREDENTIAL,"Failed to Login,check your username or password");			

		
	}

}
