#!/usr/bin/env sh
set -eu

VERSION="${1:-local}"

mvn -f ./currency-rate-provider/pom.xml -DskipTests package
mvn -f ./rate-printer/pom.xml -DskipTests package

docker build -t "currency-rate-provider:${VERSION}" ./currency-rate-provider
docker build -t "rate-printer:${VERSION}" ./rate-printer
