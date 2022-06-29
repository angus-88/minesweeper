package Minesweeper;

import java.awt.EventQueue;
import java.util.Random;

public class MinesweeperGUI {
	private ScanIn scan = new ScanIn();
	private int size;
	private int numMines;
	private Location[][] board;
	private GUI window;



	
	@SuppressWarnings("unused")
	public static void main(String[] args) {

		
		
		MinesweeperGUI newGame = new MinesweeperGUI();


		
	}

	private MinesweeperGUI() {
		window = new GUI();
		window.getFrame().setVisible(true);
		
		boolean valid, newGame = false;
		char input;
		do {
			System.out.println("Enter a grize size between 5 and 26");
			this.size = scan.getInt(5, 26);
			System.out.println("Enter number of mines between 5 and " + Math.floorDiv((size * size), 2));
			int mineLimit = (int) ((size * size) / 2);
			this.numMines = scan.getInt(5, mineLimit);
			this.board = new Location[size][size];

			fillBoard();
			fillMines(board, numMines);
			calculateValues();
			// makeVisible(board); printBoard(board); makeHidden(board); Test
			// Line
			playGame();

			makeVisible(board);
			printBoard(board);
			do {
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

			} while (!valid);

		} while (newGame);

		System.out.println("Have a nice day");

	}

	private void playGame() {
		int choice = 0;

		do {

			choice = haveGo(board);
			int win = checkWin(this.board);
			if (win == 0) {
				System.out.println("You have won. Congratulations");
				choice = 9;
			} else {
				System.out.println("You have " + win + " bombs left to find!");
			}

		}

		while (choice != -1 && choice != 9);
	}

	private int checkWin(Location[][] aBoard) {
		int totalLoc = (aBoard.length * aBoard.length) - this.numMines;
		int mines = this.numMines;
		Location aLoc = null;
		for (int column = 0; column < size; column++) {
			for (int row = 0; row < size; row++) {
				aLoc = aBoard[row][column];
				if (aLoc.isClicked()) {
					totalLoc--;
				} else if (aLoc.isFlag()) {
					mines--;
				}
			}
		}

		if (totalLoc == 0) {
			return 0;
		} else {
			return Math.abs(mines);
		}

	}

	private int haveGo(Location[][] aBoard) {
		printBoard(aBoard);
		System.out.println("");
		int[] pos = getCoordinates(aBoard);
		char choice;

		int result = 0;
		do {
			System.out.println(
					"\nPress:\n c - to click\n f - to set/unset a flag\n b - to pick different position\n q - to quit");
			choice = scan.getChar();

			switch (choice) {
			case 'c':
				result = doClick(aBoard, pos);
				break;
			case 'f':
				doFlag(aBoard, pos);
				break;
			case 'b':
				getCoordinates(aBoard);
				choice = '#';
				break;
			case 'q':
				result = -1;
				break;
			default:
				choice = '#';
				break;
			}
		} while (choice == '#');

		return result;

	}

	private void doFlag(Location[][] aBoard, int[] pos) {
		Location aLoc = aBoard[pos[1]][pos[0]];
		if (aLoc.isFlag()) {
			aLoc.setFlag(false);
		} else {
			aLoc.setFlag(true);
		}
	}

	private int doClick(Location[][] aBoard, int[] pos) {
		Location aLoc = aBoard[pos[1]][pos[0]];
		if (aLoc.isBomb() && !aLoc.isFlag()) {
			System.out.println("BOOM! You found a bomb\n Game Over :(");
			return -1;
		} else if (aLoc.isFlag()) {
			System.out.println("You cannot click a flag");
			return 0;
		} else if (aLoc.isClicked()) {
			System.out.println("You have already tried this location");
			return 0;
		} else {
			System.out.println("Few.. This has a value of " + aLoc.getValue());
			aLoc.setClicked(true);
			if (aLoc.getValue() == 0) {
				findLocalBlanks(aBoard, pos);
			}

			return 0;
		}

	}

	private void findLocalBlanks(Location[][] aBoard, int[] pos) {
		int x = pos[0];
		int y = pos[1];
		Location tmpLoc = null;
		int[] tmpPos = new int[] { pos[0], pos[1] };

		for (int column = x - 1; column <= x + 1; column++) {// for each column
			for (int row = y - 1; row <= y + 1; row++) {// for each row
				if (!(row == y && column == x)) {// if not central
					if (validPos(column, row)) {// if position in board
						tmpPos[0] = column;
						tmpPos[1] = row;
						tmpLoc = aBoard[row][column];

						if (tmpLoc.getValue() == 0 && !tmpLoc.isClicked()) {// if
																			// next
																			// to
																			// bomb
																			// and
																			// not
																			// already
																			// visible
							tmpLoc.setClicked(true);
							findLocalBlanks(aBoard, tmpPos);
						} else {
							tmpLoc.setClicked(true);

						}
					}
				}
			}
		}
	}

	private int[] getCoordinates(Location[][] aBoard) {
		int pos[] = new int[2];
		boolean valid = false;

		do {
			System.out.println("Type in the coordinates of the square you wish to pick");
			String[] input = scan.getPos(size - 1);

			pos[0] = Converter.charConvert(input[0].charAt(0));
			pos[1] = Integer.parseInt(input[1]);
			
			valid=true;

			if (aBoard[pos[1]][pos[0]].isClicked()) {
				valid=false;
				System.out.println("You have already tried this location, choose again.");
			}
		} while (!valid);
		return pos;
	}

	private void printBoard(Location[][] board) {
		Location aLoc;
		for (int row = 0; row < board.length; row++) {
			for (int column = 0; column < board.length; column++) {
				aLoc = board[row][column];
				printLocation(aLoc);
			}
			System.out.println("\n");

		}

	}

	private void makeVisible(Location[][] aBoard) {
		Location aLoc;
		for (int row = 0; row < aBoard.length; row++) {
			for (int column = 0; column < aBoard.length; column++) {
				aLoc = aBoard[row][column];
				aLoc.setClicked(true);
			}

		}
	}

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

	private void fillBoard() {
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				board[x][y] = new Location(x, Converter.intConvert(y));
			}
		}
	}

	private void fillMines(Location[][] aBoard, int mines) {
		Random rand = new Random();
		int i = 0;
		int x;
		int y;

		while (i < mines) {
			x = rand.nextInt(aBoard.length);
			y = rand.nextInt(aBoard.length);
			if (!checkPos(x, y)) {
				board[x][y].setBomb(true);
				;
				i++;
			}
		}
	}

	private void calculateValues() {
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				board[x][y].setValue(checkNeighbors(x, y));
				;
			}
		}
	}

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

	private boolean validPos(int x, int y) {
		if (x < size && x >= 0) {
			if (y < size && y >= 0) {
				return true;
			}
		}

		return false;
	}

	private boolean checkPos(int x, int y) {
		if (board[x][y].isBomb()) {
			return true;
		} else {
			return false;
		}
	}

}
