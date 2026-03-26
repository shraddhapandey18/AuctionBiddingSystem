import core.AuctionManager;
import model.*;
import auction.*;
import observer.*;
import storage.FileManager;
import exceptions.*;
import java.util.Scanner;
import java.util.List;

/**
 * Main console application for the Auction Bidding System.
 * Provides a menu-driven interface for managing auctions and bidders.
 */
public class Main {
    private static AuctionManager auctionManager;
    private static BidderNotifier bidderNotifier;
    private static AuctionLogger auctionLogger;
    private static Scanner scanner;

    public static void main(String[] args) {
        initializeSystem();
        showWelcomeMessage();

        boolean running = true;
        while (running) {
            showMenu();
            int choice = getMenuChoice();

            try {
                switch (choice) {
                    case 1:
                        createAuction();
                        break;
                    case 2:
                        placeBid();
                        break;
                    case 3:
                        viewAuctions();
                        break;
                    case 4:
                        viewBidders();
                        break;
                    case 5:
                        registerBidder();
                        break;
                    case 6:
                        runDemo();
                        break;
                    case 7:
                        viewStatistics();
                        break;
                    case 8:
                        closeExpiredAuctions();
                        break;
                    case 0:
                        running = false;
                        System.out.println("Thank you for using the Auction Bidding System!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Please try again.");
            }

            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }

        scanner.close();
    }

    /**
     * Initialize the auction system components
     */
    private static void initializeSystem() {
        auctionManager = new AuctionManager();
        bidderNotifier = new BidderNotifier();
        auctionLogger = new AuctionLogger();
        scanner = new Scanner(System.in);

        // Ensure data directory exists
        FileManager.ensureDataDirectory();

        System.out.println("Auction Bidding System initialized successfully.");
    }

    /**
     * Display welcome message
     */
    private static void showWelcomeMessage() {
        System.out.println("=====================================");
        System.out.println("    AUCTION BIDDING SYSTEM");
        System.out.println("=====================================");
        System.out.println("A comprehensive Java auction platform");
        System.out.println("featuring multiple auction types and");
        System.out.println("real-time bidding capabilities.");
        System.out.println("=====================================");
    }

    /**
     * Display main menu
     */
    private static void showMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("1. Create New Auction");
        System.out.println("2. Place a Bid");
        System.out.println("3. View Auctions");
        System.out.println("4. View Bidders");
        System.out.println("5. Register New Bidder");
        System.out.println("6. Run Demo");
        System.out.println("7. View Statistics");
        System.out.println("8. Close Expired Auctions");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    /**
     * Get menu choice from user with input validation
     */
    private static int getMenuChoice() {
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a valid number.");
            scanner.nextLine(); // consume invalid input
            System.out.print("Enter your choice: ");
        }
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return choice;
    }

