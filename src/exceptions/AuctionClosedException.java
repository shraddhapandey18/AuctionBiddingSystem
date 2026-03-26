package exceptions;

public class AuctionClosedException extends RuntimeException {
    private int auctionId;

    public AuctionClosedException(int auctionId) {
        super();
        this.auctionId = auctionId;
    }

    @Override
    public String getMessage() {
        return "Auction #" + auctionId + " is closed. No further bids accepted.";
    }
}
