package observer;

import auction.Auction;
import model.Bid;
import model.Bidder;

public class BidderNotifier implements AuctionObserver {
    private Bidder bidder;

    public BidderNotifier(Bidder bidder) {
        this.bidder = bidder;
    }

    @Override
    public void update(String event, Auction auction, Bid bid) {
        if ("OUTBID".equals(event) && bid != null && bid.getBidderId() != bidder.getBidderId()) {
            System.out.println("⚠ [OUTBID ALERT] " + bidder.getName() + ", you have been outbid in Auction #" +
                    auction.getAuctionId() + " by " + bid.getBidderName() + " with $" +
                    String.format("%.2f", bid.getAmount()));
        } else if ("AUCTION_ENDED".equals(event)) {
            Bid winner = auction.getWinner();
            if (winner != null && winner.getBidderId() == bidder.getBidderId()) {
                System.out.println("🎉 [WIN NOTIFICATION] Congratulations " + bidder.getName() +
                        "! You won Auction #" + auction.getAuctionId() + " with a final bid of $" +
                        String.format("%.2f", winner.getAmount()));
            } else {
                System.out.println("📋 [AUCTION ENDED] " + bidder.getName() + ", Auction #" +
                        auction.getAuctionId() + " has ended. You did not win this time.");
            }
        }
    }
}
