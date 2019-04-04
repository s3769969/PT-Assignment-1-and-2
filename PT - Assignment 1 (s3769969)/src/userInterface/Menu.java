package userInterface;

import java.util.Scanner;
import application.Car;
import application.CreateCar;
import application.BookCar;
import application.Booking;
import utilities.DateTime;

public class Menu
{

	boolean validMenuChoice = false;
	
	public Menu() {

	}

	public void run() {

		System.out.println("*** MiRides System Menu ***" + "\n");
		System.out.println("Create Car                         CC" + "\n");
		System.out.println("Book Car                           BC" + "\n");
		System.out.println("Complete Booking                   CB" + "\n");
		System.out.println("Display ALL Cars                   DA" + "\n");
		System.out.println("Search Specific Car                SS" + "\n");
		System.out.println("Search available cars              SA" + "\n");
		System.out.println("Seed data                          SD" + "\n");
		System.out.println("Exit Program                       EX" + "\n");

		Scanner scanner = new Scanner(System.in);
		
		do {
			String menuChoice = scanner.nextLine();
			if (menuChoice.equalsIgnoreCase("CC")) {
				CreateCar createCar = new CreateCar();
				validMenuChoice = true;
			} else if (menuChoice.equalsIgnoreCase("BC")) {
				BookCar bookCar = new BookCar();
				validMenuChoice = true;
			} else if (menuChoice.equalsIgnoreCase("CB")) {
				
				validMenuChoice = true;
			} else if (menuChoice.equalsIgnoreCase("DA")) {
				
				validMenuChoice = true;
			} else if (menuChoice.equalsIgnoreCase("SS")) {
				validMenuChoice = true;
			} else if (menuChoice.equalsIgnoreCase("SA")) {
				validMenuChoice = true;
			} else if (menuChoice.equalsIgnoreCase("SD")) {
				SeedData();
				validMenuChoice = true;
			} else if (menuChoice.equalsIgnoreCase("EX")) {
				System.exit(0);
			} else {
				validMenuChoice = false;
			}
		} while (validMenuChoice == false);
	}
	
	public void SeedData() {
		
		Car car0 = new Car("MJB007", "Aston Martin", "DB4", "James Bond", 4);
		Car car1 = new Car("ARC237", "Alfa Romeo", "4C", "Posh Spice", 2);
		Car car2 = new Car("CTR999", "SCE", "Naughty Dog", "Crash Bandicoot", 1);
		Car car3 = new Car("GGG123", "Cadillac", "Pimpmobile", "Snoop Dogg", 9);
		Car car4 = new Car("SSS001", "Rolls-Royce", "Phantom", "Jeeves Sir", 9);
		Car car5 = new Car("VVV888", "Chevrolet", "Corvette Z06", "Richie Rich", 3);
		
		currentBookings();
	}
	
	public void currentBookings() {
		
		Car[] cars = new Car[5];
//		cars[0] = car2;
//		cars[0] = car3;
//		cars[0] = car4;
//		cars[0] = car5;
		
	}
	
}
