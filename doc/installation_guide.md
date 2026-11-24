# JDK Installation Guide (Java 21)

This document outlines the manual installation of the Temurin JDK 21 on a Linux system for the Minecraft development project.

## Steps Taken
1. **Create Directory**: Created `~/opt/java` to house the JDK binaries.
2. **Download**: Retrieved the latest Temurin 21 binaries via `curl` from the Adoptium API.
3. **Extraction**: Extracted the tarball into `~/opt/java`.
4. **Environment Configuration**: Added the following lines to `~/.bashrc`:
   ```bash
   export JAVA_HOME=/home/madrax/opt/java
   export PATH=$JAVA_HOME/bin:$PATH
   ```
5. **Verification**: Verified via `java -version`.

## How to Use
Ensure your `~/.bashrc` is sourced:
```bash
source ~/.bashrc
```
Check version:
```bash
java -version
```

## PaperMC Installation
To get the server running with high performance, we'll use PaperMC.

1. **Download**: The PaperMC jar has been placed in `~/src/ai/programacion-minecraft/bin/paper.jar`.
2. **Run Command**: To start the server, navigate to the bin directory and run:
   ```bash
   java -Xms2G -Xmx2G -jar paper.jar --nogui
   ```
3. **EULA Agreement**: On first run, it will generate an `eula.txt` file. Open it and change `eula=false` to `eula=true`.
4. **Restart**: Run the same command again.
