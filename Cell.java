
public class Cell {

	
	private char pawn;
	
	//Constructor creates a new object with given parameters
	public Cell() {
		pawn = 'E';
	}
	
	//setter changes the value of pawn attribute
	public void setPawn(char pawnType) {
			pawn = pawnType;
	}
	
	//getter returns the value of pawn
	public char getPawn() {
		return pawn;
	}
	
	
	//checks if pawn is set to empty
	public boolean isEmpty() {
		if(pawn == 'E')
			return true;
		return false;
	}
	
	
	public String toString() {
		char display = pawn;
		
		//if pawn is empty whitespace is displayed.
		if(pawn == 'E')
			display = ' ';
		
		return "" + display;
	}
}
