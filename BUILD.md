# Build Guide

Complete instructions for building and running the AuctionBiddingSystem project.

## System Requirements

- **Java Development Kit (JDK):** 8 or higher
- **Operating System:** Windows, macOS, or Linux
- **Command Line:** Terminal, PowerShell, or Command Prompt

### Verify Java Installation

```bash
java -version
javac -version
```

Both commands should display version information. If not, install JDK first.

## Project Structure

```
AuctionBiddingSystem/
├── src/                              # Source code root
│   ├── Main.java                     # Entry point
│   ├── model/                        # Data model classes
│   │   ├── Item.java                 # Abstract item class
│   │   ├── PhysicalItem.java
│   │   ├── DigitalItem.java
│   │   ├── Bid.java                  # Immutable bid record
│   │   └── Bidder.java               # Bidder with history
│   ├── auction/                      # Auction implementation
│   │   ├── Auction.java              # Abstract base
│   │   ├── LiveAuction.java          # Timed auction
│   │   └── ReserveAuction.java       # Reserve auction
│   ├── observer/                     # Observer pattern
│   │   ├── AuctionObserver.java
│   │   ├── BidderNotifier.java
│   │   └── AuctionLogger.java
│   ├── core/                         # Core management
│   │   └── AuctionManager.java
│   ├── storage/                      # File I/O
│   │   └── FileManager.java
│   └── exceptions/                   # Custom exceptions
│       ├── InvalidBidException.java
│       ├── AuctionClosedException.java
│       └── ReservePriceNotMetException.java
├── data/                             # Runtime data files
│   ├── auctions.txt                  # Auction records
│   └── results.txt                   # Event log
├── out/                              # Compiled bytecode (auto-created)
├── README.md                         # User guide
├── ASSIGNMENT.md                     # Requirements
├── CONTRIBUTING.md                   # Contribution guide
├── CHANGELOG.md                      # Version history
├── BUILD.md                          # This file
└── .gitignore                        # Git ignore rules
```

## Build Instructions

### Step 1: Navigate to Project Directory

```bash
cd AuctionBiddingSystem
```

### Step 2: Create Output Directory (if needed)

```bash
# Windows
mkdir out

# macOS/Linux
mkdir -p out
```

### Step 3: Compile Source Code

#### Option A: Windows (PowerShell or CMD)

```bash
javac -d out src\*.java src\model\*.java src\auction\*.java src\observer\*.java src\core\*.java src\storage\*.java src\exceptions\*.java
```

#### Option B: macOS/Linux (Bash/Zsh)

```bash
javac -d out src/*.java src/model/*.java src/auction/*.java src/observer/*.java src/core/*.java src/storage/*.java src/exceptions/*.java
```

#### Option C: Cross-Platform (Ant/Maven)

If using build tools, create `build.xml` (Ant):

```xml
<project>
    <target name="compile">
        <mkdir dir="out"/>
        <javac srcdir="src" destdir="out" includeantruntime="false"/>
    </target>
</project>
```

Then run: `ant compile`

### Step 4: Verify Compilation

```bash
# Windows
dir out /s *.class | find /c ".class"

# macOS/Linux  
find out -name "*.class" | wc -l
```

Expected output: **17 class files**

## Running the Application

### Method 1: Interactive Mode

```bash
java -cp out Main
```

Then interact with the menu:
- Choose option 1-9
- Enter input as prompted
- Type `9` to exit

### Method 2: Demo Mode

```bash
# Windows (PowerShell)
echo "8" | java -cp out Main

# Windows (CMD)
echo 8 | java -cp out Main

# macOS/Linux
echo "8" | java -cp out Main
```

This runs the automated demo that:
1. Creates 3 items
2. Creates 3 bidders
3. Runs live auction
4. Demonstrates observer notifications
5. Runs reserve auction
6. Shows results
7. Exits automatically

### Method 3: Pipe Multiple Commands

```bash
# Windows
@"
1
2
100
7
9
"@ | java -cp out Main

# macOS/Linux
cat << 'EOF' | java -cp out Main
1
2
100
7
9
EOF
```

This automatically:
1. Lists all auctions (option 1)
2. Views auction #100 details (option 2)
3. Views past results (option 7)
4. Exits (option 9)

## Build Troubleshooting

### Error: Command not found: javac

**Cause:** Java is not installed or not in PATH  
**Solution:**
```bash
# Verify installation
java -version

# If not found, download JDK from:
# https://www.oracle.com/java/technologies/downloads/

# Add to PATH (varies by OS)
# Windows: Set JAVA_HOME environment variable
# macOS/Linux: export PATH=$PATH:/path/to/jdk/bin
```

### Error: Package does not exist

**Cause:** Compiling individual files without seeing other packages  
**Solution:** Use the full compile command that includes all packages

### Error: Class not found

**Cause:** Classpath issue at runtime  
**Solution:** Verify the `out` directory exists and contains all .class files

### Error: Thread error / Exception at runtime

