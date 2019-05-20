package userInterface;


	/*
	 * Class:			InvalidBookingFeeException
	 * Description:		The class wraps all exceptions for invalid booking conditions
	 * Author:			Sebastian Wisidagama - s3769969
	 */
public class InvalidBookingFeeException extends Exception {

		public InvalidBookingFeeException(String message) {
			super(message);
		}
		
		public InvalidBookingFeeException() {
			super();
		}
		
		public String getMessage() {
			return super.getMessage();
		}
	}
