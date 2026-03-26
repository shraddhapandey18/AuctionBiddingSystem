# Architecture & Design

Comprehensive guide to the system design, architecture, and implementation details of AuctionBiddingSystem.

## System Overview

```
┌─────────────────────────────────────────────────────────────┐
│                   CONSUMER INTERFACE LAYER                  │
│                        Main.java                            │
│                   (Console Menu System)                     │
└──────────────────────┬────────────────────────────────────────┘
                       │
┌──────────────────────▼────────────────────────────────────────┐
│                APPLICATION LOGIC LAYER                        │
│                                                               │
│  ┌──────────────────┐  ┌────────────────────┐                │
│  │  AuctionManager  │  │  Bidder / Bid      │                │
│  │  (Facades &      │  │  (Value Objects)   │                │
│  │   Registry)      │  └────────────────────┘                │
│  └──────────────────┘                                        │
└──────────┬────────────────────────────────┬────────────────────┘
           │                                │
┌──────────▼──────────┐       ┌──────────────▼────────────────────┐
│   AUCTION LAYER     │       │    OBSERVER PATTERN LAYER         │
│                     │       │                                    │
│ ┌─────────────────┐ │       │ ┌──────────────────────────────┐  │
│ │   Auction (A)   │ │       │ │   AuctionObserver (I)        │  │
│ │ - submitBid()   │ │       │ ├──────────────────────────────┤  │
│ │ - getWinner()   │ │       │ │ + update(event, auction, bid)│  │
│ │ - notify()      │ │       │ └──────────────────────────────┘  │
│ └────────┬─────────┘ │       │          ▲                       │
│          │           │       │          │                       │
│ ┌────────▼──────────┐│       │ ┌────────┴───────┬──────────────┐│
│ │ LiveAuction       ││       │ │BidderNotifier  │ AuctionLogger ││
│ │ ┌──────────────┐  ││       │ │(notify bidders)│ (log events) ││
│ │ │ Timer Thread │  ││       │ └────────────────┴──────────────┘│
│ │ │ run()        │  ││       │                                    │
│ │ └──────────────┘  ││       │                                    │
│ └────────────────────┘│       │                                    │
│ ┌───────────────────────┐     │                                    │
│ │ ReserveAuction        │     │                                    │
│ │ - closeAuction()      │     │                                    │
│ │ - validateReserve()   │     │                                    │
│ └───────────────────────┘     │                                    │
└────────────────────────────────┴──────────────────────────────────┘

┌──────────────────────────────────────────────────────────────┐
│                  DOMAIN MODEL LAYER                          │
│                                                              │
│  ┌──────────────┐         ┌──────────────────────────────┐  │
│  │ Item (A)     │         │ Bid (Immutable VO)           │  │
│  ├──────────────┤         ├──────────────────────────────┤  │
│  │ - id         │         │ - bidderId (final)           │  │
│  │ - name       │         │ - bidderName (final)         │  │
│  │ - price      │         │ - amount (final)             │  │
│  └─────┬────────┘         │ - timestamp (final)          │  │
│        │                  └──────────────────────────────┘  │
│        ├─▶ PhysicalItem                                      │
│        │   - weight                                          │
│        │   - shippingAddress                                 │
│        │                                                     │
│        └─▶ DigitalItem                                       │
│            - fileFormat                                      │
│            - downloadLink                                    │
│                                                              │
│  ┌──────────────────────────────────────────────────────┐  │
│  │ Bidder                                               │  │
│  ├──────────────────────────────────────────────────────┤  │
│  │ - id                                                 │  │
│  │ - name                                               │  │
│  │ - email                                              │  │
│  │ - bidHistory: List<Bid> (unmodifiable)               │  │
│  └──────────────────────────────────────────────────────┘  │
└──────────────────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────────────────┐
│                 PERSISTENCE LAYER                            │
│                                                              │
│  ┌────────────────────────────────────────────────────────┐ │
│  │ FileManager (Buffered I/O)                             │ │
│  ├────────────────────────────────────────────────────────┤ │
│  │ + saveAuction(auction)                                 │ │
│  │ + saveResult(auction)                                  │ │
│  │ + loadAuctionsFromFile()                               │ │
│  │ + logEvent(event)                                      │ │
│  └────────────────────────────────────────────────────────┘ │
│                                                              │
│  Files:                                                      │
│  ├── data/auctions.txt (Auction Records)                    │
│  └── data/results.txt (Event Log)                           │
└──────────────────────────────────────────────────────────────┘

┌────────────────────────────────────────────────────────────┐
│             EXCEPTION HANDLING LAYER                        │
│                                                            │
│  ├─ InvalidBidException (checked)                         │
│  │   Exception → business logic error                     │
│  │                                                         │
│  ├─ AuctionClosedException (unchecked)                    │
│  │   RuntimeException → programming error                │
│  │                                                         │
│  └─ ReservePriceNotMetException (unchecked)               │
│      RuntimeException → control flow                      │
└────────────────────────────────────────────────────────────┘
```

