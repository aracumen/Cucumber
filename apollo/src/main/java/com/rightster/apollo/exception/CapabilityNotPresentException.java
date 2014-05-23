package com.rightster.apollo.exception;

/**
 * Exception class to throw exception, when specified capability is not found.
 * 
 * @author Pradeepta Swain
 */
public class CapabilityNotPresentException extends Exception
{
    private static final long serialVersionUID = 1L;

    public CapabilityNotPresentException(String message)
    {
        super(message);
    }
}
