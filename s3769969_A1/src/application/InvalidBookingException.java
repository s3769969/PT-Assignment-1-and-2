package application;

/*
 * Class:			InvalidBookingException
 * Description:		The class wraps all exceptions for invalid booking conditions
 * Author:			Sebastian Wisidagama - s3769969
 */
public class InvalidBookingException extends Exception{
	
	public InvalidBookingException(String message) {
		super(message);
	}
	
	public InvalidBookingException() {
		super();
	}
	
	public String getMessage() {
		return super.getMessage();
	}
}
