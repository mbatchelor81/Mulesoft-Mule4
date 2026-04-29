#!/usr/bin/env bash
# Build all Mule 4 projects using the parent POM.
# Usage: ./scripts/build-all.sh
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
PROJECT_ROOT="$(dirname "$SCRIPT_DIR")"

echo "=== Building all Mule 4 projects ==="
cd "$PROJECT_ROOT"
mvn clean package -B "$@"
echo ""
echo "=== Build complete ==="
echo "Packaged artifacts:"
find . -name "*-mule-application.jar" -path "*/target/*" | sort
