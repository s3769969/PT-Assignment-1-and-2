package application;

import java.util.Scanner;
import application.Car;
import utilities.DateTime;

/*
 * Class:			MiRidesApplication
 * Description:		The class presents a contains methods validating user inputs against the data
 *                  in the system
 * Author:			Sebastian Wisidagama - s3769969
 */
public class MiRidesSystem {
	
	private Car[] allCars;
	private Car[] availableCars;
	
	public MiRidesSystem() {
		
	}
	
	//calls method to create allCars array
	public void createAllCarsArray() {
		allCars = new Car[100];
	}

	/*Checks registrations no of two cars and returns true if the same as another otherwise returns
	false*/
	public boolean equalsCar(Car car1, Car car2) {
		
		if (car1.getRegNo().equalsIgnoreCase(car2.getRegNo())) {
			return true;
		}else {
			return false;
		}
	}
	
	/*Compares two registration number arguments and returns true if the same as another otherwise
	returns false*/
		public boolean equalsRegNo(String regNo1, String regNo2) {
			
			if (regNo1.equalsIgnoreCase(regNo2)) {
				return true;
			}else {
				return false;
			}
		}
	
	//Checks car argument against allCars arrays and returns true if reg no matches. Else returns false
	public boolean checkCar(Car car) {
				
		int i = 0;
		boolean outcome = false; 
		while(i < allCars.length) {
			if (allCars[i] == null){
				i = allCars.length;
			}else if (equalsCar(allCars[i], car) == true){
				outcome = true;
				i = allCars.length;
			}else if (equalsCar(allCars[i], car) == false){
				i++;
			}
		}
		
		if (outcome == false) {
			return false;
		}else{
			return true;
		}
	}

	/*Checks reg number against allCars arrays for match. Returns true if reg no matches, else
	returns false*/
	public boolean checkCar(String regNo) {
		
		int i = 0;
		boolean outcome = false; 
		while(i < allCars.length) {
			if (allCars[i] == null){
				i = allCars.length;
			}else if (equalsRegNo(allCars[i].getRegNo(), regNo) == true){
				outcome = true;
				i = allCars.length;
			}else if (equalsRegNo(allCars[i].getRegNo(), regNo) == false){
				i++;
			}
		}
		
		if (outcome == false) {
			return false;
		}else{
			return true;
		}
	}
	
	/*Adds car argument to allCars array if element index is empty else looks for next empty element
	index*/
	public void addCar(Car car) {
		
		int i = 0;
		while(i < allCars.length) {
			if (allCars[i] == null) {
				allCars[i] = car;
				i = allCars.length;
			}else {
				i++;
			}
		}
	}
	
	/*Uses car instance variables as argument to create Car object. Then adds Car object to allCars
	array if element index is empty, else looks for next empty element index and adds it there*/
	public void addCar(String regNo, String make, String model, String driverName, 
			int passengerCapacity) {
		
		Car car = new Car(regNo, make, model, driverName, passengerCapacity);
		int i = 0;
		while(i < allCars.length) {
			if (allCars[i] == null) {
				allCars[i] = car;
				i = allCars.length;
			}else {
				i++;
			}
		}
	}
	
	/*Uses car instance variables as argument to create Car object. Then adds Car object to allCars
	array if element index is empty, else looks for next empty element index and adds it there*/
	public void addCar(String regNo, String make, String model, String driverName, 
			int passengerCapacity, double bookingFee, String refreshmentsList) {
		
		String[] refreshments = refreshmentsList.split(",");
		Car car = new SilverServiceCar(regNo, make, model, driverName, passengerCapacity, bookingFee, refreshments);
		int i = 0;
		while(i < allCars.length) {
			if (allCars[i] == null) {
				allCars[i] = car;
				i = allCars.length;
			}else {
				i++;
			}
		}
	}
	
	/*Checks Car object argument and passes all argument data to book method in Car class and checks
	if booking can be made. If matching car is found in allCars array and booking can be made, then
	booking has been made and returns true. Else, returns false.*/
	public boolean bookCar(Car car, String firstName, String lastName, DateTime required, 
			int numPassengers) {
		
		int i = 0;
		while(i < allCars.length) {
			if (allCars[i] == null) {
				i = allCars.length;
			}else if (equalsCar(allCars[i], car) == true){
				if (allCars[i].book(firstName, lastName, required, numPassengers) == true) {
					return true;
				}else {
					return false;
				}				
			}else if (equalsCar(allCars[i], car) != true){
				i++;
			}
		}
		return false;
	}
	
	/*Searches car argument against allCars array for matching reg number and if found returns index of
	  element. Else returns 1000, which is out of array length*/
	public int returnCarInArray(Car car) {
		
		int i = 0;
		while(i < allCars.length) {
			if (allCars[i] == null){
				i = allCars.length;
			}else if (equalsCar(allCars[i], car) == true) {
				return i;
			}else {
				i++;
			}
		}
		return 10000;
	}
	
