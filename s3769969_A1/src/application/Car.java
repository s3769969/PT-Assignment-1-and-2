package application;

import utilities.DateTime;
import java.util.regex.Pattern;
import application.Booking;

/*
 * Class:			Car
 * Description:		The class represents a specific Car
 * Author:			Sebastian Wisidagama - s3769969
 */
public class Car {

	private String regNo;
	private String make;
	private String model;
	private String driverName;
	private int passengerCapacity;
	private boolean available;
	private Booking[] currentBookings;
	private Booking[] pastBookings;
	private double bookingFee;
	private final double STANDARD_BOOKING_FEE = 1.50;
	private double tripFeeRate;

	/*
	 * Creates Car object based on arguments. Changes arguments to satisfy rules
	 * before assigning them to class variables.
	 */
	public Car(String regNo, String make, String model, String driverName, int passengerCapacity) throws InvalidRegException {

		/*
		 * Checks registration string format is in format 'ABC123' else defaults it to
		 * "ZZZ999". Then converts to upper case and assigns to class variable.
		 */
		String regex = "[a-zA-Z]{3}[0-9]{3}";
		if (regNo.length() == 6 && Pattern.matches(regex, regNo)) {
			regNo = regNo.toUpperCase();
			this.regNo = regNo;
		} else {
			throw new InvalidRegException(
					"Error - Registration format is invalid," + " needs to be in the form 'ABC123'\n");
		}

		this.bookingFee = STANDARD_BOOKING_FEE; // Assigns standard booking fee to SD Car
		this.setTripFeeRate(0.3); // Assigns rate of 30% rate to SD Car
		this.make = make; // Assigns make argument to class variable
		this.model = model; // Assigns model argument to class variable
		this.driverName = driverName; // Assigns driver name argument to class variable

		/*
		 * Checks if passenger capacity is less than 1 or greater than 9 and reassigns
		 * default values before assigning to class variable
		 */
		if (passengerCapacity < 1) {
			this.passengerCapacity = 1;
		} else if (passengerCapacity > 9) {
			this.passengerCapacity = 9;
		} else {
			this.passengerCapacity = passengerCapacity;
		}
		currentBookings = new Booking[5]; // Initialises currentBookings[] to 5 elements for each car object
		pastBookings = new Booking[5]; // Initialises pastBookings[] to 5 elements for each car object
		available = true; // Initialises available state of new car object as true
	}

	/*
	 * Booking method for Car. Takes arguments and checks that it satisfies rules.
	 * If all rules are satisfied a booking is made and method returns true.
	 * Otherwise method returns false. If 5 bookings exist in currentBookings array
	 * then method updates available variable to false.
	 */
	public boolean book(String firstName, String lastName, DateTime required, int numPassengers)
			throws InvalidPassCapException, InvalidBookingException {

		// Checks booking date is available and return true/false
		boolean dateAvailable = false;
		dateAvailable = checkForDoubleBooking(required);
		// Checks no more than 5 current bookings, returns true/false and updates
		// available status
		boolean bookingLimit = false;
		bookingLimit = checkForOverBooking(required);
		// Checks date is not in the past or more than a week ahead
		boolean validDate = false;
		validDate = checkValidDate(required);

		/*
		 * Checks that number of passengers is not greater than car capacity. If so, it
		 * is displays error and returns false. Else passes through.
		 */
		if (numPassengers > passengerCapacity) {
			throw new InvalidPassCapException(
					"Error - Number of passengers exceeds passenger capacity for this car.\n");
		}

		/*
		 * Creates new booking object using arguments in currentBookings array if
		 * element is null. Else checks for next null element in the array and
		 * references booking there
		 */

		int i = 0;
		if (dateAvailable && bookingLimit && validDate) {
			Booking booking = new Booking(firstName, lastName, required, numPassengers, this);
			booking.setBookingFee(this.bookingFee);
			while (i < currentBookings.length) {
				if (currentBookings[i] == null) {
					currentBookings[i] = booking;
					i = currentBookings.length;
				} else {
					i++;
				}
			}
		} else {
			return false;
		}
		return true; // Returns true once booking is made
	}

	/*
	 * Checks booking date is not already in currentBookings array. If so, it is
	 * displays error and returns false. Else passes through.
	 */
	public boolean checkForDoubleBooking(DateTime required) throws InvalidBookingException {

		int i = 0;
		while (i < currentBookings.length) {
			if (currentBookings[i] == null) {
				i = currentBookings.length;
			} else if (currentBookings[i].getFormattedPickUpDate().equals(required.getFormattedDate())) {
				throw new InvalidBookingException("Error - Car can only be booked once per day.\n");
			} else if (currentBookings[i].getFormattedPickUpDate().equals(required.getFormattedDate()) == false) {
				i++;
			}
		}
		return true;
	}

