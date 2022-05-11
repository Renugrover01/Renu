package com.shine.exception;

public class CustomException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5067647608309646790L;

	public CustomException()
	{
	}

	public CustomException(String message)
	{
		super(message);
	}

	public CustomException(Throwable cause)
	{
		super(cause);
	}

	public CustomException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public CustomException(String message, Throwable cause, 
			boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
