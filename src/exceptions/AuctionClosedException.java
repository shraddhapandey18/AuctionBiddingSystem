package exceptions;

/**
 * Exception thrown when attempting to bid on a closed auction.
 * This is an unchecked exception (extends RuntimeException).
 */
public class AuctionClosedException extends RuntimeException {

    /**
     * Constructor with message
     * @param message Description of the error
     */
    public AuctionClosedException(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause
     * @param message Description of the error
     * @param cause The underlying cause
     */
    public AuctionClosedException(String message, Throwable cause) {
        super(message, cause);
    }
}