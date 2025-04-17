#!/bin/bash

set -e  # Exit immediately on any error

echo "☕ Building Spring Boot JAR..."
./mvnw clean package -DskipTests

echo "🐳 Stopping and removing containers/volumes..."
docker-compose down -v

echo "🐋 Building Docker images..."
docker-compose build

echo "🚀 Starting up containers..."
docker-compose up
