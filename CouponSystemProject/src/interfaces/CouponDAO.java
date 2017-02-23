package interfaces;
import java.util.Collection;

import beans.Coupon;
import enum_package.CouponType;
import exceptions.DBException;
/**
 * @author marina daich
 * coupon DAO interface with all methods for coupon actions
 */
public interface CouponDAO {
	 void createCoupon(Coupon coupon) throws DBException;
	 void removeCoupon(Coupon coupon) throws DBException;
	 void updateCoupon(Coupon coupon) throws DBException;
	 Coupon getCoupon(Long id) throws DBException;
	 Collection<Coupon> getCouponByType(CouponType type) throws DBException;
	 Collection<Coupon> getAllCoupons() throws DBException;
}
