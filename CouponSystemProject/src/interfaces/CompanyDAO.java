package interfaces;
import java.util.Collection;

import beans.Company;
import beans.Coupon;
import exceptions.DBException;

/**
 * @author marina daich
 * company DAO interface with all methods for company actions
 */
public interface CompanyDAO {
 void createCompany(Company compName) throws DBException;
 void removeCompany(Company compName) throws DBException;
 void updateCompany(Company compName) throws DBException;
 Company getCompany (Long id) throws DBException;
 Collection<Company> getAllCompanies() throws DBException;
 boolean login(String compName,String password) throws DBException;
 Collection<Coupon> getCoupons() throws DBException;

}
