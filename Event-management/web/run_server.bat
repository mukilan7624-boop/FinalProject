@echo off
REM ==============================================================================
REM Launch Localhost Web Server on http://localhost:8080
REM ==============================================================================

echo Starting Localhost Web Server on http://localhost:8080 ...
powershell -ExecutionPolicy Bypass -File "%~dp0server.ps1"
pause