	/*
	 * Checks if there are 5 or more elements in currentBookings array. If so, it is
	 * displays error, updates available status of car to false and returns false.
	 * Else, checks elements in the array for null element. If the null element is
	 * the 5th element, available status is changed to false and passes through
	 * loop. If next null element is <5th element, available status is set to true
	 * and passes through the loop.
	 */
	public boolean checkForOverBooking(DateTime required) throws InvalidDateException {

		int i = 0;
		do {
			if (i > 4) {
				available = false;
				throw new InvalidDateException("Error - Car can only have 5 current bookings.\n");
			} else if (currentBookings[i] == null) {
				if (i < 4) {
					available = true;
				} else if (i == 4) {
					available = false;
				}
				i = currentBookings.length;
			} else if (currentBookings[i] != null) {
				i++;
			}
		} while (i < currentBookings.length);
		return true;
	}

	/*
	 * Checks if required booking date is in the past or more than 7 days in the
	 * future. If so, it is displays error and returns false. Else passes through.
	 */
	public boolean checkValidDate(DateTime required) throws InvalidDateException {

		DateTime current = new DateTime();
		int dayDiff = DateTime.actualDiffDays(required, current);
		if (dayDiff < 0) {
			throw new InvalidDateException("Error - Cannot book for days in the past.\n");
		} else if (dayDiff > 7) {
			throw new InvalidDateException("Error - Cannot book for more than 7 days in future.\n");
		}
		return true;
	}

	/*
	 * Checks string argument for date match in currentBookings array. If found,
	 * returns booking ID. Else, returns error.
	 */
	public String getBookingRef(DateTime required) {

		int i = 0;
		while (i < currentBookings.length) {
			if (currentBookings[i] == null) {
				System.out.println("Error - No booking reference found.\n");
				i = currentBookings.length;
			} else if (currentBookings[i].getFormattedPickUpDate().equals(required.getFormattedDate())) {
				return currentBookings[i].getId();
			} else if (currentBookings[i].getFormattedPickUpDate().equals(required.getFormattedDate()) == false) {
				i++;
			}
		}
		return "Error - No booking reference found.\n";
	}

	// Checks arguments match existing past booking and returns total fee. Else,
	// returns error message.
	public String getTotalFee(Double kilometersTravelled, String firstName, String lastName) {

		int i = 0; // Initialise local variable i

		/*
		 * Checks if arguments names match names in past booking for car. If true,
		 * returns total fee. Else, returns error message
		 */
		while (i < pastBookings.length) {
			if (pastBookings[i] == null) {
				i = pastBookings.length;
			} else if (pastBookings[i].getFirstName().equalsIgnoreCase(firstName)
					&& pastBookings[i].getLastName().equalsIgnoreCase(lastName)
					&& pastBookings[i].getKilometersTravelled() == kilometersTravelled) {
				return pastBookings[i].totalFee();
			} else {
				i++;
			}
		}
		return "Error - Booking could not be located\n";
	}

	/*
	 * Checks arguments match existing booking and returns true if booking is
	 * completed. Else, returns false.
	 */
	public boolean completeBookingUsingReg(Double kilometersTravelled, String firstName, String lastName) {

		int i = 0; // Initialise local variable i
		int j = 100; // Initialise local variable j out of booking array lengths

		/*
		 * Checks if arguments names(ignoring case) match names in existing booking for
		 * car. If true overrides j with index number for location of booking in
		 * currentBookings array. Else, passes through and j remains 100
		 */
		while (i < currentBookings.length) {
			if (currentBookings[i] == null) {
				i = currentBookings.length;
			} else if (currentBookings[i].getFirstName().equalsIgnoreCase(firstName)
					&& currentBookings[i].getLastName().equalsIgnoreCase(lastName)) {
				j = i;
				i = currentBookings.length;
			} else {
				i++;
			}
		}

		/*
		 * Assigns i to 0 and if match was found, then references next null element in
		 * pastBookings array with matched booking from currentBookings array and set
		 * distance travelled in Booking object to argument double
		 */
		i = 0;
		if (j != 100) {
			while (i < pastBookings.length) {
				if (pastBookings[i] == null) {
					pastBookings[i] = currentBookings[j];
					pastBookings[i].setKilometersTravelled(kilometersTravelled);
					i = pastBookings.length;
				} else {
					i++;
				}
			}
		}

		/*
		 * Then overrides currentBookings element that was copied to pastBookings[] with
		 * next element in currentBookings array, only if booking was copied. Then all
		 * elements in currentBookings after index j are moved down 1 index.
		 */
		while (j < currentBookings.length) {
			if (j == currentBookings.length - 1) {
				currentBookings[j] = null;
				j = currentBookings.length;
			} else {
				currentBookings[j] = currentBookings[j + 1];
				j++;
			}
		}

		if (j == 100) {
			return false; // If booking could not be completed
		} else {
			return true; // If booking was completed
		}
	}

	/*
	 * Checks if there are in bookings in currentBookings array. If so, returns
	 * true. Else, returns false.
	 */
	public boolean findCurrentBookingByReg() {

		int i = 0;
		if (currentBookings[i] == null) {
			return false;
		} else {
			return true;
		}
	}