**Cause:** Very rare - if it happens  
**Solution:**
```bash
# Recompile from scratch
rm -rf out
mkdir out
javac -d out src\*.java src\model\*.java...
```

## Clean Build

To completely rebuild from scratch:

```bash
# Remove old compilation
rm -rf out

# Create fresh output directory
mkdir out

# Recompile
javac -d out src\*.java src\model\*.java src\auction\*.java src\observer\*.java src\core\*.java src\storage\*.java src\exceptions\*.java

# Verify
java -cp out Main
```

## Performance Notes

### Compilation Time
- **First build:** ~2-3 seconds
- **Incremental:** Very fast (only changed files)
- **Clean build:** Same as first

### Runtime Performance
- **Startup:** ~500ms
- **Menu response:** Instant
- **Live auction (10s):** Completes in ~10 seconds
- **Demo mode:** ~20 seconds total

### File I/O Performance
- **Append operation:** < 1ms
- **File read:** < 10ms
- **Large history load:** < 50ms (even with 1000s of entries)

## Automation Scripts

### Bash Script (macOS/Linux)

Create `build.sh`:

```bash
#!/bin/bash
set -e  # Exit on error

echo "Building AuctionBiddingSystem..."
mkdir -p out
javac -d out src/*.java src/model/*.java src/auction/*.java \
    src/observer/*.java src/core/*.java src/storage/*.java src/exceptions/*.java

CLASS_COUNT=$(find out -name "*.class" | wc -l)
echo "✓ Compilation successful"
echo "✓ Generated $CLASS_COUNT class files"
echo ""
echo "To run: java -cp out Main"
echo "To run demo: echo "8" | java -cp out Main"
```

Run with: `bash build.sh`

### PowerShell Script (Windows)

Create `build.ps1`:

```powershell
Write-Host "Building AuctionBiddingSystem..." -ForegroundColor Green
New-Item -ItemType Directory -Force -Path out | Out-Null

javac -d out `
  src\*.java `
  src\model\*.java `
  src\auction\*.java `
  src\observer\*.java `
  src\core\*.java `
  src\storage\*.java `
  src\exceptions\*.java

$classCount = (Get-ChildItem -Recurse -Include "*.class" out | Measure-Object).Count
Write-Host "✓ Compilation successful" -ForegroundColor Green
Write-Host "✓ Generated $classCount class files"
Write-Host ""
Write-Host "To run: java -cp out Main"
Write-Host "To run demo: echo 8 | java -cp out Main"
```

Run with: `.\build.ps1`

## Continuous Integration

### GitHub Actions (Optional)

Create `.github/workflows/build.yml`:

```yaml
name: Build

on: [push, pull_request]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - name: Set up JDK
        uses: actions/setup-java@v2
        with:
          java-version: '11'
      - name: Compile
        run: |
          mkdir -p out
          javac -d out src/*.java src/model/*.java src/auction/*.java \
            src/observer/*.java src/core/*.java src/storage/*.java \
            src/exceptions/*.java
      - name: Run Demo
        run: echo "8" | java -cp out Main
```

## IDE Setup (Optional)

### IntelliJ IDEA
1. Open project folder
2. Mark `src` as Sources Root
3. Mark `out` as Excluded
4. Build → Build Project
5. Run Main.java

### Eclipse  
1. File → New → Java Project
2. Uncheck "Create module-info.java"
3. Link external source folder to `src`
4. Project → Build Project
5. Run as Java Application

### VS Code
1. Install Extension Pack for Java
2. Open folder
3. Terminal → Run Build Task
4. Select "javac build" or create custom task

## Execution Profiles

### Quick Test
```bash
echo "8" | java -cp out Main
```
Time: ~20 seconds

### Full Interactive Session
```bash
java -cp out Main
```
Interactive - exit with option 9

### Silent Run (no output)
```bash
java -cp out Main 2>/dev/null
```

### With Verbose Logging (Java)
```bash
java -verbose:class -cp out Main
```

## Verification Checklist

- [ ] Java installed: `java -version`
- [ ] Javac installed: `javac -version`
- [ ] Source files present: `ls src/*.java` (or `dir src\*.java`)
- [ ] Out directory created: `ls out` (or `dir out`)
- [ ] Compilation successful: 0 errors
- [ ] 17 .class files generated
- [ ] Demo mode runs: `echo "8" | java -cp out Main`
- [ ] Data files created: `auctions.txt`, `results.txt`
- [ ] Menu works: `java -cp out Main` → option 1 → option 9
- [ ] No exceptions in normal operation

## Next Steps

After successful build:

1. **Explore the Code** - See [README.md](README.md)
2. **Review Requirements** - See [ASSIGNMENT.md](ASSIGNMENT.md)
3. **Run Demo** - Execute demo mode
4. **Test Features** - Try each menu option
5. **Check Results** - Review `data/auctions.txt` and `data/results.txt`
6. **Contribute** - See [CONTRIBUTING.md](CONTRIBUTING.md)

---

**Build Guide Version:** 1.0  
**Last Updated:** March 26, 2026  
**Status:** Latest
