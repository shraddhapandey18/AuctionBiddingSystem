package observer;

import auction.Auction;
import model.Bid;
import storage.FileManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuctionLogger implements AuctionObserver {
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public void update(String event, Auction auction, Bid bid) {
        String timestamp = LocalDateTime.now().format(timeFormatter);
        String logLine;

        if (bid != null) {
            logLine = String.format("[LOG][%s] EVENT: %s | Auction: #%d | Bid: $%.2f by %s",
                    timestamp, event, auction.getAuctionId(), bid.getAmount(), bid.getBidderName());
        } else {
            logLine = String.format("[LOG][%s] EVENT: %s | Auction: #%d | Bid: N/A",
                    timestamp, event, auction.getAuctionId());
        }

        System.out.println(logLine);
        FileManager.logEvent(logLine);
    }
}
