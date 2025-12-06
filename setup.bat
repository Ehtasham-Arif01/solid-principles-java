@echo off
chcp 65001 >nul
title Smart City Alerts - Setup Script

echo ==========================================
echo    SMART CITY ALERTS - SETUP SCRIPT
echo ==========================================
echo.

echo [*] Checking Java installation...
java -version >nul 2>&1
if %errorlevel% equ 0 (
    for /f "tokens=3" %%i in ('java -version 2^>^&1 ^| findstr /i "version"') do (
        set "java_version=%%i"
    )
    echo [OK] Java is installed: Version %java_version:"=%
) else (
    echo [ERROR] Java is not installed.
    echo [*] Please download and install Java from:
    echo [*] https://adoptium.net/temurin/releases/
    echo [*]
    echo [*] After installation, please run this script again.
    pause
    exit /b 1
)

echo [*] Checking project structure...
set "all_files_exist=1"

if not exist "ISP\ISP_Main.java" (
    echo [ERROR] Missing file: ISP\ISP_Main.java
    set "all_files_exist=0"
)
if not exist "OCP\OCP_Main.java" (
    echo [ERROR] Missing file: OCP\OCP_Main.java
    set "all_files_exist=0"
)
if not exist "DIP\DIP_Main.java" (
    echo [ERROR] Missing file: DIP\DIP_Main.java
    set "all_files_exist=0"
)
if not exist "RoleBased\RoleBased_Main.java" (
    echo [ERROR] Missing file: RoleBased\RoleBased_Main.java
    set "all_files_exist=0"
)

if %all_files_exist% equ 0 (
    echo [ERROR] Project structure is incomplete.
    echo [*] Expected structure:
    echo     ^|-- ISP\ISP_Main.java
    echo     ^|-- OCP\OCP_Main.java
    echo     ^|-- DIP\DIP_Main.java
    echo     ^|-- RoleBased\RoleBased_Main.java
    pause
    exit /b 1
) else (
    echo [OK] All required files are present
)

echo [*] Creating bin directory...
if not exist "bin" (
    mkdir bin
    echo [OK] Created bin directory for compiled classes
)

echo [*] Compiling Java programs...

for %%p in (
    "ISP:ISP_Main"
    "OCP:OCP_Main"
    "DIP:DIP_Main"
    "RoleBased:RoleBased_Main"
) do (
    for /f "tokens=1,2 delims=:" %%a in ("%%p") do (
        set "dir=%%a"
        set "main=%%b"
        
        echo [*] Compiling !dir!...
        javac -d bin "!dir!\!main!.java" 2>nul
        if !errorlevel! equ 0 (
            echo [OK] !dir! compiled successfully
        ) else (
            echo [ERROR] Failed to compile !dir!
        )
    )
)

echo [*] Compiling SmartCityLauncher...
if exist "SmartCityLauncher.java" (
    javac -d bin -cp "bin;." SmartCityLauncher.java 2>nul
    if !errorlevel! equ 0 (
        echo [OK] SmartCityLauncher compiled successfully
    ) else (
        echo [WARNING] Could not compile SmartCityLauncher
    )
)

echo [*] Creating run script...
(
    echo @echo off
    echo echo ==========================================
    echo echo    SMART CITY ALERTS - LAUNCHER
    echo echo ==========================================
    echo echo.
    echo.
    echo if not exist "bin\SmartCityLauncher.class" (
    echo     echo [*] Compiling launcher...
    echo     javac -d bin -cp "bin;." SmartCityLauncher.java
    echo )
    echo.
    echo if exist "bin\SmartCityLauncher.class" (
    echo     java -cp "bin;." SmartCityLauncher
    echo ) else (
    echo     echo [ERROR] Could not compile or find launcher
    echo     echo.
    echo     echo You can run programs individually:
    echo     echo   java -cp "bin;." ISP_Main
    echo     echo   java -cp "bin;." OCP_Main
    echo     echo   java -cp "bin;." DIP_Main
    echo     echo   java -cp "bin;." RoleBased_Main
    echo     pause
    echo )
) > "run.bat"

echo [OK] Created run.bat

echo.
echo ==========================================
echo    SETUP COMPLETED SUCCESSFULLY!
echo ==========================================
echo.
echo To run the launcher:
echo   Double-click run.bat
echo.
echo Or run from Command Prompt:
echo   run.bat
echo.
echo The launcher will show a menu to select which program to run.
echo.
pause
