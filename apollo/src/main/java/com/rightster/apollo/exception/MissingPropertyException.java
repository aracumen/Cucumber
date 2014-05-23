package com.rightster.apollo.exception;

/**
 * Exception class to throw exception, when specified key can not be retrieved
 * from property files.
 * 
 * @author Pradeepta Swain
 */
public class MissingPropertyException extends Exception
{
    private static final long serialVersionUID = 1L;

    public MissingPropertyException(String message)
    {
        super(message);
    }
}
