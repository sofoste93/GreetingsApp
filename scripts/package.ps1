param([string]$Type = "app-image", [string]$Destination = "dist")
$ErrorActionPreference = "Stop"
mvn -B package -DskipTests
New-Item -ItemType Directory -Force $Destination | Out-Null
$app = Join-Path $Destination "GreetingsApp"
if (Test-Path $app) { Remove-Item -Recurse -Force $app }
jpackage --type $Type --name GreetingsApp --app-version 2.0.0 --vendor sofoste93 --description "Event Horizon first-contact station" --input target --main-jar GreetingsApp.jar --main-class com.sofoste.greetings.GreetingsApp --dest $Destination

