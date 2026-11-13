$ErrorActionPreference = 'Stop'
Set-StrictMode -Version Latest

$dependencyDir = Join-Path $PSScriptRoot '.deps'
$mainOutput = Join-Path $PSScriptRoot 'target/classes'
$testOutput = Join-Path $PSScriptRoot 'target/test-classes'
New-Item -ItemType Directory -Force -Path $dependencyDir, $mainOutput, $testOutput | Out-Null

$dependencies = @(
    @{ File = 'junit-4.13.2.jar'; Url = 'https://repo.maven.apache.org/maven2/junit/junit/4.13.2/junit-4.13.2.jar' },
    @{ File = 'hamcrest-core-1.3.jar'; Url = 'https://repo.maven.apache.org/maven2/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar' }
)
foreach ($dependency in $dependencies) {
    $destination = Join-Path $dependencyDir $dependency.File
    if (-not (Test-Path -LiteralPath $destination)) {
        Invoke-WebRequest -Uri $dependency.Url -OutFile $destination -UseBasicParsing
    }
}

$junit = Join-Path $dependencyDir 'junit-4.13.2.jar'
$hamcrest = Join-Path $dependencyDir 'hamcrest-core-1.3.jar'
$separator = [IO.Path]::PathSeparator
$mainSources = @(Get-ChildItem -LiteralPath (Join-Path $PSScriptRoot 'src/main/java') -Filter '*.java' -Recurse | ForEach-Object { $_.FullName })
$testSources = @(Get-ChildItem -LiteralPath (Join-Path $PSScriptRoot 'src/test/java') -Filter '*.java' -Recurse | ForEach-Object { $_.FullName })

& javac --release 8 -encoding UTF-8 -d $mainOutput @mainSources
if ($LASTEXITCODE -ne 0) { throw 'Fallo al compilar las clases de produccion.' }
& javac --release 8 -encoding UTF-8 -cp "$mainOutput$separator$junit$separator$hamcrest" -d $testOutput @testSources
if ($LASTEXITCODE -ne 0) { throw 'Fallo al compilar las pruebas.' }
& java -cp "$mainOutput$separator$testOutput$separator$junit$separator$hamcrest" org.junit.runner.JUnitCore TestVaso TestCafetera TestAzuquero TestMaquinaDeCafe
if ($LASTEXITCODE -ne 0) { throw 'Hay pruebas fallidas.' }
