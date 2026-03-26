package model;

import java.time.LocalDateTime;

/**
 * Immutable value object representing a bid in the auction system.
 * Once created, bid details cannot be modified.
 */
public final class Bid {
    private final String bidderId;
    private final double amount;
    private final LocalDateTime timestamp;

    /**
     * Constructor for Bid
     * @param bidderId ID of the bidder
     * @param amount Bid amount
     */
    public Bid(String bidderId, double amount) {
        if (bidderId == null || bidderId.trim().isEmpty()) {
            throw new IllegalArgumentException("Bidder ID cannot be null or empty");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Bid amount must be positive");
        }
        this.bidderId = bidderId;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
    }

    // Getters only - immutable
    public String getBidderId() { return bidderId; }
    public double getAmount() { return amount; }
    public LocalDateTime getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return String.format("Bid{bidder='%s', amount=%.2f, time='%s'}",
                           bidderId, amount, timestamp);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Bid bid = (Bid) obj;
        return Double.compare(bid.amount, amount) == 0 &&
               bidderId.equals(bid.bidderId) &&
               timestamp.equals(bid.timestamp);
    }

    @Override
    public int hashCode() {
        return bidderId.hashCode() + Double.hashCode(amount) + timestamp.hashCode();
    }
}