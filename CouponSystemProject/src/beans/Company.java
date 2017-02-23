package beans;
import java.util.Collection;

/**
 * @author Marina Daich
 *  Class Company structure gettin id ,name of company,password,email
 *   and collection of coupons for company as parameters from customer
 *   include getter,setter,toString for every parameter
 *
 */
public class Company {
private long id;
private String compName,password,email;
Collection<Coupon> coupons;
public Company(long id, String compName, String password, String email, Collection<Coupon> coupons) {
	super();
	this.id = id;
	this.compName = compName;
	this.password = password;
	this.email = email;
	this.coupons = coupons;
}
public Company() {

}
public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}
public String getCompName() {
	return compName;
}
public void setCompName(String compName) {
	this.compName = compName;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public Collection<Coupon> getCoupons() {
	return coupons;
}
public void setCoupons(Collection<Coupon> coupons) {
	this.coupons = coupons;
}
@Override
public String toString() {
	return "Company [id=" + id + ", compName=" + compName + ", password=" + password + ", email=" + email + ", coupons="
			+ coupons + "]";
}

}
