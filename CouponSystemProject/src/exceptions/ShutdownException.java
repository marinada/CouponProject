package exceptions;

import enum_package.ErrorDeclaration;


/**
 * @author marina daich
 * shutdown exception
 */
public class ShutdownException extends Exception{
    // Constructor that accepts error and  message
    public ShutdownException(ErrorDeclaration e,String message)
    {
    	
       super(e+message);
    }	

}
