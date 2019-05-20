package application;

import utilities.DateTime;
import application.Car;

/*
 * Class:			Booking
 * Description:		The class represents a specific Booking
 * Author:			Sebastian Wisidagama - s3769969
 */
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

	/*Creates Booking object based on arguments. Changes arguments to satisfy rules before assigning
	them to class variables.*/
	public Booking(String firstName, String lastName, DateTime required, int numPassengers, Car car) {
		
		this.car = car;
		if (firstName.trim().length() < 3) {
			firstName = "Invalid"; //Makes first name invalid if first name length is <3 characters
		} else {
			this.firstName = firstName;
		}
		if (lastName.trim().length() < 3) {
			lastName = "Invalid"; //Makes last name invalid if last name length is <3 characters
		} else {
			this.lastName = lastName;
		}

		/*Creates new DateTime instance and compares to argument. If argument is in the past or greater
	 	than 7 days in future pickUpDateTime is made to current DateTime. Else, it remains as the
	 	argument. Then is assigned to class variable*/ 
		DateTime current = new DateTime();
		int dayDiff = DateTime.actualDiffDays(required, current);
		if (dayDiff > 7 || dayDiff < 0) {
			required = current;
			this.pickUpDateTime = current;
		} else {
			this.pickUpDateTime = required;
		}

		/*Checks if number of passengers is less than 1 or greater than passenger capacity and reassigns
		default values of 1	or passenger capacity before assigning to class variable. If not, uses
		argument integer and assigns to class variable*/
		int passengerCapacity = car.getPassengerCapacity();
		if(numPassengers > passengerCapacity) {
			numPassengers = passengerCapacity;
			this.numPassengers = passengerCapacity;
		}else if (numPassengers < 1) {
			this.numPassengers = 1;
		}else {
			this.numPassengers = numPassengers;
		}
		
		//Creates booking ID based on corrected and reformatted arguments
		id = car.getRegNo().toUpperCase() + "_" + firstName.toUpperCase().substring(0, 3)
				+ lastName.toUpperCase().substring(0, 3) + "_" + required.getEightDigitDate();
	}

	//Getter for booking id string
	public String getId() {
			return id;
	}
	
	//Getter for first name string
	public String getFirstName() {
			return firstName;
	}

	//Getter for last name string
	public String getLastName() {
			return lastName;
	}

	//Getter for pick up time DateTime object
	public DateTime getPickUpDateTime() {
			return pickUpDateTime;
	}

	//Getter for pick up time String in "dd/MM/yyyy" format
	public String getFormattedPickUpDate() {
		return pickUpDateTime.getFormattedDate();
	}

	//Getter for number of passengers integer
	public int getNumPassengers() {
			return numPassengers;
	}

	//Getter for Car object associated with booking
	public Car getCar() {
		return car;
	}

	//Getter for kilometers travelled double
	public double getKilometersTravelled() {
		return kilometersTravelled;
	}

	//Setter for kilometers travelled double
	public void setKilometersTravelled(double kilometersTravelled) {
		this.kilometersTravelled = kilometersTravelled;
	}

	//Getter for trip fee double
	public double getTripFee(Double tripFeeRate) {
		tripFee = kilometersTravelled * bookingFee * tripFeeRate;
		return tripFee ;
	}
	
	//Getter for booking fee double
	public double getBookingFee() {
		return bookingFee;
	}
	
	//Setter for booking fee double
		public double setBookingFee(double bookingFee) {
			this.bookingFee = bookingFee;
			return bookingFee;
		}
	
	//Getter for kilometers travelled string. Returns N/A, if booking not completed
	public String travelled() {
		if (getKilometersTravelled() != 0) {
			return kilometersTravelled + "km";
		} else
			return "N/A";
	}
	
	//Getter for kilometers travelled for toString method. Returns 0.0, if booking not completed
	public String travelledToString() {
		if (getKilometersTravelled() != 0) {
			return "" + kilometersTravelled;
		} else
			return "0.0";
	}
	
	//Getter for trip fee string. Returns N/A, if booking not completed
	public String tripFee() {
		if (getKilometersTravelled() != 0) {
			tripFee = kilometersTravelled * bookingFee * this.getCar().getTripFeeRate();
			String tripFeeTo2Decimals = String.format("%.2f", tripFee);
			return "" + tripFeeTo2Decimals;
		} else
			return "N/A";
	}
	
	//Getter for trip fee cost for toString. Returns 0.0, if booking not completed
	public String tripFeeToString() {
		if (getKilometersTravelled() != 0) {
			tripFee = kilometersTravelled * bookingFee * this.getCar().getTripFeeRate();
			String tripFeeTo2Decimals = String.format("%.2f", tripFee);
			return "" + tripFeeTo2Decimals;
		} else
			return "0.0";
	}
	
	//Getter for total fee string. Returns N/A, if booking not completed
	public String totalFee() {
		if (getKilometersTravelled() != 0) {
			double totalFee = bookingFee + kilometersTravelled * bookingFee * this.getCar().getTripFeeRate();
			String totalFeeTo2Decimals = String.format("%.2f", totalFee);
			return "" + totalFeeTo2Decimals;
		} else
			return "N/A";
	}

	//Getter for Booking details in human readable format
	public String getDetails() {

		return "id:           " + id + "\n" + "Booking Fee:  " + "$" + bookingFee + "\n"
		+ "Pick up Date: " + getPickUpDateTime().getFormattedDate() + "\n" + "Name:         "
		+ firstName + " " + lastName + "\n"	+ "Passengers:   " + numPassengers + "\n"
		+ "Travelled:    " + travelled() + "\n" + "Trip Fee:     " + tripFee()	+ "\n"
		+ "Car Id:       " + car.getRegNo().toUpperCase() + "\n";
	}

	//Getter for Booking details in predefined format for computer
	public String toString() {
		return id + ":" + bookingFee + ":" + pickUpDateTime.getEightDigitDate() + ":"
		+ firstName + " " + lastName + ":" + numPassengers + ":" + travelledToString() + ":"
		+ tripFeeToString() + ":" + car.getRegNo().toUpperCase();
	}
}
