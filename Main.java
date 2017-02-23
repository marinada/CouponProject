package main;

import java.sql.Date;

import beans.Company;
import beans.Coupon;
import beans.Customer;
import connection.CouponSystem;
import connection.DailyCouponExpirationTask;
import enum_package.CouponType;
import exceptions.DBException;
import exceptions.InvalidLoginException;
import facade.AdminFacade;
import facade.CompanyFacade;
import facade.CustomerFacade;

public class Main {

public static void main(String[] args) throws InvalidLoginException, DBException {
AdminFacade adminFacade =  (AdminFacade)CouponSystem.getInstance().login("Admin", "1234", "Admin");
//Create new company		
//	Company company = new Company(0, "INVICTA", "invicta123", "invicta1@gmail.com", null);
//	Company company1= new Company(0, "TEVA", "teva11", "teva1@gmail.com", null);			
//	Company company2 = new Company(0, "CELLCOM", "cellcom", "cellcom@gmail.com", null);
//	Company company3 = new Company(0, "GINGER", "ginger1", "ginger1@gmail.com", null);	
//	adminFacade.createCompany(company);
//	adminFacade.createCompany(company1);
//	adminFacade.createCompany(company2);
//	adminFacade.createCompany(company3);

//	

//	Company invicta = adminFacade.getCompany(23l);
//	System.out.println("company "+invicta);
//	Company teva = adminFacade.getCompany(26l);
//	Company ginger=adminFacade.getCompany(5l);
	

//
//	adminFacade.updateCompany(company1);
//	 System.out.println(company1);
//	 adminFacade.removeCompany(company1);
//System.out.println(adminFacade.getAllCompanies());
CompanyFacade companyFacade = (CompanyFacade)CouponSystem.getInstance().login("CELLCOM", "cellcom", "Company");
		
//
Coupon coupon1 = new Coupon(0,"Watch",Date.valueOf("2017-01-01")
											,Date.valueOf("2017-01-28"),
						20,CouponType.TRAVELLING,"buy invicta watch 50% sale ",250,null);
//				companyFacade.createCoupon(coupon1);		
		

		//Coupon watch = companyFacade.getCoupon(11L);
		//System.out.println(watch);
//		System.out.println(companyFacade.getCouponByType(CouponType.TRAVELLING));
		
		//companyFacade.getAllCoupon();
		
 	
Coupon coupon2 = new Coupon(0,"GINGER",Date.valueOf("2017-01-04"),Date.valueOf("2017-02-20"),10,
				CouponType.FOOD,"vegeterian mill + business lunch for 30%",99,null);
//				companyFacade.createCoupon(coupon2);	
				
Coupon coupon3 = new Coupon(0,"TEVA",Date.valueOf("2017-01-04"),Date.valueOf("2017-02-20"),10,
						CouponType.HEALTH,"new vitamins on sale",200,null);
//						companyFacade.createCoupon(coupon3);

Coupon coupon4 = new Coupon(0,"PARTNER",Date.valueOf("2017-02-04"),Date.valueOf("2017-02-20"),10,
		CouponType.CELULAR,"router for 16X36 ",84,null);
//		companyFacade.createCoupon(coupon4);

//		System.out.println(companyFacade.getAllCoupon());
//		companyFacade.updateCoupon(coupon1);
//		companyFacade.removeCoupon(coupon1);			
		 
		Customer cust = new Customer(0,"six","six6@gmail.com");
		Customer cust1 = new Customer(0,"two","two2@gmail.com");
		Customer cust2 = new Customer(0,"three","three3@gmail.com");
		Customer cust3 = new Customer(0,"four","four4@gmail.com");
		Customer cust4 = new Customer(0,"five","five5@gmail.com");

//		adminFacade.createCustomer(cust);
//		adminFacade.createCustomer(cust1);
//		adminFacade.createCustomer(cust2);
//		adminFacade.createCustomer(cust3);
//		adminFacade.createCustomer(cust4);
//	    adminFacade.removeCustomer(cust1);		
		CustomerFacade customerFacade =  (CustomerFacade)CouponSystem.getInstance().login("two", "two2@gmail.com", "Customer");	

		customerFacade.purchaseCoupon(coupon3);
//		customerFacade.getAllPurchasedCouponsByType(coupon1);
//		customerFacade.getAllPurchasedCoupons();
//		customerFacade.getAllPurchasedCouponsByPrice(1000);
		
		
//		DailyCouponExpirationTask.startTask();	 
		
}
}




