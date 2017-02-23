package facade;
import java.util.Collection;

import beans.Coupon;
import databaselogic.CompanyDBDAO;
import databaselogic.CouponDBDAO;
import enum_package.CouponType;
import enum_package.ErrorDeclaration;
import exceptions.DBException;
import exceptions.InvalidLoginException;
import interfaces.CouponClientFacade;

/**
 * @author marina daich
 * this class implements CouponClientFacade for all 
 * company actions 
 */
public class CompanyFacade implements CouponClientFacade {

		 // DAO
		 CompanyDBDAO companyDBDAO= null;
		 CouponDBDAO  couponDBDAO = null;
	
public CompanyFacade()
{
companyDBDAO=new CompanyDBDAO();
couponDBDAO=new CouponDBDAO();
}
//create create coupon for specific company
public void createCoupon(Coupon coupon) throws DBException
{	
	couponDBDAO.createCoupon(coupon);
	companyDBDAO.addCouponToCompany(coupon);
}

//remove coupon
public void removeCoupon(Coupon coupon) throws DBException
{	
	couponDBDAO.removeCoupon(coupon);
	
}
//update coupon
public void updateCoupon(Coupon coupon) throws DBException
{
	couponDBDAO.updateCoupon(coupon);
}
//get coupon by id
public Coupon getCoupon(Long id) throws DBException
{
	return couponDBDAO.getCoupon(id);
}
//get all coupons
public Collection<Coupon> getAllCoupon() throws DBException
{
	return couponDBDAO.getAllCoupons();
}
//get coupon by type
public Collection<Coupon> getCouponByType(CouponType type) throws DBException
{
	return couponDBDAO.getCouponByType(type);
}
//login company
	@Override
	public CouponClientFacade login(String name, String password, String clientType) throws DBException, InvalidLoginException 
	{
		boolean result = companyDBDAO.login(name, password);
		if (result)
		{	
		
			return this;
		}
		else
			  throw new InvalidLoginException(ErrorDeclaration.INVALID_CREDENTIAL,"Failed to Login,check your username or password");			

		
	}

}
