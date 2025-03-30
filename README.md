# Cyboard - Strategic Board Game

A Java implementation of a Tic-Tac-Toe-like game with advanced movement mechanics, supporting human vs human, human vs computer, and computer vs computer gameplay.

 

## 🕹️ Game Rules

**Placement Phase:**
   - Players alternately place their pawns (X and O) on empty cells
   - Number of pawns equals board size (e.g., 3 pawns for 3x3 board)

**Movement Phase:**
   - On each turn, players move one pawn to an adjacent cell (no diagonals)
   - Goal: Create a complete row or column of your pawns

**Winning:**
   - First player to complete a full row or column wins
   - Game ends immediately when a win is detected

---

## 🎮 Features

- **Two-Phase Gameplay**:
  - Placement phase: Players alternately place pawns
  - Movement phase: Players slide pawns to adjacent spaces
    
- **Multiple Game Modes**:
  - Human vs Human
  - Human vs Computer (AI)
  - Computer vs Computer
    
- **Configurable Board Size** (3x3 to 5x5)
  
- **Graphical Interface** using StdDraw
  
- **Intelligent AI** that:
  - Blocks opponent wins
  - Creates winning opportunities
  - Makes strategic moves

---

## 📂 Project Structure

| Class       | Description |
|-------------|-------------|
| `Cyboard`   | Main entry point, handles game initialization |
| `Game`      | Manages game flow and turn logic |
| `Player`    | Contains human/computer player logic (placement and movement) |
| `Board`     | Manages game state, win detection, and rendering |
| `Cell`      | Represents individual board cells (X, O, or empty) |
| `Position`  | Handles coordinate positions on the board |

---

## 🚀 How to Run

### Prerequisites
- Java JDK 8+
- `stdlib.jar` (included in repository)

### Steps
1. **Clone the repository**:
   ```bash
   git clone https://github.com/npomiloridis/Cyboard.git
   cd Cyboard
   ```

2. **Compile the code**:
   - ***Windows:***  ```javac -cp .;stdlib.jar Cyboard.java```
   - ***Linux:*** ```javac -cp .:stdlib.jar Cyboard.java```
     
3. **Launch the game**:
   - ***Windows:***  ```java -cp .;stdlib.jar Cyboard```
   - ***Linux:*** ```java -cp .:stdlib.jar Cyboard```
