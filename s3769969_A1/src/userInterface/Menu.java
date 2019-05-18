package userInterface;

import java.util.Scanner;
import java.util.regex.Pattern;

import application.Car;
import application.MiRidesSystem;
import utilities.DateTime;

/*
 * Class:			Menu
 * Description:		The class presents a menu to the user for interacting with the program calls
 * 					methods depending on user choices
 * Author:			Sebastian Wisidagama - s3769969
 */
public class Menu {

	MiRidesSystem system;	//class of MiRidesSystem is accessible to all Menu methods
	
	public Menu() {
		
	}
	
	//Instantiates MiRidesSystem class and calls method to create allCars array
	public void createSystem() {
		
		system = new MiRidesSystem();	//instantiates MiRidesSystem class after driver runs program
		system.createAllCarsArray(); //calls method to create allCars array
	}
	
	/*Displays menu and redirects to new method or exits system if input(not case sensitive) matches
	choice strings*/
	public void run() {
		
		//Print menu display
		System.out.println("*** MiRides System Menu ***" + "\n");
		System.out.println("Create Car                         CC" + "\n");
		System.out.println("Book Car                           BC" + "\n");
		System.out.println("Complete Booking                   CB" + "\n");
		System.out.println("Display ALL Cars                   DA" + "\n");
		System.out.println("Search Specific Car                SS" + "\n");
		System.out.println("Search available cars              SA" + "\n");
		System.out.println("Seed Data                          SD" + "\n");
		System.out.println("Exit Program                       EX" + "\n");

		Scanner scanner = new Scanner(System.in);
		String menuChoice;
		
		/*Keeps waiting for correct input (case insensitive) and if matches, then redirects to
		 another method in Menu class or exits system if user inputs "ex" (case insensitive) */
		do {
			menuChoice = scanner.nextLine().trim();
			if (menuChoice.equalsIgnoreCase("CC")) {
				createCar();
			} else if (menuChoice.equalsIgnoreCase("BC")) {
				bookCar();
			} else if (menuChoice.equalsIgnoreCase("CB")) {
				completeBooking();
			} else if (menuChoice.equalsIgnoreCase("DA")) {
				displayAll();
			} else if (menuChoice.equalsIgnoreCase("SS")) {
				displaySpecific();
			} else if (menuChoice.equalsIgnoreCase("SA")) {
				searchAvailable();
			} else if (menuChoice.equalsIgnoreCase("SD")) {
				seedData();
			} else if (menuChoice.equalsIgnoreCase("EX")) {
				System.exit(0);
			}
		}while (menuChoice != "EX");
	}
	
	//Takes user input and adds car to allCars array if car object is not already in array
	public void createCar() {
		
		//Awaits user input for registration number string and converts it to upper case
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter Registration No: ");
		String regNo = scanner.nextLine().toUpperCase();
		
		/*Checks string format and if format is not "abc123" returns to display menu else passes
		through id statement*/
		if (checkRegFormat(regNo) == false){
			System.out.println("Error - Registration number is invalid\n");
			run();
		}else if (system.checkCar(regNo)  == true){
			System.out.println("Error - already exists in system\n");
			run();
		}
	
		//Records user input car details for new car creation
		System.out.print("Enter make: ");
		String make = scanner.nextLine();
		System.out.print("Enter model: ");
		String model = scanner.nextLine();
		System.out.print("Enter Driver's Name: ");
		String driverName = scanner.nextLine();

		//Loop where user enters input for passenger number and loops ends when number is between 1-9
		int i = 0;
		int passengerCapacity;
		do {
			System.out.print("Enter Passenger Capacity: ");
			passengerCapacity = scanner.nextInt();
			if (passengerCapacity < 1 || passengerCapacity > 9) {
				System.out.println("Error - Passenger Capacity is invalid\n");
			} else {
				i = 1;
			}
		} while (i == 0);

		String fix = scanner.nextLine();
		System.out.print("Enter Service Type (SD/SS): ");
		String serviceType = scanner.nextLine();
		
		double bookingFee = 0.0;
		String refreshments = null;
		//Checks if Car is SS and asks for more input details
		if (serviceType.equalsIgnoreCase("SS")) {
			System.out.print("Enter Standard Fee: ");
			bookingFee = scanner.nextDouble();
			fix = scanner.nextLine();
			System.out.print("Enter List of Refreshments: ");
			refreshments = scanner.nextLine();
		}
		
		/*Checks if the car is in the allCars array and depending on outcome then adds car or SS car and
		prints response*/ 
		if (system.checkCar(regNo)  == false) {
			if (serviceType.equalsIgnoreCase("SS")) {
				system.addCar(regNo, make, model, driverName, passengerCapacity, bookingFee, refreshments);
				System.out.println("New SS Car successfully added for registration number: " + regNo + "\n");
			}else {
				system.addCar(regNo, make, model, driverName, passengerCapacity);	
				System.out.println("New Car successfully added for registration number: " + regNo + "\n");
			}	
		}else {
			System.out.println("Error - " + regNo + " already exists in system\n");
		}
	
		run(); //Returns to menu display whether or not car was added
	}
	
