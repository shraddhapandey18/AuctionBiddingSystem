package observer;

import java.util.*;
import model.*;

public class BidderNotifier implements AuctionObserver {
    private List<Bidder> bidders = new ArrayList<>();

    public void registerBidder(Bidder bidder) {
        bidders.add(bidder);
    }

    @Override
    public void update(String message) {
        // Notify all registered bidders
        for (Bidder bidder : bidders) {
            System.out.println("Notifying " + bidder.getName() + ": " + message);
        }
    }
}