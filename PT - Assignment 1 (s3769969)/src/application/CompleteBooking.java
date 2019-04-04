package application;

import java.util.Scanner;

public class CompleteBooking {

	private String regNoOrDate;
	private String firstName;
	private String lastName;
	private boolean validRegNoOrDate = false;
	
	public CompleteBooking() {	
	
	Scanner scanner = new Scanner(System.in);
	
	do {
		System.out.print("Enter Registration No ");
		regNoOrDate = scanner.nextLine();
		if (regNoOrDate.length() != 6 || Character.isLetter(regNoOrDate.charAt(0)) != true
				|| Character.isLetter(regNoOrDate.charAt(1)) != true || Character.isLetter(regNoOrDate.charAt(2)) != true
				|| Character.isDigit(regNoOrDate.charAt(3)) != true || Character.isDigit(regNoOrDate.charAt(4)) != true
				|| Character.isDigit(regNoOrDate.charAt(5)) != true) {
			System.out.println("Error - Booking could not be located");
		} else if(regNoOrDate != regNoOrDate) {
			System.out.println("Error - Booking could not be located");
		}else{
			validRegNoOrDate = true;
		}

	} while (validRegNoOrDate == false);
	
	do {
		System.out.println("Enter First Name ");
		firstName = scanner.nextLine();
		if (firstName.length() < 3) {
			System.out.println("Error - First Name is too short.");
		}
	} while (firstName.length() < 3);

	do {
		System.out.println("Enter Last Name ");
		lastName = scanner.nextLine();
		if (lastName.length() < 3) {
			System.out.println("Error - Last Name is too short.");
		}
	} while (lastName.length() < 3);
	
	System.out.println("Enter kilometers");
	double travelled = scanner.nextDouble();
	
	System.out.println("Thank you for riding with MiRide. We hope you enjoyed your trip.\n$" + "tripFee" + " has been dedeucted from your account.");
	
	
	scanner.close();
	}
}
