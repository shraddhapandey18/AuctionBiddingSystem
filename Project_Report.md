# Auction Bidding System - Project Report

---

<div style="text-align: center; font-size: 14pt; font-weight: bold; margin: 50px 0; border: 2px solid #007acc; padding: 30px; background-color: #f8f9fa;">

**Course Name:** OOPS Lab  
**Project Title:** Auction Bidding System  
**Student Name:** Shraddha Pandey  
**Roll Number:** 7   
**Semester:** Fourth(4) 

</div>

---

<div style="page-break-after: always;"></div>

## 1. Problem Statement

<div style="background-color: #f0f8ff; padding: 25px; border-left: 5px solid #007acc; margin: 20px 0; border-radius: 5px;">

The **Auction Bidding System** is designed to facilitate online auctions for various types of items, supporting both **live auctions** with fixed durations and **reserve auctions** that require meeting a minimum price threshold. The system needs to handle multiple bidders, manage auction lifecycles, provide real-time bidding capabilities, and maintain data persistence.

### 🎯 Key Requirements:
- ✅ Support for different auction types (live and reserve)
- ✅ Item categorization (physical and digital items)
- ✅ Bidder registration and management
- ✅ Bid validation and auction rules enforcement
- ✅ Observer pattern for notifications and logging
- ✅ File-based data persistence
- ✅ Command-line interface for user interaction
- ✅ Error handling for various failure scenarios

The system must demonstrate strong **object-oriented programming principles** including inheritance, polymorphism, abstraction, and encapsulation while providing a robust and user-friendly auction platform.

</div>

---

<div style="page-break-after: always;"></div>

## 2. System Design

### 📊 UML Class Diagram

<div style="background-color: #f8f9fa; padding: 20px; border: 2px solid #dee2e6; border-radius: 8px; margin: 20px 0; font-family: 'Courier New', monospace;">

```
┌─────────────────────────────────────────────────────────────────────────┐
│                              AUCTION SYSTEM                             │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│  ┌─────────────────┐       ┌─────────────────┐       ┌─────────────────┐ │
│  │    Auction      │       │      Item       │       │ AuctionManager  │ │
│  │─────────────────┤       │─────────────────┤       │─────────────────┤ │
│  │ -auctionId      │       │ -itemId         │       │ -auctions        │ │
│  │ -item           │       │ -name           │       │ -bidders         │ │
│  │ -startingBid    │       │ -description    │       │ -observers       │ │
│  │ -active         │       │ -startingPrice  │       │                 │ │
│  │ -bids           │       │ -sellerId       │       └─────────────────┘ │
│  │ -observers      │       └─────────────────┘       │ +registerAuction │ │
│  │ -startTime      │       │ +getCategory()  │       │ +getAuction      │ │
│  │ -endTime        │       │ +getShipping()  │       │ +registerBidder  │ │
│  └─────────────────┘       └─────────────────┘       └─────────────────┘ │
│           ▲                       ▲                                           │
│           │                       │                                           │
│     ┌─────┴─────┐           ┌─────┴─────┐                                       │
│     │ LiveAuction│           │PhysicalItem│           ┌─────────────────┐     │
│     │ -scheduler │           │ -weight     │           │     Bidder      │     │
│     │ +run()     │           │ -dimensions │           │─────────────────┤     │
│     └───────────┘           │ -address    │           │ -bidderId        │     │
│           ▲                 └─────────────┘           │ -name            │     │
│           │                       ▲                 │ -budget          │     │
│     ┌─────┴─────┐           ┌─────┴─────┐           │ -bidHistory       │     │
│     │ReserveAuction│        │DigitalItem │           └─────────────────┘     │
│     │ -reservePrice│        │ -format     │                                     │
│     │ -reserveMet  │        │ -size       │                                     │
│     └─────────────┘        │ -link       │                                     │
│                            └─────────────┘                                     │
│                                                                             │
│  ┌─────────────────┐       ┌─────────────────┐       ┌─────────────────┐     │
│  │ AuctionObserver │       │ AuctionLogger  │       │ BidderNotifier  │     │
│  │─────────────────┤       │─────────────────┤       │─────────────────┤     │
│  │ +update()       │       │ -log           │       │ -bidders         │     │
│  └─────────────────┘       └─────────────────┘       └─────────────────┘     │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

</div>

### 📁 Package Structure

<div style="background-color: #fff3cd; padding: 15px; border: 1px solid #ffeaa7; border-radius: 5px; margin: 20px 0;">

```
📦 src/
├── 📄 Main.java                    # 🎮 CLI Entry Point & Menu System
├── 📂 auction/                     # 🔨 Auction Implementations
│   ├── 📄 Auction.java             # 📋 Abstract Base Class
│   ├── 📄 LiveAuction.java         # ⏰ Time-based Auctions
│   └── 📄 ReserveAuction.java      # 💰 Reserve Price Auctions
├── 📂 core/                        # ⚙️ Business Logic
│   └── 📄 AuctionManager.java      # 🎯 Central Management
├── 📂 exceptions/                  # ⚠️ Error Handling
│   ├── 📄 AuctionClosedException.java
│   ├── 📄 InvalidBidException.java
│   └── 📄 ReservePriceNotMetException.java
├── 📂 model/                       # 📦 Data Models
│   ├── 📄 Bid.java                 # 💵 Bid Value Object
│   ├── 📄 Bidder.java              # 👤 Bidder Entity
│   ├── 📄 Item.java                # 📦 Abstract Item
│   ├── 📄 PhysicalItem.java        # 📦 Physical Goods
│   └── 📄 DigitalItem.java         # 💿 Digital Goods
├── 📂 observer/                    # 👁️ Observer Pattern
│   ├── 📄 AuctionObserver.java     # 🔌 Observer Interface
│   ├── 📄 AuctionLogger.java       # 📝 Logging Observer
│   └── 📄 BidderNotifier.java      # 📢 Notification Observer
└── 📂 storage/                     # 💾 Persistence
    └── 📄 FileManager.java         # 💾 File I/O Operations
