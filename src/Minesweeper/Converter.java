package Minesweeper;

public class Converter {

	/**
	 * Convert char to int
	 * 
	 * @param aChar
	 *            the character 'a-z' to be converted to its integer equivalent
	 *            [0-25]
	 * @return the integer equivelent of the character from the alphabet or -1
	 *         if out of bounds
	 */
	public static int charConvert(char aChar) {
		switch (aChar) {
		case 'a':
			return 0;
		case 'b':
			return 1;
		case 'c':
			return 2;
		case 'd':
			return 3;
		case 'e':
			return 4;
		case 'f':
			return 5;
		case 'g':
			return 6;
		case 'h':
			return 7;
		case 'i':
			return 8;
		case 'j':
			return 9;
		case 'k':
			return 10;
		case 'l':
			return 11;
		case 'm':
			return 12;
		case 'n':
			return 13;
		case 'o':
			return 14;
		case 'p':
			return 15;
		case 'q':
			return 16;
		case 'r':
			return 17;
		case 's':
			return 18;
		case 't':
			return 19;
		case 'u':
			return 20;
		case 'v':
			return 21;
		case 'w':
			return 22;
		case 'x':
			return 23;
		case 'y':
			return 24;
		case 'z':
			return 25;
		default:
			return -1;
		}
	}

	/**
	 * Converts int between 0 and 25 to its lowercase character at the index in
	 * the alphabet
	 * 
	 * @param aInt
	 *            the integer to convert between 0 and 25
	 * @return the character at the int index in the alphabet or -1 if out of
	 *         bounds
	 */
	public static char intConvert(int aInt) {

		switch (aInt + 1) {
		case 1:
			return 'a';
		case 2:
			return 'b';
		case 3:
			return 'c';
		case 4:
			return 'd';
		case 5:
			return 'e';
		case 6:
			return 'f';
		case 7:
			return 'g';
		case 8:
			return 'h';
		case 9:
			return 'i';
		case 10:
			return 'j';
		case 11:
			return 'k';
		case 12:
			return 'l';
		case 13:
			return 'm';
		case 14:
			return 'n';
		case 15:
			return 'o';
		case 16:
			return 'p';
		case 17:
			return 'q';
		case 18:
			return 'r';
		case 19:
			return 's';
		case 20:
			return 't';
		case 21:
			return 'u';
		case 22:
			return 'v';
		case 23:
			return 'w';
		case 24:
			return 'x';
		case 25:
			return 'y';
		case 26:
			return 'z';
		default:
			return '#';
		}
	}
}
