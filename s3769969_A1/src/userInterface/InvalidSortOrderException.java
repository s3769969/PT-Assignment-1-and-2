package userInterface;


	/*
	 * Class:			InvalidSortOrderException
	 * Description:		The class wraps all exceptions for invalid sort order conditions
	 * Author:			Sebastian Wisidagama - s3769969
	 */
public class InvalidSortOrderException extends Exception {

		public InvalidSortOrderException(String message) {
			super(message);
		}
		
		public InvalidSortOrderException() {
			super();
		}
		
		public String getMessage() {
			return super.getMessage();
		}
	}