	/*Checks that string argument matches reg number of car in allCars array and that a current
	booking is attached to car. If true, then returns true. Else, returns false.*/
	public boolean findCurrentBookingByReg(String regNo) {
		
		int i = 0;
		while(i < allCars.length) {
			if (allCars[i] == null){
				i = allCars.length;
			}else if (equalsRegNo(allCars[i].getRegNo(), regNo) && allCars[i].findCurrentBookingByReg()) {
				return true;
			}else {
				i++;
			}
		}
		
		return false;
	}
	
	/*Checks that string argument matches booking date of any car in allCars array. If true, then
	returns true. Else, returns false.*/
	public boolean searchByDate(String required) {
		
		int i = 0;
		while(i <  allCars.length) {
			if (allCars[i] == null){
				i = allCars.length;
			}else if(allCars[i].findCurrentBooking(required)) {
				return true;
			}else {
				i++;
			}
		}
		return false;
	}
	
	/*Searches string argument against reg number of Car objects in allCars array. If a match is found,
	the Car object details are printed. Otherwise error message is displayed*/
	public void searchByRegPrint(String regNo) {
		
		int i = 0;
		while(i < allCars.length){
			if (allCars[i] == null){
				System.out.println("Error - Car could not be located in system\n");
				i = allCars.length;
			}else if(equalsRegNo(allCars[i].getRegNo(), regNo)) {
				System.out.println(allCars[i].getDetails() + "\n");
				i = allCars.length;
			}else if(allCars[i] != null) {
				i++;
		}
		}
	}
	
	/*Converts string argument to DateTime object if string is in format "dd/MM/yyyy" and returns
	DateTime object*/
	public DateTime convertStringToTime(String string) {
		
		int day = Integer.parseInt(string.substring(0, 2));
		int month = Integer.parseInt(string.substring(3, 5));
		int year = Integer.parseInt(string.substring(6));
		DateTime stringToTime = new DateTime(day, month, year);
		return stringToTime;
	}
	
	/*Checks allCars array for Car objects that are available. Returns false if there are none.
	Else, returns true */
	public boolean availableCars() {
		
		int i = 0; 
		boolean found = false;
		while(i < allCars.length) {
			if (allCars[i] == null){
				i = allCars.length;
			}else if (allCars[i].getAvailable() == true){
				i++;
				found = true;
			}else if (allCars[i].getAvailable() == false){
				i++;
			}
		}
		if (found == true) {
			return true;
		}else {
			return false;
		}
	}
	
	//Returns available cars as array
	public Car[] getAvailableCars() {
		return availableCars;
	}

	/*Initialises new availableCars array equal to allCars length. Searches allCars array for
	available Cars. If found then prints Car reg number with number starting from 1 next to reg
	number and adds the Car object to availableCars array starting at index 0. Then continues to
	search for available cars until no more cars are found. */
	public void printAvailableCars() {
		
		availableCars = new Car[allCars.length];
		int i = 0; 
		int j = 1;
		while(i < allCars.length) {
			if (allCars[i] == null){
				i = allCars.length;
			}else if (allCars[i].getAvailable() == true){
				System.out.println(j + ".\t" + allCars[i].getRegNo());
				availableCars[j - 1] = allCars[i];
				j++;
				i++;
			}else if (allCars[i].getAvailable() == false){
				i++;
			}
		}		
	}
	
	/*Checks regNo argument to find matching reg number in allCars array and loops until element in
	array is empty. If found, name arguments is checked to see if it matches existing car booking. If
	details match and Booking using travel distance is completed, returns true. Else, returns false.*/
	public boolean completeBookingWithReg(String regNo, Double kilometersTravelled, String firstName,
			String lastName) {
		
		int i = 0;
		while(i < allCars.length) {
			if (allCars[i] == null){
				i = allCars.length;
			}else if (equalsRegNo(allCars[i].getRegNo(), regNo)) {
				if (allCars[i].completeBookingUsingReg(kilometersTravelled, firstName, lastName)) {
					return true;
				}else {
					System.out.println("Error - Name given does not match any name in current"
							+ "booking(s) for " + regNo + "\n");
					i = allCars.length;
				}
			}else {
				i++;
			}
		}
		return false;
	}
	
	/*Checks required argument to find matching booking date in allCars array and loops until element
	in array is empty. If found, name arguments is checked to see if it matches existing car booking.
	If details match and Booking using travel distance is completed, returns true. Else, checks next
	car in allCars array for match. If no cars in array match, then returns false.*/
	public boolean completeBookingWithDate(String required, Double kilometersTravelled,
			String firstName, String lastName) {
		
		int i = 0;
		while(i < allCars.length) {
			if (allCars[i] != null){
				if (allCars[i].findCurrentBooking(required)) {
					if (allCars[i].completeBookingUsingReg(kilometersTravelled, firstName, lastName)) {
						return true;
					}else {
						i++;
					}
				}else {
					i++;
				}
			}else if (allCars[i] == null){
				i = allCars.length;
			}
		}
		System.out.println("Error - Name given does not match any name in current booking(s) found"
				+ " for " + required + "\n");
		return false;
	}
	
