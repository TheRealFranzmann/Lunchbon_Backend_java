#!/usr/bin/env bash

host="$1"
port="$2"
shift 2

echo "⏳ Waiting for $host:$port to be ready..."

until nc -z "$host" "$port"; do
  echo "Still waiting for $host:$port..."
  sleep 2
done

echo "✅ $host:$port is up. Starting app..."
exec "$@"
