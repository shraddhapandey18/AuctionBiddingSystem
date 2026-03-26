package core;

import auction.Auction;
import model.Item;
import model.Bidder;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Central manager for all auctions in the system.
 * Provides registration, lookup, and management of auctions.
 */
public class AuctionManager {
    private Map<String, Auction> auctions;
    private Map<String, Bidder> bidders;

    /**
     * Constructor for AuctionManager
     */
    public AuctionManager() {
        this.auctions = new HashMap<>();
        this.bidders = new HashMap<>();
    }

    /**
     * Register a new auction
     * @param auction The auction to register
     * @return true if registered successfully, false if auction ID already exists
     */
    public boolean registerAuction(Auction auction) {
        if (auction == null || auctions.containsKey(auction.getAuctionId())) {
            return false;
        }
        auctions.put(auction.getAuctionId(), auction);
        return true;
    }

    /**
     * Get an auction by ID
     * @param auctionId The auction ID to look up
     * @return The auction, or null if not found
     */
    public Auction getAuction(String auctionId) {
        return auctions.get(auctionId);
    }

    /**
     * Get all auctions
     * @return List of all auctions
     */
    public List<Auction> getAllAuctions() {
        return new ArrayList<>(auctions.values());
    }

    /**
     * Get active auctions only
     * @return List of active auctions
     */
    public List<Auction> getActiveAuctions() {
        return auctions.values().stream()
                      .filter(Auction::isActive)
                      .collect(Collectors.toList());
    }

    /**
     * Get auctions by seller ID
     * @param sellerId The seller ID
     * @return List of auctions for the seller
     */
    public List<Auction> getAuctionsBySeller(String sellerId) {
        return auctions.values().stream()
                      .filter(auction -> sellerId.equals(auction.getItem().getSellerId()))
                      .collect(Collectors.toList());
    }

    /**
     * Register a new bidder
     * @param bidder The bidder to register
     * @return true if registered successfully, false if bidder ID already exists
     */
    public boolean registerBidder(Bidder bidder) {
        if (bidder == null || bidders.containsKey(bidder.getBidderId())) {
            return false;
        }
        bidders.put(bidder.getBidderId(), bidder);
        return true;
    }

    /**
     * Get a bidder by ID
     * @param bidderId The bidder ID to look up
     * @return The bidder, or null if not found
     */
    public Bidder getBidder(String bidderId) {
        return bidders.get(bidderId);
    }

    /**
     * Get all bidders
     * @return List of all bidders
     */
    public List<Bidder> getAllBidders() {
        return new ArrayList<>(bidders.values());
    }

    /**
     * Remove an auction
     * @param auctionId The ID of the auction to remove
     * @return true if removed successfully
     */
    public boolean removeAuction(String auctionId) {
        return auctions.remove(auctionId) != null;
    }

    /**
     * Get statistics about the auction system
     * @return Map containing various statistics
     */
    public Map<String, Integer> getStatistics() {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("totalAuctions", auctions.size());
        stats.put("activeAuctions", getActiveAuctions().size());
        stats.put("totalBidders", bidders.size());

        int totalBids = auctions.values().stream()
                               .mapToInt(auction -> auction.getBids().size())
                               .sum();
        stats.put("totalBids", totalBids);

        return stats;
    }

    /**
     * Close all expired auctions
     * @return Number of auctions closed
     */
    public int closeExpiredAuctions() {
        int closedCount = 0;
        for (Auction auction : auctions.values()) {
            if (!auction.isActive()) {
                auction.closeAuction();
                closedCount++;
            }
        }
        return closedCount;
    }
}