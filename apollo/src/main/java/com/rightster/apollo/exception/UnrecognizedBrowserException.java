package com.rightster.apollo.exception;

/**
 * Exception class to throw exception, when specified browser can not be
 * recognized.
 * 
 * @author Pradeepta Swain
 */
public class UnrecognizedBrowserException extends Exception
{
    private static final long serialVersionUID = 1L;

    public UnrecognizedBrowserException(String message)
    {
        super(message);
    }
}
