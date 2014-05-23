package com.rightster.apollo.utils;

/**
 * Utility class to get exception messages from objects and conver it to string,
 * so that we can log it in logger.
 * 
 * @author Pradeepta Swain
 */
public class StackTraceUtils
{
    public static String stackTraceToString(Throwable e)
    {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : e.getStackTrace())
        {
            sb.append(element.toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}