## Package Organization

### `model` - Domain Model Layer
**Responsibility:** Represent core business entities

**Classes:**
- `Item` (abstract) - Contract for all auction items
- `PhysicalItem` - Concrete implementation for physical goods
- `DigitalItem` - Concrete implementation for digital goods
- `Bid` - Immutable value object representing single bid
- `Bidder` - Bidder profile with bid history

**Key Decisions:**
- Item is abstract to enforce common interface
- PhysicalItem and DigitalItem override abstract methods
- Bid is immutable (all fields final) for data integrity
- Bidder maintains unmodifiable bid history

### `auction` - Auction Management Layer
**Responsibility:** Implement bidding logic and auction types

**Classes:**
- `Auction` (abstract) - Core bidding engine
  - Validates bids via `isValidBid()`
  - Manages bidders and observer notifications
  - Maintains bid history and highest bid
  - Defines abstract methods for subclasses
  
- `LiveAuction` - Timed auction implementation
  - Implements `Runnable` for timer thread
  - Countdown from duration to 0
  - Auto-closes when time expires
  - Throws AuctionClosedException if closed
  
- `ReserveAuction` - Auction with minimum price
  - Validates reserve price when closing
  - Enforces 5% minimum bid increment
  - Throws ReservePriceNotMetException if not met

**Key Decisions:**
- submitBid() in abstract class (DRY principle)
- Bid validation via strategy pattern (isValidBid)
- Observer notification in base class
- Separate thread for timer (non-blocking)

### `observer` - Observer Pattern Implementation
**Responsibility:** Decouple auctions from event handlers

**Classes:**
- `AuctionObserver` (interface) - Observer contract
  - Single `update()` method for notifications
  - Receives event, auction, and bid information
  
- `BidderNotifier` - Notifies individual bidders
  - Receives outbid alerts
  - Gets win notifications
  - Maintains bidder reference for personalization
  
- `AuctionLogger` - Event audit trail
  - Logs to console with timestamps
  - Persists to file via FileManager
  - Records all event types

**Key Decisions:**
- Interface-based observer contract
- Auctions never know specific observer implementations
- Observers receive full context (auction, bid)
- Timestamps added by logger for audit trail

### `core` - Application Orchestration
**Responsibility:** Manage auctions and coordinate system

**Classes:**
- `AuctionManager`
  - Registry of all auctions (Map<id, auction>)
  - O(1) lookup via HashMap
  - Provides query and manipulation operations
  - Never exposes internal registry

**Key Decisions:**
- Map for constant-time lookups
- Getter returns unmodifiable copy
- Exception thrown for missing auctions
- Manages auction lifecycle

### `storage` - Persistence Layer
**Responsibility:** Handle file I/O operations

**Classes:**
- `FileManager` (utility class)
  - Buffered reader/writer for efficiency
  - Append mode for non-destructive logging
  - Proper exception handling
  - Static methods (no instance needed)

**Key Decisions:**
- Buffered I/O for performance
- Try-with-resources for resource management
- Graceful error messages
- Separate methods for different operations

### `exceptions` - Custom Exceptions
**Responsibility:** Domain-specific error handling

**Classes:**
- `InvalidBidException` - Checked exception
  - Thrown by submitBid()
  - Caller must handle
  - Includes bid amount in message
  
- `AuctionClosedException` - Unchecked exception
  - Thrown by LiveAuction.submitBid()
  - Programming error (auction state)
  - Inherits from RuntimeException
  
- `ReservePriceNotMetException` - Unchecked exception
  - Thrown by ReserveAuction.closeAuction()
  - Control flow exception
  - Shows both reserve and highest bid

