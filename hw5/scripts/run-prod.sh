#!/usr/bin/env sh
set -eu

if [ ! -f .env.prod ]; then
  cp .env.prod.example .env.prod
  echo "Created .env.prod from .env.prod.example. Edit it and run again."
  exit 1
fi

docker compose --env-file .env.prod -f compose.app.yml up -d --build
