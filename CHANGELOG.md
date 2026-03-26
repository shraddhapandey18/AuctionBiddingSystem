# Changelog

All notable changes to the AuctionBiddingSystem project are documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.0] - 2026-03-26

### Added - Initial Release

#### Core Features
- ✅ Live timed auctions with 10-second countdown
- ✅ Reserve price auctions with automatic validation
- ✅ Bidder profile management with bid history tracking
- ✅ Real-time outbid notifications via Observer pattern
- ✅ Auction logging with timestamps to persistent storage
- ✅ Menu-driven console user interface with 9 options

#### Model Classes
- ✅ Item (abstract) - Base class for all auction items
- ✅ PhysicalItem - Tangible goods with weight and shipping address
- ✅ DigitalItem - Digital goods with format and download link
- ✅ Bid - Immutable value object representing a single bid
- ✅ Bidder - Bidder profile with email and bid history

#### Auction System
- ✅ Auction (abstract) - Base auction class with bidding logic
- ✅ LiveAuction - Implements Runnable for threaded timer countdown
- ✅ ReserveAuction - Validates reserve price and 5% increment rule

#### Observer Pattern
- ✅ AuctionObserver (interface) - Observer contract
- ✅ BidderNotifier - Notifies bidders of outbids and wins
- ✅ AuctionLogger - Logs all events to console and file

#### Management & Persistence
- ✅ AuctionManager - Map-based auction registry
- ✅ FileManager - Buffered I/O for auction records and logs

#### Exception Handling
- ✅ InvalidBidException - Checked exception for bid validation
- ✅ AuctionClosedException - Runtime exception for closed auctions
- ✅ ReservePriceNotMetException - Runtime exception for reserve failures

#### Demo & Testing
- ✅ Comprehensive demo mode (Option 8) that:
  - Creates 3 items (2 physical, 1 digital)
  - Creates 3 bidders with sample bids
  - Runs live auction with countdown
  - Demonstrates outbid notifications
  - Runs reserve auction (fails then succeeds)
  - Shows winner determination
  - Validates data persistence

#### Documentation
- ✅ README.md with complete usage guide
- ✅ ASSIGNMENT.md with requirements and rubric
- ✅ CONTRIBUTING.md with contribution guidelines
- ✅ CHANGELOG.md (this file)
- ✅ .gitignore for clean repository

### Technical Details

#### Threading
- LiveAuction spawns separate thread for countdown
- Thread-safe timer with proper sleep intervals
- Graceful termination when time expires
- Main thread waits for completion

#### Collections
- List<Bid> for maintaining bid order
- List<AuctionObserver> for notification list
- Map<Integer, Auction> for O(1) auction lookups
- Unmodifiable views to maintain encapsulation

#### File I/O
- BufferedReader/Writer for efficient I/O
- Append mode for non-destructive logging
- Proper exception handling with try-catch
- Creates data/ directory with auctions.txt and results.txt

#### Exception Handling
- ValidBidException for validation errors
- AuctionClosedException for closed auction bids
- ReservePriceNotMetException for unmet reserves
- Input validation in Scanner methods

### Code Quality

#### Compilation
- ✅ All 17 classes compile without errors
- ✅ 0 warnings
- ✅ Generates 17 .class files

#### Testing
- ✅ Demo mode runs successfully
- ✅ All menu options functional
- ✅ No runtime exceptions in normal operation
- ✅ File I/O validated
- ✅ Thread lifecycle verified
- ✅ Graceful exit without exceptions

#### Encapsulation
- ✅ All fields private
- ✅ Controlled access via getters
- ✅ No internal collection exposure
- ✅ Immutable Bid class

#### Design Patterns
- ✅ Observer pattern for notifications
- ✅ Abstract Factory pattern (implicit)
- ✅ Strategy pattern (isValidBid)
- ✅ Template Method (Auction.submitBid)

### Deliverables

#### Source Code (17 files)
- 5 model classes
- 3 auction classes
- 3 observer classes
- 2 core/storage classes
- 3 exception classes
- 1 main entry point

#### Project Structure
```
AuctionBiddingSystem/
├── src/                    # 1,000+ lines of source code
│   ├── Main.java
│   ├── model/              (5 classes)
│   ├── auction/            (3 classes)
│   ├── observer/           (3 classes)
│   ├── core/               (1 class)
│   ├── storage/            (1 class)
│   └── exceptions/         (3 classes)
├── data/                   # Persistent storage
├── out/                    # Compiled bytecode
├── README.md               # User guide
├── ASSIGNMENT.md           # Requirements
├── CONTRIBUTING.md         # Contribution guide
├── CHANGELOG.md            # This file
└── .gitignore              # Git configuration
```

### Performance Characteristics

| Operation | Time Complexity | Notes |
|-----------|-----------------|-------|
| Get Auction | O(1) | HashMap lookup |
| List Auctions | O(n) | Linear scan |
| Submit Bid | O(1) | Direct assignment |
| Get Bid History | O(n) | Linear scan of bids |
| Save Auction | O(b) | b = number of bids |
| Load Results | O(1) | Sequential read |

### Known Limitations

- Single-process, no network support
- File-based storage only (no database)
- No user authentication
- No auction data recovery on restart
- Console UI blocks during live auction timer

### Future Roadmap

#### Version 1.1 (Planned)
- [ ] Database persistence (SQL)
- [ ] Unit testing framework (JUnit)
- [ ] Enhanced bidding strategies
- [ ] Auction categories

#### Version 2.0 (Planned)
- [ ] Web UI (Spring Boot)
- [ ] REST API
- [ ] Authentication system
- [ ] Email notifications

#### Version 3.0 (Planned)
- [ ] Mobile app support
- [ ] Real-time bidding with WebSockets
- [ ] Auction analytics dashboard
- [ ] Machine learning price prediction

### Acknowledgments

- Course: Advanced Object-Oriented Programming (Spring 2026)
- Created as educational project demonstrating OOP principles
- Design inspired by real-world auction systems like eBay and Sotheby's

---

## Version History

| Version | Release Date | Status |
|---------|--------------|--------|
| 1.0.0 | 2026-03-26 | ✅ Complete |
| 1.1.0 | TBD | 📋 Planned |
| 2.0.0 | TBD | 📋 Planned |

---

For detailed implementation information, see [ASSIGNMENT.md](ASSIGNMENT.md).
For usage instructions, see [README.md](README.md).
