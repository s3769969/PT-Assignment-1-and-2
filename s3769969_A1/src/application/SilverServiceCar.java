package application;

import utilities.DateTime;

/*
 * Class:			SilverServiceCar (SS Car)
 * Description:		The class represents a specific SilverServiceCar
 * Author:			Sebastian Wisidagama - s3769969
 */
public class SilverServiceCar extends Car {

	private String[] refreshments;

	/*
	 * Creates SS Car object based on arguments. Throws exception if argument does not satisfy rules
	 * before assigning them to class variables.
	 */
	protected SilverServiceCar(String regNo, String make, String model, String driverName, int passengerCapacity,
			double bookingFee, String[] refreshments) throws InvalidRefreshmentsException, InvalidBookingFeeException,
			InvalidRegException {
		super(regNo, make, model, driverName, passengerCapacity);
		// throws error if SSCar booking fee < $3 else sets booking fee to driver input else assigns fee
		if (bookingFee < 3.00) {
			throw new InvalidBookingFeeException("Error - SS Cars minimum booking fee is $3\n");
		}else {
			setBookingFee(bookingFee);
		}
		// throws error if < 3 refreshments or repeated refreshments else assigns refreshments
		if (refreshments.length < 3) {
			throw new InvalidRefreshmentsException(
					"Error - you need a minimum of 3 refreshments qualify as a SS Car\n");
		}
		int i = 0;
		int j = 0;
		for (i = 0; i < refreshments.length; i++) {
			for (j = i + 1; j < refreshments.length; j++) {
				if (refreshments[i].trim().equalsIgnoreCase(refreshments[j].trim())) {
					throw new InvalidRefreshmentsException("Error - you have listed the same refreshment twice\n");
				}
			}
		}
		this.refreshments = refreshments; // Assigns car refreshments list to string array in SSCar
		this.setTripFeeRate(0.4); // Assigns trip fee rate of 40% rate for SS Cars
	}

	//Books SS Car if no rules are broken, else throws relevant exception
	@Override
	protected boolean book(String firstName, String lastName, DateTime required, int numPassengers)
			throws InvalidPassCapException, InvalidBookingException {

		/*
		 * Checks if required booking date is more than 3 days in the future. If so, it
		 * is displays error and returns false. Else passes through.
		 */
		DateTime current = new DateTime();
		int dayDiff = DateTime.actualDiffDays(required, current);
		if (dayDiff > 3) {
			throw new InvalidBookingException("Error - Cannot book for more than 3 days in future.\n");
		}
		return super.book(firstName, lastName, required, numPassengers); // Returns booking through superclass

	}

	/*
	 * Checks string argument for date match in currentBookings array. If found,
	 * returns booking ID. Else, returns error.
	 */
	public String getBookingRef(DateTime required) {
		return super.getBookingRef(required);
	}

	// Checks arguments match existing past booking and returns total fee. Else, returns error message.
	protected String getTotalFee(Double kilometersTravelled, String firstName, String lastName) {
		return super.getTotalFee(kilometersTravelled, firstName, lastName);
	}

	/*
	 * Checks arguments match existing booking and returns true if booking is
	 * completed. Else, returns false.
	 */
	protected boolean completeBookingUsingReg(Double kilometersTravelled, String firstName, String lastName) {
		return super.completeBookingUsingReg(kilometersTravelled, firstName, lastName);
	}

	/*
	 * Checks if there are in bookings in currentBookings array. If so, returns
	 * true. Else, returns false.
	 */
	protected boolean findCurrentBookingByReg() {
		return super.findCurrentBookingByReg();
	}

	/*
	 * Checks if argument matches a booking in currentBookings array. If so, returns
	 * true. Else, returns false.
	 */
	protected boolean findCurrentBooking(String required) {
		return super.findCurrentBooking(required);
	}

	/*
	 * Checks if argument matches a booking in pastBookings array. If so, returns
	 * true. Else, returns false.
	 */
	protected boolean findPastBooking(String required) {
		return super.findPastBooking(required);
	}

	/*
	 * Converts string argument to DateTime object if string is in format
	 * "dd/MM/yyyy" and returns DateTime object
	 */
	protected DateTime convertStringToTime(String string) {
		return super.convertStringToTime(string);
	}

	// Getter for registration number string of SS Car
	protected String getRegNo() {
		return super.getRegNo();
	}

	// Getter for make string of SS Car
	protected String getMake() {
		return super.getMake();
	}

	// Getter for model string of SS Car
	protected String getModel() {
		return super.getModel();
	}

	// Getter for driver name string of SS Car
	public String getDriverName() {
		return super.getDriverName();
	}