	/*Searches available cars in allCars array, displays choices for the user to select from and
	  once selected user is able to input details and receive a booking id, if successful*/
	public void bookCar() {
		
		Scanner scanner = new Scanner(System.in);
		int i = 0;
		String requiredString; //String that user will input required date with
		DateTime required = new DateTime(); //New DateTime object for booking is initialised
		
		/*User enters input for date required in format dd/MM/yyyy, the format is checked and if
		invalid format message is printed and user loops back. If format is correct a copy user string
		is converted to DateTime object and overrides the required DateTime object. If DateTime is in
		the past or more than 1 week in future, for either case an error message is displayed.
		Otherwise the loop is passed through*/
		do {
			System.out.println("Enter Date Required in dd/MM/yyyy: ");
			requiredString = scanner.nextLine();
			if (checkDateFormat(requiredString) == false) {
				System.out.println("Error - Date format is invalid\n");
			} else {
				required = system.convertStringToTime(requiredString); //Converts string to DateTimee
				DateTime currentExact = new DateTime(); //Initialises exact current DateTime object
				String currentExactString = currentExact.getFormattedDate(); //Converts current DateTime to string
				DateTime current = system.convertStringToTime(currentExactString); //Initialises DateTime 00:00hrs
				int diffDays = DateTime.diffDays(required, current);
				if (diffDays < 0) {
					System.out.println("Error - Can't make bookings for past dates\n");
				}else if (diffDays > 7) {
					System.out.println("Error - Can't make bookings for more than one week in the future\n");
				}else {
					i = 1;
				}
			}	
		} while (i == 0);
		
		
		/*Checks MiRidesSystem to see if there are available cars. If false prints error and returns
		to menu. Else prints available cars in list and prompts user to input number*/
		if (system.availableCars() == false) {
			System.out.println("Error - no cars available on this date\n");
			run();
		}else {
			system.printAvailableCars();
			System.out.println("Please select the number next to the car you wish to book: ");
		}
		
		//Initialises new Car array that references availableCars array in Menu class
		Car[] availableCars = system.getAvailableCars();
		
		int selection = scanner.nextInt(); //User inputs integer as selection choice
		Car car = availableCars[selection - 1]; //Car object that user selects is initialised 
		
		String firstName = "Invalid"; //Initialises first name string as Invalid
		System.out.println("Enter First Name "); //Prints input instruction
		firstName = checkNameLength(firstName); //Overrides first name when >2 character length
		
		String lastName = "Invalid"; //Initialises last name string as Invalid
		System.out.println("Enter Last Name "); //Prints input instruction
		lastName = checkNameLength(lastName); //Overrides last name when >2 character length

		/*Takes user input as passenger number and checks if number is greater than car passenger
		capacity or less than one. In either case error message is printed and loops returns.
		Otherwise loop exits and numPassenegrs is overridden with user input*/
		int numPassengers;
		int passengerCapacity;
		i = 0;
		do {
			System.out.print("Enter Number of Passengers ");
			numPassengers = scanner.nextInt();
			passengerCapacity = car.getPassengerCapacity();
			if (numPassengers > passengerCapacity) {
				System.out.println("Error - Passenger number exceeds passenger capacity for this car\n");
			}else if (numPassengers < 1) {
				System.out.println("Error - There must be at least 1 passenger\n");
			}else {
				i = 1;
			}
		} while (i == 0);

		/*Checks user Car object choice and all input data and checks if booking can be made. If true,
		then booking is made using user data, booking id is returned, driver name is returned and a
		confirmation message is printed. Otherwise an error message is printed.*/
		if (system.bookCar(car, firstName, lastName, required, numPassengers) == true) {
			String id = car.getBookingRef(required);
			String driverName = car.getDriverName();
			System.out.println("\nThank you for booking. " + driverName + " will pick you up on "
					+ required.getFormattedDate() + ".\n" + "Your booking reference is: " + id + ".\n");
		}else {
			System.out.println("Error - Booking could not be completed\n");
		}
		
		run(); //Returns to menu display whether or not booking was completed
	}
	