```

</div>

### 🔄 Interaction Flow

<div style="background-color: #e8f5e8; padding: 20px; border: 1px solid #c8e6c9; border-radius: 5px; margin: 20px 0;">

1. **🚀 System Initialization**
   - AuctionManager, BidderNotifier, AuctionLogger initialized
   - FileManager ensures data directory exists

2. **🏗️ Auction Creation**
   - User selects auction type and item type
   - Auction instance created with observers attached
   - Registered with AuctionManager and saved to file

3. **💰 Bidding Process**
   - Bidder validation and bid amount checking
   - Bid submitted to auction, observers notified
   - Bid added to bidder's history

4. **📊 Auction Monitoring**
   - LiveAuction uses scheduler for automatic closure
   - ReserveAuction checks reserve price conditions
   - FileManager persists auction state

</div>

---

<div style="page-break-after: always;"></div>

## 3. OOP Concepts Demonstrated

| Concept | Location | Purpose | Code Snippet | Explanation |
|---------|----------|---------|--------------|-------------|
| **Inheritance** | `Auction` → `LiveAuction`/`ReserveAuction`<br>`Item` → `PhysicalItem`/`DigitalItem` | Code reuse and IS-A relationships | ```java<br>public abstract class Auction {<br>    // Common auction properties<br>}<br><br>public class LiveAuction extends Auction {<br>    // Live auction specific behavior<br>}<br>``` | Abstract base classes define common behavior, concrete subclasses provide specific implementations |
| **Polymorphism** | Auction submission, Observer notifications | Uniform treatment of different types | ```java<br>Auction auction = new LiveAuction(...);<br>auction.submitBid(bid); // Polymorphic call<br>``` | Same method calls work on different auction types |
| **Abstraction** | Abstract classes and interfaces | Hide complexity, define contracts | ```java<br>public abstract class Item {<br>    public abstract String getCategory();<br>}<br>``` | Abstract methods define what subclasses must implement |
| **Encapsulation** | Private fields with getters/setters | Data protection and controlled access | ```java<br>private double budget;<br>public double getBudget() { return budget; }<br>``` | Internal state protected from external modification |
| **Observer Pattern** | Auction event notifications | Loose coupling between components | ```java<br>auction.addObserver(logger);<br>auction.addObserver(notifier);<br>``` | Auctions notify observers without knowing their implementations |

---

<div style="page-break-after: always;"></div>

## 4. Implementation Highlights

### 🔧 Template Method Pattern in Auction Bidding
```java
public void submitBid(Bid bid) throws InvalidBidException, AuctionClosedException {
    if (!active) {
        throw new AuctionClosedException("Auction is closed");
    }
    validateBidAmount(bid);  // Abstract method - implemented by subclasses
    bids.add(bid);
    checkAuctionEndCondition(bid);  // Abstract method - implemented by subclasses
    notifyObservers("New bid: $" + bid.getAmount());
}
```
**🎯 Justification:** Provides a consistent bidding workflow while allowing different auction types to customize validation and end conditions.

### 👁️ Observer Pattern for Event Handling
```java
// In auction creation
auction.addObserver(bidderNotifier);
auction.addObserver(auctionLogger);

// Automatic notification on bid submission
notifyObservers("New bid on " + item.getName() + ": $" + bid.getAmount());
```
**🎯 Justification:** Decouples auction logic from notification concerns, enabling easy addition of new notification types.

### ⚠️ Custom Exception Hierarchy
```java
public class InvalidBidException extends Exception {
    public InvalidBidException(String message) {
        super(message);
    }
}

try {
    auction.submitBid(bid);
} catch (InvalidBidException | AuctionClosedException e) {
    System.out.println("Bid rejected: " + e.getMessage());
}
```
**🎯 Justification:** Provides specific error handling for different auction failure scenarios, improving user experience and debugging.

---

<div style="page-break-after: always;"></div>

## 5. Testing & Error Handling

### ✅ Test Cases Considered

| Test Case | Description | Expected Behavior |
|-----------|-------------|-------------------|
| **Valid Bid** | Bid > current highest, auction active, bidder registered | Bid accepted, observers notified |
| **Invalid Bid Amount** | Bid ≤ current highest | `InvalidBidException` thrown |
| **Closed Auction** | Bid on inactive auction | `AuctionClosedException` thrown |
| **Unregistered Bidder** | Bid from unknown bidder | Error message, bid rejected |
| **Reserve Price** | Reserve auction bid validation | Must exceed reserve if set |

### 🎯 Edge Cases Handled

- **🏆 First Bid:** Initial bid validation against starting price
- **💰 Reserve Met:** Auction closure when reserve price reached
- **⏰ Timing:** Live auctions close automatically after duration
- **💾 Persistence:** File I/O error handling and recovery
- **📝 Input Validation:** CLI prevents invalid numeric inputs

### 🚨 Failure Scenarios

| Scenario | Exception/Error | Handling |
|----------|----------------|----------|
| **Auction Closed** | `AuctionClosedException` | User notified, bid rejected |
| **Invalid Bid** | `InvalidBidException` | Specific error message shown |
| **Reserve Not Met** | `ReservePriceNotMetException` | Auction continues or closes |
| **File I/O Error** | `IOException` | Graceful degradation, logging |
| **Invalid Input** | `InputMismatchException` | Input cleared, retry prompted |

---

<div style="page-break-after: always;"></div>

## 6. Git Workflow

### 📈 Development Progression

The project followed an **incremental development approach** with clear commit messages documenting each feature addition:

<div style="background-color: #f8f9fa; padding: 15px; border: 1px solid #dee2e6; border-radius: 5px; margin: 20px 0; font-family: 'Courier New', monospace;">

```
* Initial commit: Project setup with basic structure
* Add abstract Item base class with common properties
* Add PhysicalItem and DigitalItem concrete classes
* Add Bid immutable value object and Bidder class
* Add abstract Auction base class with template method pattern
* Add LiveAuction and ReserveAuction concrete implementations  
* Add observer pattern implementation
* Add AuctionManager central registry
* Add custom exception classes for error handling
* Add FileManager utility class for data persistence
* Add Main.java console UI with complete menu system
* Add comprehensive documentation and project files
* Test application functionality - demo runs successfully
* Add sample auction data and test results
* Implement auction manager and file persistence
* Add observer pattern for logging and notifications
* Implement auction system with multiple types
* Initial project structure and exceptions
* Refactor auction core, add observers & storage
* Update README with project information
```

</div>

### 📸 Screenshot of Commit History

**[Insert screenshot of `git log --oneline --graph` output here]**

The development followed a **bottom-up approach**, starting with core data models and building up to the complete application with proper separation of concerns.

---

<div style="page-break-after: always;"></div>

## 7. Conclusion & Future Scope

### 🎉 Conclusion

The **Auction Bidding System** successfully demonstrates comprehensive **object-oriented programming principles** through a well-structured Java application. The implementation includes:

<div style="background-color: #d4edda; padding: 15px; border: 1px solid #c3e6cb; border-radius: 5px; margin: 20px 0;">

- ✅ **Strong OOP Design:** Inheritance hierarchies, polymorphism, abstraction, and encapsulation
- ✅ **Design Patterns:** Observer pattern for event handling, Template Method for auction workflows
- ✅ **Robust Error Handling:** Custom exception hierarchy for different failure scenarios
- ✅ **Data Persistence:** File-based storage for auction and bidder data
- ✅ **User Interface:** Command-line interface with comprehensive menu system
- ✅ **Testing:** Edge case handling and validation throughout the application

</div>

The system provides a **solid foundation** for auction management with support for multiple auction types and item categories.

### 🚀 Future Scope

| Enhancement | Description | Benefits |
|-------------|-------------|----------|
| **🌐 Web Interface** | Convert CLI to Spring Boot web app | Better UX, accessibility |
| **🗄️ Database Integration** | Replace files with MySQL/PostgreSQL | Scalability, concurrent access |
| **⚡ Real-time Bidding** | WebSocket implementation | Live bid updates |
| **💳 Payment Integration** | Stripe/PayPal integration | Secure transactions |
| **🔨 Advanced Auctions** | Dutch, sealed-bid auctions | More auction types |
| **📧 Notification System** | Email/SMS alerts | Better user engagement |
| **📊 Analytics Dashboard** | Bid patterns, performance metrics | Business insights |
| **🔄 Multi-threading** | Improved concurrent handling | Better performance |
| **🔐 Security Features** | Authentication, authorization | Secure access |
| **📱 Mobile App** | Android/iOS bidding app | Mobile accessibility |

---

<div style="page-break-after: always;"></div>

## Appendix

### 📋 A. Initial Project Proposal

**OOP Concepts to be Used:**
- Abstraction
- Inheritance  
- Polymorphism
- Exception Handling
- Collections / Threads (if applicable)

### 📧 B. Review Mail (APPROVED_MINOR)

*[Review mail content not available - to be inserted when received]*

### 📂 C. Source Code Structure

```
src/
├── Main.java
├── auction/
├── core/
├── exceptions/
├── model/
├── observer/
└── storage/
```

**Note:** Full source code not included as per guidelines. Available in project repository.

---

<div style="text-align: center; margin-top: 50px; color: #666;">
**End of Report** - Total Pages: 8-12 (depending on formatting)
</div>
- Bid validation and auction rules enforcement
- Observer pattern for notifications and logging
- File-based data persistence
- Command-line interface for user interaction
- Error handling for various failure scenarios

The system must demonstrate strong object-oriented programming principles including inheritance, polymorphism, abstraction, and encapsulation while providing a robust and user-friendly auction platform.

---

## 2. System Design

### UML Class Diagram

```
+----------------+       +-------------------+
|   Auction      |       |     Item          |
|----------------|       |-------------------|
| -auctionId     |       | -itemId           |
| -item          |       | -name             |
| -startingBid   |       | -description      |
| -active        |       | -startingPrice    |
| -bids          |       | -sellerId         |
| -observers     |       |                   |
| -startTime     |       +-------------------+
| -endTime       |       | +getCategory()    |
+----------------+       | +getShippingInfo()|
| +submitBid()   |       +-------------------+
| +isActive()    |               ^
| +getCurrentHighestBid()|               |
+----------------+               |
        ^                       |
        |                       |
        |                       |
+-------+-------+               +-------+-------+
| LiveAuction   |               | PhysicalItem  |
|---------------|               |---------------|
| -scheduler    |               | -weight       |
|               |               | -dimensions   |
|               |               | -address      |
+---------------+               +---------------+
| +run()        |               +---------------+
+---------------+               | DigitalItem   |
        ^                       |---------------|
        |                       | -format       |
        |                       | -size         |
+-------+-------+               | -link         |
| ReserveAuction|               +---------------+
|---------------|
| -reservePrice |
| -reserveMet   |
+---------------+
```

**Additional Classes:**

```
+----------------+       +-------------------+
| AuctionManager |       |     Bidder        |
|----------------|       |-------------------|
| -auctions      |       | -bidderId         |
| -bidders       |       | -name             |
| -observers     |       | -budget           |
|               |       | -bidHistory       |
+----------------+       +-------------------+
| +registerAuction()|    | +addBidToHistory()|
| +getAuction()   |       +-------------------+
| +registerBidder()|       +-------------------+
+----------------+               ^
        |                       |
        |                       |
+-------+-------+       +-------+-------+
|   FileManager  |       | AuctionObserver  |
|---------------|       |-------------------|
|               |       |                   |
+---------------+       | +update()         |
| +saveAuction() |       +-------------------+
| +loadAuction() |               ^
+---------------+               |
                                |
                +-------+-------+
                | AuctionLogger |
                |---------------|
                | -log          |
                +---------------+
                | BidderNotifier|
                |---------------|
                | -bidders      |
                +---------------+
```

### Description of Major Classes

**Auction (Abstract Base Class):**
- Represents the core auction entity with common properties like auction ID, item, starting bid, and status
- Manages bid collection and observer notifications
- Defines abstract methods for bid validation and auction end conditions

**LiveAuction:**
- Extends Auction for time-based auctions
- Uses ScheduledExecutorService for automatic closure after specified duration
- Implements Runnable for timer-based operations

**ReserveAuction:**
- Extends Auction for auctions requiring minimum price thresholds
- Includes reserve price validation and tracking
- Can close immediately when reserve is met (configurable)

**Item (Abstract Base Class):**
- Defines common item properties (ID, name, description, price, seller)
- Abstract methods for category and shipping information

**PhysicalItem/DigitalItem:**
- Concrete implementations with specific attributes
- PhysicalItem includes weight, dimensions, shipping address
- DigitalItem includes file format, size, download link

**AuctionManager:**
- Central registry for auctions and bidders
- Provides CRUD operations and statistics
- Manages observer notifications

**Observer Pattern Classes:**
- AuctionObserver interface for event notifications
- AuctionLogger for logging auction events
- BidderNotifier for notifying registered bidders

### Package Structure

```
src/
├── Main.java                 # Entry point and CLI
├── auction/                  # Auction types
│   ├── Auction.java          # Abstract base class
│   ├── LiveAuction.java      # Time-based auctions
│   └── ReserveAuction.java   # Reserve price auctions
├── core/                     # Business logic
│   └── AuctionManager.java   # Central management
├── exceptions/               # Custom exceptions
│   ├── AuctionClosedException.java
│   ├── InvalidBidException.java
│   └── ReservePriceNotMetException.java
├── model/                    # Data models
│   ├── Bid.java              # Bid value object
│   ├── Bidder.java           # Bidder entity
│   ├── Item.java             # Abstract item
│   ├── PhysicalItem.java     # Physical goods
│   └── DigitalItem.java      # Digital goods
├── observer/                 # Observer pattern
│   ├── AuctionObserver.java  # Observer interface
│   ├── AuctionLogger.java    # Logging observer
│   └── BidderNotifier.java   # Notification observer
└── storage/                  # Persistence
    └── FileManager.java      # File I/O operations
```

### Interaction Flow

1. **System Initialization:**
   - AuctionManager, BidderNotifier, AuctionLogger initialized
   - FileManager ensures data directory exists

2. **Auction Creation:**
   - User selects auction type and item type
   - Auction instance created with observers attached
   - Registered with AuctionManager and saved to file

3. **Bidding Process:**
   - Bidder validation and bid amount checking
   - Bid submitted to auction, observers notified
   - Bid added to bidder's history

4. **Auction Monitoring:**
   - LiveAuction uses scheduler for automatic closure
   - ReserveAuction checks reserve price conditions
   - FileManager persists auction state

---

## 3. OOP Concepts Demonstrated

### Inheritance
**Where Used:** Auction hierarchy (Auction → LiveAuction/ReserveAuction), Item hierarchy (Item → PhysicalItem/DigitalItem)

**Why Used:** To promote code reuse and establish "is-a" relationships between related classes.

**Code Snippet:**
```java
public abstract class Auction {
    protected String auctionId;
    protected Item item;
    protected double startingBid;
    protected boolean active;
    
    public Auction(String auctionId, Item item, double startingBid) {
        this.auctionId = auctionId;
        this.item = item;
        this.startingBid = startingBid;
        this.active = true;
    }
    
    public abstract void validateBidAmount(Bid bid) throws InvalidBidException;
    public abstract void checkAuctionEndCondition(Bid latestBid);
}

public class LiveAuction extends Auction {
    public LiveAuction(String auctionId, Item item, int durationMinutes) {
        super(auctionId, item, item.getStartingPrice());
        // Live auction specific initialization
    }
}
```

**Explanation:** The abstract Auction class defines common auction behavior, while LiveAuction and ReserveAuction provide specific implementations for different auction types.

### Polymorphism
**Where Used:** Auction submission and observer notifications

**Why Used:** To allow different auction types to be treated uniformly while exhibiting specific behavior.

**Code Snippet:**
```java
// In AuctionManager
public boolean registerAuction(Auction auction) {
    auctions.put(auction.getAuctionId(), auction);
    return true;
}

// In Main.java
Auction auction = null;
if (typeChoice == 1) {
    auction = new LiveAuction(auctionId, item, duration);
} else {
    auction = new ReserveAuction(auctionId, item, duration, reservePrice);
}
auctionManager.registerAuction(auction);
```

**Explanation:** Both LiveAuction and ReserveAuction can be referenced as Auction type, allowing polymorphic behavior in bidding and management operations.

### Abstraction
**Where Used:** Abstract classes (Auction, Item) and interfaces (AuctionObserver)

**Why Used:** To hide implementation details and define contracts for subclasses.

**Code Snippet:**
```java
public abstract class Item {
    public abstract String getCategory();
    public abstract String getShippingInfo();
}

public interface AuctionObserver {
    void update(String message);
}
```

**Explanation:** Abstract methods define what subclasses must implement without specifying how, enabling different concrete implementations.

### Encapsulation
**Where Used:** Private fields with public getters/setters in all model classes

**Why Used:** To protect internal state and provide controlled access to object data.

**Code Snippet:**
```java
public class Bidder {
    private String bidderId;
    private String name;
    private double budget;
    private List<Bid> bidHistory;
    
    public String getBidderId() { return bidderId; }
    public String getName() { return name; }
    public double getBudget() { return budget; }
    
    public void addBidToHistory(Bid bid) {
        bidHistory.add(bid);
    }
}
```

**Explanation:** Private fields ensure data integrity, while public methods provide safe access and modification.

### Observer Pattern
**Where Used:** Auction event notifications (logging and bidder notifications)

**Why Used:** To implement loose coupling between auction events and interested parties.

**Code Snippet:**
```java
public interface AuctionObserver {
    void update(String message);
}

public class Auction {
    private List<AuctionObserver> observers = new ArrayList<>();
    
    public void addObserver(AuctionObserver observer) {
        observers.add(observer);
    }
    
    protected void notifyObservers(String message) {
        for (AuctionObserver observer : observers) {
            observer.update(message);
        }
    }
}
```

**Explanation:** Auctions notify observers of events without knowing their specific implementations, enabling extensible event handling.

---

## 4. Implementation Highlights

### 1. Template Method Pattern in Auction Bidding
```java
public void submitBid(Bid bid) throws InvalidBidException, AuctionClosedException {
    if (!active) {
        throw new AuctionClosedException("Auction is closed");
    }
    validateBidAmount(bid);  // Abstract method - implemented by subclasses
    bids.add(bid);
    checkAuctionEndCondition(bid);  // Abstract method - implemented by subclasses
    notifyObservers("New bid: $" + bid.getAmount());
}
```

**Justification:** Provides a consistent bidding workflow while allowing different auction types to customize validation and end conditions.

### 2. Observer Pattern for Event Handling
```java
// In auction creation
auction.addObserver(bidderNotifier);
auction.addObserver(auctionLogger);

// Automatic notification on bid submission
notifyObservers("New bid on " + item.getName() + ": $" + bid.getAmount());
```

**Justification:** Decouples auction logic from notification concerns, enabling easy addition of new notification types.

### 3. Custom Exception Hierarchy
```java
public class InvalidBidException extends Exception {
    public InvalidBidException(String message) {
        super(message);
    }
}

try {
    auction.submitBid(bid);
} catch (InvalidBidException | AuctionClosedException e) {
    System.out.println("Bid rejected: " + e.getMessage());
}
```

**Justification:** Provides specific error handling for different auction failure scenarios, improving user experience and debugging.

---

## 5. Testing & Error Handling

### Test Cases Considered

1. **Valid Bid Submission:**
   - Bid amount higher than current highest bid
   - Auction is active
   - Bidder is registered

2. **Invalid Bid Scenarios:**
   - Bid amount too low
   - Auction already closed
   - Bidder not registered

3. **Auction Creation:**
   - Valid auction and item parameters
   - Duplicate auction IDs
   - Invalid reserve prices

4. **Edge Cases:**
   - First bid on auction
   - Reserve price exactly met
   - Multiple bidders on same auction

### Edge Cases Handled

- **Reserve Price Validation:** Ensures reserve price is higher than starting price
- **Bid Amount Validation:** Prevents bids equal to or lower than current highest
- **Auction Timing:** Live auctions close automatically after duration
- **File Persistence:** Handles file I/O errors gracefully
- **Input Validation:** CLI validates numeric inputs and prevents crashes

### Failure Scenarios

1. **Auction Closed:** Attempting to bid on inactive auction throws AuctionClosedException
2. **Invalid Bid:** Bid amount validation fails throws InvalidBidException  
3. **Reserve Not Met:** Reserve auction ends without meeting minimum price
4. **File I/O Errors:** Graceful handling of save/load failures
5. **Invalid Input:** CLI input validation prevents runtime errors

---

## 6. Git Workflow

### Development Progression

The project followed an incremental development approach with clear commit messages documenting each feature addition:

```
* Initial commit: Project setup with basic structure
* Add abstract Item base class with common properties
* Add PhysicalItem and DigitalItem concrete classes
* Add Bid immutable value object and Bidder class
* Add abstract Auction base class with template method pattern
* Add LiveAuction and ReserveAuction concrete implementations  
* Add observer pattern implementation
* Add AuctionManager central registry
* Add custom exception classes for error handling
* Add FileManager utility class for data persistence
* Add Main.java console UI with complete menu system
* Add comprehensive documentation and project files
* Test application functionality - demo runs successfully
* Add sample auction data and test results
* Implement auction manager and file persistence
* Add observer pattern for logging and notifications
* Implement auction system with multiple types
* Initial project structure and exceptions
* Refactor auction core, add observers & storage
* Update README with project information
```

### Screenshot of Commit History

[Insert screenshot of `git log --oneline --graph` output here]

The development followed a bottom-up approach, starting with core data models and building up to the complete application with proper separation of concerns.

---

## 7. Conclusion & Future Scope

### Conclusion

The Auction Bidding System successfully demonstrates comprehensive object-oriented programming principles through a well-structured Java application. The implementation includes:

- **Strong OOP Design:** Inheritance hierarchies, polymorphism, abstraction, and encapsulation
- **Design Patterns:** Observer pattern for event handling, Template Method for auction workflows
- **Robust Error Handling:** Custom exception hierarchy for different failure scenarios
- **Data Persistence:** File-based storage for auction and bidder data
- **User Interface:** Command-line interface with comprehensive menu system
- **Testing:** Edge case handling and validation throughout the application

The system provides a solid foundation for auction management with support for multiple auction types and item categories.

### Future Scope

1. **Web Interface:** Convert CLI to web-based application using Spring Boot
2. **Database Integration:** Replace file storage with relational database (MySQL/PostgreSQL)
3. **Real-time Bidding:** WebSocket implementation for live bid updates
4. **Payment Integration:** Integration with payment gateways for bid deposits
5. **Advanced Auction Types:** Dutch auctions, sealed-bid auctions
6. **Notification System:** Email/SMS notifications for auction events
7. **Analytics Dashboard:** Bid patterns and auction performance metrics
8. **Multi-threading:** Improved concurrent bidding handling
9. **Security Features:** User authentication and authorization
10. **Mobile Application:** Android/iOS app for mobile bidding

---

## Appendix

### A. Initial Project Proposal

[Insert copy of initial project proposal document]

### B. Review Mail (APPROVED_MINOR)

[Insert copy of review mail with APPROVED_MINOR status]

### C. Source Code Structure

```
src/
├── Main.java
├── auction/
├── core/
├── exceptions/
├── model/
├── observer/
└── storage/
```

**Note:** Full source code not included as per guidelines. Available in project repository.