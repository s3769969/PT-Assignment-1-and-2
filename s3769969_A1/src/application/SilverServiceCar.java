package application;

import utilities.DateTime;

/*
 * Class:			SilverServiceCar (SS Car)
 * Description:		The class represents a specific SilverServiceCar
 * Author:			Sebastian Wisidagama - s3769969
 */
public class SilverServiceCar extends Car{

	private String[] refreshments;
	
	
	/*Creates SS Car object based on arguments. Changes arguments to satisfy rules before assigning
	them to class variables.*/
	public SilverServiceCar(String regNo, String make, String model, String driverName,
			int passengerCapacity, double bookingFee, String[] refreshments) throws InvalidRefreshmentsException {
		super(regNo, make, model, driverName, passengerCapacity);
		//Assigns standard SSCar booking fee if < $3 else sets booking fee to driver input
		if (bookingFee < 3.00) {
			setBookingFee(3.00);
		}else {
			setBookingFee(bookingFee);
		}
		if (refreshments.length < 3) {
			throw new InvalidRefreshmentsException("Error - you need a minimum of 3 refreshments qualify as a SS Car\n");
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
		this.refreshments = refreshments; //Assigns car refreshments list to string array in SSCar
		this.setTripFeeRate(0.4); //Assigns rate of 40% rate for SS Cars
	}
	
	@Override
	public boolean book(String firstName, String lastName, DateTime required, int numPassengers) throws InvalidPassCapException, InvalidBookingException {
		
		/*Checks if required booking date is more than 3 days in the future. If so, it is displays
		error and returns false. Else passes through.*/
		DateTime current = new DateTime();
		int dayDiff = DateTime.actualDiffDays(required, current);
		if (dayDiff > 3) {
			throw new InvalidDateException("Error - Cannot book for more than 3 days in future.\n");
		}
		return super.book(firstName, lastName, required, numPassengers); //Returns booking through superclass
		
	}

	@Override
	public String getBookingRef(DateTime required) {
		return super.getBookingRef(required);
	}

	@Override
	public String getTotalFee(Double kilometersTravelled, String firstName, String lastName) {
		return super.getTotalFee(kilometersTravelled, firstName, lastName);
	}

	@Override
	public boolean completeBookingUsingReg(Double kilometersTravelled, String firstName, String lastName) {
		return super.completeBookingUsingReg(kilometersTravelled, firstName, lastName);
	}

	@Override
	public boolean findCurrentBookingByReg() {
		return super.findCurrentBookingByReg();
	}

	@Override
	public boolean findCurrentBooking(String required) {
		return super.findCurrentBooking(required);
	}

	@Override
	public boolean findPastBooking(String required) {
		return super.findPastBooking(required);
	}

	@Override
	public DateTime convertStringToTime(String string) {
		return super.convertStringToTime(string);
	}

	@Override
	public String getRegNo() {
		return super.getRegNo();
	}

	@Override
	public String getMake() {
		return super.getMake();
	}

	@Override
	public String getModel() {
		return super.getModel();
	}

	@Override
	public String getDriverName() {
		return super.getDriverName();
	}

	@Override
	public int getPassengerCapacity() {
		return super.getPassengerCapacity();
	}

	public String[] getRefreshments() {
		return refreshments;
	}

	public void setRefreshments(String[] refreshments) {
		this.refreshments = refreshments;
	}

	@Override
	public boolean getAvailable() {
		return super.getAvailable();
	}

	@Override
	public String availableString() {
		return super.availableString();
	}
	
	//Getter for booking fee double of SS Car
	@Override
	public double getBookingFee() {
		return super.getBookingFee();
	}

	//Setter for booking fee double of SS Car
	@Override
	public void setBookingFee(double bookingFee) {
		super.setBookingFee(bookingFee);
	}

	@Override
	public double getTripFeeRate() {
		return super.getTripFeeRate();
	}

	@Override
	public void setTripFeeRate(double tripFeeRate) {
		super.setTripFeeRate(tripFeeRate);
	}

	public Booking[] getCurrentBookings() {
		return super.getCurrentBookings();
	}

	public void setCurrentBookings(Booking[] currentBookings) {
		super.setCurrentBookings(currentBookings);
	}

	public Booking[] getPastBookings() {
		return super.getPastBookings();
	}

	public void setPastBookings(Booking[] pastBookings) {
		super.setPastBookings(pastBookings);
	}
	
	public String currentBookingsToString(){ 
		return "";
	}
	
	public String pastBookingsToString(){
		return "";
	}

	@Override
	public String getDetails() {
		return "RegNo:\t\t\t" + getRegNo() + "\n" + "Make and Model:\t\t" + getMake() + " " + getModel() + "\n"
		+ "DriverName:\t\t" + getDriverName() + "\n" + "Capacity:\t\t" + getPassengerCapacity() + "\n" + "Standard Fee:\t\t$"
		+ this.getBookingFee() + "\nAvailable:\t\t" + availableString() + "\n\nRefreshments" + refreshmentsGetDetails(refreshments)
		+ "\nCURRENT BOOKINGS" + bookingsGetDetails(getCurrentBookings()) + "\nPAST BOOKINGS" + bookingsGetDetails(getPastBookings());
	}
	
	@Override
	public String toString() {
		return getRegNo().toUpperCase() + ":" + getMake() + ":" + getModel() + ":" + getDriverName() + ":" + getPassengerCapacity()
		+ ":" + availableString() + ":" + String.format("%.2f", this.getBookingFee()) + refreshmentsToString(refreshments) +
		bookingsToString(getCurrentBookings()) + bookingsToString(getPastBookings());
	}
	
	public String refreshmentsGetDetails(String[] refreshments) {
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
	
	public String refreshmentsToString(String[] refreshments) {
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
	
	public String bookingsGetDetails(Booking[] bookings) {
		return super.bookingsGetDetails(bookings);
	}
	
	public String bookingsToString(Booking[] bookings) {
		return super.bookingsToString(bookings);
	}
}
