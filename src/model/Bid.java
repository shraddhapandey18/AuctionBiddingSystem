package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Bid {
    private final int bidderId;
    private final String bidderName;
    private final double amount;
    private final LocalDateTime timestamp;

    public Bid(int bidderId, String bidderName, double amount, LocalDateTime timestamp) {
        this.bidderId = bidderId;
        this.bidderName = bidderName;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public int getBidderId() {
        return bidderId;
    }

    public String getBidderName() {
        return bidderName;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Bidder: " + bidderName + " | Amount: $" + String.format("%.2f", amount) +
               " | Time: " + timestamp.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
}
