package beans;

import java.util.Date;

import enum_package.CouponType;

/**
 * @author marina daich
 *	Class Coupon structure getting id ,title of coupon,start date,end date,amount,
 *	COUPON TYPE,message,price 
 *  and image  for coupon 
 *  include getter,setter,toString for every parameter
 */
public class Coupon {
	
private long id;
private String title,message,image;
private Date startDate;
private Date endDate;
private int amount;
private CouponType type;
private double price;


public Coupon(long id, String title, Date startDate, Date endDate, int amount,
		CouponType type, String message, double price ,String image) {
	super();
	this.id = id;
	this.title = title;
	this.message = message;
	this.image = image;
	this.startDate =startDate;
	this.endDate = endDate;
	this.amount = amount;
	this.type = type;
	this.price = price;
}

public Coupon(){}
public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public String getImage() {
	return image;
}
public void setImage(String image) {
	this.image = image;
}
public Date getStartDate(){

	return startDate;
}
public void setStartDate(Date startDate) {
	this.startDate = startDate;
	
}
public Date getEndDate() {
	return endDate;
}
public void setEndDate(Date endDate) {
	this.endDate = endDate;
}
public int getAmount() {
	return amount;
}
public void setAmount(int amount) {
	this.amount = amount;
}
public CouponType getType() {
	return type;
}
public void setType(CouponType type) {
	this.type = type;
}
public double getPrice() {
	return price;
}
public void setPrice(double price) {
	this.price = price;
}
@Override
public String toString() {
	return "Coupon [id=" + id + ", title=" + title + ", message=" + message + ", image=" + image + ", startDate="
			+ startDate + ", endDate=" + endDate + ", amount=" + amount + ", type=" + type + ", price=" + price + "]";
}

}
