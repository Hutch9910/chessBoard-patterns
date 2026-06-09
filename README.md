# ChessBoard Patterns

> **Note**: This README was created with AI assistance. It may lack some accuracy.

A Java-based interactive visualization project that generates and displays chess piece attack patterns on a board. This project explores the geometric patterns created by various chess pieces and their attack positions.

## Project Overview

This project simulates the placement of chess pieces on a multi-team chessboard and visualizes the attack patterns they create. The key concept is to automatically position pieces on the board in a spiral pattern, ensuring that pieces from the same team avoid attacking each other while filling the board with pieces from multiple teams.

## Inspiration

This project was inspired by the mathematical and visual patterns created by chess pieces, as demonstrated in the following YouTube videos:
- [Video 1](https://www.youtube.com/watch?v=UiX4CFIiegM&t=666s)
- [Video 2](https://www.youtube.com/watch?v=VgmDuBCayPw)

## Features

- **Multi-team Support**: Support for up to 6 different teams with different colors (Black, Yellow, Blue, Magenta, Green)
- **8 Chess Piece Types**: Each with unique movement patterns and attack positions
- **Automatic Board Generation**: Uses a spiral algorithm to automatically place pieces on the board:
  - Starts from the center
  - Spirals outward in rings
  - Checks for safe positions (avoiding conflicts between same-team pieces)
- **Interactive Visualization**: GUI to visualize the board with color-coded teams

## Project Structure

```
src/
├── app/
│   └── App.java                 # Main entry point
├── models/
│   ├── Board.java              # Board management and piece placement logic
│   ├── Piece.java              # Abstract base class for all chess pieces
│   ├── Simulation.java         # Simulation orchestration
│   └── pieces/                 # Chess piece implementations
│       ├── Antelope.java
│       ├── Dabbaba.java
│       ├── Dromedary.java
│       ├── Elephant.java
│       ├── Ferz.java
│       ├── Knight.java
│       ├── Wazir.java
│       └── Zebra.java
├── input/
│   └── MouseInput.java         # Mouse event handling
├── visualisation/
│   ├── VisualFrame.java        # Main window frame
│   ├── StartPanel.java         # Initial setup panel
│   └── BoardPanel.java         # Board visualization panel
├── utility/
│   └── Point.java              # 2D coordinate representation
└── variousEnum/
    ├── Team.java               # Team enumeration
    └── TypeOfPiece.java        # Piece type enumeration
```

## How It Works

### 1. Initialization
- User specifies the number of teams (2-6)
- User specifies the board size (automatically rounds to odd numbers)

### 2. Board Generation
The board uses a spiral algorithm to place pieces:
- First piece is placed at the center
- Subsequent pieces spiral outward in rings
- Each piece is placed in the first "safe" position found, where:
  - The position is not already occupied
  - The position is either not under attack, or only attacked by the same team

### 3. Visualization
- The board is displayed with color-coded pieces for each team

## Getting Started

1. Compile the Java files:
```bash
javac -d bin src/app/*.java src/models/*.java src/models/pieces/*.java src/input/*.java src/visualisation/*.java src/utility/*.java src/variousEnum/*.java
```

2. Run the application:
```bash
java -cp bin app.App
```

3. Follow the prompts:
   - Enter the number of teams (2-6)
   - Enter the desired board size

4. The visualization window will open showing the generated board pattern

## Chess Pieces

### Piece Attack Patterns

- **Wazir**: (0,1) - Moves one square orthogonally
- **Ferz**: (1,1) - Moves one square diagonally
- **Dabbaba**: (2,0) - Jumps two squares orthogonally
- **Elephant**: (2,2) - Jumps two squares diagonally
- **Knight**: (1,2) or (2,1) - Classic L-shaped move
- **Dromedary**: (1,3) - Leaps to (3,1) offset
- **Zebra**: (2,3) - Leaps to (3,2) offset
- **Antelope**: (3,4) - Leaps to (4,3) offset

## Algorithm Details

### Spiral Placement Algorithm

The algorithm maintains the last found position for each team and spirals outward from there:

1. Calculate the current ring radius based on the last position
2. Move in a square spiral pattern: right → up → left → down
3. For each position in the spiral:
   - Check if it's safe (not occupied, not attacked by other teams)
   - If safe, place the piece there
   - If unsafe, continue to next position

This creates interesting visual patterns as pieces tend to cluster around the center while maintaining the constraint that same-team pieces don't attack each other.

## Visual Output

The GUI displays:
- **Board**: Shows the chessboard
- **Pieces**: Color-coded by team

## Technical Notes

- The board is always an odd-sized square (automatically rounds up even inputs)
- The center of the board serves as the starting point
- The visualization uses Swing for the GUI
- Mouse input handling is available for future interactive features

## Future Enhancements

- Interactive piece placement/selection
- Pattern analysis and statistics
- Export board state to file
- Animation of the spiral placement process
- Additional chess pieces

## License

This project is open source. Feel free to use, modify, and distribute as needed.