package observer;

import auction.Auction;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Observer that logs auction events to a file.
 * Maintains an audit trail of all auction activities.
 */
public class AuctionLogger implements AuctionObserver {
    private static final String LOG_FILE = "data/auction_log.txt";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Constructor for AuctionLogger
     */
    public AuctionLogger() {
        // Ensure log directory exists
        try {
            java.io.File logDir = new java.io.File("data");
            if (!logDir.exists()) {
                logDir.mkdirs();
            }
        } catch (Exception e) {
            System.err.println("Warning: Could not create log directory: " + e.getMessage());
        }
    }

    @Override
    public void update(Auction auction, String message) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        String logEntry = String.format("[%s] Auction %s: %s (Highest Bid: $%.2f)",
                                      timestamp, auction.getAuctionId(), message,
                                      auction.getCurrentHighestBid());

        // Print to console
        System.out.println("LOG: " + logEntry);

        // Write to file
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            writer.println(logEntry);
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }

    /**
     * Get the log file path
     * @return Path to the log file
     */
    public String getLogFilePath() {
        return LOG_FILE;
    }

    /**
     * Clear the log file
     */
    public void clearLog() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, false))) {
            writer.println("=== Auction Log Cleared ===");
        } catch (IOException e) {
            System.err.println("Error clearing log file: " + e.getMessage());
        }
    }
}