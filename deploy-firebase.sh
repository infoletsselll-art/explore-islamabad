#!/bin/bash
# ==============================================================================
# Visit Islamabad - Zero-to-Hero Firebase Deployment Script
# ==============================================================================
# Usage:
#   1. Run: ./deploy-firebase.sh
#   Or with a project ID:
#   2. Run: ./deploy-firebase.sh <your-firebase-project-id>
# ==============================================================================

set -e

PROJECT_ID="$1"

echo "=========================================================="
echo "  🚀 Deploying Visit Islamabad to Firebase Hosting"
echo "=========================================================="

# Check if firebase-tools is installed
if ! command -v firebase &> /dev/null; then
    echo "⚠️ Firebase CLI is not installed globally."
    echo "Installing Firebase CLI via npm..."
    npm install -g firebase-tools
fi

# Ensure user is logged in
echo "Checking Firebase authentication..."
firebase login --interactive || true

# If project ID was provided, set it as default
if [ -n "$PROJECT_ID" ]; then
    echo "Configuring Firebase Project: $PROJECT_ID"
    firebase use --add "$PROJECT_ID" --alias default
fi

# Verify public directory and files exist
if [ ! -f "public/visit-islamabad.apk" ]; then
    echo "Synchronizing APK package to public/ directory..."
    cp visit-islamabad.apk public/visit-islamabad.apk
fi

# Deploy to Firebase Hosting
echo "Deploying hosting files to Firebase..."
firebase deploy --only hosting

echo "=========================================================="
echo "  ✅ Deployment Complete! Visit Islamabad is live on Firebase!"
echo "=========================================================="