    /**
     * Create a new auction
     */
    private static void createAuction() {
        System.out.println("\n=== CREATE NEW AUCTION ===");

        System.out.print("Enter auction ID: ");
        String auctionId = scanner.nextLine().trim();

        if (auctionManager.getAuction(auctionId) != null) {
            System.out.println("Auction ID already exists!");
            return;
        }

        System.out.println("Select auction type:");
        System.out.println("1. Live Auction (fixed duration)");
        System.out.println("2. Reserve Auction (minimum price required)");
        System.out.print("Enter choice: ");
        int typeChoice = getMenuChoice();

        System.out.println("Select item type:");
        System.out.println("1. Physical Item");
        System.out.println("2. Digital Item");
        System.out.print("Enter choice: ");
        int itemChoice = getMenuChoice();

        Item item = createItem(itemChoice);
        if (item == null) return;

        Auction auction = null;
        try {
            if (typeChoice == 1) {
                System.out.print("Enter duration in minutes: ");
                int duration = getMenuChoice();
                auction = new LiveAuction(auctionId, item, duration);
            } else if (typeChoice == 2) {
                System.out.print("Enter duration in minutes: ");
                int duration = getMenuChoice();
                System.out.print("Enter reserve price: ");
                double reservePrice = scanner.nextDouble();
                scanner.nextLine(); // consume newline
                auction = new ReserveAuction(auctionId, item, duration, reservePrice);
            } else {
                System.out.println("Invalid auction type!");
                return;
            }

            // Add observers
            auction.addObserver(bidderNotifier);
            auction.addObserver(auctionLogger);

            // Register auction
            if (auctionManager.registerAuction(auction)) {
                System.out.println("Auction created successfully!");
                FileManager.saveAuction(auction);
            } else {
                System.out.println("Failed to create auction!");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Error creating auction: " + e.getMessage());
        }
    }

    /**
     * Create an item based on user choice
     */
    private static Item createItem(int itemChoice) {
        System.out.print("Enter item ID: ");
        String itemId = scanner.nextLine().trim();
        System.out.print("Enter item name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter description: ");
        String description = scanner.nextLine().trim();
        System.out.print("Enter starting price: ");
        double startingPrice = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        System.out.print("Enter seller ID: ");
        String sellerId = scanner.nextLine().trim();

        if (itemChoice == 1) {
            // Physical item
            System.out.print("Enter weight (kg): ");
            double weight = scanner.nextDouble();
            scanner.nextLine(); // consume newline
            System.out.print("Enter dimensions: ");
            String dimensions = scanner.nextLine().trim();
            System.out.print("Enter shipping address: ");
            String address = scanner.nextLine().trim();

            return new PhysicalItem(itemId, name, description, startingPrice, sellerId,
                                  weight, dimensions, address);
        } else if (itemChoice == 2) {
            // Digital item
            System.out.print("Enter file format: ");
            String format = scanner.nextLine().trim();
            System.out.print("Enter file size (bytes): ");
            long size = scanner.nextLong();
            scanner.nextLine(); // consume newline
            System.out.print("Enter download link: ");
            String link = scanner.nextLine().trim();

            return new DigitalItem(itemId, name, description, startingPrice, sellerId,
                                 format, size, link);
        } else {
            System.out.println("Invalid item type!");
            return null;
        }
    }

    /**
     * Place a bid on an auction
     */
    private static void placeBid() {
        System.out.println("\n=== PLACE A BID ===");

        System.out.print("Enter auction ID: ");
        String auctionId = scanner.nextLine().trim();

        Auction auction = auctionManager.getAuction(auctionId);
        if (auction == null) {
            System.out.println("Auction not found!");
            return;
        }

        if (!auction.isActive()) {
            System.out.println("Auction is not active!");
            return;
        }

        System.out.print("Enter bidder ID: ");
        String bidderId = scanner.nextLine().trim();

        Bidder bidder = auctionManager.getBidder(bidderId);
        if (bidder == null) {
            System.out.println("Bidder not registered! Please register first.");
            return;
        }

        System.out.print("Enter bid amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // consume newline

        try {
            Bid bid = new Bid(bidderId, amount);
            auction.submitBid(bid);
            bidder.addBidToHistory(bid);
            System.out.println("Bid placed successfully!");
            FileManager.saveAuction(auction);

        } catch (InvalidBidException | AuctionClosedException e) {
            System.out.println("Bid rejected: " + e.getMessage());
        }
    }

    /**
     * View all auctions
     */
    private static void viewAuctions() {
        System.out.println("\n=== AUCTIONS ===");

        List<Auction> auctions = auctionManager.getAllAuctions();
        if (auctions.isEmpty()) {
            System.out.println("No auctions found.");
            return;
        }

        for (Auction auction : auctions) {
            System.out.println(auction);
            System.out.println("---");
        }
    }

    /**
     * View all bidders
     */
    private static void viewBidders() {
        System.out.println("\n=== REGISTERED BIDDERS ===");

        List<Bidder> bidders = auctionManager.getAllBidders();
        if (bidders.isEmpty()) {
            System.out.println("No bidders registered.");
            return;
        }

        for (Bidder bidder : bidders) {
            System.out.println(bidder);
            System.out.println("---");
        }
    }

    /**
     * Register a new bidder
     */
    private static void registerBidder() {
        System.out.println("\n=== REGISTER NEW BIDDER ===");

        System.out.print("Enter bidder ID: ");
        String bidderId = scanner.nextLine().trim();

        if (auctionManager.getBidder(bidderId) != null) {
            System.out.println("Bidder ID already exists!");
            return;
        }

        System.out.print("Enter name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter email: ");
        String email = scanner.nextLine().trim();

        Bidder bidder = new Bidder(bidderId, name, email);
        if (auctionManager.registerBidder(bidder)) {
            bidderNotifier.registerBidder(bidder);
            System.out.println("Bidder registered successfully!");
        } else {
            System.out.println("Failed to register bidder!");
        }
    }

    /**
     * Run a demonstration of the system
     */
    private static void runDemo() {
        System.out.println("\n=== RUNNING DEMO ===");

        try {
            // Create demo bidders
            Bidder bidder1 = new Bidder("bidder1", "Alice Johnson", "alice@email.com");
            Bidder bidder2 = new Bidder("bidder2", "Bob Smith", "bob@email.com");
            auctionManager.registerBidder(bidder1);
            auctionManager.registerBidder(bidder2);
            bidderNotifier.registerBidder(bidder1);
            bidderNotifier.registerBidder(bidder2);

            // Create demo items
            PhysicalItem laptop = new PhysicalItem("item1", "Gaming Laptop", "High-performance gaming laptop",
                                                 1000.0, "seller1", 2.5, "15x10x1 inches", "123 Main St");
            DigitalItem ebook = new DigitalItem("item2", "Java Programming Book", "Comprehensive Java guide",
                                              25.0, "seller2", "PDF", 5242880, "http://example.com/book.pdf");

            // Create demo auctions
            LiveAuction liveAuction = new LiveAuction("live1", laptop, 5); // 5 minutes
            ReserveAuction reserveAuction = new ReserveAuction("reserve1", ebook, 10, 50.0); // 10 min, $50 reserve

            // Add observers
            liveAuction.addObserver(bidderNotifier);
            liveAuction.addObserver(auctionLogger);
            reserveAuction.addObserver(bidderNotifier);
            reserveAuction.addObserver(auctionLogger);

            // Register auctions
            auctionManager.registerAuction(liveAuction);
            auctionManager.registerAuction(reserveAuction);

            System.out.println("Demo setup complete!");
            System.out.println("Created 2 bidders, 2 auctions (1 live, 1 reserve)");
            System.out.println("You can now place bids using the menu options.");

        } catch (Exception e) {
            System.out.println("Error in demo: " + e.getMessage());
        }
    }

    /**
     * View system statistics
     */
    private static void viewStatistics() {
        System.out.println("\n=== SYSTEM STATISTICS ===");

        var stats = auctionManager.getStatistics();
        System.out.println("Total Auctions: " + stats.get("totalAuctions"));
        System.out.println("Active Auctions: " + stats.get("activeAuctions"));
        System.out.println("Total Bidders: " + stats.get("totalBidders"));
        System.out.println("Total Bids Placed: " + stats.get("totalBids"));
    }

    /**
     * Close expired auctions
     */
    private static void closeExpiredAuctions() {
        System.out.println("\n=== CLOSING EXPIRED AUCTIONS ===");

        int closedCount = auctionManager.closeExpiredAuctions();
        System.out.println("Closed " + closedCount + " expired auctions.");

        // Save results for closed auctions
        for (Auction auction : auctionManager.getAllAuctions()) {
            if (!auction.isActive()) {
                FileManager.saveResult(auction);
            }
        }
    }
}