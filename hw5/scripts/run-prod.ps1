if (!(Test-Path .env.prod)) {
    Copy-Item .env.prod.example .env.prod
    Write-Host "Created .env.prod from .env.prod.example. Edit it and run again."
    exit 1
}

docker compose --env-file .env.prod -f compose.app.yml up -d --build
