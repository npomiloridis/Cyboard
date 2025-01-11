
public class Player {

	
	private String playerType;
	private Board board;
	private char pawnType;
	
	//Constructor creates a new object with given parameters
	public Player(String playerType, Board board, char pawnType) {
		this.playerType = playerType;
		this.board = board;
		this.pawnType = pawnType;

	}
	
	//Returns the position of mouse click on board.
	private Position getMouseClick() {
		
		//wait for mouse click
		while(!StdDraw.isMousePressed());
		
		//get mouse coordinates and transpose them to match board coordinates.
		int row = board.getSize() -1 -(int)StdDraw.mouseY();
		int col = (int)StdDraw.mouseX();
		
		Position coordinates = new Position(row,col);
		
		//delay programme for 200 milliseconds to avoid error messages printing too many times.
		StdDraw.pause(200);

		return coordinates;
	}
	
	
	//returns the position where only one pawn is missing to win. If no possible win is detected returns (-1,-1).
	private Position oneAway(char pawn) {
		
		int countFull, countEmpty;
		Position coordinates = null;
		Position winningPosition = null;
		
		//check for possible horizontal win.
		for(int row=0; row<board.getSize(); row++) {
			countFull = 0;
			countEmpty = 0;
			
			//counts pawns for every row.
			for(int col = 0; col < board.getSize(); col ++) {
				
				coordinates = new Position(row, col);
				
				if(board.getCell(coordinates) == pawn)
					countFull ++;
				else if(board.getCell(coordinates) == 'E') {
					countEmpty++;
					winningPosition = coordinates;
				}
			}
			
			//If there is a row with only one cell missing returns the Position of that cell.
			if(countFull == board.getSize()-1 && countEmpty == 1) {
				return winningPosition;
			}
		}
		
		//check for possible vertical win.
		for(int col=0; col<board.getSize(); col ++) {
			countFull = 0;
			countEmpty = 0;
			
			//counts pawns for every column
			for(int row=0; row<board.getSize(); row++) {
				
				coordinates = new Position(row, col);
				
				if(board.getCell(coordinates) == pawn)
					countFull++;
				else if(board.getCell(coordinates) == 'E') {
					countEmpty++;
					winningPosition = coordinates;
				}
			}
			
			//If there is a row with only one cell missing returns the Position of that cell.
			if(countFull == board.getSize()-1 && countEmpty == 1)
				return winningPosition; 
		}
		
		//if no possible win was detected returns position (-1,-1)
		winningPosition = new Position(-1,-1);
		return winningPosition;
	}
	
	
	
