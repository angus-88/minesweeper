package Minesweeper;

import java.util.Random;

/**
 * Main controller class that creates and tracks the game.
 * 
 * @author Angus
 *
 */
public class Minesweeper {
	private ScanIn scan = new ScanIn();
	private int size;
	private int numMines;
	private Location[][] board;

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		Minesweeper newGame = new Minesweeper();

	}

	/**
	 * Game constructor
	 */
	private Minesweeper() {

		boolean valid, newGame = false;
		char input;
		do {// while new game is true / user wants to quit
			System.out.println("Enter a grid size between 5 and 26");
			this.size = scan.getInt(5, 26);
			System.out.println("Enter number of mines between 5 and " + Math.floorDiv((size * size), 2));
			int mineLimit = (int) ((size * size) / 2);
			this.numMines = scan.getInt(5, mineLimit);
			this.board = new Location[size][size];

			// fills the board with location objects
			fillBoard();
			// randomboly distribute mines accross the board
			fillMines(board, numMines);
			// calculate the bomb values for each location
			calculateValues();
			// makeVisible(board); printBoard(board); makeHidden(board); Test
			// Line

			// play the game
			playGame();

			// after the use quits make the entire board visible
			makeVisible(board);
			// display the visible board to the user
			printBoard(board);
			do {
				// game menu
				System.out.println("Do you want to play again\n y - yes \n n - no");
				input = scan.getChar();
				switch (input) {
				case 'y':
					newGame = true;
					valid = true;
					break;
				case 'n':
					newGame = false;
					valid = true;
					break;
				default:
					valid = false;
				}

			} while (!valid);// while user choice isnt valid

		} while (newGame);

		System.out.println("Have a nice day");// be polite

	}

	private void playGame() {
		int choice = 0;

		do {

			choice = haveGo(board);// choose a location and action
			int win = checkWin(this.board);// check for win situation
			if (win == 0) {
				System.out.println("You have won. Congratulations");
				choice = 9;
			} else {
				System.out.println("You have " + win + " bombs left to find!");
			}

		}

		while (choice != -1 && choice != 9);// while valid menu choice
	}

	/**
	 * check for win situation
	 * 
	 * @param aBoard
	 *            the current game board
	 * @return returns 0 if game has been won or a positive int showing how many
	 *         bombs are left to find
	 */
	private int checkWin(Location[][] aBoard) {
		// number of locations not including mines
		int totalLoc = (aBoard.length * aBoard.length) - this.numMines;
		int mines = this.numMines;
		Location aLoc = null;

		// loop through every location
		for (int column = 0; column < size; column++) {
			for (int row = 0; row < size; row++) {
				aLoc = aBoard[row][column];
				// if location has been clicked reduce the number of locations
				// left
				if (aLoc.isClicked()) {
					totalLoc--;
				} else if (aLoc.isFlag()) {// reduce number of mines if flagged
					mines--;
				}
			}
		}

		// if every non bomb location clicked game won
		if (totalLoc == 0) {
			return 0;
		} else {
			// return number of actual mines left to find
			return Math.abs(mines);
		}

	}

	/**
	 * Have a single go at one location on the board
	 * 
	 * @param aBoard
	 *            the board to be played
	 * @return the result of the go, -1 if bomb found
	 */
	private int haveGo(Location[][] aBoard) {
		printBoard(aBoard); // display current board
		System.out.println("");
		// get coordinates for the go
		int[] pos = getCoordinates(aBoard);
		char choice;

		int result = 0;
		do {
			// display click menu for the go
			System.out.println(
					"\nPress:\n c - to click\n f - to set/unset a flag\n b - to pick different position\n q - to quit");
			choice = scan.getChar();

			switch (choice) {
			case 'c':
				result = doClick(aBoard, pos);// click the location
				break;
			case 'f':
				doFlag(aBoard, pos);// place flag
				break;
			case 'b':
				getCoordinates(aBoard);// get more coordinates
				choice = '#';
				break;
			case 'q':
				result = -1;// quit game
				break;
			default:
				choice = '#';
				break;
			}
		} while (choice == '#');// while choice is not valid

		return result;

	}

	/**
	 * Place flag on the location
	 * 
	 * @param aBoard
	 *            the game board
	 * @param pos
	 *            the position on the game board
	 */
	private void doFlag(Location[][] aBoard, int[] pos) {
		Location aLoc = aBoard[pos[1]][pos[0]];
		// if not flagged place flag, if already flagged remove it
		if (aLoc.isFlag()) {
			aLoc.setFlag(false);
		} else {
			aLoc.setFlag(true);
		}
	}

	/**
	 * click the location
	 * 
	 * @param aBoard
	 *            the game board
	 * @param pos
	 *            the position on the game board
	 * @return result of the click, -1 for loss
	 */
	private int doClick(Location[][] aBoard, int[] pos) {
		Location aLoc = aBoard[pos[1]][pos[0]];// get location
		// if location is a bomb and not already flagged
		if (aLoc.isBomb() && !aLoc.isFlag()) {
			System.out.println("BOOM! You found a bomb\n Game Over :(");
			return -1;
			// if location is flagged ignore the click
		} else if (aLoc.isFlag()) {
			System.out.println("You cannot click a flag");
			return 0;
			// if location is already clicked
		} else if (aLoc.isClicked()) {
			System.out.println("You have already tried this location");
			return 0;
			// otherwise return the value of the location
		} else {
			System.out.println("Few.. This has a value of " + aLoc.getValue());
			aLoc.setClicked(true);// make visible
			if (aLoc.getValue() == 0) {
				// if the location has a value of 0, it checks its neighbours
				// for more zeros to open up the space
				findLocalBlanks(aBoard, pos);
			}

			return 0;
		}

	}

	/**
	 * recursive method to expand any empty space on the board where the value is 0
	 * 
	 * @param aBoard the board in play
	 * @param pos the position in the board arrays to start
	 */
	private void findLocalBlanks(Location[][] aBoard, int[] pos) {
		int x = pos[0];
		int y = pos[1];
		Location tmpLoc = null;
		int[] tmpPos = new int[] { pos[0], pos[1] };

		for (int column = x - 1; column <= x + 1; column++) {// for each column
			for (int row = y - 1; row <= y + 1; row++) {// for each row
				if (!(row == y && column == x)) {// if not central
					if (validPos(column, row)) {// if position in board

						// tmp location and coordinates
						tmpPos[0] = column;
						tmpPos[1] = row;
						tmpLoc = aBoard[row][column];

						// if not next to a bomb and has not already been
						// clicked
						if (tmpLoc.getValue() == 0 && !tmpLoc.isClicked()) {
							// set clicked flag and check that locations
							// neighbours
							tmpLoc.setClicked(true);
							findLocalBlanks(aBoard, tmpPos);
						} else {
							// if location has a value other than 0 or has
							// already been clicked
							tmpLoc.setClicked(true);

						}
					}
				}
			}
		}
	}

	/**
	 * gets valid coordinates from the user
	 * 
	 * @param aBoard
	 *            the board currently being played
	 * @return int array containing two coordinates
	 */
	private int[] getCoordinates(Location[][] aBoard) {
		int pos[] = new int[2];
		boolean valid = false;

		do {
			System.out.println("Type in the coordinates of the square you wish to pick");
			String[] input = scan.getPos(size - 1);

			//attempt to parse cooridnates
			pos[0] = Converter.charConvert(input[0].charAt(0));
			pos[1] = Integer.parseInt(input[1]);

			valid = true;

			//if choosen location is already clicked, warn user
			if (aBoard[pos[1]][pos[0]].isClicked()) {
				valid = false;
				System.out.println("You have already tried this location, choose again.");
			}
		} while (!valid);//while input not valid
		return pos;
	}

	/**
	 * displays the current game board on screen
	 * @param board the board to be displayed
	 */
	private void printBoard(Location[][] board) {
		Location aLoc;
		
		//extract and print each location from the two dimensional array
		for (int row = 0; row < board.length; row++) {
			for (int column = 0; column < board.length; column++) {
				aLoc = board[row][column];
				printLocation(aLoc);
			}
			System.out.println("\n"); //add new line character between each row in the grid

		}

	}

	/**
	 * makes every location in the board visible
	 * @param aBoard the board to make visible
	 */
	private void makeVisible(Location[][] aBoard) {
		Location aLoc;
		for (int row = 0; row < aBoard.length; row++) {
			for (int column = 0; column < aBoard.length; column++) {
				aLoc = aBoard[row][column];
				aLoc.setClicked(true);
			}

		}
	}

	/**
	 * makes every location on the board hidden, used for testing purposes only
	 * @param aBoard the board to make hidden
	 */
	@SuppressWarnings("unused")
	private void makeHidden(Location[][] aBoard) {
		Location aLoc;
		for (int row = 0; row < aBoard.length; row++) {
			for (int column = 0; column < aBoard.length; column++) {
				aLoc = aBoard[row][column];
				aLoc.setClicked(false);
			}

		}
	}

	/**
	 * print the value of a location, depending on its flags and value
	 * @param aLoc the location to be printed
	 */
	private void printLocation(Location aLoc) {
		if (aLoc.isBomb() && aLoc.isClicked()) {
			System.out.print("BOOM!!\t");
		} else if (aLoc.isFlag()) {
			System.out.print("[F]" + aLoc.getColumn() + "," + aLoc.getRow() + "\t");
		} else if (aLoc.isClicked()) {
			System.out.print(aLoc.getValue() + "\t");
		} else {
			System.out.print(aLoc.getColumn() + "," + aLoc.getRow() + "\t");
		}
	}

	/**
	 * fills the board with new location objects with there location
	 */
	private void fillBoard() {
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				board[x][y] = new Location(x, Converter.intConvert(y));
			}
		}
	}

	/**
	 * Randomly spread the designated number of mines throughout the board
	 * @param aBoard the board for mines
	 * @param mines the number of mines to be placed on the board
	 */
	private void fillMines(Location[][] aBoard, int mines) {
		Random rand = new Random();
		int i = 0;
		int x;
		int y;

		while (i < mines) {
			//generate random indexes 
			x = rand.nextInt(aBoard.length);
			y = rand.nextInt(aBoard.length);
			//checks the random position for a mine
			if (!checkPos(x, y)) {
				board[x][y].setBomb(true);
				;
				i++;
			}
		}
	}

	/**
	 * calculates the values for each location in the board
	 */
	private void calculateValues() {
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				board[x][y].setValue(checkNeighbors(x, y));
				;
			}
		}
	}

	/**
	 * given a coordinates it checks every location around it
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @return the number of mines it finds in the surrounding locations
	 */
	private int checkNeighbors(int x, int y) {
		int numMines = 0;

		for (int column = x - 1; column <= x + 1; column++) {// for each column
			for (int row = y - 1; row <= y + 1; row++) {// for each row
				if (!(row == y && column == x)) {// if not central
					if (validPos(column, row)) {// if position in board
						if (board[column][row].isBomb()) {// if next to bomb
							numMines++;// add to value
						}
					}
				}
			}
		}

		return numMines;
	}

	/**
	 * checks the coordinates are valid and within the current board
	 * @param x the x coordinate to check
	 * @param y the y coordinate to check
	 * @return true if position is allowed, otherwise false
	 */
	private boolean validPos(int x, int y) {
		if (x < size && x >= 0) {
			if (y < size && y >= 0) {
				return true;
			}
		}

		return false;
	}

	
	/**
	 * Checks if the location at that position contains a bomb
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @return true if that location contains a bomb otherwise false
	 */
	private boolean checkPos(int x, int y) {
		if (board[x][y].isBomb()) {
			return true;
		} else {
			return false;
		}
	}

}
