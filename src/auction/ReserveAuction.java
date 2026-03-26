package auction;

import exceptions.InvalidBidException;
import exceptions.ReservePriceNotMetException;
import model.Bid;
import model.Item;

public class ReserveAuction extends Auction {
    private double reservePrice;

    public ReserveAuction(int auctionId, Item item, double reservePrice) {
        super(auctionId, item);
        this.reservePrice = reservePrice;
    }

    @Override
    public void startAuction() {
        auctionOpen = true;
        setCurrentPrice(getItem().getStartingPrice());
        System.out.println("\n🔔 RESERVE AUCTION #" + getAuctionId() + " STARTED!");
        System.out.println("Item: " + getItem().getName());
        System.out.println("Starting Price: $" + String.format("%.2f", getItem().getStartingPrice()));
        System.out.println("(Reserve price in effect - will be revealed only if met)");
    }

    @Override
    public void closeAuction() {
        auctionOpen = false;
        Bid winner = getWinner();

        if (winner != null && winner.getAmount() >= reservePrice) {
            System.out.println("\n✅ RESERVE MET!");
            System.out.println("Winner: " + winner.getBidderName());
            System.out.println("Final Price: $" + String.format("%.2f", winner.getAmount()));
            notifyObservers("AUCTION_ENDED");
        } else {
            System.out.println("\n❌ RESERVE NOT MET!");
            System.out.println("Reserve Price: $" + String.format("%.2f", reservePrice));
            if (winner != null) {
                System.out.println("Highest Bid: $" + String.format("%.2f", winner.getAmount()));
            } else {
                System.out.println("No bids received.");
            }
            notifyObservers("RESERVE_NOT_MET");
            throw new ReservePriceNotMetException(reservePrice, winner != null ? winner.getAmount() : 0);
        }
    }

    @Override
    public boolean isValidBid(double amount) {
        if (getCurrentPrice() == getItem().getStartingPrice()) {
            return amount >= getItem().getStartingPrice();
        }
        double minimumIncrement = getCurrentPrice() * 0.05;
        return amount > getCurrentPrice() && (amount - getCurrentPrice()) >= minimumIncrement;
    }

    @Override
    public String getAuctionType() {
        return "Reserve Price Auction";
    }

    public void tryClose() {
        try {
            closeAuction();
        } catch (ReservePriceNotMetException e) {
            System.out.println("Item remains unsold: " + e.getMessage());
        }
    }

    public double getReservePrice() {
        return reservePrice;
    }
}
