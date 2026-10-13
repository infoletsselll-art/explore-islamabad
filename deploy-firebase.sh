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
firebase login --interactive || true

# 3. Handle Project Setup (from Zero)
if [ -n "$PROJECT_ID" ]; then
    echo "Linking your new Firebase Project: $PROJECT_ID"
    # Update .firebaserc with the new project
    cat <<EOF > .firebaserc
{
  "projects": {
    "default": "$PROJECT_ID"
  }
}
EOF
    firebase use "$PROJECT_ID"
else
    echo "No Project ID provided in arguments."
    echo "Current linked project in .firebaserc:"
    cat .firebaserc || true
    echo ""
    echo "To switch or link a new project, run:"
    echo "  ./deploy-firebase.sh <your-new-project-id>"
    echo "  OR: firebase use --add"
fi

# 4. Ensure public assets are synchronized
if [ -f "visit-islamabad.apk" ] && [ ! -f "public/visit-islamabad.apk" ]; then
    echo "Copying APK package into public/ folder..."
    cp visit-islamabad.apk public/visit-islamabad.apk
fi

# 5. Deploy only hosting files
echo "Deploying hosting files to Firebase..."
firebase deploy --only hosting

echo "=========================================================="
echo "  🎉 Deployment Complete! Your site is live on Firebase!"
echo "=========================================================="
