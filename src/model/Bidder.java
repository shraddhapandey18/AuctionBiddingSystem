package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a bidder in the auction system.
 * Maintains bidding history and bidder information.
 */
public class Bidder {
    private String bidderId;
    private String name;
    private String email;
    private List<Bid> biddingHistory;

    /**
     * Constructor for Bidder
     * @param bidderId Unique identifier for the bidder
     * @param name Full name of the bidder
     * @param email Email address
     */
    public Bidder(String bidderId, String name, String email) {
        this.bidderId = bidderId;
        this.name = name;
        this.email = email;
        this.biddingHistory = new ArrayList<>();
    }

    // Getters
    public String getBidderId() { return bidderId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public List<Bid> getBiddingHistory() { return Collections.unmodifiableList(biddingHistory); }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }

    /**
     * Add a bid to the bidder's history
     * @param bid The bid to add
     */
    public void addBidToHistory(Bid bid) {
        if (bid != null && bid.getBidderId().equals(this.bidderId)) {
            biddingHistory.add(bid);
        }
    }

    /**
     * Get the total number of bids made by this bidder
     * @return Number of bids
     */
    public int getTotalBids() {
        return biddingHistory.size();
    }

    /**
     * Get the highest bid amount from this bidder's history
     * @return Highest bid amount, or 0 if no bids
     */
    public double getHighestBid() {
        return biddingHistory.stream()
                           .mapToDouble(Bid::getAmount)
                           .max()
                           .orElse(0.0);
    }

    @Override
    public String toString() {
        return String.format("Bidder{id='%s', name='%s', email='%s', totalBids=%d}",
                           bidderId, name, email, getTotalBids());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Bidder bidder = (Bidder) obj;
        return bidderId.equals(bidder.bidderId);
    }

    @Override
    public int hashCode() {
        return bidderId.hashCode();
    }
}