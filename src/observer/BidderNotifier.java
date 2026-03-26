package observer;

import auction.Auction;
import model.Bidder;
import java.util.HashMap;
import java.util.Map;

/**
 * Observer that notifies bidders of auction events.
 * Maintains a registry of bidders interested in specific auctions.
 */
public class BidderNotifier implements AuctionObserver {
    private Map<String, Bidder> registeredBidders;

    /**
     * Constructor for BidderNotifier
     */
    public BidderNotifier() {
        this.registeredBidders = new HashMap<>();
    }

    /**
     * Register a bidder for notifications
     * @param bidder The bidder to register
     */
    public void registerBidder(Bidder bidder) {
        if (bidder != null) {
            registeredBidders.put(bidder.getBidderId(), bidder);
        }
    }

    /**
     * Unregister a bidder from notifications
     * @param bidderId The ID of the bidder to unregister
     */
    public void unregisterBidder(String bidderId) {
        registeredBidders.remove(bidderId);
    }

    @Override
    public void update(Auction auction, String message) {
        System.out.println("=== BIDDER NOTIFICATION ===");
        System.out.println("Auction: " + auction.getAuctionId());
        System.out.println("Item: " + auction.getItem().getName());
        System.out.println("Message: " + message);
        System.out.println("Current Highest Bid: $" + String.format("%.2f", auction.getCurrentHighestBid()));

        // Notify all registered bidders
        for (Bidder bidder : registeredBidders.values()) {
            notifyBidder(bidder, auction, message);
        }
        System.out.println("==========================");
    }

    /**
     * Send notification to a specific bidder
     * @param bidder The bidder to notify
     * @param auction The auction
     * @param message The notification message
     */
    private void notifyBidder(Bidder bidder, Auction auction, String message) {
        // In a real system, this would send email/SMS notifications
        System.out.println("Notifying bidder " + bidder.getName() +
                          " (" + bidder.getEmail() + ") about auction " + auction.getAuctionId());
    }

    /**
     * Get the number of registered bidders
     * @return Number of registered bidders
     */
    public int getRegisteredBidderCount() {
        return registeredBidders.size();
    }
}