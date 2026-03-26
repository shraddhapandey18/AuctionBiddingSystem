package observer;

import auction.Auction;
import model.Bid;

public interface AuctionObserver {
    void update(String event, Auction auction, Bid bid);
}
