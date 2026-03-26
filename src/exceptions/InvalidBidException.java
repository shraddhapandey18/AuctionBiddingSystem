package exceptions;

/**
 * Exception thrown when a bid is invalid.
 * This is a checked exception that must be handled.
 */
public class InvalidBidException extends Exception {

    /**
     * Constructor with message
     * @param message Description of why the bid is invalid
     */
    public InvalidBidException(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause
     * @param message Description of the error
     * @param cause The underlying cause
     */
    public InvalidBidException(String message, Throwable cause) {
        super(message, cause);
    }
}