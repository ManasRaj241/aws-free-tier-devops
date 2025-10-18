#!/bin/bash

set -e

echo "=========================================="
echo "AWS Free Tier DevOps - Deployment Script"
echo "=========================================="
echo ""

# Configuration
APP_DIR="/opt/webapp"
JAR_FILE="app.jar"
LOG_FILE="/var/log/webapp.log"
SERVICE_NAME="webapp"

echo "[INFO] Starting deployment..."

# Create application directory if it doesn't exist
if [ ! -d "$APP_DIR" ]; then
    echo "[INFO] Creating application directory: $APP_DIR"
    mkdir -p $APP_DIR
fi

# Stop existing application if running
echo "[INFO] Checking for running application..."
if pgrep -f "java.*app.jar" > /dev/null; then
    echo "[INFO] Stopping existing application..."
    pkill -f "java.*app.jar" || true
    sleep 2
fi

# Copy new JAR file
echo "[INFO] Deploying new JAR file..."
if [ -f "deployment/$JAR_FILE" ]; then
    cp deployment/$JAR_FILE $APP_DIR/
    chmod +x $APP_DIR/$JAR_FILE
    echo "[INFO] JAR file copied successfully"
else
    echo "[ERROR] JAR file not found in deployment directory"
    exit 1
fi

# Start the application
echo "[INFO] Starting application..."
cd $APP_DIR
nohup java -jar $JAR_FILE > $LOG_FILE 2>&1 &
APP_PID=$!
echo "[INFO] Application started with PID: $APP_PID"

# Wait for application to start
echo "[INFO] Waiting for application to start..."
sleep 5

# Health check
echo "[INFO] Performing health check..."
for i in {1..30}; do
    if curl -f http://localhost:2000/health > /dev/null 2>&1; then
        echo "[SUCCESS] Application health check passed!"
        echo "[INFO] Application is running on http://localhost:2000"
        break
    fi
    if [ $i -eq 30 ]; then
        echo "[ERROR] Application failed to start within 30 seconds"
        cat $LOG_FILE
        exit 1
    fi
    echo "[INFO] Waiting for application... (attempt $i/30)"
    sleep 1
done

echo ""
echo "=========================================="
echo "Deployment completed successfully!"
echo "=========================================="
echo "[INFO] Application is accessible at: http://localhost:2000"
echo "[INFO] Logs are available at: $LOG_FILE"
echo ""

# Display last few lines of log
echo "[INFO] Recent log entries:"
tail -n 10 $LOG_FILE

exit 0