**Key Decisions:**
- InvalidBidException is checked (expected failure)
- AuctionClosedException is unchecked (shouldn't happen)
- ReservePriceNotMetException is unchecked (control flow)

## Design Patterns

### 1. Observer Pattern (Primary)
```
Subject (Auction)
    ├─ registerObserver(observer)
    ├─ removeObserver(observer)
    └─ notifyObservers(event, bid)
           ↓
        Observers:
        ├─ BidderNotifier.update()
        └─ AuctionLogger.update()
```

**Benefits:**
- Loose coupling between auctions and handlers
- Dynamic observer registration
- Event broadcasting to multiple handlers
- Easy to add new observer types

### 2. Template Method Pattern
```
Auction.submitBid() [concrete]
    ├─ isValidBid(amount) [abstract - overridden]
    ├─ createBid()
    ├─ updateState()
    └─ notifyObservers()
        
LiveAuction.isValidBid()      ← checks time remaining
ReserveAuction.isValidBid()   ← checks increment
```

**Benefits:**
- Common logic in base class
- Variation points defined by subclasses
- No code duplication
- Consistent algorithm structure

### 3. Strategy Pattern
```
isValidBid() serves as validation strategy:
    ├─ LiveAuction: amount > current AND auctionOpen
    └─ ReserveAuction: amount meets increment rule
```

**Benefits:**
- Different validation rules per auction type
- Runtime strategy selection
- Easy to add new auction types

### 4. Immutable Value Object
```
Bid (immutable)
    ├─ final int bidderId
    ├─ final String bidderName
    ├─ final double amount
    └─ final LocalDateTime timestamp
    
No setters, only getters
```

**Benefits:**
- Thread-safe by design
- Can be shared freely
- Guarantees data integrity
- Natural for value objects

### 5. Registry Pattern
```
AuctionManager
    └─ Map<Integer, Auction> activeAuctions
        ├─ registerAuction()
        ├─ getAuction()
        ├─ removeAuction()
        └─ listAllAuctions()
```

**Benefits:**
- Central auction management
- O(1) lookup performance
- Centralized lifecycle management
- Easy to query all auctions

## Threading Model

### LiveAuction Timer Thread

```
Main Thread                          Timer Thread
─────────────────                   ──────────────
startAuction()
  ├─ set auctionOpen=true
  ├─ new Thread(this)
  └─ thread.start() ──────────────┐
                                   │
                                   ▼
                                run()
                                  │
                                  ├─ while timeRemaining > 0
                                  │   ├─ sleep(1000)
                                  │   ├─ timeRemaining--
                                  │   └─ notify on milestones
                                  │
                                  └─ closeAuction()
                                      ├─ set auctionOpen=false
                                      └─ notify observers

Wait for             ◄─ auction.isOpen() loop
completion             every 100ms

bidding operates ────► isValidBid() check
during timer          in submitBid()
```

**Key Points:**
- Main thread doesn't block (though menu does wait)
- Timer thread has independent lifecycle
- Shared state: `auctionOpen` flag (volatile)
- Bidding continues until timer ends
- Safe termination via interrupt handling

**Synchronization:**
- `auctionOpen` boolean is read-only after start
- Bid list is append-only (no modification)
- No explicit locking needed (simple state changes)

## Data Flow

### Bidding Flow

```
User/Console
    ↓
Main.placeABid()
    ↓
Bidder.placeBid(auction, amount)
    ↓
Auction.submitBid(bidder, amount)
    ├─ isValidBid(amount)? ─→ InvalidBidException
    ├─ Create Bid object
    ├─ Update highestBid & currentPrice
    ├─ Add to allBids List
    └─ notifyObservers()
        ├─ BidderNotifier.update() ──→ Console
        └─ AuctionLogger.update() ──→ Console + File
            ↓
        Bidder.addToBidHistory()
```

### Auction Lifecycle

```
Created (not open)
    ↓
startAuction()
    ├─ auctionOpen = true
    ├─ Spawn timer thread (LiveAuction)
    └─ Accept bids
    
During Bidding
    ├─ User places bids
    ├─ Bidders notified
    ├─ Events logged
    └─ Timer counts down (LiveAuction)
    
closeAuction()
    ├─ auctionOpen = false
    ├─ Validate requirements:
    │   ├─ Reserve price (ReserveAuction)
    │   └─ Time expired (LiveAuction)
    ├─ Determine winner
    ├─ Notify observers
    ├─ Save results
    └─ Closed (no more bids)
```

## State Management

### Auction State Machine

```
┌─────────────┐
│  CREATED    │
│ (notOpen)   │
└──────┬──────┘
       │ startAuction()
       ▼
┌─────────────┐
│    OPEN     │ ← accept bids
│ (auctionOpen)  isValidBid() must pass
└──────┬──────┘
       │
       ├─ Time expires (LiveAuction)
       │
       ├─ User calls close (ReserveAuction)
       │
       └─ Timer expires ──┐
                          │
       closeAuction() ◄───┘
       │
       ▼
┌─────────────┐
│  CLOSED     │
│ (notOpen)   │ ← Determine winner
└─────────────┘    Notify observers
                   Save results
```

### Bid State

```
Auction.currentPrice ────────────────────────┐
                                             │
    Each new bid updates:                    │
    ├─ currentPrice = bid.amount             │ only highest
    ├─ highestBid = new Bid(...)             │ bid kept
    ├─ allBids.add(newBid)                   │ in memory
    └─ Notify "OUTBID" to previous holder ◄─┘
```

## Encapsulation & Access Control

### Bidder Encapsulation Example

```java
// WRONG - Exposes internal list
public List<Bid> getBidHistory() {
    return bidHistory;  // Can be modified externally!
}

// CORRECT - Returns unmodifiable copy
public List<Bid> getBidHistory() {
    return Collections.unmodifiableList(bidHistory);
}

// Also correct - Builder/getter pattern
public Bid getBidAt(int index) {
    return bidHistory.get(index);
}
```

### Auction Encapsulation

```java
// History: all bids
public List<Bid> getAllBids() {
    return new ArrayList<>(allBids);  // Defensive copy
}

// Winner: current highest bid (or null)
public Bid getWinner() {
    return highestBid;  // Safe - Bid is immutable
}

// Status: current price
public double getCurrentPrice() {
    return currentPrice;  // Primitive - safe
}
```

## Performance Considerations

### Time Complexity
| Operation | Complexity | Implementation |
|-----------|-----------|-----------------|
| submitBid | O(1) | Direct assignment, list append |
| getWinner | O(1) | Cached reference |
| getAuction | O(1) | HashMap lookup |
| listAuctions | O(n) | Map iteration |
| getBidHistory | O(n) | List copy |
| Timer drift | O(1) | Each second |

### Space Complexity
| Data Structure | Space | Notes |
|---|---|---|
| Auctions Map | O(a) | a = number of auctions |
| Bids per auction | O(b) | b = number of bids |
| Observers list | O(o) | o = observers per auction |
| Bidder history | O(h) | h = bids per bidder |
| **Total** | **O(a·b + a·o)** | a << b typically |

### Optimization Opportunities
1. Lazy-load observer notification
2. Cache bid summaries instead of full list
3. Use database for large auction sets
4. Index bids by bidder for faster history lookup

## Error Handling Strategy

### Exception Hierarchy
```
Throwable
    ├─ Exception (checked)
    │   └─ InvalidBidException
    │       └─ Caller must handle
    │
    └─ RuntimeException (unchecked)
        ├─ AuctionClosedException
        │   └─ Indicates programming error
        │
        └─ ReservePriceNotMetException
            └─ Control flow exception
```

### Try-Catch Usage

**Scanner Input:** Handle user input errors
```java
try {
    int choice = scanner.nextInt();
} catch (InputMismatchException e) {
    System.err.println("Invalid input");
}
```

**File I/O:** Graceful degradation
```java
try (BufferedWriter writer = 
    new BufferedWriter(new FileWriter(file, true))) {
    writer.write(data);
} catch (IOException e) {
    System.err.println("Cannot write: " + e);
}
```

**Bidding:** Validate before accepting
```java
try {
    bidder.placeBid(auction, amount);
} catch (InvalidBidException e) {
    System.err.println(e.getMessage());
}
```

## Testing Strategy

### Test Cases Covered in Demo

1. **Model Classes**
   - Item hierarchy (Physical/Digital)
   - Bid creation and immutability
   - Bidder profile and history

2. **Auctions**
   - Live auction countdown
   - Reserve price logic
   - Bid validation rules
   - Winner determination

3. **Observer Pattern**
   - Bidder notifications
   - Event logging
   - Outbid alerts
   - Win notifications

4. **File I/O**
   - Auction record saving
   - Result logging
   - Append mode integrity

5. **Concurrency**
   - Timer thread lifecycle
   - Bidding during countdown
   - Clean termination

6. **Exception Handling**
   - Invalid bid rejection
   - Closed auction protection
   - Reserve not met handling

## Extensibility Points

### Adding New Auction Types
```java
class VickreyAuction extends Auction {
    // Sealed-bid auction logic
    @Override
    public void closeAuction() {
        // Find second-highest bid
    }
    
    @Override
    public boolean isValidBid(double amount) {
        return amount > 0;  // Only validate positive
    }
}
```

### Adding New Observers
```java
class EmailNotifier implements AuctionObserver {
    @Override
    public void update(String event, Auction auction, Bid bid) {
        // Send emails to bidders
    }
}

// Usage
auction.registerObserver(new EmailNotifier());
```

### Adding Database Persistence
```java
class DatabaseManager {
    public void saveAuction(Auction a) {
        // SQL INSERT with JDBC/Hibernate
    }
    
    public Auction loadAuction(int id) {
        // SQL SELECT
    }
}
```

---

**Version:** 1.0  
**Last Updated:** March 26, 2026  
**Status:** Complete Documentation
