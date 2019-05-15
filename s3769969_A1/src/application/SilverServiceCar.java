package application;

import utilities.DateTime;

/*
 * Class:			SilverServiceCar (SS Car)
 * Description:		The class represents a specific SilverServiceCar
 * Author:			Sebastian Wisidagama - s3769969
 */
public class SilverServiceCar extends Car{

	private String[] refreshments;
	
	
//	Car car = new SilverServiceCar("SSC123", "Toyota", "Hilux", "Road Warrior", 9, 1.50, refreshments);
//	Car car4 = (Car) new SilverServiceCar("SSC123", "Toyota", "Hilux", "Road Warrior", 9, 1.50, refreshments);
//	
//	Car car2 = (SilverServiceCar) new Car("SSC123", "Toyota", "Hilux", "Road Warrior", 9);
//	Car car3 = (SilverServiceCar) car;
	
	
	/*Creates SS Car object based on arguments. Changes arguments to satisfy rules before assigning
	them to class variables.*/
	public SilverServiceCar(String regNo, String make, String model, String driverName,
			int passengerCapacity, double bookingFee, String[] refreshments) {
		super(regNo, make, model, driverName, passengerCapacity);
		if (bookingFee < 3.00) {
			setBookingFee(3.00);
		}
		this.refreshments = refreshments;
		this.setTripFeeRate(0.4); //Assigns rate of 40% rate for SS Cars
	}
	
	@Override
	public boolean book(String firstName, String lastName, DateTime required, int numPassengers) {
		
		/*Checks if required booking date is more than 3 days in the future. If so, it is displays
		error and returns false. Else passes through.*/
		DateTime current = new DateTime();
		int dayDiff = DateTime.actualDiffDays(required, current);
		if (dayDiff > 3) {
			System.out.println("Error - Cannot book for more than 3 days in future.\n");
			return false;
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
		return super.getDetails() + "Standard Fee:\t\t" + this.getBookingFee() + "\n\nRefreshments"
				+ " Available\n" + refreshmentsToString(refreshments) + "CURRENT BOOKINGS" + currentBookingsToString()
				+ "PAST BOOKINGS" + pastBookingsToString();
	}
	
	@Override
	public String toString() {
		return super.toString() + "Standard Fee:\t\t" + this.getBookingFee() + "\n\nRefreshments"
				+ " Available\n" + refreshmentsToString(refreshments) + "CURRENT BOOKINGS" + currentBookingsToString()
				+ "PAST BOOKINGS" + pastBookingsToString();
	}
	
	public String refreshmentsToString(String[] array) {
		String items = null;
		for (int i = 0; i < array.length; i++) {
			int itemNum = i + 1;
			items += "\n" + "Item " + itemNum + "\t\t" + array[i];
		}
		return items;
	}
	
	public String bookingsToString(String[] array) {
		String items = null;
		for (int i = 0; i < array.length; i++) {
			int itemNum = i + 1;
			items += "\n\t\t\t" + "---------------------------------------" +
					"\n\t\t\t" + "Item " + itemNum + "\t\t" + array[i];
		}
		return items;
	}
}
