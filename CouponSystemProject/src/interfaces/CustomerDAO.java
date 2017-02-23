package interfaces;
import java.util.Collection;

import beans.Coupon;
import beans.Customer;
import exceptions.DBException;
/**
 * @author marina daich
 * customer DAO interface with all methods for customer actions
 */
public interface CustomerDAO {
	 void createCustomer(Customer custName) throws DBException;
	 void removeCustomer(Customer custName) throws DBException;
	 void updateCustomer(Customer custName) throws DBException;
	 Customer getCustomer(Long id) throws DBException;
	 Collection<Customer> getAllCustomers() throws DBException;
	 Collection<Coupon> getCoupons() throws DBException;
	 void purchaseCoupon(Coupon coupon) throws DBException;
	 boolean login(String custName,String password) throws DBException;
}
