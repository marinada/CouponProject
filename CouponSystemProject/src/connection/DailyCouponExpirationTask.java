package connection;

import java.util.Calendar;

import java.util.Collection;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;

import beans.Coupon;
import databaselogic.CouponDBDAO;
import enum_package.ErrorDeclaration;
import exceptions.DBException;
import exceptions.ShutdownException;


/**
 * @author marina daich
 *	DailyCouponExpirationTask = delete all expired coupons ones a day
 */
public class DailyCouponExpirationTask extends TimerTask implements Runnable {
	private boolean runningTask;
	Thread thread ;
	private static long sleepTime =1000*24*60*60;

			
	CouponDBDAO coupon = new CouponDBDAO();
//constractor
	public DailyCouponExpirationTask(){
		runningTask=true;
	}
	
//	 public static void startTask() throws InterruptedException{
//		 
//		 	Timer timer = new Timer();
//		    Calendar date = Calendar.getInstance();
//
//			    date.set(Calendar.HOUR, 0);
//			    date.set(Calendar.MINUTE,0);
//			    date.set(Calendar.SECOND, 0);
//			    date.set(Calendar.MILLISECOND, 0);
//			    
//			    DailyCouponExpirationTask task = new DailyCouponExpirationTask();
//			    
//			    timer.schedule(task,date.getTime(),sleepTime);
//			  
//
//	        }
	//stop current thread
	public void StopTask(){	
	Thread.currentThread().isDaemon();
	Thread.currentThread().interrupt();
	runningTask=false;}
	
	
	
	@Override
		public void run() {
       
        while(runningTask )
				{

				try {
				
				Thread.sleep(sleepTime);
				Collection<Coupon> c=coupon.getAllCoupons();
				
			    coupon.removeNotValidCoupon(c);
	    
				}
				catch (DBException | InterruptedException e) {
					try {
						throw new ShutdownException(ErrorDeclaration.CONNECTION_SHUTDOWN,"failed because connection is interrupted");
					} catch (ShutdownException e1) {
						e1.printStackTrace();
					}

				}
				finally{
					runningTask=false;
					StopTask();
					
				} 
				
		}}}
	
	


