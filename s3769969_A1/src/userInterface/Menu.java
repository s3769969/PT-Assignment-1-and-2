package userInterface;

import java.util.InputMismatchException;
import java.util.Scanner;
import application.Car;
import application.InvalidBookingException;
import application.InvalidBookingFeeException;
import application.InvalidDateException;
import application.InvalidNameException;
import application.InvalidPassCapException;
import application.InvalidRefreshmentsException;
import application.InvalidRegException;
import application.InvalidServiceTypeException;
import application.InvalidSortOrderException;
import application.MiRidesSystem;
import utilities.DateTime;

/*
 * Class:			Menu
 * Description:		The class presents a menu to the user for interacting with the program calls
 * 					methods depending on user choices
 * Author:			Sebastian Wisidagama - s3769969
 */
public class Menu {

	private MiRidesSystem system; // class of MiRidesSystem is accessible to all Menu methods

	// Instantiates MiRidesSystem class and calls method to create allCars array and
	// load save data
	public Menu() {

		system = new MiRidesSystem(); // instantiates MiRidesSystem class after driver runs program
		try {
			system.loadData();
		} catch (InvalidRegException e) {
			System.out.println(e.getMessage());// attempts to load data from save file or back up if unsuccessful
		} 
		run(); // Goes to menu print/selection method
	}

