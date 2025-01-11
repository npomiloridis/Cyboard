/**
* Author: Nikolas Pomiloridis
* Written: 26/11/2024
* Last updated: 02/12/2024
*
* Compilation command: 	Windows:	javac -cp .;stdlib.jar Cyboard.java
* 			Linux:		javac -cp .:stdlib.jar Cyboard.java
* 
* Execution command:	Windows:	java -cp .;stdlib.jar Cyboard
* 			Linux: 		java -cp .:stdlib.jar Cyboard
* 
* 
* This programme is a tic-tac-toe like game. When the execution command is entered, user is prompted to choose a game mode
* and the size of the board. 
* 
* Players place pawns equal to the size of the board and then take turns moving one of their pieces to an adjacent square
* (pawns cannot move diagonally).
* 
* The aim of the game is to fill a row or column of the board with your pawns, while trying to stop the opponent from doing
* so themselves.
* 
*/


public class Cyboard {

	public static void main(String[] args) {
		
		Game game;
		int gameMode, N;
	
		//prints welcome message and asks user to initialise game mode and board size.
		System.out.print("Welcome to the Game!\n"
				+ "1. Human vs Human\n"
				+ "2. Human vs Computer - not implemented\n"
				+ "3. Computer vs Computer - not implemented\n"
				+ "Choose game mode (1-3) and board size (>2 and <6):");
		
		
		//read game mode and board size and check for invalid input.
		gameMode = StdIn.readInt();
		N = StdIn.readInt();
		
		while(N <= 2 || N >=6 || gameMode < 1 || gameMode > 3) {
			System.out.print("Invalid input. Choose game mode (1-3) and board size (>2 and <6):");
			gameMode = StdIn.readInt();
			N = StdIn.readInt();
		}
		
		
		//Depending on the input given, creates a new Game object and calls it's play method:
		
		if(gameMode == 1) 
			game = new Game("Human", "Human", N);
		else if(gameMode == 2)
			game = new Game("Human", "Computer", N);
		else
			game = new Game("Computer", "Computer", N);

		
		game.play();
		
		

	}

}
