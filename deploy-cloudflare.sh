#!/bin/bash
# ==============================================================================
# Visit Islamabad - Cloudflare Pages Deployment Script
# ==============================================================================
# Usage:
#   Option 1 (Automated with API token):
#     export CLOUDFLARE_API_TOKEN="your-token"
#     ./deploy-cloudflare.sh
#
#   Option 2 (Direct Web Upload - 100% Free & Zero Config):
#     Simply drag and drop 'cloudflare_pages_deploy.zip' at:
#     https://dash.cloudflare.com/?to=/:account/pages
# ==============================================================================

set -e

echo "=========================================================="
echo "  🚀 Packaging Visit Islamabad for Cloudflare Pages"
echo "=========================================================="

# 1. Ensure latest APK is in public directory
if [ -f "app/build/outputs/apk/debug/app-debug.apk" ]; then
    echo "Syncing latest compiled APK (12.9 MB) to public/..."
    cp -f app/build/outputs/apk/debug/app-debug.apk public/visit-islamabad.apk
    cp -f app/build/outputs/apk/debug/app-debug.apk visit-islamabad.apk
fi

# 2. Sync all HTML pages, ads.txt, sitemap, headers, and redirects
cp -f *.html public/ || true
cp -f ads.txt public/ads.txt || true
cp -f sitemap.xml public/sitemap.xml || true
cp -f _headers public/_headers || true
cp -f _redirects public/_redirects || true

# 3. Build pristine cloudflare_pages_deploy.zip with root-level assets
echo "Creating cloudflare_pages_deploy.zip..."
rm -f cloudflare_pages_deploy.zip
(cd public && zip -q -r ../cloudflare_pages_deploy.zip . -x ".*" -x "__MACOSX*")

ZIP_SIZE=$(du -h cloudflare_pages_deploy.zip | cut -f1)
echo "✅ Package created: cloudflare_pages_deploy.zip ($ZIP_SIZE)"

# 4. Attempt deployment via Wrangler if API token or credentials are available
if [ -n "$CLOUDFLARE_API_TOKEN" ]; then
    echo "🔑 Cloudflare API token detected. Deploying via Wrangler..."
    npx wrangler pages deploy public --project-name=visitislamabad --commit-dirty=true
elif [ -n "$CLOUDFLARE_ACCOUNT_ID" ]; then
    echo "🔑 Cloudflare Account detected. Deploying via Wrangler..."
    npx wrangler pages deploy public --project-name=visitislamabad --commit-dirty=true
else
    echo "----------------------------------------------------------"
    echo "ℹ️  Cloudflare Credentials Notice:"
    echo "   To deploy automatically via terminal:"
    echo "     export CLOUDFLARE_API_TOKEN=\"your-api-token\""
    echo "     npx wrangler pages deploy public --project-name=visitislamabad"
    echo ""
    echo "👉 Direct Drag-and-Drop Deployment (Recommended & Instant):"
    echo "   1. Open Cloudflare Pages: https://dash.cloudflare.com/?to=/:account/pages"
    echo "   2. Click 'Create application' -> 'Pages' -> 'Upload assets'"
    echo "   3. Project name: visitislamabad"
    echo "   4. Drag and drop 'cloudflare_pages_deploy.zip'"
    echo "   5. Click 'Deploy site' — your website goes live immediately!"
    echo "----------------------------------------------------------"
fi

echo "=========================================================="
echo "  🎉 Cloudflare Pages Deployment Package Ready!"
echo "=========================================================="
