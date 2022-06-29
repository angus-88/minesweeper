package Minesweeper;

import java.util.Scanner;

/**
 * Scanner class that takes and verifies input from the user
 * 
 * @author Angus
 *
 */
public class ScanIn {
	private Scanner scan = new Scanner(System.in);

	/**
	 * Gets integer input from the user that is between two limits
	 * 
	 * @param downLimit
	 *            the lower int limit
	 * @param upLimit
	 *            the upper int limit
	 * @return
	 */
	public int getInt(int downLimit, int upLimit) {
		// scan = new Scanner(System.in);
		int choice = -1;
		while (choice == -1) {// while choice is not between limits
			System.out.println("Enter the number for your choice");
			String sChoice = scan.nextLine().toLowerCase().trim();
			try {// try parse string input to int
				choice = Integer.parseInt(sChoice);
			} catch (NumberFormatException nfe) {
				System.out.println("Not a valid number choice, Try Again");
				choice = -1;
			}

			// check choice is between limits
			if (choice > upLimit || choice < downLimit) {
				System.out.println("Please choose a number between " + upLimit + " and " + downLimit);
				choice = -1;
			}

		}

		return choice;

	}

	/**
	 * takes next line from the input and converts it to lowercase, trims and
	 * then takes the first character from the line
	 * 
	 * @return the first character in the string input, with no whitespace and
	 *         in lowercase
	 */
	public char getChar() {
		return scan.nextLine().toLowerCase().trim().charAt(0);
	}

	/**
	 * Takes string input and trys to parse it into valid coordinates
	 * 
	 * @param limit
	 *            the size of the game board
	 * @return string array containing the two coordinates
	 */
	public String[] getPos(int limit) {
		// works out the character limit for the x coordinate
		char charLimit = Converter.intConvert(limit);
		// the regular expression string using the char limit followed by a
		// comma and 1 or two digits
		String regex = "[a-" + charLimit + "],\\d?\\d";
		String input;
		String[] tokens = new String[2];
		Boolean valid = false;

		do {
			// trims and converts to lowercase
			input = scan.nextLine().toLowerCase().trim();

			if (input.matches(regex)) {
				// split the string input on the comma to create the two
				// coordinates
				tokens = input.split(",");
				// attempt to parse to valid coordinates or reject
				if (Integer.parseInt(tokens[1]) < limit) {
					valid = true;
				} else {
					System.out.println("Coordinates not valid");
				}
			}
			// if input is a character followed by one or two digits without the
			// comma seperation it splits after the first character
			else if (input.matches("[a-" + charLimit + "]\\d?\\d")) {
				tokens[0] = Character.toString(input.charAt(0));
				if (Integer.parseInt(input.substring(1)) <= limit) {
					tokens[1] = input.substring(1);
					valid = true;
				} else {
					System.out.println("Coordinates not valid");
				}
			} else {
				System.out.println("Coordinates not valid");
			}

		} while (!valid);// keep looping untill valid coordinates have been
							// entered

		return tokens;
	}

}
