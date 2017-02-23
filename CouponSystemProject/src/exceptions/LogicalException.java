package exceptions;

import enum_package.ErrorDeclaration;
/**
 * @author marina daich
 * logical  exception
 */
public class LogicalException extends Exception{
	
    public LogicalException(ErrorDeclaration e,String message)
    {
       super(e+message);
    }	
}
