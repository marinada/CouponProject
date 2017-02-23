package exceptions;

import enum_package.ErrorDeclaration;

/**
 * @author marina daich
 * data base exception
 */
public class DBException extends Exception
	{
		// Parameterless Constructor
	    public DBException() 
	    {
	    
	    }
	    // Constructor that accepts a message
	    public DBException(ErrorDeclaration e, String message)
	    {
	       super(e+message);
	    }	

	}

