@echo off
REM ==============================================================================
REM Compilation and Execution Script for Windows
REM College Event Management System (Reg No: 31)
REM ==============================================================================

echo [1/3] Creating bin output directory...
if not exist "bin" mkdir bin

echo [2/3] Compiling Java source files...
javac -d bin src\com\eventmanagement\model\*.java src\com\eventmanagement\exception\*.java src\com\eventmanagement\dao\*.java src\com\eventmanagement\service\*.java src\com\eventmanagement\Main.java

if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Compilation failed. Please check JDK installation and PATH.
    pause
    exit /b %ERRORLEVEL%
)

echo [3/3] Running Application...
echo.
java -cp bin com.eventmanagement.Main
pause
