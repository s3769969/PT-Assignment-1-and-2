package application;

/*
 * Class:			InvalidPassCapException
 * Description:		The class wraps all exceptions for invalid passenger capacity conditions
 * Author:			Sebastian Wisidagama - s3769969
 */
public class InvalidPassCapException extends Exception {

	public InvalidPassCapException(String message) {
		super(message);
	}
	
	public InvalidPassCapException() {
		super();
	}
	
	public String getMessage() {
		return super.getMessage();
	}
}
