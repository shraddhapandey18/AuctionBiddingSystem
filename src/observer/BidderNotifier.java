public class BidderNotifier implements AuctionObserver {
    @Override
    public void update(String message) {
        // Notify the bidder with the new message
        System.out.println("Notifying bidder: " + message);
    }
}