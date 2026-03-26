package exceptions;

public class InvalidBidException extends Exception {
    private double attemptedAmount;

    public InvalidBidException(String reason, double attemptedAmount) {
        super(reason);
        this.attemptedAmount = attemptedAmount;
    }

    @Override
    public String getMessage() {
        return "Invalid bid of $" + String.format("%.2f", attemptedAmount) + ": " + super.getMessage();
    }
}
