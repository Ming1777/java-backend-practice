param(
    [ValidateRange(1024, 65535)][int]$Port = 8081,
    [string]$Name = 'user-center-study'
)

$ErrorActionPreference = 'Stop'
if ($Name -notmatch '^[a-zA-Z0-9][a-zA-Z0-9_.-]+$') { throw 'Invalid container name.' }
$existing = docker ps -a --filter "name=^/$Name$" --format '{{.Names}}'
if ($LASTEXITCODE -ne 0) { throw 'Docker is not available.' }
if ($existing) { throw "Container already exists: $Name. Use docker start $Name to restart it." }

$previousPassword = $env:DB_PASSWORD
try {
    if ([string]::IsNullOrWhiteSpace($env:DB_PASSWORD)) {
        $securePassword = Read-Host 'MySQL password for java_app' -AsSecureString
        $env:DB_PASSWORD = [System.Net.NetworkCredential]::new('', $securePassword).Password
    }
    # Pass the password through the process environment, not a literal command argument.
    # This is a local learning setup; Docker administrators can inspect container env values.
    $dockerArgs = @(
        'run', '-d', '--name', $Name,
        '-p', "127.0.0.1:${Port}:8080",
        '-e', 'DB_PASSWORD',
        '-e', 'SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/java_backend_practice?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai',
        '-e', 'SPRING_DATA_REDIS_HOST=host.docker.internal',
        'user-center:study'
    )
    & docker @dockerArgs
    if ($LASTEXITCODE -ne 0) { throw 'Container creation failed.' }
    Write-Output "Container created. Check logs: docker logs $Name"
    Write-Output "Swagger: http://localhost:$Port/swagger-ui/index.html"
} finally {
    if ($null -eq $previousPassword) { Remove-Item Env:DB_PASSWORD -ErrorAction SilentlyContinue }
    else { $env:DB_PASSWORD = $previousPassword }
}
