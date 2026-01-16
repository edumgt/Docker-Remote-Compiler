# build-images.ps1
$ErrorActionPreference = "Stop"

$images = @(
  "gcc",
  "mono",
  "golang",
  "openjdk:11.0.6-jre-slim",
  "zenika/kotlin",
  "python:3",
  "rust",
  "denvazh/scala",
  "ruby",
  "haskell"
)

# Only for compiled languages (language -> dockerfile suffix)
$compilationDockerfilesPaths = [ordered]@{
  c       = "c"
  cpp     = "cpp"
  cs      = "cs"
  go      = "go"
  haskell = "hs"
  kotlin  = "kt"
  rust    = "rs"
  scala   = "scala"
  java    = "java"
}

Write-Host "Pulling all images..."
foreach ($i in $images) {
  Write-Host "==> $i"
  & docker pull $i
  if ($LASTEXITCODE -ne 0) {
    Write-Host "!!! Error while pulling images !!!"
    exit 1
  }
}

Write-Host "Building compilation images..."
foreach ($language in $compilationDockerfilesPaths.Keys) {
  $suffix = $compilationDockerfilesPaths[$language]
  $tag = "compiler.$language"
  $dockerfile = "environment/dockerfiles/Dockerfile.$suffix.compilation"

  Write-Host "==> $tag"
  & docker image build -f $dockerfile -t $tag "environment/dockerfiles"
  if ($LASTEXITCODE -ne 0) {
    Write-Host "!!! Error while building compilation images !!!"
    exit 1
  }
}

Write-Host "*** End of building images ***"
