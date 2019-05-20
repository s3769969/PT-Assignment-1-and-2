package application;

/*
 * Class:			InvalidIdException
 * Description:		The class wraps all exceptions for invalid id conditions
 * Author:			Sebastian Wisidagama - s3769969
 */
public class InvalidIdException extends Exception{

	
	public InvalidIdException(String message) {
		super(message);
	}
	
	public InvalidIdException() {
		super();
	}
	
	public String getMessage() {
		return super.getMessage();
	}
}
