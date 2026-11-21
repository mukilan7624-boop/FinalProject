@echo off
REM ==============================================================================
REM Quick Terminal Launcher for College Event Management Tables & Queries
REM Velalar College of Engineering and Technology | Reg No: 31
REM ==============================================================================

powershell -NoProfile -ExecutionPolicy Bypass -File "%~dp0scripts\display_tables.ps1" %*
if "%1"=="" pause
