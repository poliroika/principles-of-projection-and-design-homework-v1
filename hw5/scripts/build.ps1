param(
    [string]$Version = "local"
)

mvn -f .\currency-rate-provider\pom.xml -DskipTests package
mvn -f .\rate-printer\pom.xml -DskipTests package

docker build -t "currency-rate-provider:$Version" .\currency-rate-provider
docker build -t "rate-printer:$Version" .\rate-printer
