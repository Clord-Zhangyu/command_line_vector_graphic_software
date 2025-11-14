# Clevis Geometry System

A JavaFX-based geometry application for creating, manipulating, and querying geometric shapes.

## Quick Start

### Option 1: Use Installers (Recommended)
Download the appropriate installer for your system:
- **Windows**: `Clevis-1.0.exe` (from GitHub Actions artifacts)
- **macOS**: `Clevis-1.0.dmg` (from GitHub Actions artifacts)
- **Linux**: `Clevis-1.0.deb` (from GitHub Actions artifacts)

These installers are self-contained and require no additional setup.

### Option 2: Run from Source

#### Prerequisites
- Java 21 or later
- Maven 3.6+ (optional but recommended)

#### Windows
1. Open Command Prompt or PowerShell in the project directory
2. Run: `run.bat` (full version) or `run-simple.bat` (simplified)

#### macOS/Linux
1. Open Terminal in the project directory
2. Run: `./run.sh` (full version) or `./run-simple.sh` (simplified)

#### If Maven is not installed:
The scripts will guide you through:
1. Installing Maven and configuring PATH
2. Using pre-compiled classes (if available)
3. Falling back to the installer (recommended)

#### Manual Maven Build
```bash
# Compile the project
mvn compile

# Create JAR file
mvn package

# Run the application
mvn javafx:run
```

## Features

- **Shape Creation**: Create circles, squares, rectangles, and lines
- **Grouping**: Group shapes together for collective operations
- **Transformations**: Move shapes and groups
- **Undo/Redo**: Full history support
- **Interactive Canvas**: Zoom and pan with mouse
- **Grid System**: Labeled coordinate grid with 2-unit spacing
- **Command Interface**: Text-based command input

## Example Commands

```
circle c1 0 0 5          # Create circle at origin with radius 5
square s1 10 10 8        # Create square at (10,10) with side 8
line l1 0 0 10 10        # Create line from (0,0) to (10,10)
group g1 c1 s1           # Group circle and square
move g1 5 -3             # Move group by (5,-3)
listall                  # List all shapes
undo                     # Undo last operation
redo                     # Redo last undone operation
```

## Architecture

- **Backend**: Java 22 with modular design
- **Frontend**: JavaFX 17 with FXML
- **Build**: Maven with JavaFX Maven Plugin
- **Packaging**: jlink + jpackage for native installers

## Troubleshooting

### JavaFX Issues
If you get JavaFX-related errors:
1. Ensure Java 22+ is installed
2. For manual execution, you may need to download JavaFX separately
3. Use the provided installers for guaranteed compatibility

### Module Issues
If you get module-related errors:
1. Ensure you're using Java 22+
2. Check that `module-info.java` is properly configured
3. Use Maven build instead of manual compilation

## Development

### Building Installers
The project includes GitHub Actions workflow that automatically builds installers for all platforms when you push to master or create a tag.

### Project Structure
```
src/
├── module-info.java          # Module definition
├── Clevis/
│   ├── App.java             # JavaFX Application entry point
│   ├── Controller/          # Command parsing and execution
│   ├── Model/              # Geometry classes and data
│   └── view/               # FXML files
└── resource/
    └── fxml/
        └── main.fxml       # Main UI layout
```

## License

This project is for educational purposes (OOP Course Project).
