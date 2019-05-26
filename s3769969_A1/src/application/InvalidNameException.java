package application;


/*
 * Class:			InvalidNameException
 * Description:		The class wraps all exceptions for invalid name conditions
 * Author:			Sebastian Wisidagama - s3769969
 */
public class InvalidNameException extends Exception {

	public InvalidNameException(String message) {
		super(message);
	}
	
	public InvalidNameException() {
		super();
	}
	
	public String getMessage() {
		return super.getMessage();
	}
}
