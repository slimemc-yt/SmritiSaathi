#!/bin/bash

# SmritiSaathi Firebase Setup Script
# Run this after creating your Firebase project in the console

echo "🚀 Setting up SmritiSaathi Firebase Backend..."
echo ""

# Check if firebase CLI is installed
if ! command -v firebase &> /dev/null; then
    echo "❌ Firebase CLI not found. Installing..."
    npm install -g firebase-tools
fi

# Login to Firebase
echo "📱 Please login to Firebase..."
firebase login

# Initialize Firebase project
echo ""
echo "🔧 Initializing Firebase project..."
echo "Please select your Firebase project from the list."
echo ""

firebase init

echo ""
echo "✅ Firebase initialized!"
echo ""
echo "📋 Next Steps:"
echo "   1. Go to Firebase Console: https://console.firebase.google.com"
echo "   2. Select your project"
echo "   3. Enable Phone Authentication (Auth → Sign-in method → Phone)"
echo "   4. Create Firestore Database (Firestore → Create database → Native mode)"
echo "   5. Enable Storage (Storage → Get started)"
echo ""
echo "🚀 After enabling services, run:"
echo "   cd backend/functions && npm install"
echo "   cd .."
echo "   firebase deploy"
echo ""
echo "📖 See backend/README.md for detailed instructions"
