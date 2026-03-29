// AuctionManager.java
package core;

import java.util.*;
import auction.*;
import model.*;
import observer.*;

public class AuctionManager {
    private Map<String, Auction> auctions = new HashMap<>();
    private Map<String, Bidder> bidders = new HashMap<>();
    private List<AuctionObserver> observers = new ArrayList<>();

    public boolean registerAuction(Auction auction) {
        if (auctions.containsKey(String.valueOf(auction.getAuctionId()))) {
            return false;
        }
        auctions.put(String.valueOf(auction.getAuctionId()), auction);
        notifyObservers("New auction registered: " + auction.getItem());
        return true;
    }

    public Auction getAuction(String auctionId) {
        return auctions.get(auctionId);
    }

    public Bidder getBidder(String bidderId) {
        return bidders.get(bidderId);
    }

    public List<Auction> getAllAuctions() {
        return new ArrayList<>(auctions.values());
    }

    public List<Bidder> getAllBidders() {
        return new ArrayList<>(bidders.values());
    }

    public boolean registerBidder(Bidder bidder) {
        if (bidders.containsKey(bidder.getBidderId())) {
            return false;
        }
        bidders.put(bidder.getBidderId(), bidder);
        return true;
    }

    public Map<String, Object> getStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalAuctions", auctions.size());
        stats.put("totalBidders", bidders.size());
        stats.put("activeAuctions", auctions.values().stream().filter(Auction::isActive).count());
        return stats;
    }

    public int closeExpiredAuctions() {
        int closedCount = 0;
        for (Auction auction : auctions.values()) {
            if (!auction.isActive()) {
                // Assume closing logic
                closedCount++;
            }
        }
        return closedCount;
    }

    public void addObserver(AuctionObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers(String message) {
        for (AuctionObserver observer : observers) {
            observer.update(message);
        }
    }
}