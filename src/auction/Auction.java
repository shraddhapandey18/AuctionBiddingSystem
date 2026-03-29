public class Auction {
    private int auctionId;
    private String item;
    private double startingBid;
    
    public Auction(int auctionId, String item, double startingBid) {
        this.auctionId = auctionId;
        this.item = item;
        this.startingBid = startingBid;
    }
    
    // Getters and Setters
    public int getAuctionId() {
        return auctionId;
    }
    
    public String getItem() {
        return item;
    }
    
    public double getStartingBid() {
        return startingBid;
    }
}