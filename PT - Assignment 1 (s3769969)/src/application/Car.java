package application;

import utilities.DateTime;

public class Car {

	private String regNo;
	private String make;
	private String model;
	private String driverName;
	private int passengerCapacity;
	private boolean available;
	private String[] currentBookings;
	private String[] pastBookings;

	public Car(String regNo, String make, String model, String driverName, int passengerCapacity) {

		if (regNo.length() != 6 || Character.isLetter(regNo.charAt(0)) != true
				|| Character.isLetter(regNo.charAt(1)) != true || Character.isLetter(regNo.charAt(2)) != true
				|| Character.isDigit(regNo.charAt(3)) != true || Character.isDigit(regNo.charAt(4)) != true
				|| Character.isDigit(regNo.charAt(5)) != true) {
			regNo = "ZZZ999";
		} else {
			this.regNo = regNo;
		}

		this.make = make;
		this.model = model;
		this.driverName = driverName;
		if (passengerCapacity < 1) {
			this.passengerCapacity = 1;
		} else if (passengerCapacity > 9) {
			this.passengerCapacity = 9;
		} else {
			this.passengerCapacity = passengerCapacity;
		}
	}

	public boolean book(String firstName, String lastName, DateTime required, int numPassengers) {
		
		Car car = new Car(this.regNo, this.make, this.model, this.driverName, this.passengerCapacity);
		Booking booking = new Booking(firstName, lastName, required, numPassengers, car);

		Car[] currentBookings = new Car[5];
		for (int i = 0; i < currentBookings.length; i++)
		if(currentBookings[i] == null)
		{
			currentBookings[i] = car;
			System.out.println("YES");
			
		}else
		{
			System.out.println("NO");
			
		}
		return true;
	}

	public String getRegNo() {
		return regNo;
	}

	public String getMake() {
		return make;
	}

	public String getModel() {
		return model;
	}

	public String getDriverName() {
		return driverName;
	}

	public int getPassengerCapacity() {
		return passengerCapacity;
	}

	public String available() {
		if (available == true) {
			return "YES";
		} else {
			return "NO";
		}
	}

	public String getDetails() {
		return "RegNo:          " + regNo.toUpperCase() + "\n" + "Make and Model: " + make + " " + model + "\n"
				+ "DriverName:     " + driverName + "\n" + "Capacity:       " + passengerCapacity + "\n"
				+ "Available:      " + available() + "\n";
	}

	public String toString() {
		return regNo.toUpperCase() + ":" + make + ":" + model + ":" + driverName + ":" + passengerCapacity + ":"
				+ available();
	}
}