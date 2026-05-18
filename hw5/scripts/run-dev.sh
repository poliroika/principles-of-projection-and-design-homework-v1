#!/usr/bin/env sh
set -eu
docker compose --env-file .env.dev -f compose.app.yml up --build
