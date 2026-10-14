#!/bin/bash
# ==============================================================================
# Visit Islamabad - Zero-to-Hero Firebase Deployment Script
# Fixes: "Unsupported engine" and "missing package" errors by using
# the standalone Firebase binary or forced npm install.
# ==============================================================================
# Usage:
#   ./deploy-firebase.sh <your-firebase-project-id>
# Example:
#   ./deploy-firebase.sh visit-islamabad-2026
# ==============================================================================

set -e

PROJECT_ID="$1"

echo "=========================================================="
echo "  🚀 Deploying Visit Islamabad to Firebase Hosting from Zero"
echo "=========================================================="

# 1. Install Firebase CLI without npm dependency issues if not found
if ! command -v firebase &> /dev/null; then
    echo "⚠️ Firebase CLI is not installed."
    echo "Attempting standalone official binary installation (bypasses npm/Node engine errors)..."
    
    if command -v curl &> /dev/null; then
        curl -sL https://firebase.tools | bash || true
    fi

    # Fallback to npm with --force and --ignore-engines if curl is not used
    if ! command -v firebase &> /dev/null && command -v npm &> /dev/null; then
        echo "Installing Firebase CLI via npm (ignoring engine mismatches)..."
        npm install -g firebase-tools --force --ignore-engines || sudo npm install -g firebase-tools --force --ignore-engines
    fi
fi

# Verify firebase command is available
if ! command -v firebase &> /dev/null; then
    echo "❌ Error: Could not automatically install Firebase CLI."
    echo "Please run one of the following commands manually in your terminal:"
    echo "  1) Standalone (Recommended): curl -sL https://firebase.tools | bash"
    echo "  2) Or via npm: npm install -g firebase-tools --force --ignore-engines"
    exit 1
fi

echo "✅ Firebase CLI is ready: $(firebase --version)"

# 2. Login to Google / Firebase
echo "Checking Firebase authentication..."
if ! firebase login:list 2>&1 | grep -q "@"; then
    echo "⚠️ You are not currently logged in to Firebase CLI."
    echo "Running 'firebase login'..."
    firebase login || true
fi

# 3. Handle Project Setup (Auto-detect from google-services.json if not provided)
if [ -z "$PROJECT_ID" ]; then
    if [ -f "app/google-services.json" ]; then
        DETECTED_ID=$(grep '"project_id"' app/google-services.json | head -n 1 | sed -E 's/.*"project_id": "([^"]+)".*/\1/')
        if [ -n "$DETECTED_ID" ]; then
            PROJECT_ID="$DETECTED_ID"
            echo "Auto-detected project from google-services.json: $PROJECT_ID"
        fi
    fi
fi

if [ -n "$PROJECT_ID" ]; then
    echo "Targeting Firebase Project: $PROJECT_ID"
    # Update .firebaserc with the target project
    cat <<EOF > .firebaserc
{
  "projects": {
    "default": "$PROJECT_ID"
  }
}
EOF
    firebase use "$PROJECT_ID" || true
else
    echo "No Project ID provided. Current linked project:"
    cat .firebaserc || true
    echo ""
    echo "To target a specific Firebase project:"
    echo "  ./deploy-firebase.sh <your-project-id>"
fi

# 4. Ensure latest APK and web assets are synchronized into public/
echo "Synchronizing static web files and latest APK into public/..."
if [ -f "app/build/outputs/apk/debug/app-debug.apk" ]; then
    cp -f app/build/outputs/apk/debug/app-debug.apk public/visit-islamabad.apk
    cp -f app/build/outputs/apk/debug/app-debug.apk visit-islamabad.apk
fi
cp -f index.html public/index.html
cp -f _headers public/_headers
cp -f _redirects public/_redirects

# 5. Deploy only hosting files
echo "Deploying hosting files to Firebase Hosting..."
if firebase deploy --only hosting; then
    echo "=========================================================="
    echo "  🎉 Deployment Complete! Your site is live on Firebase!"
    echo "=========================================================="
else
    echo "----------------------------------------------------------"
    echo "⚠️ Firebase Hosting deployment encountered an error."
    echo "Common reasons and instant fixes:"
    echo "  1) Account Permission: Ensure the Google Account logged into"
    echo "     Firebase CLI has 'Owner' or 'Editor' rights on project $PROJECT_ID."
    echo "     Run: firebase login --reauth"
    echo "  2) Create/Use your own project: You can create a free project at:"
    echo "     https://console.firebase.google.com"
    echo "     Then deploy instantly with: ./deploy-firebase.sh <your-created-project-id>"
    echo "  3) Alternatively, use Cloudflare Pages (100% Free & Fast):"
    echo "     Run ./deploy-cloudflare.sh or drag-and-drop 'cloudflare_pages_deploy.zip'"
    echo "     into https://dash.cloudflare.com/?to=/:account/pages"
    echo "----------------------------------------------------------"
fi
