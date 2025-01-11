
public class Position {
	
	private int row, column;
	
	
	//Constructor creates a new object with given parameters
	public Position(int r, int c) {
		row = r;
		column = c;
	}
	
	
	//Getters return an attribute of an object
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
	
	
	public String toString() {
		return "("+row+","+column+")";
	}
		
}