	//checks if the two positions given are next to each other.
	private boolean isNextTo(Position a, Position b) {
		//for two positions to be next to each other, the distance between them has to equal 1:
		
		//calculate distance of two positions using the euclidian distance equation
		double distance = Math.sqrt( Math.pow(a.getRow() - b.getRow(), 2) + Math.pow(a.getColumn() - b.getColumn(), 2));
		return distance == 1;
	}
	
	
	//Returns true if there is at least one cell containing pawn character next to the position given.
	private boolean hasNeighbour(Position p, char pawn) {
		
		Position neighbour = null;
		
		for(int row=0; row<board.getSize(); row++) {
			for(int col=0; col<board.getSize(); col++) {
				
				neighbour = new Position(row, col);
				if(board.getCell(neighbour) == pawn && isNextTo(neighbour, p)) 
					return true;
					
			}
		}
		
		return false;
	}
	
	
	//Returns true if the player can make a legal move this round.
	private boolean hasMove() {
		
		Position p = null;
		
		for(int row=0; row<board.getSize(); row++) {
			for(int col=0; col<board.getSize(); col++) {
				
				p = new Position(row,col);
				
				if(board.getCell(p) == pawnType && hasNeighbour(p, 'E')) 
						return true;
					
			}
		}
		return false;
	}
	
	
	//Moves pawn at Position p to a random direction. Checks whether the position the pawn is being moved to is empty to avoid bugs.
	//User should check if Position p has an empty cell it can move to before calling this method. Not doing so may result in an infinite loop.
	private void moveRandomly(Position p) {
		
		int direction;
		Position moveTo = null;
		
		//repeats he following code until a piece is moved and the command return is executed to terminate the method.
		while(true) {
			
			//generates a random integer from 0 - 3
			direction = (int)(Math.random() * 4);
			
			//moves to square above p if empty
			if(direction == 0) {
				moveTo = new Position(p.getRow()-1, p.getColumn());
				if(board.getCell(moveTo) == 'E') {
					board.moveSquare(p, moveTo);
					return;
				}
			}
			//moves to square right of p is empty
			else if(direction == 1) {
				moveTo = new Position(p.getRow(), p.getColumn()+1);
				if(board.getCell(moveTo) == 'E') {
					board.moveSquare(p, moveTo);
					return;
				}
			}
			//moves to square below p if empty
			else if(direction == 2) {
				moveTo = new Position(p.getRow()+1, p.getColumn());
				if(board.getCell(moveTo) == 'E') {
					board.moveSquare(p, moveTo);
					return;
				}
			}
			//moves to square left of p if empty
			else if(direction == 3) {
				moveTo = new Position(p.getRow(), p.getColumn()-1);
				if(board.getCell(moveTo) == 'E') {
					board.moveSquare(p, moveTo);
					return;
				}
			}
			
			//if the square at the direction generated isn't empty, the loop continues and generates new direction.
		}	
	}
	
	
	//Checks if there is a pawnToMove next to coordinates. If there is it moves that pawn to that position and returns true
	//else it returns false.
	private boolean moveToWin(Position coordinates, char pawnToMove, char winningPawn) {
		Position p = null;
		int countVertical = 0, countHorizontal = 0;
		
		//counts players pawns in same column:
		for(int row=0; row<board.getSize(); row++) {
			p = new Position(row, coordinates.getColumn());
			if(board.getCell(p) == winningPawn)
				countVertical++;					
		}
		
		//counts players pawns in same row:
		for(int col=0; col<board.getSize(); col++) {
			p = new Position(coordinates.getRow(), col);
			if(board.getCell(p) == winningPawn)
				countHorizontal++;
		}
		
		if(countVertical>countHorizontal) {
			//Check if there is a pawn left of winning cell and if so move it to winning cell.
			p = new Position(coordinates.getRow(), coordinates.getColumn()-1);
			if(board.getCell(p) == pawnToMove) {
				board.moveSquare(p, coordinates);
				return true;
			}
			
			//Check for pawn right of winning cell.
			p = new Position(coordinates.getRow(), coordinates.getColumn()+1);
			if(board.getCell(p) == pawnToMove) {
				board.moveSquare(p, coordinates);
				return true;
			}
		}
		else {
			//Check for pawn above winning cell.
			p = new Position(coordinates.getRow()-1, coordinates.getColumn());
			if(board.getCell(p) == pawnToMove) {
				board.moveSquare(p, coordinates);
				return true;
			}
			
			//check for pawn below winning cell.
			p = new Position(coordinates.getRow()+1, coordinates.getColumn());
			if(board.getCell(p) == pawnToMove) {
				board.moveSquare(p, coordinates);
				return true;
			}
		}
		
		//if no pawn was found return false.
		return false;
	}

	
	
