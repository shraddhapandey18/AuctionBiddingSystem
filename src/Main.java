import auction.Auction;
import auction.LiveAuction;
import auction.ReserveAuction;
import core.AuctionManager;
import exceptions.InvalidBidException;
import exceptions.ReservePriceNotMetException;
import model.Bidder;
import model.DigitalItem;
import model.PhysicalItem;
import observer.AuctionLogger;
import observer.BidderNotifier;
import storage.FileManager;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static AuctionManager auctionManager;
    private static Scanner scanner;
    private static int bidderCounter = 10;
    private static int auctionCounter = 100;

    public static void main(String[] args) {
        auctionManager = new AuctionManager();
        scanner = new Scanner(System.in);

        System.out.println("==========================================");
        System.out.println("  AUCTION & BIDDING SYSTEM");
        System.out.println("==========================================\n");

        boolean running = true;
        while (running) {
            displayMenu();
            try {
                if (!scanner.hasNextInt()) {
                    break;
                }
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        listAllAuctions();
                        break;
                    case 2:
                        viewAuctionDetails();
                        break;
                    case 3:
                        placeABid();
                        break;
                    case 4:
                        startAnAuction();
                        break;
                    case 5:
                        closeAuction();
                        break;
                    case 6:
                        viewBidHistory();
                        break;
                    case 7:
                        viewPastResults();
                        break;
                    case 8:
                        runDemo();
                        break;
                    case 9:
                        System.out.println("\nThank you for using Auction System. Goodbye!");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.err.println("Input error: " + e.getMessage());
                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                } else {
                    break;
                }
            }
        }
        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n==========================================");
        System.out.println(" MAIN MENU");
        System.out.println("==========================================");
        System.out.println("1. List All Auctions");
        System.out.println("2. View Auction Details");
        System.out.println("3. Place a Bid");
        System.out.println("4. Start an Auction");
        System.out.println("5. Close an Auction (Reserve)");
        System.out.println("6. View Bid History (by Bidder)");
        System.out.println("7. View Past Results (from file)");
        System.out.println("8. Run Demo");
        System.out.println("9. Exit");
        System.out.println("==========================================");
        System.out.print("Enter choice: ");
    }

    private static void listAllAuctions() {
        auctionManager.listAllAuctions();
    }

    private static void viewAuctionDetails() {
        try {
            auctionManager.listAllAuctions();
            System.out.print("\nEnter Auction ID: ");
            int auctionId = scanner.nextInt();
            scanner.nextLine();

            Auction auction = auctionManager.getAuction(auctionId);
            auction.displayAuctionStatus();
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            scanner.nextLine();
        }
    }

    private static void placeABid() {
        try {
            auctionManager.listAllAuctions();
            System.out.print("\nEnter Auction ID: ");
            int auctionId = scanner.nextInt();
            scanner.nextLine();

            Auction auction = auctionManager.getAuction(auctionId);

            System.out.print("Enter Bidder ID: ");
            int bidderId = scanner.nextInt();
            System.out.print("Enter Bidder Name: ");
            scanner.nextLine();
            String bidderName = scanner.nextLine();
            System.out.print("Enter Bidder Email: ");
            String email = scanner.nextLine();

            Bidder bidder = new Bidder(bidderId, bidderName, email);

            System.out.print("Enter Bid Amount: $");
            double amount = scanner.nextDouble();
            scanner.nextLine();

            bidder.placeBid(auction, amount);
            System.out.println("✓ Bid placed successfully!");

        } catch (InvalidBidException e) {
            System.err.println("Bid rejected: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            scanner.nextLine();
        }
    }

    private static void startAnAuction() {
        try {
            auctionManager.listAllAuctions();
            System.out.print("\nEnter Auction ID to start: ");
            int auctionId = scanner.nextInt();
            scanner.nextLine();

            Auction auction = auctionManager.getAuction(auctionId);

            if (auction.isOpen()) {
                System.out.println("⚠ Auction #" + auctionId + " is already open!");
                return;
            }

            auction.startAuction();

            if (auction instanceof LiveAuction) {
                System.out.println("Waiting for live auction to complete...");
                LiveAuction liveAuction = (LiveAuction) auction;
                while (liveAuction.isOpen()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }

        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            scanner.nextLine();
        }
    }

    private static void closeAuction() {
        try {
            auctionManager.listAllAuctions();
            System.out.print("\nEnter Auction ID to close: ");
            int auctionId = scanner.nextInt();
            scanner.nextLine();

            Auction auction = auctionManager.getAuction(auctionId);

            if (!auction.isOpen()) {
                System.out.println("⚠ Auction #" + auctionId + " is already closed!");
                return;
            }

            if (auction instanceof ReserveAuction) {
                ((ReserveAuction) auction).tryClose();
            } else {
                auction.closeAuction();
            }

            FileManager.saveAuction(auction);
            if (auction.getWinner() != null) {
                FileManager.saveResult(auction);
            }

        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            scanner.nextLine();
        }
    }

    private static void viewBidHistory() {
        try {
            System.out.print("Enter Bidder ID: ");
            int bidderId = scanner.nextInt();
            System.out.print("Enter Bidder Name: ");
            scanner.nextLine();
            String bidderName = scanner.nextLine();
            System.out.print("Enter Bidder Email: ");
            String email = scanner.nextLine();

            Bidder bidder = new Bidder(bidderId, bidderName, email);

            for (Auction auction : auctionManager.getAllAuctions().values()) {
                for (model.Bid bid : auction.getAllBids()) {
                    if (bid.getBidderId() == bidderId) {
                        bidder.addToBidHistory(bid);
                    }
                }
            }

            bidder.displayBidHistory();

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            scanner.nextLine();
        }
    }

    private static void viewPastResults() {
        FileManager.loadAuctionsFromFile();
    }

    private static void runDemo() {
        System.out.println("\n🎪 DEMO MODE - Creating sample auctions and bidders...\n");

        PhysicalItem item1 = new PhysicalItem(1, "Vintage Camera", "Classic 1970s film camera",
                150.0, 0.8, "123 Auction St");
        PhysicalItem item2 = new PhysicalItem(2, "Antique Watch", "Swiss mechanical watch",
                300.0, 0.2, "456 Market Ave");
        DigitalItem item3 = new DigitalItem(3, "Photoshop Template Pack",
                "50 premium design templates", 40.0, "ZIP", "https://download.example.com/pack");

        Bidder alice = new Bidder(101, "Alice", "alice@mail.com");
        Bidder bob = new Bidder(102, "Bob", "bob@mail.com");
        Bidder carol = new Bidder(103, "Carol", "carol@mail.com");

        LiveAuction liveAuction = new LiveAuction(100, item1, 10);
        ReserveAuction reserveAuction = new ReserveAuction(101, item2, 400.0);

        auctionManager.registerAuction(liveAuction);
        auctionManager.registerAuction(reserveAuction);

        AuctionLogger logger1 = new AuctionLogger();
        AuctionLogger logger2 = new AuctionLogger();

        liveAuction.registerObserver(new BidderNotifier(alice));
        liveAuction.registerObserver(new BidderNotifier(bob));
        liveAuction.registerObserver(new BidderNotifier(carol));
        liveAuction.registerObserver(logger1);

        reserveAuction.registerObserver(new BidderNotifier(alice));
        reserveAuction.registerObserver(new BidderNotifier(bob));
        reserveAuction.registerObserver(new BidderNotifier(carol));
        reserveAuction.registerObserver(logger2);

        System.out.println("=== LIVE AUCTION #100 ===\n");
        liveAuction.startAuction();

        try {
            Thread.sleep(2000);
            alice.placeBid(liveAuction, 160);
            Thread.sleep(2000);
            bob.placeBid(liveAuction, 175);
            Thread.sleep(2000);
            alice.placeBid(liveAuction, 200);
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (InvalidBidException e) {
            System.err.println(e.getMessage());
        }

        while (liveAuction.isOpen()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        FileManager.saveAuction(liveAuction);
        if (liveAuction.getWinner() != null) {
            FileManager.saveResult(liveAuction);
        }

        System.out.println("\n=== RESERVE AUCTION #101 ===\n");
        reserveAuction.startAuction();

        try {
            bob.placeBid(reserveAuction, 320);
            Thread.sleep(1000);
            carol.placeBid(reserveAuction, 370);
            Thread.sleep(1000);

            boolean reserveMet = false;
            try {
                reserveAuction.closeAuction();
                FileManager.saveAuction(reserveAuction);
                if (reserveAuction.getWinner() != null) {
                    FileManager.saveResult(reserveAuction);
                }
                reserveMet = true;
            } catch (Exception e) {
                System.out.println("First attempt - reserve not met, trying again...\n");
            }

            if (!reserveMet) {
                reserveAuction = new ReserveAuction(101, item2, 400.0);
                auctionManager.removeAuction(101);
                auctionManager.registerAuction(reserveAuction);

                AuctionLogger logger3 = new AuctionLogger();
                reserveAuction.registerObserver(new BidderNotifier(alice));
                reserveAuction.registerObserver(new BidderNotifier(bob));
                reserveAuction.registerObserver(new BidderNotifier(carol));
                reserveAuction.registerObserver(logger3);

                reserveAuction.startAuction();
                carol.placeBid(reserveAuction, 420);
                reserveAuction.closeAuction();
                FileManager.saveAuction(reserveAuction);
                if (reserveAuction.getWinner() != null) {
                    FileManager.saveResult(reserveAuction);
                }
            }

        } catch (InvalidBidException e) {
            System.err.println(e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("\n✓ Demo completed!");
    }
}
