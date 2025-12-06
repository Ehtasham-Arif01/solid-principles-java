
# Smart City Alerts - SOLID Principles Demonstration

## Project Overview
This project demonstrates four SOLID principles through separate Java programs for a Smart City Public Services Management System.

## Project Structure
```
SmartCityAlerts/
├── ISP/                           # Interface Segregation Principle
│   └── ISP_Main.java
├── OCP/                           # Open/Closed Principle
│   └── OCP_Main.java
├── DIP/                           # Dependency Inversion Principle
│   └── DIP_Main.java
├── RoleBased/                     # Role-Based Notifications
│   └── RoleBased_Main.java
├── SmartCityLauncher.java         # Single launcher file
├── setup.sh                       # Setup script for Linux/Mac
├── setup.bat                      # Setup script for Windows
├── run.sh                         # Run script for Linux/Mac
├── run.bat                        # Run script for Windows
└── README.md
```

## Quick Start

### Step 1: Run Setup Script
**Linux/Mac:**
```bash
chmod +x setup.sh
./setup.sh
```

**Windows:**
Double-click `setup.bat` or run in Command Prompt.

### Step 2: Run the Launcher
**After setup completes:**
```bash
./run.sh          # Linux/Mac
```
or
```bash
run.bat           # Windows
```

## What the Launcher Does
The `SmartCityLauncher.java` provides a menu-driven interface to:
1. Run ISP Program (Interface Segregation Principle)
2. Run OCP Program (Open/Closed Principle)
3. Run DIP Program (Dependency Inversion Principle)
4. Run Role-Based Program (Role-Based Notifications)
5. Run All Programs Sequentially
6. Exit

## Manual Compilation and Running
If you prefer to run programs individually:

```bash
# Compile a specific program
javac ISP/ISP_Main.java

# Run the program
java -cp "ISP" ISP_Main
```

Repeat for OCP, DIP, and RoleBased folders.

## Programs Description

### 1. ISP Program
- Demonstrates Interface Segregation Principle
- Different user types implement only needed interfaces
- Citizens: Email, SMS, In-App
- City Officers: Email only
- Emergency Responders: Email and SMS

### 2. OCP Program
- Demonstrates Open/Closed Principle
- New notification services can be added without modifying existing code
- Shows extension without modification

### 3. DIP Program
- Demonstrates Dependency Inversion Principle
- High-level modules depend on abstractions
- Easy to switch notification channels

### 4. Role-Based Program
- Demonstrates role-based notification system
- Different notification methods for different roles
- Citizens: SMS + In-App
- City Officers: Email only
- Emergency Responders: High-Priority SMS + Email

## Requirements
- Java JDK 8 or higher
- Linux, Windows, or macOS

## Troubleshooting

### Java not found:
Run the setup script which will install Java automatically (Linux/Mac) or provide instructions (Windows).

### Permission denied (Linux/Mac):
```bash
chmod +x setup.sh
chmod +x run.sh
```

### Compilation errors:
Ensure you're in the correct directory and all files are present.

## Support
For issues, ensure:
1. Java is installed: `java -version`
2. All project files are in place
3. You're running setup from the project root directory
```

## How to Use This System:

### 1. Create the folder structure:
```
SmartCityAlerts/
├── ISP/
│   └── ISP_Main.java
├── OCP/
│   └── OCP_Main.java
├── DIP/
│   └── DIP_Main.java
├── RoleBased/
│   └── RoleBased_Main.java
```

### 2. Add these new files to the root folder:
- `SmartCityLauncher.java` (the main launcher)
- `setup.sh` (Linux/Mac setup)
- `setup.bat` (Windows setup)
- `README.md` (updated instructions)

### 3. Run the setup:
```bash
# Ubuntu/Linux/Mac
chmod +x setup.sh
./setup.sh

# After setup completes:
./run.sh
```

**OR** for Windows:
- Double-click `setup.bat`
- Then double-click `run.bat`

## The Flow:

1. **User runs setup script** → Installs Java, compiles everything
2. **User runs run.sh/run.bat** → Launches `SmartCityLauncher`
3. **SmartCityLauncher shows menu** → User selects which program to run
4. **Launcher runs selected program** → Automatically compiles if needed
5. **After program finishes** → Returns to main menu

This gives you a **single file to run** (`run.sh` or `run.bat`) that provides a user-driven menu to select which SOLID principle demonstration to run.
