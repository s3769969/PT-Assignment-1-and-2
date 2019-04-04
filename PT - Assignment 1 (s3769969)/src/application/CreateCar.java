package application;
import java.util.Scanner;
import application.Car;

public class CreateCar {

	private String regNo;
	private String make;
	private String model;
	private String driverName;
	private int passengerCapacity;
	private boolean validRegNo = false;
	private boolean validPassCap = false;
	
	public CreateCar()
	{
		Scanner scanner = new Scanner(System.in);
		
		do {
			System.out.print("Enter Registration No ");
			regNo = scanner.nextLine();
			if (regNo.length() != 6 || Character.isLetter(regNo.charAt(0)) != true
					|| Character.isLetter(regNo.charAt(1)) != true || Character.isLetter(regNo.charAt(2)) != true
					|| Character.isDigit(regNo.charAt(3)) != true || Character.isDigit(regNo.charAt(4)) != true
					|| Character.isDigit(regNo.charAt(5)) != true) {
				System.out.println("Error - Registration number is invalid");
			} else if(regNo == regNo){
				System.out.println("Error - Registration number already exists in system");
			}else{
				validRegNo = true;
			}

		} while (validRegNo == false);
		
		System.out.print("Enter make ");
		make = scanner.nextLine();
		System.out.print("Enter model ");
		model = scanner.nextLine();
		System.out.print("Enter Driver's Name ");
		driverName = scanner.nextLine();
		
		do {
			System.out.print("Enter Passenger Capacity ");
			passengerCapacity = scanner.nextInt();
			if (passengerCapacity < 1 || passengerCapacity > 9) {
				System.out.println("Error - Passenger Capacity is invalid");
			}else{
					validPassCap = true;
				}
		}while(validPassCap == false);
		
		Car car = new Car(regNo, make, model, driverName, passengerCapacity);
		System.out.println("New Car successfully added for registration number: " + regNo);
		
		scanner.close();
	}
}
