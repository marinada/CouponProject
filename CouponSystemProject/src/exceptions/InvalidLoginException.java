package exceptions;

import enum_package.ErrorDeclaration;

/**
 * @author marina daich
 *	invalid login exception
 */
public class InvalidLoginException extends Exception
{

    // Constructor that accepts error and  message
    public InvalidLoginException(ErrorDeclaration e,String message)
    {
       super(e+message);
    }	

}