package observer;

/**
 * Observer interface for auction events.
 * Classes implementing this interface can be notified of auction state changes.
 */
public interface AuctionObserver {

    /**
     * Called when an auction event occurs
     * @param auction The auction that triggered the event
     * @param message Description of the event
     */
    void update(auction.Auction auction, String message);
}