	/*Locates booking by car registration number or date and completes the booking after travel
	distance is input. Then prints total cost of the trip.*/
	public void completeBooking() {
		
		Scanner scanner = new Scanner(System.in);
		
		int i = 0;
		String regNoOrDate; //String that user will input required date or time with
		DateTime required = new DateTime(); //New DateTime object for booking is initialised
		
		/*User enters input for date required in format dd/MM/yyyy or reg number in format "abc123",
		the format is checked and if invalid format message is printed and user loops back. If format
		is correct booking is searched for in allCars array for a match and if found loop passes
		through and integer i is assigned the value 1 or 2 depending on if it was recognised as
		registration number or date format. Otherwise, if booking is not located error message is
		printed and returns console to display menu.*/
		do {
			System.out.print("Enter Registration or Booking Date: ");
			regNoOrDate = scanner.nextLine().toUpperCase();
			if (checkRegFormat(regNoOrDate) == true) {
				if (system.findCurrentBookingByReg(regNoOrDate)) {
					i = 1;
				}else {
					System.out.println("Error - Booking could not be located\n");
					run();
				}
			}else if (checkDateFormat(regNoOrDate) == true) {
				if (system.searchByDate(regNoOrDate) == true) {
					i = 2;					
				}else {
					System.out.println("Error - Booking could not be located\n");
					run();
				}
			}else{
				System.out.println("Error - Registration number format (eg: 'ABC123') "
						+ "or Date format (eg: 'dd/MM/yyyy') is invalid\n");
			}
		} while (i == 0);
		
		String firstName = "Invalid"; //Initialises first name string as Invalid
		System.out.println("Enter First Name "); //Prints input instruction
		firstName = checkNameLength(firstName); //Overrides first name when >2 character length
		
		String lastName = "Invalid"; //Initialises last name string as Invalid
		System.out.println("Enter Last Name "); //Prints input instruction
		lastName = checkNameLength(lastName); //Overrides last name when >2 character length
			
		System.out.println("Enter kilometers:"); //Prompts travel distance input
		double kilometersTravelled = scanner.nextDouble(); //Initialises local travel distance 
		                                                   //variable as double from input
		
		String totalFee = "N/A"; //Initialises total fee string as N/A
		
		/*Completes booking using travel distance input and regNoOrDate string as reg number.
		Then overrides total fee after getting it from booking class. Then, prints response 
		after total fee is overridden*/
		if (i == 1) {
			if (system.completeBookingWithReg(regNoOrDate, kilometersTravelled, firstName, lastName)) {
				totalFee = system.getTotalFeeUsingReg(regNoOrDate, kilometersTravelled, firstName, lastName);
				System.out.println("Thank you for riding with MiRide. We hope you enjoyed your trip.\n\n$" +
						totalFee + " has been deducted from your account.\n");
			}else {
				System.out.println("Error - Booking could not be located\n");
			}
			
		}
		
		/*Completes booking using travel distance input and regNoOrDate string as formatted date. Then
		overrides total fee after getting it from booking class.Then, prints response after total fee
		is overridden*/
		if (i == 2) {
			if (system.completeBookingWithDate(regNoOrDate, kilometersTravelled, firstName, lastName)) {
				totalFee = system.getTotalFeeUsingDate(regNoOrDate, kilometersTravelled, firstName, lastName);
				System.out.println("Thank you for riding with MiRide. We hope you enjoyed your trip.\n\n$" +
					totalFee + " has been deducted from your account.\n");
			}else {
				System.out.println("Error - Booking could not be located\n");
			}
		}
		
		run(); //Returns to menu display
	}
	
