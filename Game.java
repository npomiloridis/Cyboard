
public class Game {

	private String player1Type;
	private String player2Type;
	private int boardSize;
	private int turn = 0; //Keep track of turn number

	
	//Constructor creates a new object with given parameters
	public Game(String p1Type, String p2Type, int N) {
		
		player1Type = p1Type;
		player2Type = p2Type;
		boardSize = N;
		
	}
	
	
	public void play() {
		
		//Creates players and board
		Board board = new Board(boardSize);
		
		Player player1 = new Player(player1Type, board, 'X');
		Player player2 = new Player(player2Type, board, 'O');
		
		
		

		//Create and display board to start the game.
		board.createCanvas();
		board.updateBoard();
			
		//Placing phase:
		//If any player wins during the placing phase then the loop stops.
		for(int i=0; i< boardSize; i++) {
			
			turn++;
			System.out.println(player1);
			player1.setPiece();
			board.updateBoard();
			
			if(board.winningState())
				break;
			
			turn++;
			System.out.println(player2);
			player2.setPiece();
			board.updateBoard();
	
		}
			
		//Moving phase:
		//check for win. While no player has won the game the programme asks players to move pawns.
		while(!board.winningState()) {
			
			turn ++;
			
			//during odd numbered turns, player1 plays
			if(turn % 2 == 1) {
				
				System.out.println(player1);
				player1.movePiece();
				board.updateBoard();
				
			}
			
			//during even numbered turns player2 plays
			else {
				
				System.out.println(player2);
				player2.movePiece();
				board.updateBoard();
			}
			
		}
		
		//Display win message.
		if(turn % 2 == 1) {
			System.out.println("Player X wins!");
			board.winMessage('X');
		}
		else {
			System.out.println("Player O wins!");
			board.winMessage('O');
		}
		
	}	
	

}