	public void setPiece() {
		
		//If the player is human then the programme asks for a position, else if player is machine the programme decides on its on.
		if(playerType.equals("Human")) {
			
			System.out.printf("Enter row and column to place your pawn (0-%d): \n", board.getSize()-1 );
					
			//get input from mouse click:
			Position coordinates = getMouseClick();
			
			//Checks whether the coordinates are inside the bounds of the board and that cell is empty. If not asks the user to reenter row and col.			
			while(board.getCell(coordinates) == 'I' || !board.isEmpty(coordinates)) {
				
				//display error message depending on why the position entered is invalid.
				if(board.getCell(coordinates) == 'I')
					System.out.printf("Position entered is out of bounds of the board. Enter row and column to place your pawn (0-%d): \n", board.getSize()-1);
				else
					System.out.printf("Position is already occupied. Enter the position of an empty cell on the board (0-%d): \n", board.getSize() - 1);
				
				//get input from mouse click:
				coordinates = getMouseClick();
				
			}
			
			board.setCell(coordinates, pawnType);
			
		}
		
		//If the player is not human the machine chooses a position.
		else {
			
			Position coordinates = null;
			
			//first checks if the computer can win:
			coordinates = oneAway(pawnType);
			
			if(board.getCell(coordinates) != 'I') {
				board.setCell(coordinates, pawnType);
				return;
			}
			
			//If the computer doesn't have a winning move, it checks whether it can block the winning move of an opponent.
			char opponentPawn;
			
			if(pawnType == 'X')
				opponentPawn = 'O';
			else
				opponentPawn = 'X';
			
			
			coordinates = oneAway(opponentPawn);
			if(board.getCell(coordinates) != 'I') {
				board.setCell(coordinates, pawnType);
				return;
			}
			
			//If no player has a winning move the computer chooses a cell at random.
			int row, col;
			do {
				row = (int)(Math.random()*board.getSize());
				col = (int)(Math.random()*board.getSize());
				
				coordinates = new Position(row, col);
			}while(board.getCell(coordinates) != 'E');
			
			board.setCell(coordinates, pawnType);
			
			
		}
		
		
	}
	
	
	public void movePiece() {
		
		//If the player cannot make a legal move then the programme skips his turn:
		if(!hasMove()) {
			System.out.println("No valid move available for player " + pawnType);
			return;
		}

		//If the player is human the programme asks for parameters and checks their validity.
		if (playerType.equals("Human")) {
			
			
			//Read position to move from:
			System.out.printf("Enter the row and column of the pawn you want to move (0-%d): \n", board.getSize() - 1);
			
			
			//get input from mouse.
			Position moveFromCoordinates = getMouseClick();
			

			//check validity of input.
			while(board.getCell(moveFromCoordinates) != pawnType || !hasNeighbour(moveFromCoordinates, 'E')) {
				
				//print error message depending on why the position entered is invalid.
				if(!hasNeighbour(moveFromCoordinates, 'E') && board.getCell(moveFromCoordinates) == pawnType)
					System.out.printf("Pawn entered cannot move to any spaces. Enter the row and column of the pawn you want to move (0-%d): \n", board.getSize()-1);
				else if(board.getCell(moveFromCoordinates) == 'E')
					System.out.printf("Can't move pawn from an epty cell. Enter the row and column of the pawn you want to move (0-%d): \n", board.getSize() -1);
				else if(board.getCell(moveFromCoordinates) == 'I')
					System.out.printf("Position entered is out of bounds of the board. Enter the row and column of the pawn you want to move (0-%d): \n", board.getSize()-1);
				else
					System.out.printf("Can't move opponents pawn. Enter the row and column of the pawn you want to move (0-%d): \n", board.getSize() - 1);
				

				moveFromCoordinates = getMouseClick();

			}
			
			
			//Read position to move to:
			System.out.printf("Enter the row and column where you want to move the pawn (0-%d): \n", board.getSize() - 1);
			
			//get mouse input
			Position moveToCoordinates = getMouseClick();
			
			//Asks the user to reenter row and column while the position isn't empty, or position isn't next to selected cell. (If the user enters the same position as before, the programme
			//will ask to reenter position as that cell isn't empty yet).
			while(board.getCell(moveToCoordinates) != 'E' || !isNextTo(moveToCoordinates, moveFromCoordinates)) {
				
				//print error message depending on why the position isn't valid.
				if(!isNextTo(moveToCoordinates, moveFromCoordinates))
					System.out.printf("You can only move a pawn one space vertically or horizontally. Enter the row and column where you want to move the pawn (0-%d): \n", board.getSize()-1);
				else if(board.getCell(moveToCoordinates) == 'I')
					System.out.printf("Position entered is out of bounds of the board. Enter the row and column where you want to move the pawn (0-%d): \n", board.getSize()-1);
				else
					System.out.printf("Can't move pawn to a non empty cell. Enter the row and column where you want to move the pawn (0-%d): \n", board.getSize()-1);
				

				moveToCoordinates = getMouseClick();
				
			}
			
			board.moveSquare(moveFromCoordinates, moveToCoordinates);
			
			
		}
		
		else {
			
			//Checks whether computer can win:
			Position coordinates = oneAway(pawnType);
			
			if(board.getCell(coordinates) != 'I') {
				boolean moved;
				moved = moveToWin(coordinates, pawnType, pawnType);
				
				//if a pawn was moved then the computers turn ends.
				if(moved)
					return;
			}
			
			//If computer can't win, checks if opponent can win next move:
			char opponentPawn;
			
			if(pawnType == 'X')
				opponentPawn = 'O';
			else
				opponentPawn = 'X';
			
			coordinates = oneAway(opponentPawn);
			
			if(board.getCell(coordinates) != 'I') {
				boolean moved;
				moved = moveToWin(coordinates, pawnType, opponentPawn);
				//if a pawn was moved then the computers turn ends.
				if(moved)
					return;
			}
			
			//If no player is one away from win the computer checks whether one of its pawns is being used to block opponent.
			
			Position p = null;
			Position blocking = null;
			boolean isBlocking = false;
			
			//Checks for blocking pawn in rows.
			for(int row =0; row<board.getSize(); row++) {
				int countMyPawn = 0, countOpponentPawn = 0;
				
				//counts pawns in each row
				for(int col=0; col<board.getSize(); col++) {
					p = new Position(row, col);
					
					//if a pawn is the same type as the players, that might be a blocking pawn, so the programme keeps a deep copy of Position p at blocking
					if(board.getCell(p) == pawnType) {
						countMyPawn++;
						blocking = new Position(p.getRow(), p.getColumn());
					}
					else if(board.getCell(p) == opponentPawn)
						countOpponentPawn++;
				}
				
				//if any row has exactly one of the player's pawns and every other pawn is the opponents pawns then that pawn is blocking a win
				if(countMyPawn == 1 && countOpponentPawn == board.getSize()-1) {
					isBlocking = true;
					break;
				}
			}
			
			//If no blocking pawn was found in a row checks columns.
			if(!isBlocking) {
				
				for(int col = 0; col <board.getSize(); col++) {
					int countMyPawn = 0, countOpponentPawn = 0;
					
					for(int row=0; row<board.getSize(); row++) {
						p = new Position(row, col);
						
						if(board.getCell(p) == pawnType) {
							countMyPawn++;
							blocking = new Position(p.getRow(), p.getColumn());
						}
						else if(board.getCell(p) == opponentPawn)
							countOpponentPawn++;
					}
					
					if(countMyPawn == 1 && countOpponentPawn == board.getSize()-1) {
						isBlocking = true;
						break;
					}
				}
			}
			
			boolean found = false;
			Position randomPawn = null;
			
			//find a pawn that is not blocking opponent and can move
			//start from random position in board
			int row = (int)(Math.random()*board.getSize());
			int col = (int)(Math.random()*board.getSize());
			
			int startingRow = row;
			int startingCol = col;
			
			do {
				
				randomPawn = new Position(row, col);
				
		/*if the pawn at randomPawn is one of the player's pawns and the player either doesn't have a blocking pawn
		**or their blocking pawn isn't at randomPawn, then the programme checks if that pawn can be moved to a neighbouring
		**cell. If yes then the programme moves that pawn in a random direction.*/
				if(board.getCell(randomPawn) == pawnType ) {
					if(!isBlocking || (randomPawn.getRow() != blocking.getRow() || randomPawn.getColumn() != blocking.getColumn()))
						if(hasNeighbour(randomPawn, 'E')) {
							moveRandomly(randomPawn);
							found = true;
						}
				}
				
				//if the pawn at that position cannot be moved then the programme checks the next cell.
				
				col++;

				if(col >= board.getSize()) {
					col = 0;
					row++;
				}
				
				if(row >=board.getSize()) {
					row = 0;
				}
				
			//this loop stops either when a pawn that can be moved was found, or if the programme iterated through the whole board and no pawns were found
			}while(!found && !(startingRow == row && startingCol == col));
			
			
			
			//if the previous loop iterates through the whole board and doesn't find a movable piece, then the only piece that
			//can be moved is the blocking pawn (since we checked whether the player can move at least one pawn at the start of this method)
			if(!found)
				moveRandomly(blocking);
			
		}
			
	}
	
		
	public String toString() {
		return "Current player : " + pawnType + "\n";
	}
	
	
}
