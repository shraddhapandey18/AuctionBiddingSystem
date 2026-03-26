package exceptions;

/**
 * Exception thrown when attempting to close a reserve auction
 * before the reserve price has been met.
 * This is an unchecked exception (extends RuntimeException).
 */
public class ReservePriceNotMetException extends RuntimeException {

    /**
     * Constructor with message
     * @param message Description of why the reserve price was not met
     */
    public ReservePriceNotMetException(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause
     * @param message Description of the error
     * @param cause The underlying cause
     */
    public ReservePriceNotMetException(String message, Throwable cause) {
        super(message, cause);
    }
}