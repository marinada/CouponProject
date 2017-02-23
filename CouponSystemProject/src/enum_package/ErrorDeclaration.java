package enum_package;

/**
 * @author marina daich
 * error declaration for exceptions
 */
public enum ErrorDeclaration {

	
DB_CREATE_FAIL(1),
DUPLICATE_DATA(2),
DB_DELETE_FAIL(3),
DB_UPDATE_FAIL(4),
	
COMPANY_ALREADY_EXIST(5),
COMPANY_DOESNT_EXIST(6),

CUSTOMER_ALREADY_EXIST(7),
CUSTOMER_DOESNT_EXIST(8),

COUPON_ALREADY_EXIST(9),
COUPON_ALREADY_PURCHASED(10),
COUPON_DOESNT_EXIST(11),
INCORRECT_DATE(12),

TABLE_DOESNT_EXIST(13),
INVALID_CREDENTIAL(14),
CONNECTION_SHUTDOWN(15)
;

private int ecode;
ErrorDeclaration(int ecode){this.ecode=ecode;}
public int getEcode(){return ecode;}
}
