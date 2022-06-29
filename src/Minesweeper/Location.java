package Minesweeper;

/**
 * Location class that stores the properties of each location in the grid including bomb, flag, value and its location so that its ware of its own location
 * @author Angus
 *
 */
public class Location {
	private boolean bomb;
	private boolean flag;
	private boolean clicked; //true makes the value visible when displayed
	private int value;//how many bombs are in the surrounding locations
	private int row; 
	private char column;

	/**
	 * Default constructor that creates a new location, with everything set to false and a value of 0.
	 */
	public void location() {
		this.bomb = false;
		this.flag = false;
		this.clicked = false;
		this.value = 0;
	}

	public boolean isClicked() {
		return clicked;
	}

	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}

	/**
	 * Give the location object a physical location using x and y cooridinates 
	 * @param x The location row with in the two dimensional array
	 * @param y the location column with in the two dimensional array
	 */
	public Location(int x, char y) {
		this.row = x;
		this.column = y;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public boolean isBomb() {
		return bomb;
	}

	public void setBomb(boolean isBomb) {
		this.bomb = isBomb;
	}

	public int getRow() {
		return this.row;
	}

	public void setRow(int x) {
		this.row = x;
	}

	public char getColumn() {
		return this.column;
	}

	public void setColumn(char column) {
		this.column = column;
	}

	/**
	 * Overrides the toString method.
	 * Returns a string description of the location depending on the flag statuses
	 * If its not clicked it returns the location
	 * If its been flagged it returns a flag symbol
	 * If its been clicked and its a bomb it returns a bomb symbol
	 * Otherwise it returns the locations value.
	 */
	public String toString() {
		if (!this.clicked) {
			return "" + this.row + this.column;
		} else if (this.flag) {
			return "P";
		} else if (this.bomb) {
			return "B";
		} else {
			return "" + this.value;
		}
	}
}
