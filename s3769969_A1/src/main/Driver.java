package main;


import application.MiRidesSystem;
import userInterface.Menu;

/*
 * Class:			Driver
 * Description:		The class contain the main method and is responsible for initiating the program by first calling the allArrays method
 *  in the menu to initiate all arrays once at the beginning of program before calling the run method in the Menu.
 * Author:			Sebastian Wisidagama - s3769969
 */
public class Driver {

	public static void main(String[] args) {

		Menu menu = new Menu();
		menu.createSystem();
		menu.run();
		//test version for git
	}

}
