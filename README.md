# AuctionBiddingSystem

**Student:** Shraddha Pandey

**Course:** 2nd Year B.Tech / B.E. (Computer Science)

**Project type:** College assignment (Java, OOP, CLI)

A Java-based auction platform built as a strong second-year college project with simple UX and clean code:
- Live auctions (real-time bidding)
- Reserve auctions (reserve price handling)
- Bidder registration and bidding workflow
- Auction manager with active/expired auctions management
- File persistence support via `FileManager`
- Observer pattern for logging and notifier events

## ✅ Project Structure

- `src/Main.java` - CLI entrypoint and user menu
- `src/auction` - `Auction`, `LiveAuction`, `ReserveAuction`
- `src/model` - `Bid`, `Bidder`, `Item`, `PhysicalItem`, `DigitalItem`
- `src/core/AuctionManager.java` - business logic
- `src/observer` - `AuctionLogger`, `AuctionObserver`, `BidderNotifier`
- `src/storage/FileManager.java` - optional save/load
- `src/exceptions` - custom exceptions

## ▶️ Build and run

From project root (Windows PowerShell):
1. `cd c:\AuctionBiddingSystem`
2. `go to src and run terminal and run main "java Main.main"`

## 🛠️ Menu options and expected behavior

- `1. Create New Auction`
  - Prompts for auction type, item details, and starting price.
  - Creates `LiveAuction` or `ReserveAuction` with unique ID.

- `2. Place a Bid`
  - Select auction by ID, select bidder by ID, enter bid amount.
  - Validates auction status and rules; may throw `InvalidBidException`.

- `3. View Auctions`
  - Lists all auctions, current bids, high bidder, status (open/closed).

- `4. View Bidders`
  - Shows registered bidders and their balances/participation.

- `5. Register New Bidder`
  - Adds a bidder with name and starting budget.

- `6. Run Demo`
  - Auto-creates sample auctions + bidders and instructs user to bid.
  - Waits for user to press Enter to continue, then returns to menu.

- `7. View Statistics`
  - Summarizes total auctions, completed sales, total revenue.

- `8. Close Expired Auctions`
  - Closes auctions past expiry/that met reserve, optionally sends notifications.

- `0. Exit`
  - Graceful shutdown with farewell message.
