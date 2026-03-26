package auction;

import model.Item;
import model.Bid;
import observer.AuctionObserver;
import exceptions.InvalidBidException;
import exceptions.AuctionClosedException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

/**
 * Abstract base class for all auction types.
 * Implements the Template Method pattern for bidding process.
 */
public abstract class Auction {
    protected String auctionId;
    protected Item item;
    protected LocalDateTime startTime;
    protected LocalDateTime endTime;
    protected boolean isActive;
    protected List<Bid> bids;
    protected List<AuctionObserver> observers;

    /**
     * Constructor for Auction
     * @param auctionId Unique auction identifier
     * @param item Item being auctioned
     * @param durationMinutes Auction duration in minutes
     */
    public Auction(String auctionId, Item item, int durationMinutes) {
        this.auctionId = auctionId;
        this.item = item;
        this.startTime = LocalDateTime.now();
        this.endTime = startTime.plusMinutes(durationMinutes);
        this.isActive = true;
        this.bids = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    // Getters
    public String getAuctionId() { return auctionId; }
    public Item getItem() { return item; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public boolean isActive() { return isActive && LocalDateTime.now().isBefore(endTime); }
    public List<Bid> getBids() { return new ArrayList<>(bids); }

    /**
     * Get the current highest bid
     * @return Highest bid amount, or starting price if no bids
     */
    public double getCurrentHighestBid() {
        if (bids.isEmpty()) {
            return item.getStartingPrice();
        }
        return bids.stream()
                  .mapToDouble(Bid::getAmount)
                  .max()
                  .orElse(item.getStartingPrice());
    }

    /**
     * Get the winning bid (highest bid)
     * @return Winning bid, or null if no bids
     */
    public Bid getWinningBid() {
        if (bids.isEmpty()) {
            return null;
        }
        return bids.stream()
                  .max((b1, b2) -> Double.compare(b1.getAmount(), b2.getAmount()))
                  .orElse(null);
    }

    /**
     * Template method for submitting a bid.
     * Validates the bid and delegates to concrete implementation.
     * @param bid The bid to submit
     * @throws InvalidBidException if bid is invalid
     * @throws AuctionClosedException if auction is closed
     */
    public final void submitBid(Bid bid) throws InvalidBidException, AuctionClosedException {
        if (!isActive()) {
            throw new AuctionClosedException("Auction " + auctionId + " is closed");
        }
        if (bid == null) {
            throw new InvalidBidException("Bid cannot be null");
        }

        // Validate bid amount
        validateBidAmount(bid);

        // Add bid to list
        bids.add(bid);

        // Notify observers
        notifyObservers("New bid submitted: " + bid);

        // Check if auction should close
        checkAuctionEndCondition(bid);
    }

    /**
     * Abstract method for validating bid amount.
     * Implemented by concrete auction types.
     * @param bid The bid to validate
     * @throws InvalidBidException if bid amount is invalid
     */
    protected abstract void validateBidAmount(Bid bid) throws InvalidBidException;

    /**
     * Abstract method to check if auction should end.
     * Implemented by concrete auction types.
     * @param latestBid The most recent bid
     */
    protected abstract void checkAuctionEndCondition(Bid latestBid);

    /**
     * Close the auction manually
     */
    public void closeAuction() {
        isActive = false;
        notifyObservers("Auction " + auctionId + " has been closed");
    }

    /**
     * Add an observer to be notified of auction events
     * @param observer The observer to add
     */
    public void addObserver(AuctionObserver observer) {
        if (observer != null) {
            observers.add(observer);
        }
    }

    /**
     * Remove an observer
     * @param observer The observer to remove
     */
    public void removeObserver(AuctionObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notify all observers of an event
     * @param message The event message
     */
    protected void notifyObservers(String message) {
        for (AuctionObserver observer : observers) {
            observer.update(this, message);
        }
    }

    @Override
    public String toString() {
        return String.format("Auction{id='%s', item='%s', active=%s, highestBid=%.2f, bids=%d}",
                           auctionId, item.getName(), isActive(), getCurrentHighestBid(), bids.size());
    }
}