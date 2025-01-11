import java.awt.Color;
import java.awt.Font;
public class Board {

	
	private Cell[][] board;
	
	//Creates a 2D Cell type array of size board_size X board_size and initialises all cells to be empty
	public Board(int board_size) {
		board = new Cell[board_size][board_size];
		
		for(int i=0; i<board_size; i++)
			for(int j=0; j<board_size; j++)
				board[i][j] = new Cell();
	}
	
	
	//checks whether the cell at this position is empty
	public boolean isEmpty(Position p) {
		return board[p.getRow()][p.getColumn()].isEmpty();
	}
	
	
	//changes the value of the cell at position p to pawnType
	public void setCell(Position p, char pawnType) {
		board[p.getRow()][p.getColumn()].setPawn(pawnType);
	}
	
	
	//changes the value of the cell at position b to the value of the cell at position a. changes the value of cell at a to empty ('E').
	public void moveSquare(Position a, Position b) {
		board[b.getRow()][b.getColumn()].setPawn(board[a.getRow()][a.getColumn()].getPawn()); 
		board[a.getRow()][a.getColumn()].setPawn('E');
	}
	
	
	//checks whether any player has won
	public boolean winningState() {
		boolean win;
		
		//check for win at rows
		for(int i=0; i < board.length; i++) {
			win = true;
			
			
			//if the first cell of the row is empty then there is no win at that row and the loop continues to the next iteration.
			if(board[i][0].isEmpty())
				continue;
			
			//if the first cell isn't empty then it checks if the rest of the cells are the same as the first one.
			//if at least one cell in the same row is different then there is no win at that row.
			for(int j=1; j < board[0].length; j++) {
				if(board[i][j].getPawn() != board[i][0].getPawn())
					win = false;
			}
			
			if(win)
				return true;

		}
		
		
		//check for win at columns
		for(int j=0; j < board[0].length; j++) {
			win = true;
			
			//if the first cell of the column is empty then there is no win at that column and the loop continues to the next iteration.
			if(board[0][j].isEmpty())
				continue;
			
			//if the first cell isn't empty then it checks if the rest of the cells are the same as the first one.
			//if at least one cell in the same column is different then there is no win at that column.
			for(int i=1; i < board.length; i++) {
				if(board[i][j].getPawn() != board[0][j].getPawn())
					win = false;
			}
			
			if(win)
				return true;
		}
		
		//if no win was found return false.
		return false;
	}
	
	
	//returns the size of the board.
	public int getSize() {
		return board.length;
	}
	
	
	//returns the pawn placed at position p of board. If p is out of bounds of table returns 'I'.
	public char getCell(Position p) {
		
		//if the position given is out of bounds, return 'I'.
		if (p.getRow() >= getSize()  || p.getRow() < 0 || p.getColumn() >= getSize() || p.getColumn() < 0)
					return 'I';
		
		return board[p.getRow()][p.getColumn()].getPawn();
	}
	
	
	
	//displays board
	public String toString() {
		
		String display = "";
		
		//prints horizontal line. There are 4 * board_size + 1 dashes in each line.
		for(int num = 0; num< 4*board.length+1; num++)
			display += "-";
		display += "\n";
		
		//prints vertical lines and cells.
		for(int i=0; i<board.length; i++) {
			for(int j =0; j<board[0].length; j++) {
				display += "| " + board[i][j] + " ";
			}
			
			display += "|\n";
			
			//prints horizontal line.
			for(int num = 0; num< 4*board.length+1; num++)
				display += "-";
			display += "\n";
		}
		
		return display;
	}
	
	
	
	//Graphics:
	//initialise some static variables that are used later:
	private static final double penRadius = 0.035;
	private static final double offset = 0.35;
	private static final Color letters = new Color(24, 29, 39);
	private static final Color square1 = new Color(210, 209, 185);
	private static final Color square2 = new Color(251, 252, 235);
	
	
	//Create canvas
	public void createCanvas() {
		StdDraw.setCanvasSize(500, 500);
		StdDraw.setXscale(0, board.length);
		StdDraw.setYscale(0, board.length);
	}
	
	//Draws the grid of the game
	private void drawGrid() {
		
		//Draw grid squares.
		for(int i=0; i<board.length; i++) {
			for(int j=0; j<board.length; j++) {
				
				if((i + j) % 2 == 0)
					StdDraw.setPenColor(square1);
				else
					StdDraw.setPenColor(square2);
				
				StdDraw.filledSquare(i+0.5, j+0.5, 0.5);
			}
		}
	}
	
	//Draws an X at (x,y):
	private void drawX(double x, double y) {
		
		
		StdDraw.setPenColor(Color.blue.brighter());
		
		StdDraw.line(x-offset, y-offset, x+offset, y+offset);
		StdDraw.line(x-offset,  y+offset,  x+offset,  y-offset);
		
	}
	
	//Draws a circle with its centre at (x,y):
	private void drawO(double x, double y) {
		
		StdDraw.setPenColor(Color.red.brighter());
		StdDraw.circle(x, y, offset);
	}
	
	//updates the board:
	public void updateBoard() {
		
		//redraws grid in order to erase previous X's and O's
		drawGrid();
		StdDraw.setPenRadius(penRadius);
		
		//for each cell in board draws an x or an o at corresponding square.
		for(int i=0; i<board.length; i++) {
			for(int j=0; j<board.length; j++) {
				
				if(board[i][j].getPawn() == 'X')
					drawX( j+0.5, board.length - i - 0.5);
				else if(board[i][j].getPawn() == 'O')
					drawO( j+0.5, board.length - i - 0.5 );
			}
		}
	}
	
	//Displays win message on board
	public void winMessage(char pawn) {
		
		//creates a new bold arial font of size 60.
		Font font = new Font("Arial", Font.BOLD, 60);
				
		//prints text using that font in the middle of the board.
		StdDraw.setPenColor(letters);
		StdDraw.setFont(font);
		StdDraw.text(board.length/2.0, board.length/2.0, "Player " + pawn + " wins!");
	}
	
	
}
