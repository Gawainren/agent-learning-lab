# Only changes environment variables in the current PowerShell process.
$env:JAVA_HOME = 'C:\Program Files\Eclipse Adoptium\jdk-17.0.19.10-hotspot'
$mavenBin = 'C:\Program Files\Maven\apache-maven-3.9.16-bin\apache-maven-3.9.16\bin'
if (-not (Test-Path -LiteralPath "$env:JAVA_HOME\bin\javac.exe")) {
    throw 'JDK path no longer exists. Check your JDK installation.'
}
if (-not (Test-Path -LiteralPath "$mavenBin\mvn.cmd")) {
    throw 'Maven path no longer exists. Check your Maven installation.'
}
$env:Path = "$env:JAVA_HOME\bin;$mavenBin;$env:Path"
Write-Output 'Current terminal: JDK 17 and Maven are ready.'
