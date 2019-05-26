package application;

import java.util.Scanner;
import java.util.regex.Pattern;

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
	private Car[] availableCarsForDate;
	
	public MiRidesSystem() {
		
	}
	
	//calls method to create allCars array
	public void createAllCarsArray() {
		allCars = new Car[20];
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
	public boolean checkCar(String regNo) throws InvalidRegException{
		
		int i = 0;
		boolean outcome = false; 
		while(i < allCars.length) {
			if (allCars[i] == null){
				i = allCars.length;
			}else if (equalsRegNo(allCars[i].getRegNo(), regNo) == true){
				throw new InvalidRegException("\"Error - " + regNo + " already exists in system\n");
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
			int passengerCapacity, double bookingFee, String refreshmentsList) throws InvalidRefreshmentsException {
		
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
			int numPassengers) throws InvalidPassCapException, InvalidBookingException {
		
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
	
	/*Checks allCars array for Car objects that are available. Returns 1 if there are none.
	Else, returns number of available cars */
	public int availableCars() {

		availableCars = new Car[allCars.length];
		int i = 0; 
		int j = 1;
		while(i < allCars.length) {
			if (allCars[i] == null){
				i = allCars.length;
			}else if (allCars[i].getAvailable() == true){
				availableCars[j - 1] = allCars[i];
				j++;
				i++;
			}else if (allCars[i].getAvailable() == false){
				i++;
			}
		}
		return j - 1;
	}
	
	//Returns available cars array Car[]
	public Car[] getAvailableCars() {
		return availableCars;
	}

	/*Print all cars in availableCars array with index number starting at 1. */
	public void printAvailableCars() throws NullPointerException {
		
		int i = 0;
		int j = 0;
		for (i = 0; i < availableCars(); i++) {
			j++;
			System.out.println(j + ".\t" + availableCars[i].getRegNo());
		}
	}
	
	/*Initialises new availableCars array equal to allCars length. Searches allCars array for
	available Cars matching DateTime. If found then prints Car reg number with number starting from
	1 next to reg number and adds the Car object to availableCars array starting at index 0. 
	Then continues to search for available cars until no more cars are found. */
	public Car[] printAvailableCars(DateTime required, String serviceType) {
		
		String requiredString = required.getFormattedDate();
		availableCarsForDate = new Car[allCars.length];
		int i = 0; 
		int j = 0;
		while(i < allCars.length) {
			if (allCars[i] == null){
				i = allCars.length;
			}else if (allCars[i].findCurrentBooking(requiredString) == false){
				if (serviceType.equalsIgnoreCase("SS") && allCars[i] instanceof SilverServiceCar == true) {
					availableCarsForDate[j] = allCars[i];
					j++;
				}else if (serviceType.equalsIgnoreCase("SD") && allCars[i] instanceof SilverServiceCar == false) {
					availableCarsForDate[j] = allCars[i];
					j++;
				}
				i++;
			}else if (allCars[i].findCurrentBooking(requiredString)){
				i++;
			}
		}	
		return availableCarsForDate;
	}
	
	/*Checks allCars array for Car objects that are available for given DateTime. Returns false if 
	there are none. Else, returns true */
	public boolean availableCars(DateTime required) {
		
		String requiredString = required.getFormattedDate();
		int i = 0; 
		boolean found = false;
		while(i < allCars.length) {
			if (allCars[i] == null){
				i = allCars.length;
			}else if (allCars[i].findCurrentBooking(requiredString)){
				i++;
			}else if (allCars[i].findCurrentBooking(requiredString) == false){
				i++;
				found = true;
			}
		}
		if (found == true) {
			return true;
		}else {
			return false;
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
	public void displayAll(String serviceType, String sortOrder) {
		
		
		int i = 0, j = 0;
		Car[] tempCars = new Car[allCars.length];
		while (i < allCars.length) {
			if (i == 0 && allCars[i] == null) {
				i = allCars.length;
			}else if (allCars[i] == null) {
				i = allCars.length;
			}else if (serviceType.equalsIgnoreCase("SS") && allCars[i] instanceof SilverServiceCar == true) {
				tempCars[j] = allCars[i];
				i++;
				j++;
			}else if (serviceType.equalsIgnoreCase("SD") && allCars[i] instanceof SilverServiceCar == false) {
				tempCars[j] = allCars[i];
				i++;
				j++;
			}else {
				i++;
			}
		}
		
		if (j == 0) {
			if (serviceType.equalsIgnoreCase("SS")) {
				System.out.println("\nError - No silver service cars were found on the system.\n");
			}else if (serviceType.equalsIgnoreCase("SD")) {
				System.out.println("\nError - No standard cars were found on the system.\n");
			}
			return;
		}
				
		Car[] sortedCars = new Car[j];
		for(i = 0; i < j; i++) {
			sortedCars[i] = tempCars[i];
		}
		
		Car temp = new Car("temp", "temp", "temp", "temp", 9);
        boolean swapped;
    	for (i = 0; i < sortedCars.length - 1; i++) {
    		swapped = false;
			for (j = 0; j < sortedCars.length - 1; j++) {
    			if (sortOrder.equalsIgnoreCase("A") && sortedCars[j].getRegNo().compareTo(sortedCars[j+1].getRegNo()) > 0) { 
	                temp = sortedCars[j]; 
	                sortedCars[j] = sortedCars[j+1]; 
	                sortedCars[j+1] = temp; 
	                swapped = true;
	            } else if (sortOrder.equalsIgnoreCase("D") && sortedCars[j].getRegNo().compareTo(sortedCars[j+1].getRegNo()) < 0) { 
	                temp = sortedCars[j]; 
	                sortedCars[j] = sortedCars[j+1]; 
	                sortedCars[j+1] = temp; 
	                swapped = true;
	            } 
	    	}if (swapped == false) {
    			break;
    		}
    	}
	
    	System.out.println("The following cars are available");
		i = 0;
		while (i < sortedCars.length) {
			System.out.println("____________________________________________________________________________________\n");
			if (sortedCars[i] != null) {
				System.out.println(sortedCars[i].getDetails() + "\n");
				i++;
			}else if (i == 0 && sortedCars[i] == null) {
				System.out.println("Error - No cars found in system\n");
				i = sortedCars.length;
			}else if (sortedCars[i] == null) {
				System.out.println("No more cars found in system\n");
				i = sortedCars.length;
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
	public void seedData() throws InvalidRefreshmentsException, InvalidPassCapException, InvalidBookingException {
		
		String[] rsscar0 = {"Coke", "Smokes", "Chocolate bars"};
		String[] rsscar1 = {"Cold beverage", "MTV", "Karoake"};
		String[] rsscar2 = {"Cadbury eggs", "Coconut water", "Cigars"};
		String[] rsscar3 = {"Seafood", "Mojito", "Face towels"};
		String[] rsscar4 = {"Lightsaber", "Fuzzy Tauntaun", "Meiloorun Juice"};
		String[] rsscar5 = {"Waffles", "Steak", "Weed"};
		
		//Instantiates 6 hardcoded car objects
		Car car0 = new Car("MJB007", "Aston Martin", "DB4", "James Bond", 5);
		Car car1 = new Car("SPD777", "Straight Flush", "Spades", "Lucky Slevin", 7);
		Car car2 = new Car("CTR999", "SCE", "Naughty Dog", "Crash Bandicoot", 1);
		Car car3 = new Car("ODD101", "Binary", "1011", "On Off", 2);
		Car car4 = new Car("TTT333", "Judgement Day", "T3", "John Connor", 3);
		Car car5 = new Car("WHY909", "Renaming", "Sucks", "Random Guy", 5);
		Car sscar0 = new SilverServiceCar("IAD666", "Lincoln", "Limosine", "Bruce Willis", 6, 6.60, rsscar0);
		Car sscar1 = new SilverServiceCar("ARC237", "Alfa Romeo", "4C", "Posh Spice", 4, 5.50, rsscar1);
		Car sscar2 = new SilverServiceCar("SSS001", "Rolls-Royce", "Phantom", "Jeeves Sir", 8, 3.50, rsscar2);
		Car sscar3 = new SilverServiceCar("VVV888", "Chevrolet", "Corvette Z06", "Richie Rich", 4, 4.00, rsscar3);
		Car sscar4 = new SilverServiceCar("JDI001", "Phantom", "Menace", "Anakin Skywalker", 3, 3.00, rsscar4);
		Car sscar5 = new SilverServiceCar("GGG123", "Cadillac", "Pimpmobile", "Snoop Dogg", 9, 9.99, rsscar5);
		
	
		//Instantiates 4 DateTime objects based on current system time
		DateTime today = new DateTime(0);  
		DateTime req1 = new DateTime(1);
		DateTime req3 = new DateTime(3);
		DateTime req5 = new DateTime(5);
		DateTime req7 = new DateTime(7);
		
		/*Checks allCars array to see if any car reg is already in the system. If not it adds the 12 
		Car objects to the array. Then books 8 Cars(4SS + 4SD) using the 5 DateTime objects and 8
		hardcoded booking instance variables and completes the booking for the last 4 cars(2SS + 2SD)
		using the hardcoded	travel distances. Prints success message if last car is added. Else, prints
		error message.*/
		if (checkCar(car0) == false) {
			addCar(car0);
		}
		if (checkCar(car1) == false) {
			addCar(car1);
		}
		if (checkCar(car2) == false) {
			addCar(car2);
			bookCar(car2, "Lone", "Dog", req5, 1);
		}
		if (checkCar(car3) == false) {
			addCar(car3);
			bookCar(car3, "John", "Doe", req7, 2);
		}
		if (checkCar(car4) == false) {
			addCar(car4);
			bookCar(car4, "Stacie", "Triumph", today, 3);
			completeBookingWithDate(today.getFormattedDate(), 43.0, "Stacie", "Triumph");
		}
		if (checkCar(car5) == false) {
			addCar(car5);
			bookCar(car5, "Jack", "Familyman", today, 4);
			completeBookingWithDate(today.getFormattedDate(), 21.5, "Jack", "Familyman");	
		}if (checkCar(sscar0) == false) {
			addCar(sscar0);
		}
		if (checkCar(sscar1) == false) {
			addCar(sscar1);
		}
		if (checkCar(sscar2) == false) {
			addCar(sscar2);
			bookCar(sscar2, "Lone", "Wolf", req1, 1);
		}
		if (checkCar(sscar3) == false) {
			addCar(sscar3);
			bookCar(sscar3, "Jane", "Doe", req3, 2);
		}
		if (checkCar(sscar4) == false) {
			addCar(sscar4);
			bookCar(sscar4, "Obi-Wan", "Kenobi", today, 3);
			completeBookingWithDate(today.getFormattedDate(), 43.0, "Obi-Wan", "Kenobi");
		}
		if (checkCar(sscar5) == false) {
			addCar(sscar5);
			bookCar(sscar5, "Ladies", "Man", today, 4);
			completeBookingWithDate(today.getFormattedDate(), 21.5, "Ladies", "Man");
			System.out.println("Seeded Data has been added to system\n");
		}else {
			System.out.println("Error - Seeded Data has already been added to system\n");
		}
	}	
	
	public Car[] sortSS() {
		int j = 0;
		Car[] ss = new Car[allCars.length];
		for (int i = 0; i< allCars.length; i++) {
			if (allCars[i] instanceof SilverServiceCar) {
				ss[j] = allCars[i];
				j++;
			}
		}	
			return ss;
	}
	
	public Car[] sortSD() {
		int j = 0;
		Car[] sd = new Car[allCars.length];
		for (int i = 0; i< allCars.length; i++) {
			if (allCars[i] instanceof SilverServiceCar == false) {
				sd[j] = allCars[i];
				j++;
			}
		}
		return sd;
	}

	public Car[] getAllCars() {
		return allCars;
	}

	public void loadData(String loadData) throws InvalidRefreshmentsException, InvalidPassCapException, InvalidBookingException {
		
		String regNo = null, make = null, model = null, driverName = null, firstName = null,
				lastName = null, available = null, id = null;
		int passengerCapacity = 0, numPassengers = 0;
		double bookingFee = 0, kmTravelled = 0;
		String[] refreshments = {"test1", "test2", "test3"};
		DateTime required = new DateTime();
		
		Car sdcar = new Car("aaa111", "test", "test", "test test", 5);
		Car sscar = new SilverServiceCar("bbb222", "test", "test", "test test", 6, 3, refreshments);
		String lines[] = loadData.split("\\r?\\n");
		boolean sdcarState = false;
		boolean sscarState = false;
		
		for (String line : lines) {
			String[] elements = line.split(":");
			if(elements.length == 7) {
				sdcarState = true;
				sscarState = false;
				regNo = elements[0];
				make = elements[1];
				model = elements[2];
				driverName = elements[3];
				passengerCapacity = Integer.parseInt(elements[4]);
				available = elements[5];
				sdcar = new Car(regNo, make, model, driverName, passengerCapacity);
				sdcar.setAvailable(available.equalsIgnoreCase("yes"));
				addCar(sdcar);
			}else if(elements.length > 8 && elements[0].startsWith("|") == false) {
				sdcarState = false;
				sscarState = true;
				regNo = elements[0];
				make = elements[1];
				model = elements[2];
				driverName = elements[3];
				passengerCapacity = Integer.parseInt(elements[4]);
				available = elements[5];
				bookingFee = Double.parseDouble(elements[6]);
				String items = "";
				for (int i = 7; i < elements.length; i++) {
					if (i == elements.length - 1) {
						items += elements[i].substring(6);
					}else {
						items += elements[i].substring(6) + ", ";
					}
				}
				refreshments = items.split(",");
				sscar = new SilverServiceCar(regNo, make, model, driverName, passengerCapacity, bookingFee, refreshments);
				sscar.setAvailable(available.equalsIgnoreCase("yes"));
				sscar.setBookingFee(bookingFee);
				addCar(sscar);
			}else if (elements.length == 8 && elements[0].startsWith("|")) {
				id = elements[0];
				bookingFee = Double.parseDouble(elements[1]);
				required = convert8DigitToTime(elements[2]);
				String[] names = elements[3].split(" ");
				firstName = names[0];
				lastName = names[1];
				numPassengers = Integer.parseInt(elements[4]);
				kmTravelled = Double.parseDouble(elements[5]);
				if (sdcarState == true) {
					if(elements[5].startsWith("0")) {
						bookCar(sdcar, firstName, lastName, required, numPassengers);
					}else{
						bookCar(sdcar, firstName, lastName, required, numPassengers);
						completeBookingWithDate(required.getFormattedDate(), kmTravelled, firstName, lastName);
					}
				} else if (sscarState == true)
					if(elements[5].startsWith("0")) {
						bookCar(sscar, firstName, lastName, required, numPassengers);
					}else{
						bookCar(sscar, firstName, lastName, required, numPassengers);
						completeBookingWithDate(required.getFormattedDate(), kmTravelled, firstName, lastName);
					}
			}
		}
	}
	
	//Converts eight digit time format to DateTime object
	public DateTime convert8DigitToTime(String required) {
		
		int day = Integer.parseInt(required.substring(0, 2));
		int month = Integer.parseInt(required.substring(2, 4));
		int year = Integer.parseInt(required.substring(4));
		DateTime stringToTime = new DateTime(day, month, year);
		return stringToTime;
	}

	/*
	 * Checks format of string argument. Returns true if format matches regex
	 * pattern. Else, returns false
	 */
	public String checkRegFormat(String regNo) throws InvalidRegException {

		String regex = "[a-zA-Z]{3}[0-9]{3}";
		if (regNo.length() == 6 && Pattern.matches(regex, regNo)) {
			return regNo;
		} else {
			throw new InvalidRegException(
					"Error - Registration format is invalid," + " needs to be in the form 'ABC123'\n");
		}
	}

	/*
	 * Checks format of string argument. Returns true if format matches regex
	 * pattern. Else, returns false
	 */
	public String checkDateFormat(String requiredString) throws InvalidDateException {

		String regex = "[0-9]{2}/[0-9]{2}/[0-9]{4}";
		if (requiredString.trim().length() == 10 && Pattern.matches(regex, requiredString.trim())) {
			return requiredString;
		} else {
			throw new InvalidDateException(
					"Error - Date format is invalid," + " needs to be in the form 'dd/MM/yyyy'\n");
		}
	}

	// Checks length string argument. Loops user input prompt until string length is
	// >2 characters
	public String checkNameLength(String nameCheck) throws InvalidNameException {

		Scanner scanner = new Scanner(System.in);
		System.out.println(nameCheck);
		String name = scanner.nextLine().trim();
		if (name.length() < 3) {
			throw new InvalidNameException("Error - Name is too short.\nRe-");
		}
		return name;
	}

	// Checks passenger capacity is between 1-9
	public int checkPassCap(int passengerCapacity) throws InvalidPassCapException {

		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter Passenger Capacity: ");
		passengerCapacity = scanner.nextInt();
		String fix = scanner.nextLine();// Adds scanner.nextLine after int/double input to fix input skip
		if (passengerCapacity < 1 || passengerCapacity > 9) {
			throw new InvalidPassCapException("Error - Passenger Capacity is invalid\n");
		}
		return passengerCapacity;
	}

	// Checks standard fee >$3
	public double checkBookingFee(double bookingFee) throws InvalidBookingFeeException {

		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter Standard Fee: ");
		bookingFee = scanner.nextDouble();
		String fix = scanner.nextLine();// Adds scanner.nextLine after int/double input to fix input skip
		if (bookingFee < 3) {
			throw new InvalidBookingFeeException("Error - SS Cars minimum booking fee is $3\n");
		}
		return bookingFee;
	}

	// Checks string argument for service type is valid
	public String checkServiceType(String serviceType) throws InvalidServiceTypeException {

		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter Service Type (SD/SS): ");
		serviceType = scanner.nextLine().trim();
		if (serviceType.equalsIgnoreCase("sd") || serviceType.equalsIgnoreCase("ss")) {
			return serviceType;
		} else {
			throw new InvalidServiceTypeException("Error - Service type must be either SD or SS\n");
		}
	}

	// Checks string argument for sort order is valid
	public String checkSortOrder(String sortOrder) throws InvalidSortOrderException {

		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter Sort Order (A/D): ");
		sortOrder = scanner.nextLine().trim();
		if (sortOrder.equalsIgnoreCase("a") || sortOrder.equalsIgnoreCase("d")) {
			return sortOrder;
		} else {
			throw new InvalidSortOrderException("Error - Sort order must be either A or D\n");
		}
	}
}
