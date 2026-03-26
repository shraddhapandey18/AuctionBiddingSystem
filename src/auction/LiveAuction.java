package auction;

import model.Bid;
import exceptions.InvalidBidException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Live auction that runs for a fixed duration.
 * Implements automatic closing after time expires.
 */
public class LiveAuction extends Auction implements Runnable {
    private ScheduledExecutorService scheduler;

    /**
     * Constructor for LiveAuction
     * @param auctionId Unique auction identifier
     * @param item Item being auctioned
     * @param durationMinutes Auction duration in minutes
     */
    public LiveAuction(String auctionId, model.Item item, int durationMinutes) {
        super(auctionId, item, durationMinutes);
        this.scheduler = Executors.newScheduledThreadPool(1);

        // Schedule automatic closing
        scheduler.schedule(this::closeAuction, durationMinutes, TimeUnit.MINUTES);
    }

    @Override
    protected void validateBidAmount(Bid bid) throws InvalidBidException {
        double currentHighest = getCurrentHighestBid();
        if (bid.getAmount() <= currentHighest) {
            throw new InvalidBidException(
                String.format("Bid amount %.2f must be higher than current highest bid %.2f",
                            bid.getAmount(), currentHighest));
        }
    }

    @Override
    protected void checkAuctionEndCondition(Bid latestBid) {
        // Live auctions don't end early - they run for full duration
        // Could be extended to implement "extended bidding" if bid comes in last minutes
    }

    /**
     * Runnable implementation for automatic auction closing
     */
    @Override
    public void run() {
        closeAuction();
        System.out.println("Live auction " + getAuctionId() + " has automatically closed.");
    }

    /**
     * Close the auction and shutdown the scheduler
     */
    @Override
    public void closeAuction() {
        super.closeAuction();
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
            try {
                if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                    scheduler.shutdownNow();
                }
            } catch (InterruptedException e) {
                scheduler.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public String toString() {
        return String.format("LiveAuction{%s, endTime='%s'}",
                           super.toString(), getEndTime());
    }
}