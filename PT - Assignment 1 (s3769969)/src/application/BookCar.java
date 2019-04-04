package application;

import java.util.Scanner;
import application.Car;
import utilities.DateTime;
import application.Booking;

public class BookCar {

	private boolean validDate = false;
	private boolean validNumPass = false;
	private String firstName;
	private String lastName;
	private int numPassengers;
	private Car car;
	private String id;
	

	public BookCar()
	{
		Scanner scanner = new Scanner(System.in);
		DateTime required = new DateTime();
		String reqFormatDate = required.getFormattedDate();
		
		do {
			System.out.println("Enter Date Required ");
			reqFormatDate = scanner.nextLine();
			if(reqFormatDate == reqFormatDate) {
				System.out.println("Error - No cars are available on this date");
			}else if("one".equals("more")) {
				System.out.println("The following cars are available.");
				System.out.println("1.	");
				System.out.println("2.	");
				System.out.println("3.	");
				System.out.println("4.	");
				System.out.println("5.	");
				System.out.println("Please select the number next to the car you wish to book ");
				int carSelect = scanner.nextInt();
				validDate =true;
			}else {
				validDate = true;
			}
		}while(validDate == false);
		
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
		
		do {
			System.out.print("Enter Number of Passengers ");
			numPassengers = scanner.nextInt();
			if (numPassengers < 1 || numPassengers > 9) {
				System.out.println("Error - Passenger Capacity is invalid");
			}else{
					validNumPass = true;
				}
		}while(validNumPass == false);
		
		Booking booking = new Booking(firstName, lastName, required, numPassengers, car);
		id = booking.getId();
		
		System.out.println("Thank you for booking. " + "driverName" + " will pick you up on " 
				+ reqFormatDate + "." + "\n" + "Your booking reference is: " + id + ".");
			
		scanner.close();
	}
}
