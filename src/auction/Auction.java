package auction;

import exceptions.InvalidBidException;
import model.Bid;
import model.Bidder;
import model.Item;
import observer.AuctionObserver;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class Auction {
    private int auctionId;
    private Item item;
    private double currentPrice;
    private double startingPrice;
    private Bid highestBid;
    private List<Bid> allBids;
    private List<AuctionObserver> observers;
    protected boolean auctionOpen;

    public Auction(int auctionId, Item item) {
        this.auctionId = auctionId;
        this.item = item;
        this.startingPrice = item.getStartingPrice();
        this.currentPrice = startingPrice;
        this.highestBid = null;
        this.allBids = new ArrayList<>();
        this.observers = new ArrayList<>();
        this.auctionOpen = false;
    }

    public abstract void startAuction();

    public abstract void closeAuction();

    public abstract boolean isValidBid(double amount);

    public abstract String getAuctionType();

    public Bid submitBid(Bidder bidder, double amount) throws InvalidBidException {
        if (!isValidBid(amount)) {
            throw new InvalidBidException("Bid must exceed current price of $" +
                    String.format("%.2f", currentPrice), amount);
        }

        Bid newBid = new Bid(bidder.getBidderId(), bidder.getName(), amount, LocalDateTime.now());

        if (highestBid != null) {
            notifyObservers("OUTBID", newBid);
        } else {
            notifyObservers("BID_PLACED", newBid);
        }

        highestBid = newBid;
        currentPrice = amount;
        allBids.add(newBid);

        return newBid;
    }

    public void registerObserver(AuctionObserver o) {
        observers.add(o);
    }

    public void removeObserver(AuctionObserver o) {
        observers.remove(o);
    }

    protected void notifyObservers(String event, Bid bid) {
        for (AuctionObserver observer : observers) {
            observer.update(event, this, bid);
        }
    }

    protected void notifyObservers(String event) {
        for (AuctionObserver observer : observers) {
            observer.update(event, this, null);
        }
    }

    public Bid getWinner() {
        return highestBid;
    }

    public void displayAuctionStatus() {
        System.out.println("\n=== AUCTION #" + auctionId + " STATUS ===");
        System.out.println("Type: " + getAuctionType());
        System.out.println("Status: " + (auctionOpen ? "OPEN" : "CLOSED"));
        item.displayDetails();
        System.out.println("Current Price: $" + String.format("%.2f", currentPrice));
        if (highestBid != null) {
            System.out.println("Highest Bidder: " + highestBid.getBidderName() + " ($" +
                    String.format("%.2f", highestBid.getAmount()) + ")");
        } else {
            System.out.println("Highest Bidder: None");
        }
        System.out.println("Total Bids: " + allBids.size());
    }

    public List<Bid> getAllBids() {
        return new ArrayList<>(allBids);
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public Item getItem() {
        return item;
    }

    public boolean isOpen() {
        return auctionOpen;
    }

    public int getAuctionId() {
        return auctionId;
    }

    protected void setCurrentPrice(double price) {
        this.currentPrice = price;
    }

    protected List<Bid> getBidsInternal() {
        return allBids;
    }
}