	/*
	 * Checks if argument matches a booking in currentBookings array. If so, returns
	 * true. Else, returns false.
	 */
	public boolean findCurrentBooking(String required) {

		int i = 0;
		while (i < currentBookings.length) {
			if (currentBookings[i] == null) {
				i = currentBookings.length;
			} else if (currentBookings[i].getFormattedPickUpDate().contentEquals(required)) {
				return true;
			} else if (currentBookings[i].getFormattedPickUpDate().contentEquals(required) == false) {
				i++;
			}
		}
		return false;
	}

	/*
	 * Checks if argument matches a booking in pastBookings array. If so, returns
	 * true. Else, returns false.
	 */
	public boolean findPastBooking(String required) {

		int i = 0;
		while (i < pastBookings.length) {
			if (pastBookings[i] == null) {
				i = pastBookings.length;
			} else if (pastBookings[i].getFormattedPickUpDate().contentEquals(required)) {
				return true;
			} else if (pastBookings[i].getFormattedPickUpDate().contentEquals(required) == false) {
				i++;
			}
		}
		return false;
	}

	/*
	 * Converts string argument to DateTime object if string is in format
	 * "dd/MM/yyyy" and returns DateTime object
	 */
	public DateTime convertStringToTime(String string) {

		int day = Integer.parseInt(string.substring(0, 2));
		int month = Integer.parseInt(string.substring(3, 5));
		int year = Integer.parseInt(string.substring(6));
		DateTime stringToTime = new DateTime(day, month, year);
		return stringToTime;
	}

	// Getter for registration number string of Car
	public String getRegNo() {
		return regNo;
	}

	// Getter for make string of Car
	public String getMake() {
		return make;
	}

	// Getter for model string of Car
	public String getModel() {
		return model;
	}

	// Getter for driver name string of Car
	public String getDriverName() {
		return driverName;
	}

	// Getter for passenger capacity integer of Car
	public int getPassengerCapacity() {
		return passengerCapacity;
	}

	// Getter for available status of Car in boolean format
	public boolean getAvailable() {
		return available;
	}

	// Setter for available status of Car in boolean format
	public void setAvailable(boolean available) {
		this.available = available;
	}

	// Getter for available status of Car in string format
	public String availableString() {
		if (available) {
			return "YES";
		} else {
			return "NO";
		}
	}

	// Getter for booking fee double of Car
	public double getBookingFee() {
		return bookingFee;
	}

	// Setter for booking fee double of Car
	public void setBookingFee(double bookingFee) {
		this.bookingFee = bookingFee;
	}

	public double getTripFeeRate() {
		return tripFeeRate;
	}

	// Setter for trip fee rate
	public void setTripFeeRate(double tripFeeRate) {
		this.tripFeeRate = tripFeeRate;
	}

	// Getter for Current Bookings
	public Booking[] getCurrentBookings() {
		return currentBookings;
	}

	// Setter for Current Bookings
	public void setCurrentBookings(Booking[] currentBookings) {
		this.currentBookings = currentBookings;
	}

	// Getter for Past Bookings
	public Booking[] getPastBookings() {
		return pastBookings;
	}

	// Setter for Past Bookings
	public void setPastBookings(Booking[] pastBookings) {
		this.pastBookings = pastBookings;
	}

	// Getter for Car details in human readable format
	public String getDetails() {
		return "RegNo:\t\t\t" + regNo + "\n" + "Make and Model:\t\t" + make + " " + model + "\n" + "DriverName:\t\t"
				+ driverName + "\n" + "Capacity:\t\t" + passengerCapacity + "\nStandard Fee:\t\t$"
				+ String.format("%.2f", this.getBookingFee()) + "\nAvailable:\t\t" + availableString()
				+ "\n\nCURRENT BOOKINGS" + bookingsGetDetails(getCurrentBookings()) + "\nPAST BOOKINGS"
				+ bookingsGetDetails(getPastBookings());
	}

	// Returns string for all current/past bookings in human readable format
	public String bookingsGetDetails(Booking[] bookings) {
		if (bookings[0] == null) {
			return "\n\t\t\t" + "__________________________________________\n" + "\t\t\tNo bookings found\n\n";
		}
		String booking = "\n";
		int i = 0;
		while (bookings[i] != null || i == bookings.length) {
			booking += "\n\t\t\t" + "__________________________________________\n";
			String lines[] = bookings[i].getDetails().split("\\r?\\n");
			for (String string : lines) {
				booking += "\t\t\t" + string + "\n";
			}
			i++;
		}
		return booking;
	}

	// Getter for Car details in predefined format for computer
	public String toString() {
		return regNo.toUpperCase() + ":" + make + ":" + model + ":" + driverName + ":" + passengerCapacity + ":"
				+ availableString() + ":" + String.format("%.2f", this.getBookingFee())
				+ bookingsToString(getCurrentBookings()) + bookingsToString(getPastBookings());
	}

	// Returns string for all current/past bookings in predefined format for
	// computer
	public String bookingsToString(Booking[] bookings) {
		if (bookings[0] == null) {
			return "";
		}
		String booking = "";
		int i = 0;
		while (bookings[i] != null || i == bookings.length) {
			booking += "\n|" + bookings[i].toString();
			i++;
		}
		return booking;
	}
}