	/*Checks allCars array for matching reg number and returns total fee if name and distance travel
	arguments also match. Else, returns error message*/
	public String getTotalFeeUsingReg(String regNo, Double kilometersTravelled, String firstName,
			String lastName) {
		
		int i = 0;
		while(i < allCars.length) {
			if (allCars[i] == null) {
				i = allCars.length;
			}else if (equalsRegNo(allCars[i].getRegNo(), regNo)) {
				String totalFee = allCars[i].getTotalFee(kilometersTravelled, firstName, lastName);
				return totalFee;
			}else {	
				i++;
			}
		}	
		return "Error - Trip Fee not found.\n";
	}
	
	/*Checks each car in allCars array for matching required argument with all dates in pastBookings[]
	for each car. If a match is found, returns total fee if name and distance travel arguments also
	match. Else, returns error message. */
	public String getTotalFeeUsingDate(String required, Double kilometersTravelled, String firstName,
			String lastName) {
		
		int i = 0;
		while(i < allCars.length) {
			if (allCars[i] == null) {
				i = allCars.length;
			}else if (allCars[i].findPastBooking(required)){
				String totalFee = allCars[i].getTotalFee(kilometersTravelled, firstName, lastName);
				return totalFee;
			}else {	
				i++;
			}
		}	
		return "Error - Trip Fee not found.\n";
	}
	
	/*Searches allCars array for car objects and prints them. If there are no assigned elements, error
	message is printed. Else "no more cars" message is printed after all car objects found in array
	are printed*/
	public void displayAll() {
		
		int i = 0;
		while (i < allCars.length) {
			if (allCars[i] != null) {
				System.out.println(allCars[i].getDetails() + "\n");
				i++;
			}else if (i == 0 && allCars[i] == null) {
				System.out.println("Error - No cars found in system\n");
				i = allCars.length;
			}else if (allCars[i] == null) {
				System.out.println("No more cars found in system\n");
				i = allCars.length;
			}else {
				i++;
			}
		}
	}
	
	/*Creates 6 new car objects with hardcoded instance variable data, creates 4 DateTime objects and
	uses 2 hardcoded travel distances. Then checks allCars array to see if any car reg is already in
	the system. If not it adds the 6 Car objects to the array. Then books 4 Cars using the 4 DateTime
	objects and 4 hardcoded booking instance variables and completes the booking for the last 2 cars
	using the hardcoded travel distances. */
	public void seedData() {
		
		//Instantiates 6 hardcoded car objects
		Car car0 = new Car("MJB007", "Aston Martin", "DB4", "James Bond", 5);
		Car car1 = new Car("ARC237", "Alfa Romeo", "4C", "Posh Spice", 2);
		Car car2 = new Car("CTR999", "SCE", "Naughty Dog", "Crash Bandicoot", 1);
		Car car3 = new Car("GGG123", "Cadillac", "Pimpmobile", "Snoop Dogg", 9);
		Car car4 = new Car("SSS001", "Rolls-Royce", "Phantom", "Jeeves Sir", 8);
		Car car5 = new Car("VVV888", "Chevrolet", "Corvette Z06", "Richie Rich", 4);
	
		//Instantiates 4 DateTime objects based on current system time
		DateTime req2 = new DateTime(5);
		DateTime req3 = new DateTime(7);
		DateTime req4 = new DateTime(0);
		DateTime req5 = new DateTime(0);
		
		/*Checks allCars array to see if any car reg is already in the system. If not it adds the 6 
		Car objects to the array. Then books 4 Cars using the 4 DateTime objects and 4 hardcoded
		booking instance variables and completes the booking for the last 2 cars using the hardcoded
		travel distances. Prints success message if last car is added. Else, prints error message.*/
		if (checkCar(car0) == false) {
			addCar(car0);
		}
		if (checkCar(car1) == false) {
			addCar(car1);
		}
		if (checkCar(car2) == false) {
			addCar(car2);
			bookCar(car2, "Lone", "Wolf", req2, 1);
		}
		if (checkCar(car3) == false) {
			addCar(car3);
			bookCar(car3, "John", "Doe", req3, 2);
		}
		if (checkCar(car4) == false) {
			addCar(car4);
			bookCar(car4, "Stacie", "Triumph", req4, 3);
			completeBookingWithDate(req4.getFormattedDate(), 43.0, "Stacie", "Triumph");
		}
		if (checkCar(car5) == false) {
			addCar(car5);
			bookCar(car5, "Jack", "Familyman", req5, 4);
			completeBookingWithDate(req5.getFormattedDate(), 21.5, "Jack", "Familyman");
			System.out.println("Seeded Data has been added to system\n");		
		}else {
			System.out.println("Error - Seeded Data has already been added to system\n");
		}
	}	
	
}
