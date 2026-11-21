@echo off
REM ==============================================================================
REM Python Execution Script for Windows
REM College Event Management Data Science Pipeline (Reg No: 31)
REM ==============================================================================

echo [1/2] Installing required Python libraries (if not already installed)...
pip install -r requirements.txt

echo [2/2] Running Python Data Science & Visualization Pipeline...
python event_analysis.py

pause
