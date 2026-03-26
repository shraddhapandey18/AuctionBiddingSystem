package exceptions;

public class ReservePriceNotMetException extends RuntimeException {
    private double reservePrice;
    private double highestBid;

    public ReservePriceNotMetException(double reservePrice, double highestBid) {
        super();
        this.reservePrice = reservePrice;
        this.highestBid = highestBid;
    }

    @Override
    public String getMessage() {
        return "Reserve price not met. Required: $" + String.format("%.2f", reservePrice) +
               ", Highest bid: $" + String.format("%.2f", highestBid);
    }
}
