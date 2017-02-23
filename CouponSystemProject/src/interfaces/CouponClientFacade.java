package interfaces;

import exceptions.DBException;
import exceptions.InvalidLoginException;

/**
 * @author marina daich
 *	interface for login methods
 */
public interface CouponClientFacade {
CouponClientFacade login(String name,String password,String clientType) throws InvalidLoginException, DBException;
}
