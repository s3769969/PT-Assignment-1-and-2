package application;


	/*
	 * Class:			InvalidSortOrderException
	 * Description:		The class wraps all exceptions for invalid sort order conditions
	 * Author:			Sebastian Wisidagama - s3769969
	 */
public class LoadDataIncompleteException extends Exception {

		public LoadDataIncompleteException(String message) {
			super(message);
		}
		
		public LoadDataIncompleteException() {
			super();
		}
		
		public String getMessage() {
			return super.getMessage();
		}
	}
