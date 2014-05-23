package com.rightster.apollo.exception;

/**
 * Exception class to throw exception, when specified platform can not be
 * recognized.
 * 
 * @author Pradeepta Swain
 */
public class UnrecognizedPlatformException extends Exception
{
    private static final long serialVersionUID = 1L;

    public UnrecognizedPlatformException(String message)
    {
        super(message);
    }
}
