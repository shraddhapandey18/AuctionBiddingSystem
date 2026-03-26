package storage;

import auction.Auction;
import model.Bid;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileManager {
    private static final String AUCTIONS_FILE = "data/auctions.txt";
    private static final String RESULTS_FILE = "data/results.txt";

    public static void saveAuction(Auction auction) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(AUCTIONS_FILE, true))) {
            writer.write("\n===== AUCTION #" + auction.getAuctionId() + " =====\n");
            writer.write("Type: " + auction.getAuctionType() + "\n");
            writer.write("Item: " + auction.getItem().getName() + "\n");
            writer.write("Starting Price: $" + auction.getItem().getStartingPrice() + "\n");

            List<Bid> bids = auction.getAllBids();
            writer.write("Total Bids: " + bids.size() + "\n");
            writer.write("--- BID HISTORY ---\n");

            for (Bid bid : bids) {
                writer.write("  " + bid + "\n");
            }

            if (auction.getWinner() != null) {
                Bid winner = auction.getWinner();
                writer.write("FINAL WINNER: " + winner.getBidderName() + " - $" + winner.getAmount() + "\n");
            } else {
                writer.write("NO BIDS PLACED\n");
            }
            writer.flush();
        } catch (IOException e) {
            System.err.println("Error saving auction data: " + e.getMessage());
        }
    }

    public static void saveResult(Auction auction) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RESULTS_FILE, true))) {
            if (auction.getWinner() != null) {
                Bid winner = auction.getWinner();
                writer.write("Auction #" + auction.getAuctionId() + " | Item: " + auction.getItem().getName() +
                        " | Winner: " + winner.getBidderName() + " | Price: $" + winner.getAmount() + "\n");
            } else {
                writer.write("Auction #" + auction.getAuctionId() + " | Item: " + auction.getItem().getName() +
                        " | Status: NO WINNER\n");
            }
            writer.flush();
        } catch (IOException e) {
            System.err.println("Error saving result: " + e.getMessage());
        }
    }

    public static void loadAuctionsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(AUCTIONS_FILE))) {
            System.out.println("\n=== LOADING AUCTION RECORDS ===");
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    System.out.println(line);
                    found = true;
                }
            }
            if (!found) {
                System.out.println("(No auction records found)");
            }
        } catch (IOException e) {
            System.out.println("(Auction history file not yet created or cannot be read)");
        }
    }

    public static void logEvent(String logLine) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RESULTS_FILE, true))) {
            writer.write(logLine + "\n");
            writer.flush();
        } catch (IOException e) {
            System.err.println("Error logging event: " + e.getMessage());
        }
    }
}
