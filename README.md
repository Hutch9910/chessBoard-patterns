# ChessBoard Patterns

> **Note**: This README was created with AI assistance. It may lack some accuracy.

A Java-based interactive visualization project that generates and displays chess piece attack patterns on a board. This project explores the geometric patterns created by various chess pieces and their attack positions.

## Project Overview

This project simulates the placement of chess pieces on a multi-team chessboard and visualizes the attack patterns they create. The key concept is to automatically position pieces on the board in a spiral pattern, ensuring that pieces from the same team avoid attacking each other while filling the board with pieces from multiple teams.

## Inspiration

This project was inspired by the mathematical and visual patterns created by chess pieces, as demonstrated in the following YouTube videos:
- [Video 1](https://www.youtube.com/watch?v=UiX4CFIiegM&t)
- [Video 2](https://www.youtube.com/watch?v=VgmDuBCayPw)

## Features

- **Multi-team Support**: Support for different teams with different colors (Black, Red, Blue, Magenta, Green, Yellow)
- **8 Chess Piece Types**: Each with unique attack patterns
- **Automatic Board Generation**: Uses a spiral algorithm to automatically place pieces on the board:
  - Starts from the center
  - Spirals outward in rings
  - Checks for safe positions (avoiding conflicts between same-team pieces)
- **Interactive Visualization**: GUI to visualize the board with color-coded teams
- **Desktop Visualization**: Open the robust Swing `BoardPanel` using the same configuration as the web visualiser
- **Scalable Web Visualization**: Canvas rendering supports board sizes up to 10001 while keeping the browser responsive
- **Configurable Sequence**: Add or remove piece/color entries; the number of teams is derived from the colors selected
- **Attack Range Library**: Inspect the relative attack offsets of every piece type

## Project Structure

```
src/
└── main/
  ├── java/com/hutch9910/chessboardPatterns/
  │   ├── ChessboardPatternsApplication.java  # Spring Boot entry point
  │   ├── input/
  │   │   └── MouseInput.java                 # Swing mouse pan/zoom handling
  │   ├── models/
  │   │   ├── Board.java                       # Dense board and placement logic
  │   │   ├── Piece.java                       # Abstract piece model
  │   │   └── pieces/                          # Chess piece implementations
  │   │       ├── Antelope.java
  │   │       ├── Dabbaba.java
  │   │       ├── Dromedary.java
  │   │       ├── Elephant.java
  │   │       ├── Ferz.java
  │   │       ├── Knight.java
  │   │       ├── Wazir.java
  │   │       └── Zebra.java
  │   ├── utility/
  │   │   └── Point.java                       # 2D coordinate representation
  │   ├── variousEnum/
  │   │   ├── Team.java                        # Team definitions and colors
  │   │   └── TypeOfPiece.java                 # Piece definitions and symbols
  │   ├── visualisation/
  │   │   ├── BoardPanel.java                  # Swing board panel
  │   │   └── VisualFrame.java                 # Swing window frame
  │   └── web/
  │       ├── AttackRangeView.java             # Attack-range page model
  │       ├── BoardGenerationService.java      # Web and desktop board generation
  │       ├── BoardView.java                   # Web board view model
  │       ├── PieceChoice.java                 # Submitted piece/team choice
  │       ├── SparseBoard.java                 # Memory-efficient web board
  │       ├── VisualizationController.java     # Web routes and desktop launch
  │       └── VisualizationSetup.java          # Submitted visualisation settings
  └── resources/
    ├── application.properties
    ├── static/
    │   ├── css/site.css
    │   └── js/board-controls.js
    └── templates/
      ├── attack-range.html
      ├── index.html
      └── visualisation.html
```

## How It Works

### 1. Initialization
- User specifies the board size and repeating piece/team sequence in the web interface
- The number of teams is derived from the selected sequence
- The board size is automatically rounded up to an odd number

### 2. Board Generation
The board uses a spiral algorithm to place pieces:
- First piece is placed at the center
- Subsequent pieces spiral outward in rings
- Each piece is placed in the first "safe" position found, where:
  - The position is not already occupied
  - The position is either not under attack, or only attacked by the same team

### 3. Visualization
- The board can be displayed in the browser canvas or opened in the desktop Swing `BoardPanel`.
- The desktop visualisation uses the Swing panel with the submitted board size; very large dense boards require more memory.

## Requirements

- JDK 21 or newer
- Git
- A graphical desktop environment for the optional Swing desktop visualisation

## Dependencies

The Java dependencies are managed by Maven through `pom.xml`:

- Spring Boot `4.1.1`
- `spring-boot-starter-webmvc` for the web application and MVC controller
- `spring-boot-starter-thymeleaf` for server-rendered HTML templates
- `spring-boot-starter-actuator` for application monitoring endpoints
- Spring Boot Thymeleaf and Web MVC test starters for test support

The frontend uses plain HTML, CSS, and JavaScript. Fonts are loaded from Google Fonts when the application has network access; the application does not require a frontend package manager.

## Clone and Run

### 1. Clone the repository

```bash
git clone https://github.com/Hutch9910/chessBoard-patterns.git
cd chessBoard-patterns
```

### 2. Check Java

```bash
java -version
```

The project is configured for Java 21 in `pom.xml`, if you have an outdated version please install the newer version.

### 3. Build and test

On Windows PowerShell:

```powershell
.\mvnw.cmd clean verify
```

On macOS or Linux:

```bash
./mvnw clean verify
```

This downloads Maven and project dependencies, compiles the application, and runs the tests.

### 4. Start the application

On Windows PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

On macOS or Linux:

```bash
./mvnw spring-boot:run
```

When startup completes, open [http://localhost:8080](http://localhost:8080) in a browser. Keep the terminal open while using the application. Stop the server with `Ctrl+C`.

### 5. Use the application

1. Open the introduction page at `/`.
2. Open the visualiser at `/visualisation`.
3. Choose a board size and repeating piece/team sequence, then select **Generate board**.
4. Use mouse-wheel scrolling to zoom and drag the board to pan.
5. Select **Open desktop visualisation** to launch the Swing window when running on a graphical desktop.
6. Open `/attack-range` to inspect each piece's attack offsets.

Even board sizes are rounded up to the next odd number. Board sizes greater than 5000 may take longer to generate.

## Recent Changes

- Added the browser-based visualiser with configurable board size and repeating piece/team sequences.
- Added the attack-range library for Wazir, Ferz, Dabbaba, Elephant, Knight, Dromedary, Zebra, and Antelope patterns.
- Added the optional Swing desktop visualisation launch from the web interface.
- Added responsive board controls for zooming, panning, and resetting the view.
- Refined the visual design with the Chessboard Patterns landing page, responsive layouts, action arrows, and selected-piece navigation styling.
- Kept spacing around selected attack-range items visually separate from the white panel background.

## Chess Pieces

### Piece Attack Patterns

Attack offsets are written as `(dx, dy)` relative to the piece's position. Each `+/-` value represents both positive and negative directions.

- **Wazir**: `(0, +/-1)` or `(+/-1, 0)` - One square orthogonally
- **Ferz**: `(+/-1, +/-1)` - One square diagonally
- **Dabbaba**: `(0, +/-2)` or `(+/-2, 0)` - Two squares orthogonally
- **Elephant**: `(+/-2, +/-2)` - Two squares diagonally
- **Knight**: `(+/-1, +/-2)` or `(+/-2, +/-1)` - Classic L-shaped move
- **Dromedary**: `(0, +/-3)` or `(+/-3, 0)` - Three squares orthogonally
- **Zebra**: `(+/-2, +/-3)` or `(+/-3, +/-2)` - Long L-shaped leap
- **Antelope**: `(+/-3, +/-4)` or `(+/-4, +/-3)` - Extended L-shaped leap

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
- The visualization uses Spring Boot and Thymeleaf for the web interface
- Board zoom and panning are handled in the browser
- The desktop visualisation is launched from the web interface and runs on the machine hosting Spring Boot

## Future Enhancements

- Pattern analysis and statistics
- Export board state to file
- Additional chess pieces

## License

This project is open source. Feel free to use, modify, and distribute as needed.