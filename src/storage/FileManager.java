package storage;

import auction.Auction;
import model.Item;
import model.Bid;
import model.Bidder;
import model.PhysicalItem;
import model.DigitalItem;
import auction.LiveAuction;
import auction.ReserveAuction;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Utility class for file I/O operations.
 * Handles saving and loading auction data to/from files.
 */
public class FileManager {
    private static final String AUCTIONS_FILE = "data/auctions.txt";
    private static final String RESULTS_FILE = "data/auction_results.txt";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Save auction data to file
     * @param auction The auction to save
     */
    public static void saveAuction(Auction auction) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(AUCTIONS_FILE, true))) {
            writer.println("=== AUCTION ===");
            writer.println("ID: " + auction.getAuctionId());
            writer.println("Start: " + auction.getStartTime().format(FORMATTER));
            writer.println("End: " + auction.getEndTime().format(FORMATTER));
            writer.println("Active: " + auction.isActive());
            writer.println("Item: " + auction.getItem().toString());
            writer.println("Current Highest Bid: " + auction.getCurrentHighestBid());
            writer.println("Number of Bids: " + auction.getBids().size());

            // Save all bids
            for (Bid bid : auction.getBids()) {
                writer.println("Bid: " + bid.getBidderId() + "," + bid.getAmount() + "," +
                             bid.getTimestamp().format(FORMATTER));
            }
            writer.println("=== END AUCTION ===");
            writer.println();

        } catch (IOException e) {
            System.err.println("Error saving auction: " + e.getMessage());
        }
    }

    /**
     * Save auction results to file
     * @param auction The completed auction
     */
    public static void saveResult(Auction auction) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(RESULTS_FILE, true))) {
            writer.println("=== AUCTION RESULT ===");
            writer.println("Auction ID: " + auction.getAuctionId());
            writer.println("Item: " + auction.getItem().getName());
            writer.println("Seller: " + auction.getItem().getSellerId());
            writer.println("Final Price: $" + String.format("%.2f", auction.getCurrentHighestBid()));
            writer.println("Total Bids: " + auction.getBids().size());

            Bid winningBid = auction.getWinningBid();
            if (winningBid != null) {
                writer.println("Winner: " + winningBid.getBidderId());
                writer.println("Winning Bid: $" + String.format("%.2f", winningBid.getAmount()));
                writer.println("Bid Time: " + winningBid.getTimestamp().format(FORMATTER));
            } else {
                writer.println("Result: No bids received");
            }

            writer.println("Completed: " + LocalDateTime.now().format(FORMATTER));
            writer.println("=== END RESULT ===");
            writer.println();

        } catch (IOException e) {
            System.err.println("Error saving auction result: " + e.getMessage());
        }
    }

    /**
     * Load auctions from file (basic implementation)
     * @return List of loaded auctions (currently returns empty list)
     */
    public static java.util.List<Auction> loadAuctionsFromFile() {
        java.util.List<Auction> auctions = new java.util.ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(AUCTIONS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Basic parsing - in a real system this would be more sophisticated
                if (line.startsWith("ID: ")) {
                    String auctionId = line.substring(4);
                    System.out.println("Loading auction: " + auctionId);
                    // Note: Full implementation would parse complete auction data
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No auction data file found. Starting fresh.");
        } catch (IOException e) {
            System.err.println("Error loading auctions: " + e.getMessage());
        }

        return auctions;
    }

    /**
     * Ensure data directory exists
     */
    public static void ensureDataDirectory() {
        java.io.File dataDir = new java.io.File("data");
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
    }

    /**
     * Get auction data file path
     * @return Path to auctions file
     */
    public static String getAuctionsFilePath() {
        return AUCTIONS_FILE;
    }

    /**
     * Get results file path
     * @return Path to results file
     */
    public static String getResultsFilePath() {
        return RESULTS_FILE;
    }
}