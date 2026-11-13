param(
    [string[]]$Clase = @()
)

$ErrorActionPreference = 'Stop'
Set-StrictMode -Version Latest

$dependencyDir = Join-Path $PSScriptRoot '.deps'
$mainOutput = Join-Path $PSScriptRoot 'target/classes'
$testOutput = Join-Path $PSScriptRoot 'target/test-classes'
$evidenceDir = Join-Path $PSScriptRoot 'evidencias'
New-Item -ItemType Directory -Force -Path $dependencyDir, $mainOutput, $testOutput, $evidenceDir | Out-Null

$dependencies = @(
    @{ File = 'junit-4.13.2.jar'; MavenPath = 'junit/junit/4.13.2/junit-4.13.2.jar' },
    @{ File = 'hamcrest-core-1.3.jar'; MavenPath = 'org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar' }
)
foreach ($dependency in $dependencies) {
    $destination = Join-Path $dependencyDir $dependency.File
    if (-not (Test-Path -LiteralPath $destination)) {
        $cachedJar = Join-Path $env:USERPROFILE ('.m2/repository/' + $dependency.MavenPath)
        if (Test-Path -LiteralPath $cachedJar) {
            Copy-Item -LiteralPath $cachedJar -Destination $destination
        } else {
            Invoke-WebRequest -Uri ('https://repo.maven.apache.org/maven2/' + $dependency.MavenPath) -OutFile $destination -UseBasicParsing
        }
    }
}

$junit = Join-Path $dependencyDir 'junit-4.13.2.jar'
$hamcrest = Join-Path $dependencyDir 'hamcrest-core-1.3.jar'
$separator = [IO.Path]::PathSeparator
$mainSources = @(Get-ChildItem -LiteralPath (Join-Path $PSScriptRoot 'src/main/java') -Filter '*.java' -Recurse | ForEach-Object { $_.FullName })
$testSources = @(Get-ChildItem -LiteralPath (Join-Path $PSScriptRoot 'src/test/java') -Filter '*.java' -Recurse)
$availableClasses = @($testSources | Where-Object { $_.BaseName -like 'Test*' } | ForEach-Object { $_.BaseName } | Sort-Object)
if ($Clase.Count -eq 0) {
    $Clase = $availableClasses
}
foreach ($testClass in $Clase) {
    if ($testClass -notin $availableClasses) {
        throw "Clase de prueba desconocida: $testClass. Disponibles: $($availableClasses -join ', ')"
    }
}
if ($Clase.Count -eq 0) { throw 'No hay clases de prueba Test*.java.' }

$testPaths = @($testSources | ForEach-Object { $_.FullName })
& javac --release 8 -encoding UTF-8 -d $mainOutput @mainSources
if ($LASTEXITCODE -ne 0) { throw 'Fallo al compilar las clases de produccion.' }
& javac --release 8 -encoding UTF-8 -cp "$mainOutput$separator$junit$separator$hamcrest" -d $testOutput @testPaths
if ($LASTEXITCODE -ne 0) { throw 'Fallo al compilar las pruebas.' }

$logPath = Join-Path $evidenceDir ((Get-Date -Format 'yyyyMMdd-HHmmss-fff') + '-junit.txt')
Write-Host "Pruebas: $($Clase -join ', ')"
& java -cp "$mainOutput$separator$testOutput$separator$junit$separator$hamcrest" org.junit.runner.JUnitCore @Clase | Tee-Object -FilePath $logPath
$testExitCode = $LASTEXITCODE
Write-Host "Registro: $logPath"
if ($testExitCode -eq 0) {
    Write-Host 'VERDE: todas las pruebas ejecutadas pasan.' -ForegroundColor Green
} else {
    Write-Host 'ROJO: revisa el fallo antes de implementar.' -ForegroundColor Red
}
exit $testExitCode
