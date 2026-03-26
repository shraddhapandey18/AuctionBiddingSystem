package model;

import auction.Auction;
import exceptions.InvalidBidException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bidder {
    private int bidderId;
    private String name;
    private String email;
    private List<Bid> bidHistory;

    public Bidder(int bidderId, String name, String email) {
        this.bidderId = bidderId;
        this.name = name;
        this.email = email;
        this.bidHistory = new ArrayList<>();
    }

    public void placeBid(Auction auction, double amount) throws InvalidBidException {
        try {
            Bid bid = auction.submitBid(this, amount);
            addToBidHistory(bid);
        } catch (InvalidBidException e) {
            throw e;
        }
    }

    public void addToBidHistory(Bid bid) {
        bidHistory.add(bid);
    }

    public List<Bid> getBidHistory() {
        return Collections.unmodifiableList(bidHistory);
    }

    public void displayBidHistory() {
        if (bidHistory.isEmpty()) {
            System.out.println("No bids placed by " + name);
            return;
        }
        System.out.println("\n=== BID HISTORY FOR " + name.toUpperCase() + " ===");
        for (Bid bid : bidHistory) {
            System.out.println(bid);
        }
    }

    public int getBidderId() {
        return bidderId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
