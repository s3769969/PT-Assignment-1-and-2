package application;

/*
 * Class:			InvalidDateException
 * Description:		The class wraps all exceptions for invalid date conditions
 * Author:			Sebastian Wisidagama - s3769969
 */
public class InvalidDateException extends InvalidBookingException{

	
	public InvalidDateException(String message) {
		super(message);
	}
	
	public InvalidDateException() {
		super();
	}
	
	public String getMessage() {
		return super.getMessage();
	}
}
