@echo off
REM SmritiSaathi Firebase Setup Script for Windows
REM Run this after creating your Firebase project in the console

echo ============================================
echo   SmritiSaathi Firebase Backend Setup
echo ============================================
echo.

REM Check if firebase CLI is installed
where firebase >nul 2>nul
if %errorlevel% neq 0 (
    echo [!] Firebase CLI not found. Installing...
    npm install -g firebase-tools
)

echo [1] Please login to Firebase...
firebase login

echo.
echo [2] Initializing Firebase project...
echo Please select your Firebase project from the list.
echo.

firebase init

echo.
echo [OK] Firebase initialized!
echo.
echo ============================================
echo   Next Steps:
echo ============================================
echo.
echo 1. Go to Firebase Console: https://console.firebase.google.com
echo 2. Select your project
echo 3. Enable Phone Authentication (Auth -^> Sign-in method -^> Phone)
echo 4. Create Firestore Database (Firestore -^> Create database -^> Native mode)
echo 5. Enable Storage (Storage -^> Get started)
echo.
echo After enabling services, run:
echo   cd backend\functions
echo   npm install
echo   cd ..
echo   firebase deploy
echo.
echo See backend\README.md for detailed instructions
echo.
pause
