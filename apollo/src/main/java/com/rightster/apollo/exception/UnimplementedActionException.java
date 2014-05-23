package com.rightster.apollo.exception;

/**
 * Exception class to throw exception, when specified action is not implemented
 * in framework.
 * 
 * @author Pradeepta Swain
 */
public class UnimplementedActionException extends Exception
{
    private static final long serialVersionUID = 1L;

    public UnimplementedActionException(String message)
    {
        super(message);
    }
}
