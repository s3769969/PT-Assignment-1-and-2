package application;


	/*
	 * Class:			InvalidServiceTypeException
	 * Description:		The class wraps all exceptions for invalid service type conditions
	 * Author:			Sebastian Wisidagama - s3769969
	 */
public class InvalidServiceTypeException extends Exception {

		public InvalidServiceTypeException(String message) {
			super(message);
		}
		
		public InvalidServiceTypeException() {
			super();
		}
		
		public String getMessage() {
			return super.getMessage();
		}
	}