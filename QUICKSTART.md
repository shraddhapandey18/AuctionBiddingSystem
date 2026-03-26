# Quick Start Guide

Get AuctionBiddingSystem up and running in 5 minutes!

## Prerequisites (1 minute)

Ensure Java is installed:
```bash
java -version
javac -version
```

If not installed, download from [java.com](https://www.java.com) or [oracle.com/java](https://www.oracle.com/java/technologies/downloads/)

## Installation (1 minute)

```bash
# Clone or download the project
cd AuctionBiddingSystem

# Create output directory
mkdir out
```

## Compilation (1 minute)

### Windows (PowerShell/CMD)
```bash
javac -d out src\*.java src\model\*.java src\auction\*.java src\observer\*.java src\core\*.java src\storage\*.java src\exceptions\*.java
```

### macOS/Linux (Bash/Zsh)
```bash
javac -d out src/*.java src/model/*.java src/auction/*.java src/observer/*.java src/core/*.java src/storage/*.java src/exceptions/*.java
```

**Expected Output:** No output = success! ✓

## Running (2 minutes)

### Option 1: Run Demo (Recommended for First Time)

```bash
echo "8" | java -cp out Main
```

This automatically:
- Creates 3 items
- Creates 3 bidders  
- Runs live 10-second auction
- Shows outbid notifications
- Runs reserve auction (fails, then succeeds)
- Exits after 20 seconds

### Option 2: Interactive Mode

```bash
java -cp out Main
```

Then use the menu:
```
1. List All Auctions
2. View Auction Details
3. Place a Bid
4. Start an Auction
5. Close an Auction (Reserve)
6. View Bid History (by Bidder)
7. View Past Results (from file)
8. Run Demo
9. Exit
```

Select option 8 to see the demo, or try other options to explore.

## Verify Installation

After running demo, check for:
- ✅ "Demo completed!" message
- ✅ `data/auctions.txt` has content (57+ lines)
- ✅ `data/results.txt` has content (36+ lines)
- ✅ Program exits cleanly

## File Structure

```
AuctionBiddingSystem/
├── src/              ← Source code (17 Java files)
├── out/              ← Compiled bytecode (17 .class files)
├── data/             ← Output files (auctions.txt, results.txt)
├── README.md         ← Full documentation
├── BUILD.md          ← Detailed build guide
└── ASSIGNMENT.md     ← Project requirements
```

## Common Issues & Fixes

### Issue: "javac: command not found"
**Solution:** Java not in PATH
```bash
# Check if Java is installed
java -version

# If not, install JDK from oracle.com/java
```

### Issue: "package does not exist"
**Solution:** Use full compile command (copy-paste from above)

### Issue: "Exception at runtime"
**Solution:** Very rare - try:
```bash
rm -rf out
mkdir out
javac -d out src\*.java src\model\*.java src\auction\*.java ...
```

### Issue: Demo doesn't automatically exit
**Solution:** It's waiting for input - just press Enter or type "9"

## What You'll See in Demo Output

```
🎪 DEMO MODE - Creating sample auctions and bidders...

✓ Auction #100 registered (Live Timed Auction)
✓ Auction #101 registered (Reserve Price Auction)
=== LIVE AUCTION #100 ===

🔔 LIVE AUCTION #100 STARTED!
Item: Vintage Camera
Duration: 10 seconds

[LOG][HH:MM:SS] EVENT: BID_PLACED | Auction: #100 | Bid: $160.00 by Alice
⚠ [OUTBID ALERT] Alice, you have been outbid...
[LOG][HH:MM:SS] EVENT: OUTBID | Auction: #100 | Bid: $175.00 by Bob

⏱ Time remaining: 5s
⏱ Time remaining: 4s
...

🛑 AUCTION #100 CLOSED!
✅ WINNER: Alice with $200.00
🎉 [WIN NOTIFICATION] Congratulations Alice!

=== RESERVE AUCTION #101 ===
...
🎉 Demo completed!
```

## Next Steps

1. **Explore the Code**
   - Read [README.md](README.md) for full feature list
   - Check [ARCHITECTURE.md](ARCHITECTURE.md) for design patterns
   - Review [CONTRIBUTING.md](CONTRIBUTING.md) to contribute

2. **Try Interactive Mode**
   ```bash
   java -cp out Main
   # Try options 1, 2, 3, 6, 7
   ```

3. **Examine Data Files**
   ```bash
   cat data/auctions.txt        # See auction history
   cat data/results.txt          # See event log
   ```

4. **Review the Code**
   - Start with: `src/Main.java` (entry point)
   - Then: `src/model/Item.java` (base class)
   - Then: `src/auction/Auction.java` (bidding logic)
   - Finally: `src/observer/` (event notifications)

## Troubleshooting Commands

```bash
# Check Java version
java -version
javac -version

# Count compiled classes
find out -name "*.class" -type f | wc -l
# Expected: 17

# Check data files
ls -la data/
# Expected: auctions.txt, results.txt

# Run with verbose output
echo "8" | java -cp out Main 2>&1 | head -50

# Run specific menu option
echo "1" | java -cp out Main
# Lists all auctions
```

## Keyboard Shortcuts in Interactive Mode

| Action | Key |
|--------|-----|
| Select menu option | Type number + Enter |
| Next input field | Enter |
| Exit program | Select option 9 |
| Cancel current input | Ctrl+C (then restart) |

## Performance Expectations

| Operation | Time |
|-----------|------|
| Compilation | 2-3 seconds |
| Startup | ~500ms |
| Demo mode | ~20 seconds |
| Menu response | Instant |
| File I/O | < 10ms |

## System Requirements Met

- ✅ Java 8+
- ✅ ~50MB disk space (source + compiled)
- ✅ No external dependencies
- ✅ Works on Windows, macOS, Linux

## Getting Help

1. **Check README.md** - Comprehensive user guide
2. **Check BUILD.md** - Detailed build instructions  
3. **Check ARCHITECTURE.md** - System design
4. **Review source code** - Well-commented
5. **Check CONTRIBUTING.md** - How to get involved

## Success Indicator

Successful setup = demo runs with:
- All 4 bidders notified
- 2 auctions completed
- Results saved to files
- Clean exit (no exceptions)

## Video Tutorial (Text Guide)

### Step-by-Step Demo Run

```
1. Open terminal/PowerShell
2. Navigate to AuctionBiddingSystem:
   cd AuctionBiddingSystem
3. Compile:
   javac -d out src\*.java src\model\*.java src\auction\*.java src\observer\*.java src\core\*.java src\storage\*.java src\exceptions\*.java
4. Run demo:
   echo "8" | java -cp out Main
5. Watch it run (~20 seconds)
6. Demo completes, program exits
```

That's it! You're done! 🎉

## What to Try Next

### Try Live Auction
```bash
java -cp out Main
# Select 4 (Start Auction)
# Enter 100 (for Live Auction #100)
# Watch 10-second countdown
```

### Try Manual Bidding
```bash
java -cp out Main
# Select 1 (List Auctions)
# Select 3 (Place Bid)
# Enter auction ID, bidder info, and bid amount
```

### Check Results
```bash
java -cp out Main
# Select 7 (View Past Results)
# See all logged events with timestamps
```

## Learning Path

**Beginner:** Run demo → explore output  
**Intermediate:** Review Main.java → try menu options  
**Advanced:** Study design patterns in ARCHITECTURE.md → modify code  
**Expert:** Add new features as described in CONTRIBUTING.md  

## Recommended Reading Order

1. This file (QUICKSTART.md) - You are here! ✓
2. [README.md](README.md) - Features & usage
3. [ASSIGNMENT.md](ASSIGNMENT.md) - What was built & why
4. [ARCHITECTURE.md](ARCHITECTURE.md) - How it's designed
5. [BUILD.md](BUILD.md) - Build details
6. Source code - Understand implementation

## FAQ

**Q: Do I need an IDE?**  
A: No! Works with any terminal + text editor + JDK

**Q: How long does it take to compile?**  
A: ~2-3 seconds. First time may take longer.

**Q: Why does "echo" command work?**  
A: Pipes "8" to stdin, simulating user input

**Q: Can I modify the code?**  
A: Absolutely! Recompile after changes.

**Q: Where does output go?**  
A: Console (stdout) + data/ files (results.txt)

**Q: Is it thread-safe?**  
A: Yes! LiveAuction uses separate thread safely.

## Environment Setup

### Windows
```bash
# Optional: Add Java to PATH permanently
setx PATH "%PATH%;C:\Program Files\Java\jdk-17\bin"
```

### macOS
```bash
# Optional: Add Java to PATH in ~/.zshrc
export PATH=$PATH:/Library/Java/JavaVirtualMachines/jdk-17.jdk/Contents/Home/bin
```

### Linux
```bash
# Optional: Add Java to PATH in ~/.bashrc
export PATH=$PATH:/usr/lib/jvm/java-17/bin
```

---

**Ready to start?** Run the demo now:

```bash
echo "8" | java -cp out Main
```

**Happy Coding!** 🚀

---

**Document Version:** 1.0  
**Last Updated:** March 26, 2026
