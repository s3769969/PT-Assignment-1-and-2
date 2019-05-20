package userInterface;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;
import application.Car;
import application.InvalidBookingException;
import application.InvalidDateException;
import application.InvalidPassCapException;
import application.InvalidRefreshmentsException;
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
		
		//Checks string format and if format is not "abc123" returns to display menu else passes through
		try {
			checkRegFormat(regNo);
		} catch (InvalidRegException e) {
			System.out.println(e.getMessage());
			run();
		}
		
		//Checks if the car is in the allCars array. If found prints error and return to menu. Else passes through
		try {
			system.checkCar(regNo);
		}catch (InvalidRegException e) {
			System.out.println(e.getMessage());
			run();
		}
	
		//Records user input car details for new car creation
		System.out.print("Enter make: ");
		String make = scanner.nextLine();
		System.out.print("Enter model: ");
		String model = scanner.nextLine();
		System.out.print("Enter Driver's Name: ");
		String driverName = scanner.nextLine();

		/*Loop where user enters input for passenger number and loops ends when number is between 1-9.
		Also displays exception error messages for number <1 or >9 or if input is not an integer*/
		int i = 0;
		int passengerCapacity = 0;
		while (i == 0) {
			try {
				passengerCapacity = checkPassCap(passengerCapacity);
				i = 1;
			}catch (InvalidPassCapException e) {
				System.out.println(e.getMessage());
			}catch (InputMismatchException e) {
				System.out.println("Please enter an integer!\n");
			}
		}
		
		//Prompts user to select service type and re-prompts details until no exceptions are created
		i = 0;
		String serviceType = null;
		while (i == 0) {
			try {
				serviceType = checkServiceType(serviceType);
				i = 1;
			}catch (InvalidServiceTypeException e) {
				System.out.println(e.getMessage());
			}
		}
		
		i = 0;
		double bookingFee = 0.0;
		String refreshments = null;
		//Checks if Car is SS and asks for more input details. Re-prompts details until no exceptions are created
		if (serviceType.equalsIgnoreCase("SS")) {
			while (i == 0) {
				try {
					bookingFee = checkBookingFee(bookingFee);
					i = 1;
				}catch (InvalidBookingFeeException e) {
					System.out.println(e.getMessage());
				}catch (InputMismatchException e) {
					System.out.println("Please enter a number!\n");
				} 
			}
			System.out.print("Enter List of Refreshments: ");
			refreshments = scanner.nextLine();
		}
		
		//Depending on service type adds SD car or SS car and prints response*/ 
		if (serviceType.equalsIgnoreCase("SS")) {
			try {
				system.addCar(regNo, make, model, driverName, passengerCapacity, bookingFee, refreshments);
				System.out.println("New SS Car successfully added for registration number: " + regNo + "\n");
			}catch (InvalidRefreshmentsException e){
				System.out.println(e.getMessage());
			}
		}else {
			system.addCar(regNo, make, model, driverName, passengerCapacity);	
			System.out.println("New Car successfully added for registration number: " + regNo + "\n");
		}
		run(); //Returns to menu display whether or not car was added
	}
	
	/*Searches available cars in allCars array, displays choices for the user to select from and
	  once selected user is able to input details and receive a booking id, if successful*/
	public void bookCar() {
		
		Scanner scanner = new Scanner(System.in);
		int i = 0;
		String requiredString = null; //String that user will input required date with
		DateTime required = new DateTime(); //New DateTime object for booking is initialised
		String fix; //Adds scanner.nextLine after int/double input to fix input skip
		
		/*User enters input for date required in format dd/MM/yyyy, the format is checked and if
		invalid format message is printed and user loops back. Otherwise the loop is passed through*/
		while (i == 0) {
			try{
				requiredString = checkDateFormat(requiredString);
				i = 1;
			}catch(InvalidDateException e) {
				System.out.println(e.getMessage());
			}	
		}
		
		/*Checks MiRidesSystem to see if there are available cars. If false prints error and returns
		to menu. Else print available car list and message prompt*/
		if (system.availableCars() == 0) {
			System.out.println("Error - no cars available on this date\n");
			run();
		}else {
			system.printAvailableCars();
			System.out.println("\nPlease select the number next to the car you wish to book: ");
		}
		
		//Initialises new Car array that references availableCars array in MiRides class
		Car[] availableCars = system.getAvailableCars();
		int j = system.availableCars();//Returns number of available cars
		
		/*If user enters choice outside selection range (1-j) or not an integer, error is displayed and
		user is re-prompted. Else, passes through*/
		i = 0;
		int selection = 0;
		while (i == 0) {
			selection = scanner.nextInt(); //User inputs integer as selection choice
			fix = scanner.nextLine();
			try {
				if (selection < 1 || selection > j) {
					System.out.println("Please enter a valid choice!\n");
				}else {
					i = 1;
				}
			}catch (InputMismatchException e) {
				System.out.println("Please enter an integer!\n");
			}
		}
		
		Car car = availableCars[selection - 1]; //Car object that user selects is initialised
		
		/*If user enters name less than 3 characters, error is displayed and user is re-prompted. Else, passes through*/
		i = 0;
		String firstName = "Enter First Name: "; //Initialises first name string
		while (i == 0) {
			try{
				firstName = checkNameLength(firstName);
				i = 1;
			}catch(InvalidNameException e) {
				System.out.print(e.getMessage());
			}	
		}
		
		i = 0;
		String lastName = "Enter Last Name: "; //Initialises last name string
		while (i == 0) {
			try{
				lastName = checkNameLength(lastName);
				i = 1;
			}catch(InvalidNameException e) {
				System.out.print(e.getMessage());
			}	
		}

		/*Takes user input as passenger number and checks if number is greater than car passenger
		capacity or less than one. In either case error message is printed and loops returns.
		Otherwise loop exits and numPassenegrs is overridden with user input*/
		int numPassengers = 0;
		try {
			System.out.print("Enter Number of Passengers ");
			numPassengers = scanner.nextInt();
			fix = scanner.nextLine();
		}catch (InputMismatchException e) {
			System.out.println("Please enter an integer!\n");
		}

		/*Checks user Car object choice and all input data and checks if booking can be made. If true,
		then booking is made using user data, booking id is returned, driver name is returned and a
		confirmation message is printed. Otherwise an error message is printed.*/
		try {
			if (system.bookCar(car, firstName, lastName, required, numPassengers) == true) {
				String id = car.getBookingRef(required);
				String driverName = car.getDriverName();
				System.out.println("\nThank you for booking. " + driverName + " will pick you up on "
						+ required.getFormattedDate() + ".\n" + "Your booking reference is: " + id + ".\n");
			}else {
				System.out.println("Error - Booking could not be completed\n");
			}
		} catch (InvalidPassCapException e) {
			System.out.println(e.getMessage());
		} catch (InvalidBookingException e) {
			System.out.println(e.getMessage());
		}finally {
			run(); //Returns to menu display whether or not booking was completed
		}
	}
	
	/*Locates booking by car registration number or date and completes the booking after travel
	distance is input. Then prints total cost of the trip.*/
	public void completeBooking() {
		
		Scanner scanner = new Scanner(System.in);
		
		int i = 0;
		String regNoOrDate = null; //String that user will input required date or time with
		DateTime required = new DateTime(); //New DateTime object for booking is initialised
		String fix; //Adds scanner.nextLine after int/double input to fix input skip
		
		/*User enters input for date required in format dd/MM/yyyy or reg number in format "abc123",
		the format is checked and if invalid format message is printed and user loops back. If format
		is correct passes through after assigning i = 1(for reg) or 2(for date)*/
		while (i == 0){
			System.out.print("Enter Registration or Booking Date: ");
			regNoOrDate = scanner.nextLine().toUpperCase();
			try {
				checkRegFormat(regNoOrDate);
				i = 1;
			}catch (InvalidRegException e) {
				try {
					checkDateFormat(regNoOrDate);
					i = 2;
				}catch (InvalidDateException f) {
					System.out.println("Error - Registration number format (eg: 'ABC123') "
							+ "or Date format (eg: 'dd/MM/yyyy') is invalid\n");
				}
			}
		}
		
		/*Searches for matching reg number or date in allCars array and if found loop passes
		through. Otherwise, if booking is not located error message is
		printed and returns console to display menu.*/
		if (i == 1) {
			if (system.findCurrentBookingByReg(regNoOrDate)) {
				i = 1;
			}else {
				System.out.println("Error - Booking could not be located\n");
				run();
			}
		}else if (i == 2) {
			if (system.searchByDate(regNoOrDate) == true) {
				i = 2;					
			}else {
				System.out.println("Error - Booking could not be located\n");
				run();
			}
		}
		
		
		/*If user enters name less than 3 characters, error is displayed and user is re-prompted. Else, passes through*/
		i = 0;
		String firstName = "Enter First Name: "; //Initialises first name string
		while (i == 0) {
			try{
				firstName = checkNameLength(firstName);
				i = 1;
			}catch(InvalidNameException e) {
				System.out.print(e.getMessage());
			}	
		}
		
		i = 0;
		String lastName = "Enter Last Name: "; //Initialises last name string
		while (i == 0) {
			try{
				lastName = checkNameLength(lastName);
				i = 1;
			}catch(InvalidNameException e) {
				System.out.print(e.getMessage());
			}	
		}
			
		double kilometersTravelled = 0; //Initialises local travel distance 
		                                                   //variable as double from input
		
		//Checks if travel distance is valid. Re-prompts details until no exceptions are created
		int j = 0;		
		while (j == 0) {
			System.out.println("Enter kilometers:");
			try {
				kilometersTravelled = scanner.nextDouble();
				fix = scanner.nextLine();
				j = 1;
			}catch (InputMismatchException e) {
				System.out.println("Please enter a number!\n");
			} 
		}
					
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
		String regNo = null;
		int i = 0;
		
		/*Converts user input to upper case. Then checks reg number string against allCars array in
		MiRidesSystem. If match is found then error message is printed and loop returns to user input
		prompt. Otherwise loop is passed through. */ 
		while (i == 0) {
			System.out.print("Enter Registration No: ");
			regNo = scanner.nextLine();
			regNo = regNo.toUpperCase();
			try {
				regNo = checkRegFormat(regNo);
				i = 1;
			} catch (InvalidRegException e) {
				System.out.println(e.getMessage());
			}
		}
		
		system.searchByRegPrint(regNo); //Prints car details from allCars array or error if not found
		run(); //Returns to menu display
	}
	
	/*Prints list of all available cars for specific date and service type based on user input*/
	public void searchAvailable() {
		
		Scanner scanner = new Scanner(System.in);
		int i = 0;
		String requiredString; //String that user will input required date with
		DateTime required = new DateTime(); //New DateTime object for booking is initialised
		
		System.out.print("Enter Service Type (SD/SS): ");
		String serviceType = scanner.nextLine();
		
		/*User enters input for date required in format dd/MM/yyyy, the format is checked and if
		invalid format message is printed and user loops back. If format is correct a copy user string
		is converted to DateTime object and overrides the required DateTime object. If DateTime is in
		the past or more than 1 week in future, for either case an error message is displayed.
		Otherwise the loop is passed through*/
		do {
			System.out.println("Enter Date Required in dd/MM/yyyy: ");
			requiredString = scanner.nextLine();
			try {
				checkDateFormat(requiredString);
			} catch (InvalidDateException e) {
				System.out.println(e.getMessage());
			}
			run	();
		} while (i == 0);
		
		
		/*Checks MiRidesSystem to see if there are available cars. If false prints error and returns
		to menu. Else prints available cars in list and prompts user to input number*/
		int j = 0;
		if (system.availableCars(required) == false) {
			System.out.println("Error - no cars available on this date\n");
			run();
		}else {
			Car[] availableCarsForDate = system.printAvailableCars(required, serviceType);
			while (availableCarsForDate[j] != null && j < availableCarsForDate.length) {
				System.out.println(availableCarsForDate[j].getDetails());
				j++;
			}
		}
		run(); //Returns to menu display
	}
	
	/*Prints list of all Car details of all Car objects found in allCars array in MiRidesSystem then
	returns console to display menu*/
	public void displayAll() {
		
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter Service Type (SD/SS): ");
		String serviceType = scanner.nextLine();
		System.out.print("Enter Sort Order (A/D): ");
		String sortOrder = scanner.nextLine();
		system.displayAll(serviceType, sortOrder); 	
		run();
	}
	
	/*Adds 6 hardcoded cars, with 2 booked only, and 2 with completed bookings into allCars array in
	MiRidesSystem, only if cars are not already in array. Then returns console to display menu*/
	public void seedData() {
		
		try {
			system.seedData();
		} catch (InvalidRefreshmentsException e) {
			System.out.println(e.getMessage());
		} catch (InvalidDateException e) {
			System.out.println(e.getMessage());
		} catch (InvalidPassCapException e) {
			System.out.println(e.getMessage());
		} catch (InvalidBookingException e) {
			System.out.println(e.getMessage());
		}
		run();
	}	
	
	/*Checks format of string argument. Returns true if format matches regex pattern. Else,
	returns false*/
	public String checkRegFormat(String regNo) throws InvalidRegException {
		
		String regex = "[a-zA-Z]{3}[0-9]{3}";
		if (regNo.length() == 6 && Pattern.matches(regex, regNo)) {
			return regNo;
		}else {
			throw new InvalidRegException("Error - Registration format is invalid,"
					+ " needs to be in the form 'ABC123'\n");
		}		
	}
	
	/*Checks format of string argument. Returns true if format matches regex pattern. Else,
	returns false*/
	public String checkDateFormat(String requiredString) throws InvalidDateException {
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter Date Required in dd/MM/yyyy: ");
		requiredString = scanner.nextLine();
		String regex = "[0-9]{2}/[0-9]{2}/[0-9]{4}";
		if (requiredString.trim().length() == 10 && Pattern.matches(regex, requiredString.trim())) {
			return requiredString;
		}else {
			throw new InvalidDateException("Error - Date format is invalid,"
					+ " needs to be in the form 'dd/MM/yyyy'\n");
		}			
	}
	
	//Checks length string argument. Loops user input prompt until string length is >2 characters
	public String checkNameLength(String nameCheck) throws InvalidNameException {
		
		Scanner scanner = new Scanner(System.in);
		System.out.println(nameCheck);
		String name = scanner.nextLine().trim();
		if (name.length() < 3) {
			throw new InvalidNameException("Error - Name is too short.\nRe-");
		}
		return name;
	}
	
	//Checks passenger capacity is between 1-9
	public int checkPassCap(int passengerCapacity) throws InvalidPassCapException {
		
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter Passenger Capacity: ");
		passengerCapacity = scanner.nextInt();
		String fix = scanner.nextLine();//Adds scanner.nextLine after int/double input to fix input skip
		if (passengerCapacity < 1 || passengerCapacity > 9) {
			throw new InvalidPassCapException("Error - Passenger Capacity is invalid\n");
		}
		return passengerCapacity;
	}
	
	//Checks standard fee  >$3
	public double checkBookingFee(double bookingFee) throws InvalidBookingFeeException {
		
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter Standard Fee: ");
		bookingFee = scanner.nextDouble();
		String fix = scanner.nextLine();//Adds scanner.nextLine after int/double input to fix input skip
		if (bookingFee < 3) {
			throw new InvalidBookingFeeException("Error - SS Cars minimum booking fee is $3\n");
		}
		return bookingFee;
	}
	
	//Checks string argument for service type is valid
	public String checkServiceType(String serviceType) throws InvalidServiceTypeException {
		
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter Service Type (SD/SS): ");
		serviceType = scanner.nextLine().trim();
		if (serviceType.equalsIgnoreCase("sd") || serviceType.equalsIgnoreCase("ss")) {
			return serviceType;
		}else {
			throw new InvalidServiceTypeException("Error - Service type must be either SD or SS\n");
		}
	}
		
	//Checks string argument for sort order is valid
	public String checkSortOrder(String sortOrder) throws InvalidSortOrderException {
		
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter Sort Order (A/D): ");
		sortOrder = scanner.nextLine().trim();
		if (sortOrder.equalsIgnoreCase("a") || sortOrder.equalsIgnoreCase("d")) {
			return sortOrder;
		}else {
			throw new InvalidSortOrderException("Error - Sort order must be either A or D\n");
		}
	}	
}
