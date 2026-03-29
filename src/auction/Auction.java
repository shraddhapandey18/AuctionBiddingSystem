package auction;

import java.time.LocalDateTime;
import java.util.*;

import model.*;
import observer.*;
import exceptions.*;

public abstract class Auction {
    protected String auctionId;
    protected Item item;
    protected double startingBid;
    protected boolean active;
    protected List<Bid> bids = new ArrayList<>();
    protected List<AuctionObserver> observers = new ArrayList<>();
    protected LocalDateTime startTime;
    protected LocalDateTime endTime;
    
    public Auction(String auctionId, Item item, double startingBid) {
        this.auctionId = auctionId;
        this.item = item;
        this.startingBid = startingBid > 0 ? startingBid : item.getStartingPrice();
        this.active = true;
        this.startTime = LocalDateTime.now();
    }
    
    // Getters and Setters
    public String getAuctionId() {
        return auctionId;
    }
    
    public Item getItem() {
        return item;
    }
    
    public double getStartingBid() {
        return startingBid;
    }

    public boolean isActive() {
        return active;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public double getCurrentHighestBid() {
        return bids.stream().mapToDouble(Bid::getAmount).max().orElse(startingBid);
    }

    public Bid getWinningBid() {
        return bids.stream().max((b1, b2) -> Double.compare(b1.getAmount(), b2.getAmount())).orElse(null);
    }

    public void addObserver(AuctionObserver observer) {
        observers.add(observer);
    }

    public void submitBid(Bid bid) throws InvalidBidException, AuctionClosedException {
        if (!active) {
            throw new AuctionClosedException("Auction is closed");
        }
        if (bid.getAmount() <= getCurrentHighestBid()) {
            throw new InvalidBidException("Bid amount must be higher than current highest bid");
        }
        bids.add(bid);
        notifyObservers("New bid on " + item.getName() + ": $" + bid.getAmount() + " by " + bid.getBidderId());
    }

    protected void notifyObservers(String message) {
        for (AuctionObserver observer : observers) {
            observer.update(message);
        }
    }

    public List<Bid> getBids() {
        return new ArrayList<>(bids);
    }

    protected void closeAuction() {
        active = false;
        endTime = LocalDateTime.now();
        notifyObservers("Auction closed: " + item.getName());
    }

    @Override
    public String toString() {
        return String.format("Auction{id='%s', item='%s', startingBid=%.2f, active=%s}",
                           auctionId, item.getName(), startingBid, active);
    }
}