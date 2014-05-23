package com.rightster.apollo.exception;

/**
 * Exception class to throw exception, when specified parameter can not be
 * passed to certain functions.
 * 
 * @author Pradeepta Swain
 */
public class InvalidParameterException extends Exception
{
    private static final long serialVersionUID = 1L;

    public InvalidParameterException(String message)
    {
        super(message);
    }
}
