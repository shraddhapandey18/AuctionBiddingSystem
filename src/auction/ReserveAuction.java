package auction;

import model.Bid;
import exceptions.InvalidBidException;
import exceptions.ReservePriceNotMetException;

/**
 * Reserve auction that requires bids to meet or exceed a reserve price.
 * Auction ends when reserve price is met or time expires.
 */
public class ReserveAuction extends Auction {
    private double reservePrice;
    private boolean reserveMet;

    /**
     * Constructor for ReserveAuction
     * @param auctionId Unique auction identifier
     * @param item Item being auctioned
     * @param durationMinutes Auction duration in minutes
     * @param reservePrice Minimum price that must be met
     */
    public ReserveAuction(String auctionId, model.Item item, int durationMinutes, double reservePrice) {
        super(auctionId, item, item.getStartingPrice());
        if (reservePrice <= item.getStartingPrice()) {
            throw new IllegalArgumentException("Reserve price must be higher than starting price");
        }
        this.reservePrice = reservePrice;
        this.reserveMet = false;
    }

    // Getters
    public double getReservePrice() { return reservePrice; }
    public boolean isReserveMet() { return reserveMet; }

    protected void validateBidAmount(Bid bid) throws InvalidBidException {
        double currentHighest = getCurrentHighestBid();
        if (bid.getAmount() <= currentHighest) {
            throw new InvalidBidException(
                String.format("Bid amount %.2f must be higher than current highest bid %.2f",
                            bid.getAmount(), currentHighest));
        }
    }

    protected void checkAuctionEndCondition(Bid latestBid) {
        if (latestBid.getAmount() >= reservePrice && !reserveMet) {
            reserveMet = true;
            notifyObservers("Reserve price met! Auction can now close.");
            // Reserve auctions can close immediately when reserve is met
            // Comment out the next line if you want to keep auction open until time expires
            // closeAuction();
        }
    }

    /**
     * Close the auction with reserve price validation
     * @throws ReservePriceNotMetException if reserve price not met
     */
    @Override
    public void closeAuction() throws ReservePriceNotMetException {
        if (!reserveMet && getCurrentHighestBid() < reservePrice) {
            throw new ReservePriceNotMetException(
                String.format("Cannot close auction: Reserve price %.2f not met. Highest bid: %.2f",
                            reservePrice, getCurrentHighestBid()));
        }
        super.closeAuction();
    }

    @Override
    public String toString() {
        return String.format("ReserveAuction{%s, reservePrice=%.2f, reserveMet=%s}",
                           super.toString(), reservePrice, reserveMet);
    }
}