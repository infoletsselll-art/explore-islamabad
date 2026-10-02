#!/bin/bash
# ==============================================================================
# Visit Islamabad - Cloudflare Pages Deployment Script
# ==============================================================================
# Usage:
#   1. Using Wrangler CLI: ./deploy-cloudflare.sh
#   2. Or drag-and-drop cloudflare_pages_deploy.zip into Cloudflare Pages Dashboard
# ==============================================================================

set -e

echo "=========================================================="
echo "  🚀 Packaging & Deploying Visit Islamabad to Cloudflare Pages"
echo "=========================================================="

# Ensure ZIP package is up to date
zip -u -r cloudflare_pages_deploy.zip public/

# If wrangler is installed, deploy directly
if command -v wrangler &> /dev/null || npx wrangler --version &> /dev/null; then
    echo "Deploying via Cloudflare Wrangler..."
    npx wrangler pages deploy public --project-name=visitislamabad
else
    echo "Wrangler CLI not found."
    echo "👉 You can upload cloudflare_pages_deploy.zip directly at:"
    echo "   https://dash.cloudflare.com/?to=/:account/pages"
fi

echo "=========================================================="
echo "  ✅ Cloudflare Pages Deployment Ready!"
echo "=========================================================="
