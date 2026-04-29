#!/usr/bin/env bash
# Run a Mule 4 application locally using Docker.
# Usage: ./scripts/run-app.sh <project-name> [port]
# Example: ./scripts/run-app.sh emp-sapi 8081
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
PROJECT_ROOT="$(dirname "$SCRIPT_DIR")"

PROJECT="${1:?Usage: $0 <project-name> [port]}"
PORT="${2:-8081}"

JAR=$(find "$PROJECT_ROOT/$PROJECT/target" -name "*-mule-application.jar" 2>/dev/null | head -1)

if [ -z "$JAR" ]; then
    echo "No packaged artifact found for '$PROJECT'. Building first..."
    cd "$PROJECT_ROOT/$PROJECT"
    mvn clean package -B -q
    JAR=$(find "$PROJECT_ROOT/$PROJECT/target" -name "*-mule-application.jar" | head -1)
fi

CONTAINER_NAME="mule4-${PROJECT}"

echo "Stopping any existing container '$CONTAINER_NAME'..."
docker rm -f "$CONTAINER_NAME" 2>/dev/null || true

echo "Starting $PROJECT on port $PORT..."
docker run -d --name "$CONTAINER_NAME" \
    -p "$PORT:8081" \
    -v "$JAR:/opt/mule/apps/$(basename "$JAR")" \
    neerajeai/mule4:4.3.0-ea2

echo ""
echo "Container '$CONTAINER_NAME' started."
echo "View logs: docker logs -f $CONTAINER_NAME"
echo "Stop:      docker rm -f $CONTAINER_NAME"
echo ""
echo "Waiting for Mule runtime to start..."
sleep 20

echo "Testing endpoint..."
if curl -sf "http://localhost:$PORT/" >/dev/null 2>&1; then
    echo "App is responding on http://localhost:$PORT"
else
    echo "App started (check docker logs $CONTAINER_NAME for endpoints)"
fi
