package facade;


import java.util.Collection;
import beans.Company;
import beans.Customer;
import databaselogic.CompanyDBDAO;
import databaselogic.CustomerDBDAO;
import enum_package.ErrorDeclaration;
import exceptions.DBException;
import exceptions.InvalidLoginException;
import interfaces.CouponClientFacade;

/**
 * @author marina daich
 * admin facade implements CouponClientFacade
 * for company and customer
 *
 */
public class AdminFacade implements CouponClientFacade {

	private CompanyDBDAO companyDBDAO;
	private CustomerDBDAO customerDBDAO;
	
	
	public AdminFacade(){
		companyDBDAO = new CompanyDBDAO();
		customerDBDAO = new CustomerDBDAO();
		
	}
	 //this function checking password and user of Administrator
	 @Override
	public CouponClientFacade login(String name, String password, String clientType) throws InvalidLoginException 
	{


		 if (name.equalsIgnoreCase("Admin") && password.equals("1234")){

			 return this; 
		 }
			 
		 else
		  throw new InvalidLoginException(ErrorDeclaration.INVALID_CREDENTIAL,"Failed to Login,check your username or password");			
		 
	}
	 //Creating new company
	 public void createCompany(Company company) throws DBException
	 {
		 companyDBDAO.createCompany(company);
	
	 }
	 //Deleting company and all coupons belonging to
	 public void removeCompany(Company company) throws DBException
	 {
		 
		   companyDBDAO.removeCompany(company);	 
	
	 }
	 //update some data for company
	 public void updateCompany(Company company) throws DBException
	 {
		 companyDBDAO.updateCompany(company);
	
	 }
	 //getting company by company id
	public Company getCompany(Long id) throws DBException {
		 return companyDBDAO.getCompany(id);
	 }
	//this function returns all companies from DB
	public Collection<Company> getAllCompanies() throws DBException{
		return companyDBDAO.getAllCompanies();
		}
	//this function checks condition when we creating the customer
	 public void createCustomer(Customer customer) throws DBException
	 {
		 customerDBDAO.createCustomer(customer);
	
	 }
	 //this function checks condition while deleting customer and all his purchased coupons
	 public void removeCustomer(Customer customer) throws DBException
	 {
		
		 customerDBDAO.removeCustomer(customer);
	
	 }
	//this function checks condition while update customer 
	 public void updateCustomer(Customer customer) throws DBException
	 {
		 customerDBDAO.updateCustomer(customer);
	
	 }
	 //this function getting customer by ID
	public Customer getCustomer(Long id) throws DBException {
		 return customerDBDAO.getCustomer(id);
	 }
	//this function gets all customers from DB
	public Collection<Customer> getAllCustomer() throws DBException
	{
		return customerDBDAO.getAllCustomers();
	}
			

}
