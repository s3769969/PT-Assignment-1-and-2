package application;

/*
 * Class:			InvalidRefreshmentsException
 * Description:		The class wraps all exceptions for invalid refreshments conditions
 * Author:			Sebastian Wisidagama - s3769969
 */
public class InvalidRefreshmentsException extends Exception{

	public InvalidRefreshmentsException(String message) {
		super(message);
	}
	
	public InvalidRefreshmentsException() {
		super();
	}
	
	public String getMessage() {
		return super.getMessage();
	}
}
