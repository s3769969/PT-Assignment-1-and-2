package application;

import utilities.DateTime;

public class SilverServiceCar extends Car{

	private String[] refreshments;
	
	public SilverServiceCar() {
		super(regNo, make, model, driverName, passengerCapacity, bookingFee);
	}
	
	@Override
	public SilverServiceCar(String regNo, String make, String model, String driverName,
			int passengerCapacity, double bookingFee, String[] refreshments) {
		super(regNo, make, model, driverName, passengerCapacity, bookingFee);
	}

	@Override
	public boolean book(String firstName, String lastName, DateTime required, int numPassengers) {
		// TODO Auto-generated method stub
		return super.book(firstName, lastName, required, numPassengers);
	}

	@Override
	public String getBookingRef(DateTime required) {
		// TODO Auto-generated method stub
		return super.getBookingRef(required);
	}

	@Override
	public String getTotalFee(Double kilometersTravelled, String firstName, String lastName) {
		// TODO Auto-generated method stub
		return super.getTotalFee(kilometersTravelled, firstName, lastName);
	}

	@Override
	public boolean completeBookingUsingReg(Double kilometersTravelled, String firstName, String lastName) {
		// TODO Auto-generated method stub
		return super.completeBookingUsingReg(kilometersTravelled, firstName, lastName);
	}

	@Override
	public boolean findCurrentBookingByReg() {
		// TODO Auto-generated method stub
		return super.findCurrentBookingByReg();
	}

	@Override
	public boolean findCurrentBooking(String required) {
		// TODO Auto-generated method stub
		return super.findCurrentBooking(required);
	}

	@Override
	public boolean findPastBooking(String required) {
		// TODO Auto-generated method stub
		return super.findPastBooking(required);
	}

	@Override
	public DateTime convertStringToTime(String string) {
		// TODO Auto-generated method stub
		return super.convertStringToTime(string);
	}

	@Override
	public String getRegNo() {
		// TODO Auto-generated method stub
		return super.getRegNo();
	}

	@Override
	public String getMake() {
		// TODO Auto-generated method stub
		return super.getMake();
	}

	@Override
	public String getModel() {
		// TODO Auto-generated method stub
		return super.getModel();
	}

	@Override
	public String getDriverName() {
		// TODO Auto-generated method stub
		return super.getDriverName();
	}

	@Override
	public int getPassengerCapacity() {
		// TODO Auto-generated method stub
		return super.getPassengerCapacity();
	}

	@Override
	public boolean getAvailable() {
		// TODO Auto-generated method stub
		return super.getAvailable();
	}

	@Override
	public String availableString() {
		// TODO Auto-generated method stub
		return super.availableString();
	}

	@Override
	public String getDetails() {
		// TODO Auto-generated method stub
		return super.getDetails();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	public String[] getRefreshments() {
		return refreshments;
	}

	public void setRefreshments(String[] refreshments) {
		this.refreshments = refreshments;
	}

}
