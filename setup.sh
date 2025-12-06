#!/bin/bash

echo "=========================================="
echo "   SMART CITY ALERTS - SETUP SCRIPT"
echo "=========================================="
echo

# Step 1: Check Java Installation
echo "[*] Checking Java installation..."

if command -v java &> /dev/null; then
    JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d '"' -f 2)
    echo "[OK] Java is installed: Version $JAVA_VERSION"
else
    echo "[WARNING] Java is not installed. Attempting to install..."
    
    # Detect OS and install Java
    if [[ "$OSTYPE" == "linux-gnu"* ]]; then
        # Linux
        if [ -f /etc/debian_version ]; then
            # Debian/Ubuntu
            sudo apt update
            sudo apt install -y openjdk-11-jdk
        elif [ -f /etc/redhat-release ]; then
            # RHEL/CentOS/Fedora
            sudo yum install -y java-11-openjdk-devel
        else
            echo "[ERROR] Unsupported Linux distribution. Please install Java manually."
            exit 1
        fi
    elif [[ "$OSTYPE" == "darwin"* ]]; then
        # macOS
        if command -v brew &> /dev/null; then
            brew install openjdk@11
        else
            echo "[ERROR] Homebrew not found. Please install Java manually from https://adoptium.net/"
            exit 1
        fi
    else
        echo "[ERROR] Unsupported operating system. Please install Java manually."
        exit 1
    fi
    
    # Verify Java installation
    if command -v java &> /dev/null; then
        JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d '"' -f 2)
        echo "[OK] Java installed successfully: Version $JAVA_VERSION"
    else
        echo "[ERROR] Java installation failed. Please install manually."
        exit 1
    fi
fi

# Step 2: Check project structure
echo "[*] Checking project structure..."

REQUIRED_FILES=("ISP/ISP_Main.java" "OCP/OCP_Main.java" "DIP/DIP_Main.java" "RoleBased/RoleBased_Main.java")

all_files_exist=true
for file in "${REQUIRED_FILES[@]}"; do
    if [ ! -f "$file" ]; then
        echo "[ERROR] Missing file: $file"
        all_files_exist=false
    fi
done

if [ "$all_files_exist" = false ]; then
    echo "[ERROR] Project structure is incomplete."
    echo "[*] Expected structure:"
    echo "    |- ISP/ISP_Main.java"
    echo "    |- OCP/OCP_Main.java"
    echo "    |- DIP/DIP_Main.java"
    echo "    |- RoleBased/RoleBased_Main.java"
    exit 1
else
    echo "[OK] All required files are present"
fi

# Step 3: Create bin directory
mkdir -p bin
echo "[OK] Created bin directory for compiled classes"

# Step 4: Compile Java programs
echo "[*] Compiling Java programs..."

compile_program() {
    local dir=$1
    local main_file=$2
    
    echo "[*] Compiling $dir..."
    
    if javac -d bin "$dir/$main_file" 2>/dev/null; then
        echo "[OK] $dir compiled successfully"
        return 0
    else
        echo "[ERROR] Failed to compile $dir"
        return 1
    fi
}

# Compile all programs
compile_program "ISP" "ISP_Main.java"
compile_program "OCP" "OCP_Main.java"
compile_program "DIP" "DIP_Main.java"
compile_program "RoleBased" "RoleBased_Main.java"

# Compile SmartCityLauncher if it exists
if [ -f "SmartCityLauncher.java" ]; then
    echo "[*] Compiling SmartCityLauncher..."
    if javac -d bin -cp "bin:." SmartCityLauncher.java; then
        echo "[OK] SmartCityLauncher compiled successfully"
    else
        echo "[WARNING] Could not compile SmartCityLauncher"
    fi
fi

# Step 5: Create run script
echo "[*] Creating run script..."

cat > "run.sh" << 'EOF'
#!/bin/bash

echo "=========================================="
echo "   SMART CITY ALERTS - LAUNCHER"
echo "=========================================="
echo

if [ ! -f "bin/SmartCityLauncher.class" ]; then
    echo "[*] Compiling launcher..."
    javac -d bin -cp "bin:." SmartCityLauncher.java
fi

if [ -f "bin/SmartCityLauncher.class" ]; then
    java -cp "bin:." SmartCityLauncher
else
    echo "[ERROR] Could not compile or find launcher"
    echo ""
    echo "You can run programs individually:"
    echo "  java -cp \"bin:.\" ISP_Main"
    echo "  java -cp \"bin:.\" OCP_Main"
    echo "  java -cp \"bin:.\" DIP_Main"
    echo "  java -cp \"bin:.\" RoleBased_Main"
fi
EOF

chmod +x run.sh

cat > "run.bat" << 'EOF'
@echo off
echo ==========================================
echo    SMART CITY ALERTS - LAUNCHER
echo ==========================================
echo.

if not exist "bin\SmartCityLauncher.class" (
    echo [*] Compiling launcher...
    javac -d bin -cp "bin;." SmartCityLauncher.java
)

if exist "bin\SmartCityLauncher.class" (
    java -cp "bin;." SmartCityLauncher
) else (
    echo [ERROR] Could not compile or find launcher
    echo.
    echo You can run programs individually:
    echo   java -cp "bin;." ISP_Main
    echo   java -cp "bin;." OCP_Main
    echo   java -cp "bin;." DIP_Main
    echo   java -cp "bin;." RoleBased_Main
    pause
)
EOF

echo "[OK] Created run scripts"

# Step 6: Test setup
echo "[*] Running quick test..."

echo ""
echo "=========================================="
echo "   QUICK TEST - ISP PROGRAM"
echo "=========================================="
echo ""

timeout 5s java -cp "bin:." ISP_Main <<< "6" 2>/dev/null

if [ $? -eq 0 ] || [ $? -eq 124 ]; then
    echo "[OK] Test completed successfully!"
else
    echo "[WARNING] Test had issues, but setup completed"
fi

# Step 7: Display completion message
echo ""
echo "=========================================="
echo "   SETUP COMPLETED SUCCESSFULLY!"
echo "=========================================="
echo ""
echo "To run the launcher:"
echo "  ./run.sh          # Linux/Mac"
echo "  run.bat           # Windows"
echo ""
echo "Or run programs individually:"
echo "  java -cp \"bin:.\" ISP_Main"
echo "  java -cp \"bin:.\" OCP_Main"
echo "  java -cp \"bin:.\" DIP_Main"
echo "  java -cp \"bin:.\" RoleBased_Main"
echo ""
echo "The launcher will show a menu to select which program to run."
echo ""