	/*
	 * Displays menu and redirects to new method or exits system if input(not case
	 * sensitive) matches choice strings
	 */
	public void run() {

		// Print menu display
		System.out.println("\n*** MiRides System Menu ***" + "\n");
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
		menuChoice = scanner.nextLine().trim();

		/*
		 * Keeps waiting for correct input (case insensitive) and if matches, then
		 * redirects to another method in Menu class or exits system if user inputs "ex"
		 * (case insensitive)
		 */
		do {

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
				system.saveData();
				System.exit(0);
			}
			menuChoice = scanner.nextLine().trim();
		} while (menuChoice != "EX");
	}

	// Takes user input and adds car to allCars array if car object is not already
	// in array
	public void createCar() {

		// Awaits user input for registration number string and converts it to upper
		// case
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter Registration No: ");
		String regNo = scanner.nextLine().toUpperCase();

		// Checks string format and if format is not "abc123" returns to display menu
		// else passes through
		try {
			system.checkRegFormat(regNo);
		} catch (InvalidRegException e) {
			System.out.println(e.getMessage());
			run();
		}

		// Checks if the car is in the allCars array. If found prints error and return
		// to menu. Else passes through
		try {
			system.checkCar(regNo);
		} catch (InvalidRegException e) {
			System.out.println(e.getMessage());
			run();
		}

		// Records user input car details for new car creation
		System.out.print("Enter make: ");
		String make = scanner.nextLine();
		System.out.print("Enter model: ");
		String model = scanner.nextLine();
		System.out.print("Enter Driver's Name: ");
		String driverName = scanner.nextLine();

		/*
		 * Loop where user enters input for passenger number and loops ends when number
		 * is between 1-9. Also displays exception error messages for number <1 or >9 or
		 * if input is not an integer
		 */
		int i = 0;
		int passengerCapacity = 0;
		while (i == 0) {
			System.out.print("Enter Passenger Capacity: ");
			Scanner scanner1 = new Scanner(System.in);
			try {
				passengerCapacity = scanner1.nextInt();
				String fix = scanner1.nextLine();// Adds scanner.nextLine after int/double input to fix input skip
				passengerCapacity = system.checkPassCap(passengerCapacity);
				i = 1;
			} catch (InputMismatchException e) {
				System.out.println("Please enter an integer!\n");
			} catch (InvalidPassCapException e) {
				System.out.println(e.getMessage());
			}
		}

		// Prompts user to select service type and re-prompts details until no
		// exceptions are created
		i = 0;
		String serviceType = null;
		while (i == 0) {
			System.out.print("Enter Service Type (SD/SS): ");
			Scanner scanner1 = new Scanner(System.in);
			serviceType = scanner1.nextLine().toUpperCase();
			try {
				serviceType = system.checkServiceType(serviceType);
				i = 1;
			} catch (InvalidServiceTypeException e) {
				System.out.println(e.getMessage());
			}
		}

		i = 0;
		double bookingFee = 0.0;
		String refreshments = null;
		// Checks if Car is SS and asks for more input details. Re-prompts details until
		// no exceptions are created from booking fee.
		if (serviceType.equalsIgnoreCase("SS")) {
			while (i == 0) {
				Scanner scanner1 = new Scanner(System.in);
				System.out.print("Enter Standard Fee: ");
				bookingFee = scanner1.nextDouble();
				String fix = scanner1.nextLine();// Adds scanner.nextLine after int/double input to fix input skip
				try {
					bookingFee = system.checkBookingFee(bookingFee);
					i = 1;
				} catch (InvalidBookingFeeException e) {
					System.out.println(e.getMessage());
				} catch (InputMismatchException e) {
					System.out.println("Please enter a number!\n");
				}
			}
			System.out.print("Enter List of Refreshments: ");
			refreshments = scanner.nextLine();
		}

		// Depending on service type adds SD car or SS car and prints response*/
		if (serviceType.equalsIgnoreCase("SS")) {
			try {
				system.addCar(regNo, make, model, driverName, passengerCapacity, bookingFee, refreshments);
				System.out.println("New SS Car successfully added for registration number: " + regNo + "\n");
			} catch (InvalidRefreshmentsException e) {
				System.out.println(e.getMessage());
			} catch (InvalidBookingFeeException e) {
				System.out.println(e.getMessage());
			} catch (InvalidRegException e) {
				System.out.println(e.getMessage());
			}
		} else {
			try {
				system.addCar(regNo, make, model, driverName, passengerCapacity);
			} catch (InvalidRegException e) {
				System.out.println(e.getMessage());
			}
			System.out.println("New Car successfully added for registration number: " + regNo + "\n");
		}
		run(); // Returns to menu display whether or not car was added
	}

	/*
	 * Searches available cars in allCars array, displays choices for the user to
	 * select from and once selected user is able to input details and receive a
	 * booking id, if successful
	 */
	public void bookCar() {

		Scanner scanner = new Scanner(System.in);
		int i = 0;
		String requiredString = null; // String that user will input required date with
		String fix = null; // Adds scanner.nextLine after int/double input to fix input skip

		/*
		 * User enters input for date required in format dd/MM/yyyy, the format is
		 * checked and if invalid format message is printed and user loops back.
		 * Otherwise the loop is passed through and input is converted to DateTime object
		 */
		while (i == 0) {
			System.out.println("Enter Date Required in dd/MM/yyyy: ");
			Scanner scanner1 = new Scanner(System.in);
			requiredString = scanner1.nextLine();
			try {
				requiredString = system.checkDateFormat(requiredString);
				i = 1;
			} catch (InvalidDateException e) {
				System.out.println(e.getMessage());
			}
		}
		DateTime required = system.convertStringToTime(requiredString);

		/*
		 * Checks MiRidesSystem to see if there are available cars. If false prints
		 * error and returns to menu. Else print available car list and message prompt
		 */
		if (system.availableCars() == 0) {
			System.out.println("Error - no cars available on this date\n");
			run();
		} else {
			system.printAvailableCars();
		}

		// Initialises new Car array that references availableCars array in MiRides
		// class
		Car[] availableCars = system.getAvailableCars();
		int j = system.availableCars();// Returns number of available cars

		/*
		 * If user enters choice outside selection range (1-j) or not an integer, error
		 * is displayed and user is re-prompted. Else, passes through
		 */
		i = 0;
		int selection = 0;
		while (i == 0) {
			System.out.println("\nPlease select the number next to the car you wish to book: ");
			Scanner scanner1 = new Scanner(System.in);
			try {
				selection = scanner1.nextInt(); // User inputs integer as selection choice
				fix = scanner1.nextLine();
				if (selection < 1 || selection > j) {
					System.out.println("Please enter a valid choice!\n");
				} else {
					i = 1;
				}
			} catch (InputMismatchException e) {
				System.out.println("Please enter an integer!\n");
			}
		}

		Car car = availableCars[selection - 1]; // Car object that user selects is initialised

		/*
		 * If user enters name less than 3 characters, error is displayed and user is
		 * re-prompted. Else, passes through
		 */
		i = 0;
		String firstName = "Enter First Name: "; // Initialises first name string
		while (i == 0) {
			try {
				firstName = system.checkNameLength(firstName);
				i = 1;
			} catch (InvalidNameException e) {
				System.out.print(e.getMessage());
			}
		}
		i = 0;
		String lastName = "Enter Last Name: "; // Initialises last name string
		while (i == 0) {
			try {
				lastName = system.checkNameLength(lastName);
				i = 1;
			} catch (InvalidNameException e) {
				System.out.print(e.getMessage());
			}
		}

		/*
		 * Takes user input as passenger number and checks if number is greater than car
		 * passenger capacity or less than one. In either case error message is printed
		 * and loops returns. Otherwise loop exits and numPassenegrs is overridden with
		 * user input
		 */
		int numPassengers = 0;
		try {
			System.out.print("Enter Number of Passengers ");
			numPassengers = scanner.nextInt();
			fix = scanner.nextLine();
		} catch (InputMismatchException e) {
			System.out.println("Please enter an integer!\n");
		}

		/*
		 * Checks user Car object choice and all input data and checks if booking can be
		 * made. If true, then booking is made using user data, booking id is returned,
		 * driver name is returned and a confirmation message is printed. Otherwise an
		 * error message is printed.
		 */
		try {
			if (system.bookCar(car, firstName, lastName, required, numPassengers) == true) {
				String id = car.getBookingRef(required);
				String driverName = car.getDriverName();
				System.out.println("\nThank you for booking. " + driverName + " will pick you up on "
						+ required.getFormattedDate() + ".\n" + "Your booking reference is: " + id + ".\n");
			} else {
				System.out.println("Error - Booking could not be completed\n");
			}
		} catch (InvalidPassCapException e) {
			System.out.println(e.getMessage());
		} catch (InvalidBookingException e) {
			System.out.println(e.getMessage());
		} finally {
			run(); // Returns to menu display whether or not booking was completed
		}
	}

	/*
	 * Locates booking by car registration number or date and completes the booking
	 * after travel distance is input. Then prints total cost of the trip.
	 */
	public void completeBooking() {

		Scanner scanner = new Scanner(System.in);

		int i = 0;
		String regNoOrDate = null; // String that user will input required date or time with
		String fix; // Adds scanner.nextLine after int/double input to fix input skip

		/*
		 * User enters input for date required in format dd/MM/yyyy or reg number in
		 * format "abc123", the format is checked and if invalid format message is
		 * printed and user loops back. If format is correct passes through after
		 * assigning i = 1(for reg) or 2(for date)
		 */
		while (i == 0) {
			System.out.print("Enter Registration or Booking Date: ");
			Scanner scanner1 = new Scanner(System.in);
			try {
				regNoOrDate = scanner1.nextLine().toUpperCase();
				system.checkRegFormat(regNoOrDate);
				i = 1;
			} catch (InvalidRegException e) {
				try {
					system.checkDateFormat(regNoOrDate);
					i = 2;
				} catch (InvalidDateException f) {
					System.out.println("Error - Registration number format (eg: 'ABC123') "
							+ "or Date format (eg: 'dd/MM/yyyy') is invalid\n");
				}
			}
		}

		/*
		 * Searches for matching reg number or date in allCars array and if found loop
		 * passes through. Otherwise, if booking is not located error message is printed
		 * and returns console to display menu.
		 */
		if (i == 1) {
			if (system.findCurrentBookingByReg(regNoOrDate)) {
			} else {
				System.out.println("Error - Booking could not be located\n");
				run();
			}
		} else if (i == 2) {
			if (system.searchByDate(regNoOrDate) == true) {
			} else {
				System.out.println("Error - Booking could not be located\n");
				run();
			}
		}

		/*
		 * If user enters name less than 3 characters, error is displayed and user is
		 * re-prompted. Else, passes through
		 */
		int j = 0;
		String firstName = "Enter First Name: "; // Initialises first name string
		while (j == 0) {
			try {
				firstName = system.checkNameLength(firstName);
				j = 1;
			} catch (InvalidNameException e) {
				System.out.print(e.getMessage());
			}
		}

		j = 0;
		String lastName = "Enter Last Name: "; // Initialises last name string
		while (j == 0) {
			try {
				lastName = system.checkNameLength(lastName);
				j = 1;
			} catch (InvalidNameException e) {
				System.out.print(e.getMessage());
			}
		}

		double kilometersTravelled = 0; // Initialises local travel distance
										// variable as double from input

		// Checks if travel distance is valid. Re-prompts details until no exceptions
		// are created
		j = 0;
		while (j == 0) {
			System.out.println("Enter kilometers:");
			Scanner scanner1 = new Scanner(System.in);
			try {
				kilometersTravelled = scanner1.nextDouble();
				fix = scanner1.nextLine();
				j = 1;
			} catch (InputMismatchException e) {
				System.out.println("Please enter a number!\n");
			}
		}

		String totalFee = "N/A"; // Initialises total fee string as N/A

		/*
		 * Completes booking using travel distance input and regNoOrDate string as reg
		 * number. Then overrides total fee after getting it from booking class. Then,
		 * prints response after total fee is overridden
		 */
		if (i == 1) {
			if (system.completeBookingWithReg(regNoOrDate, kilometersTravelled, firstName, lastName)) {
				totalFee = system.getTotalFeeUsingReg(regNoOrDate, kilometersTravelled, firstName, lastName);
				System.out.println("Thank you for riding with MiRide. We hope you enjoyed your trip.\n\n$" + totalFee
						+ " has been deducted from your account.\n");
			} else {
				System.out.println("Error - Booking could not be located\n");
			}

		}

		/*
		 * Completes booking using travel distance input and regNoOrDate string as
		 * formatted date. Then overrides total fee after getting it from booking
		 * class. Then, prints response after total fee is overridden
		 */
		if (i == 2) {
			if (system.completeBookingWithDate(regNoOrDate, kilometersTravelled, firstName, lastName)) {
				totalFee = system.getTotalFeeUsingDate(regNoOrDate, kilometersTravelled, firstName, lastName);
				System.out.println("Thank you for riding with MiRide. We hope you enjoyed your trip.\n\n$" + totalFee
						+ " has been deducted from your account.\n");
			} else {
				System.out.println("Error - Booking could not be located\n");
			}
		}
		run(); // Returns to menu display
	}

	/*
	 * Checks user string input to find matching reg number in allCars array and
	 * prints Car object details if found, then returns to menu display. Otherwise
	 * prints error message and returns to menu display
	 */
	public void displaySpecific() {

		Scanner scanner = new Scanner(System.in);
		String regNo = null;
		int i = 0;

		/*
		 * Converts user input to upper case. Then checks reg number string against
		 * allCars array in MiRidesSystem. If match is found then error message is
		 * printed and loop returns to user input prompt. Otherwise loop is passed
		 * through.
		 */
		while (i == 0) {
			System.out.print("Enter Registration No: ");
			Scanner scanner1 = new Scanner(System.in);
			regNo = scanner1.nextLine();
			regNo = regNo.toUpperCase();
			try {
				regNo = system.checkRegFormat(regNo);
				i = 1;
			} catch (InvalidRegException e) {
				System.out.println(e.getMessage());
			}
		}

		system.searchByRegPrint(regNo); // Prints car details from allCars array or error if not found
		run(); // Returns to menu display
	}

	/*
	 * Prints list of all available cars for specific date and service type based on
	 * user input
	 */
	public void searchAvailable() {

		Scanner scanner = new Scanner(System.in);
		int i = 0;
		String requiredString = null; // String that user will input required date with

		//Checks for valid service type and passes through. Else prints error and returns to menu
		System.out.print("Enter Service Type (SD/SS): ");
		String serviceType = scanner.nextLine().toUpperCase();
		try {
			system.checkServiceType(serviceType);
		} catch (InvalidServiceTypeException e) {
			System.out.println(e.getMessage());
			run();
		}

		/*
		 * User enters input for date required in format dd/MM/yyyy, the format is
		 * checked and if invalid format message is printed and user loops back. If
		 * format is correct and valid for given service type a copy user string is
		 * converted to DateTime object and overrides the required DateTime object.
		 */
		while (i == 0) {
			System.out.print("Enter Required Booking Date: ");
			Scanner scanner1 = new Scanner(System.in);
			requiredString = scanner1.nextLine().toUpperCase();
			try {
				system.checkDateFormat(requiredString);
				system.checkValidDateForService(requiredString, serviceType);
				i = 1;
			} catch (InvalidDateException e) {
				System.out.println(e.getMessage());
			}
		}
		DateTime required = system.convertStringToTime(requiredString);

		/*
		 * Checks MiRidesSystem to see if there are available cars. If false prints
		 * error and returns to menu. Else prints available cars in list
		 */
		int j = 0;
		Car[] availableCarsForDate = system.availableCars(required, serviceType);
		if (availableCarsForDate[0] != null) {
			System.out.println("Available " + serviceType + " cars for " + requiredString);
		}
		while (availableCarsForDate[j] != null && j < availableCarsForDate.length) {
			System.out
					.println("____________________________________________________________"
							+ "________________________\n");
			System.out.println(availableCarsForDate[j].getDetails());
			j++;
		}
		if (j == 0) {
			System.out.println("Error - No cars were found on this date.\n");
		}
		run(); // Returns to menu display
	}

	/*
	 * Prints list of all Car details of all Car objects found in allCars array in
	 * MiRidesSystem in specific order and based on service type then returns 
	 * console to display menu
	 */
	public void displayAll() {

		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter Service Type (SD/SS): ");
		String serviceType = scanner.nextLine();
		try {
			system.checkServiceType(serviceType);
		} catch (InvalidServiceTypeException e) {
			System.out.println(e.getMessage());
			run();
		}
		System.out.print("Enter Sort Order (A/D): ");
		String sortOrder = scanner.nextLine();
		try {
			system.checkSortOrder(sortOrder);
		} catch (InvalidSortOrderException e) {
			System.out.println(e.getMessage());
			run();
		}
		try {
			system.displayAll(serviceType, sortOrder);
		} catch (InvalidRegException e) {
			System.out.println(e.getMessage());
		}
		run();
	}

	/*
	 * Adds 12 hardcoded cars, with 4 booked only, and 4 with completed bookings into
	 * allCars array in MiRidesSystem, only if cars are not already in array. Then
	 * returns console to display menu
	 */
	public void seedData() {

		try {
			system.seedData();
		} catch (InvalidRefreshmentsException e) {
			System.out.println(e.getMessage());
		} catch (InvalidBookingFeeException e) {
			System.out.println(e.getMessage());
		} catch (InvalidDateException e) {
			System.out.println(e.getMessage());
		} catch (InvalidPassCapException e) {
			System.out.println(e.getMessage());
		} catch (InvalidBookingException e) {
			System.out.println(e.getMessage());
		} catch (InvalidRegException e) {
			System.out.println(e.getMessage());
		}
		run();
	}
}
