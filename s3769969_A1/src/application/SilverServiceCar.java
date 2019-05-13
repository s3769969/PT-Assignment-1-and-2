package application;

import utilities.DateTime;

/*
 * Class:			SilverServiceCar (SS Car)
 * Description:		The class represents a specific SilverServiceCar
 * Author:			Sebastian Wisidagama - s3769969
 */
public class SilverServiceCar extends Car{

	private String[] refreshments;
	private double bookingFee;
	
//	Car car = new SilverServiceCar("SSC123", "Toyota", "Hilux", "Road Warrior", 9, 1.50, refreshments);
//	Car car4 = (Car) new SilverServiceCar("SSC123", "Toyota", "Hilux", "Road Warrior", 9, 1.50, refreshments);
//	
//	Car car2 = (SilverServiceCar) new Car("SSC123", "Toyota", "Hilux", "Road Warrior", 9);
//	Car car3 = (SilverServiceCar) car;
	
	
	/*Creates SS Car object based on arguments. Changes arguments to satisfy rules before assigning them
	to class variables.*/
	public SilverServiceCar(String regNo, String make, String model, String driverName,
			int passengerCapacity, double bookingFee, String[] refreshments) {
		super(regNo, make, model, driverName, passengerCapacity);
		if (bookingFee < 3.00) {
			this.bookingFee = 3.00;
		}
		this.refreshments = refreshments;
	}
	
	@Override
	public boolean book(String firstName, String lastName, DateTime required, int numPassengers) {
		
		DateTime currentExact = new DateTime(); //Initialises exact current DateTime object
		String currentExactString = currentExact.getFormattedDate(); //Converts current DateTime to string
		DateTime current = convertStringToTime(currentExactString); //Initialises DateTime to 12am today
		
		/*Checks required booking date more than 3 days in the future. If so, it is displays error and
		returns false. Else passes through.*/
		current = new DateTime();
		int dayDiff = DateTime.diffDays(required, current);
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

	public String pastBookingsToString() {
		return "";
	}
	
	public String[] getRefreshments() {
		return refreshments;
	}

	public void setRefreshments(String[] refreshments) {
		this.refreshments = refreshments;
	}

	public double getBookingFee() {
		return bookingFee;
	}

	public void setBookingFee(double bookingFee) {
		this.bookingFee = bookingFee;
	}

	@Override
	public boolean getAvailable() {
		return super.getAvailable();
	}

	@Override
	public String availableString() {
		return super.availableString();
	}

	@Override
	public String getDetails() {
		return super.getDetails() + "Standard fee: " + getBookingFee() + "\n";
	}

	@Override
	public String toString() {
		return "RegNo:          " + regNo + "\n" + "Make and Model: " + make + " " + model + "\n"
				+ "DriverName:     " + driverName + "\n" + "Capacity:       " + passengerCapacity
				+ "\n" + "Available:      " + availableString() + "\n";;
	}

	public String currentBookingsToString() {
		
		if (currentBookings[i] != null){
			
		}
		
		return "";
	}
}
