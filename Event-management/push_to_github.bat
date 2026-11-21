@echo off
title Push to GitHub (girinathsr7423-gif)
echo ==============================================================================
echo Pushing College Event Management System to GitHub
echo Repository: https://github.com/mukilan7624-boop/FinalProject.git
echo Account: mukilan7624-boop
echo ==============================================================================
echo.

cd /d "%~dp0"
git remote set-url origin https://github.com/mukilan7624-boop/FinalProject.git

echo Executing: git push -u origin main
echo (If a browser window appears, please authorize mukilan7624-boop)
echo.

git push -u origin main

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ==============================================================================
    echo [SUCCESS] Successfully pushed to GitHub repository!
    echo ==============================================================================
) else (
    echo.
    echo ==============================================================================
    echo [FAILED] Push failed. If you have a Personal Access Token (PAT), run:
    echo git push https://^<YOUR_TOKEN^>@github.com/girinathsr7423-gif/Event-management.git main
    echo ==============================================================================
)

echo.
pause
