#!/bin/bash

# Script to sync version from libs.versions.toml to iOS configuration
# This script reads the version from gradle/libs.versions.toml and sets iOS build variables

# Get the directory of this script
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(dirname "$SCRIPT_DIR")"

# Read version from libs.versions.toml
LIBS_VERSIONS_FILE="$PROJECT_ROOT/gradle/libs.versions.toml"

if [ -f "$LIBS_VERSIONS_FILE" ]; then
    # Extract version name and code from libs.versions.toml
    APP_VERSION_NAME=$(grep 'app-version-name' "$LIBS_VERSIONS_FILE" | sed 's/.*= *"\([^"]*\)".*/\1/')
    APP_VERSION_CODE=$(grep 'app-version-code' "$LIBS_VERSIONS_FILE" | sed 's/.*= *"\([^"]*\)".*/\1/')

    echo "Syncing versions from libs.versions.toml:"
    echo "  APP_VERSION_NAME: $APP_VERSION_NAME"
    echo "  APP_VERSION_CODE: $APP_VERSION_CODE"

    # Export as environment variables for Xcode
    export APP_VERSION_NAME="$APP_VERSION_NAME"
    export APP_VERSION_CODE="$APP_VERSION_CODE"
else
    echo "Warning: libs.versions.toml not found, using fallback versions"
    export APP_VERSION_NAME="1.0.0"
    export APP_VERSION_CODE="1"
fi
