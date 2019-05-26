package application;

/*
 * Class:			InvalidRegException
 * Description:		The class wraps all exceptions for invalid registration number conditions
 * Author:			Sebastian Wisidagama - s3769969
 */
public class InvalidRegException extends Exception {

	public InvalidRegException(String message) {
		super(message);
	}
	
	public InvalidRegException() {
		super();
	}
	
	public String getMessage() {
		return super.getMessage();
	}
}
