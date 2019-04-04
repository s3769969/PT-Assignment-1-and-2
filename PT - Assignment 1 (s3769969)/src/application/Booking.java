package application;

import utilities.DateTime;
import application.Car;

public class Booking {

	private String id;
	private double bookingFee;
	private DateTime pickUpDateTime;
	private String firstName;
	private String lastName;
	private int numPassengers;
	private double kilometersTravelled;
	private double tripFee;
	private Car car;

	public Booking(String firstName, String lastName, DateTime required, int numPassengers, Car car) {
		if (firstName.length() < 3) {
			firstName = "Invalid";
		} else {
			this.firstName = firstName;
		}
		if (lastName.length() < 3) {
			lastName = "Invalid";
		} else {
			this.lastName = lastName;
		}

		DateTime current = new DateTime();
		int dayDiff = current.diffDays(required, current);
		if (dayDiff > 7 || dayDiff < -7) {
			required = current;
		} else {
			this.pickUpDateTime = required;
		}

		if(numPassengers < 1) {
			numPassengers = 1;
		}else if (numPassengers > 9) {
			numPassengers = 9;
		}else {
			this.numPassengers = numPassengers;
		}
		
		this.car = car;
		String regNo = car.getRegNo();
		id = regNo.toUpperCase() + "_" + firstName.toUpperCase().substring(0, 3)
				+ lastName.toUpperCase().substring(0, 3) + "_" + required.getEightDigitDate();
	}

	public String getId() {
		if (id == null) {
			return "Error - Id not found";
		}else {
			return id;
		}
	}
	
	public String getFirstName() {
		if (firstName == null) {
			return "Error - First Name not found";
		}else {
		return firstName;
		}
	}

	public String getLastName() {
		if (lastName == null) {
			return "Error - Last Name not found";
		}else {
		return lastName;
		}
	}

	public DateTime getPickUpDateTime() {
		if (pickUpDateTime == null) {
			return pickUpDateTime = new DateTime();
		}else {
		return pickUpDateTime;
		}
	}

	public int getNumPassengers() {
		if (numPassengers < 1 || numPassengers > 9) {
			return 1;
		}else {
		return numPassengers;
		}
	}

	public Car getCar() {
		return car;
	}

	public String travelled() {
		boolean finished = false;
		if (finished) {
			String travelled = "" + kilometersTravelled;
			return travelled;
		} else
			return "N/A";
	}

	public String tripFee() {
		boolean finished = false;
		if (finished) {
			bookingFee = 1.5;
			String tripFee = "" + kilometersTravelled * bookingFee * 0.3;
			return tripFee;
		} else
			return "N/A";
	}

	public String getDetails() {

		return "id:           " + id + "\n" + "Booking Fee:  " + "$" + bookingFee + "\n" + "Pick up Date: "
				+ getBookedDate() + "\n" + "Name:         " + firstName + " " + lastName + "\n" + "Passengers:   "
				+ numPassengers + "\n" + "Travelled:    " + travelled() + "km" + "\n" + "Trip Fee:     " + tripFee()
				+ "\n" + "Car Id:       " + car.getRegNo().toUpperCase() + "\n";
	}

	public String toString() {
		return id + ":" + bookingFee + ":" + getDate() + ":" + firstName + " " + lastName + ":" + numPassengers + ":"
				+ travelled() + ":" + tripFee + ":" + car.getRegNo().toUpperCase();
	}

	public String getDate() {
		return pickUpDateTime.getEightDigitDate();
	}

	public String getBookedDate() {
		return pickUpDateTime.getFormattedDate();
	}
}