	/*Checks user string input to find matching reg number in allCars array and prints Car object
	details if found, then returns to menu display. Otherwise prints error message and returns to
	menu display*/
	public void displaySpecific() {
		
		Scanner scanner = new Scanner(System.in);
		String regNo;
		int i = 0;
		
		/*Converts user input to upper case. Then checks reg number string against allCars array in
		MiRidesSystem. If match is found then error message is printed and loop returns to user input
		prompt. Otherwise loop is passed through. */ 
		do {
			System.out.print("Enter Registration No ");
			regNo = scanner.nextLine();
			regNo = regNo.toUpperCase();
			//try {
				if (checkRegFormat(regNo) == false) {
					System.out.println("Error - Registration number is invalid\n");
				} else {
					i = 1;
				}
//			}catch(InvalidId e){
//				System.out.println(e.toString());
//			}
		}while(i == 0);
		
		system.searchByRegPrint(regNo); //
		run(); //Returns to menu display
	}
	
	//Prints message and returns console to display menu
	public void searchAvailable() {
		
		System.out.println("Error - Feature not yet implemented\n"); //Prints error message
		run(); //Returns to menu display
	}
	
	/*Prints list of all Car details of all Car objects found in allCars array in MiRidesSystem then
	returns console to display menu*/
	public void displayAll() {
		
		system.displayAll(); 	
		run();
	}
	
	/*Adds 6 hardcoded cars, with 2 booked only, and 2 with completed bookings into allCars array in
	MiRidesSystem, only if cars are not already in array. Then returns console to display menu*/
	public void seedData() {
		
		system.seedData();
		run();
	}	
	
	/*Checks format of string argument. Returns true if format matches regex pattern. Else,
	returns false*/
	public boolean checkRegFormat(String regNo) /*throws InvalidId*/ {
		
		String regex = "[a-zA-Z]{3}[0-9]{3}";
		if (regNo.length() == 6 && Pattern.matches(regex, regNo)) {
			return true;
		}else {
			//throw new InvalidId(regNo);
			return false;
		}		
	}
	
	/*Checks format of string argument. Returns true if format matches regex pattern. Else,
	returns false*/
	public boolean checkDateFormat(String required) {
		
		String regex = "[0-9]{2}/[0-9]{2}/[0-9]{4}";
		if (required.trim().length() == 10 && Pattern.matches(regex, required.trim())) {
			return true;
		}else {
			return false;
		}			
	}
	
	//Checks length string argument. Loops user input prompt until string length is >2 characters
	public String checkNameLength(String name) {
		
		Scanner scanner = new Scanner(System.in);
		do {
			name = scanner.nextLine().trim();
			if (name.length() < 3) {
				System.out.println("Error - Name is too short.\nRe-enter: ");
			}
		} while (name.length() < 3);
		return name;
	}
}