	// Getter for passenger capacity integer of SS Car
	protected int getPassengerCapacity() {
		return super.getPassengerCapacity();
	}

	// Getter for refreshments list of SS Car
	protected String[] getRefreshments() {
		return refreshments;
	}

	// Setter for refreshments list of SS Car
	protected void setRefreshments(String[] refreshments) {
		this.refreshments = refreshments;
	}

	// Getter for available status of SS Car in boolean format
	protected boolean getAvailable() {
		return super.getAvailable();
	}

	// Setter for available status of SS Car in boolean format
	protected void setAvailable(boolean available) {
		super.setAvailable(available);
	}
	
	// Getter for available status of SS Car in string format
	protected String availableString() {
		return super.availableString();
	}

	// Getter for booking fee double of SS Car
	protected double getBookingFee() {
		return super.getBookingFee();
	}

	// Setter for booking fee double of SS Car
	protected void setBookingFee(double bookingFee) {
		super.setBookingFee(bookingFee);
	}

	// Getter for trip fee rate of SS Car
	protected double getTripFeeRate() {
		return super.getTripFeeRate();
	}

	// Setter for trip fee rate of SS Car
	protected void setTripFeeRate(double tripFeeRate) {
		super.setTripFeeRate(tripFeeRate);
	}

	// Getter for Current Bookings
	protected Booking[] getCurrentBookings() {
		return super.getCurrentBookings();
	}

	// Setter for Current Bookings
	protected void setCurrentBookings(Booking[] currentBookings) {
		super.setCurrentBookings(currentBookings);
	}

	// Getter for Past Bookings
	protected Booking[] getPastBookings() {
		return super.getPastBookings();
	}

	// Setter for Past Bookings
	protected void setPastBookings(Booking[] pastBookings) {
		super.setPastBookings(pastBookings);
	}

	// Getter for SS Car details in human readable format
	@Override
	public String getDetails() {
		return "RegNo:\t\t\t" + getRegNo() + "\n" + "Make and Model:\t\t" + getMake() + " " + getModel() + "\n"
				+ "DriverName:\t\t" + getDriverName() + "\n" + "Capacity:\t\t" + getPassengerCapacity() + "\n"
				+ "Standard Fee:\t\t$" + this.getBookingFee() + "\nAvailable:\t\t" + availableString()
				+ "\n\nRefreshments" + refreshmentsGetDetails(refreshments) + "\nCURRENT BOOKINGS"
				+ bookingsGetDetails(getCurrentBookings()) + "\nPAST BOOKINGS" + bookingsGetDetails(getPastBookings());
	}

	// Getter for SS Car details in predefined format for computer
	@Override
	public String toString() {
		return getRegNo().toUpperCase() + ":" + getMake() + ":" + getModel() + ":" + getDriverName() + ":"
				+ getPassengerCapacity() + ":" + availableString() + ":" + String.format("%.2f", this.getBookingFee())
				+ refreshmentsToString(refreshments) + bookingsToString(getCurrentBookings())
				+ bookingsToString(getPastBookings());
	}

	// Returns string for all refreshments in human readable format
	protected String refreshmentsGetDetails(String[] refreshments) {
		if (refreshments[0] == null) {
			return "No refreshments provided";
		}
		String items = "\n";
		for (int i = 0; i < refreshments.length; i++) {
			int itemNum = i + 1;
			items += "Item " + itemNum + "\t\t" + refreshments[i].trim() + "\n";
		}
		return items;
	}

	/* Returns string for all refreshments in predefined format for computer
	*	
	*ALGORITHM
	*IF ARGUMENT ARRAY BEGINS WITH NULL ELEMENT
	*	RETURN EMPTY STRING
	*INSTANTIATE ITEMS STRING
	*FOR EACH ELEMENT IN REFRESHMENTS ARRAY WHILE < ARRAY LENGTH
	*	ADD 1 COUNT TO ITEM NUMBER
	*	ADD ":ITEM" STRING AND TRIMMED REFRESHMENTS ELEMENT TO ITEM STRING
	*RETURN ITEMS STRING
	*/
	protected String refreshmentsToString(String[] refreshments) {
		if (refreshments[0] == null) {
			return "";
		}
		String items = "";
		for (int i = 0; i < refreshments.length; i++) {
			int itemNum = i + 1;
			items += ":Item " + itemNum + " " + refreshments[i].trim();
		}
		return items;
	}

	// Returns string for all current/past bookings in human readable format
	protected String bookingsGetDetails(Booking[] bookings) {
		return super.bookingsGetDetails(bookings);
	}

	// Returns string for all current/past bookings in predefined format for computer
	protected String bookingsToString(Booking[] bookings) {
		return super.bookingsToString(bookings);
	}
}
